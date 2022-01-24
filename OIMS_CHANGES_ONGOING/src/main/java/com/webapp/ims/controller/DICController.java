package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.EXIST_PROJ_LIST;
import static com.webapp.ims.exception.GlobalConstants.LARGE;
import static com.webapp.ims.exception.GlobalConstants.MEDIUM;
import static com.webapp.ims.exception.GlobalConstants.MEGA;
import static com.webapp.ims.exception.GlobalConstants.MEGAPLUS;
import static com.webapp.ims.exception.GlobalConstants.SMALL;
import static com.webapp.ims.exception.GlobalConstants.SUPERMEGA;

import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.login.model.Login;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicantDocument;
import com.webapp.ims.model.AvailCustomisedDetails;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.ConcernDepartment;
import com.webapp.ims.model.Department;
import com.webapp.ims.model.DeptBusinessEntityDetails;
import com.webapp.ims.model.DeptIncentiveDetails;
import com.webapp.ims.model.DeptProposedEmploymentDetails;
import com.webapp.ims.model.DeptProprietorDetails;
import com.webapp.ims.model.Dept_PhaseWiseInvestmentDetails;
import com.webapp.ims.model.EvaluateCapitalInvestment;
import com.webapp.ims.model.EvaluateExistNewProjDetails;
import com.webapp.ims.model.EvaluateInvestmentDetails;
import com.webapp.ims.model.EvaluateProjectDetails;
import com.webapp.ims.model.EvaluationAuditTrail;
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.InvBreakupCost;
import com.webapp.ims.model.InvMeansOfFinance;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.MeetingScheduler;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.model.StateDetails;
import com.webapp.ims.repository.DeptBusinessEntityDetailsRepository;
import com.webapp.ims.repository.DeptIncentiveDetailsRepository;
import com.webapp.ims.repository.DeptPhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.repository.DeptProposedEmploymentDetailsRepository;
import com.webapp.ims.repository.DeptProprietorDetailsRepository;
import com.webapp.ims.repository.EvaluateAuditTrialRepository;
import com.webapp.ims.repository.EvaluateExistNewProjDetailsRepository;
import com.webapp.ims.repository.IncentiveDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.ProjectRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BreakupCostService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.EvaluateInvestMentDetailsService;
import com.webapp.ims.service.EvaluateProjectDetailsService;
import com.webapp.ims.service.EvaluationAuditTrailService;
import com.webapp.ims.service.ExistingProjectDetailsService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.MeansOfFinanceService;
import com.webapp.ims.service.MeetingSchedulerService;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;
import com.webapp.ims.service.PrepareAgendaNoteService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;
import com.webapp.ims.service.StateDetailsService;
import com.webapp.ims.service.impl.AdditionalInterest;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;

@Controller
public class DICController {
	private final Logger logger = LoggerFactory.getLogger(DICController.class);
	private ApplicantDetailsService appDtlService;
	private ApplicantDocumentService appDocService;
	private InvestmentDetailsService invDtlService;
	private PhaseWiseInvestmentDetailsService pwInvDtlService;
	private BreakupCostService brkupService;
	private MeansOfFinanceService mofService;
	private ProposedEmploymentDetailsService emplDtlService;
	private BusinessEntityDetailsService businessEntityDetailsService;
	private BusinessEntityDetailsService businessService;
	private ProprietorDetailsService proprietorService;
	private IncentiveDetailsService incentiveDetailsService;
	private AvailCustmisedDetailsService availCustmisedDetailsService;
	private ProjectService projectService;
	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();
	private String applicantId = null;
	List<PrepareAgendaNotes> panList = new ArrayList<>();
	@Autowired
	DeptPhaseWiseInvestmentDetailsRepository deptPwInvRepository;
	@Autowired
	private DeptProposedEmploymentDetailsRepository deptPrpsEmplRepository;
	
	@Autowired
	private DeptIncentiveDetailsRepository deptIncRepository;
	@Autowired
	ProprietorDetailsService propriterService;
	@Autowired
	private DeptBusinessEntityDetailsRepository deptBusEntRepository;
	@Autowired
	public EvaluateExistNewProjDetailsRepository evaluateExistNewProjDetailsRepository;
	@Autowired
	public EvaluateInvestMentDetailsService evaluateInvestMentDetailsService;
	@Autowired
	public ApplicationDetailsViewController applicantViewController;
	@Autowired
	private MeetingSchedulerService meetingSchedulerService;
	
	public List<ExistingProjectDetails> existProjList = new ArrayList<>();
	@Autowired
	private ExistingProjectDetailsService existProjectService;
	@Autowired
	private DeptProprietorDetailsRepository  deptPropRepository;
	@Autowired
	AdditionalInterest additionalInterest;

	@Autowired
	InvestmentDetailsRepository investRepository;

	@Autowired
	PrepareAgendaNoteService prepareAgendaNoteService;

	@Autowired
	private PrepareAgendaNotesService prepareAgendaNotesService;

	@Autowired
	private ApplicantDetailsService applicantDetailsService;

	@Autowired
	private TblUsersService loginService;
	@Autowired
	private StateDetailsService stateDetailsService;

	@Autowired
	private ApplicantDocumentService fileStorageService;

	@Autowired
	ProjectRepository projectRepository; 

	@Autowired
	InvestmentDetailsRepository investmentDetailsRepository;

	@Autowired
	IncentiveDetailsRepository incentiveDetailsRepository;

	@Autowired
	private InvestmentDetailsService investDs;

	@Autowired
	private PhaseWiseInvestmentDetailsService pwInvestDs;

	@Autowired
	private ProposedEmploymentDetailsService proposedEmploymentDetailsService;

	@Autowired
	InvestmentDetailsService investmentDetailsService;

	@Autowired
	private EvaluateProjectDetailsService evaluateProjectDetailsService;

	@Autowired
	public EvaluationAuditTrailService evaluationAuditTrailService;

	@Autowired
	public EvaluateAuditTrialRepository evaluateAuditTrialRepository;
	
	@InitBinder("EvaluateProjectDetails")
	public void pwDateCustomize(WebDataBinder binder) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat("dd/MM/yyyy");
		SimpleDateFormat dateFormatter1 = new SimpleDateFormat("yyyy-MM-dd");
		binder.registerCustomEditor(Date.class, "cAStatutoryDate", new CustomDateEditor(dateFormatter, true));
		binder.registerCustomEditor(Date.class, "invCommenceDate", new CustomDateEditor(dateFormatter1, true));
		binder.registerCustomEditor(Date.class, "propCommProdDate", new CustomDateEditor(dateFormatter1, true));
		binder.registerCustomEditor(Date.class, "CreatedDate", new CustomDateEditor(dateFormatter1, true));
	}



	private AtomicInteger atomicInteger = new AtomicInteger();

	private List<AvailCustomisedDetails> availCustomisedDetailsList = new ArrayList<AvailCustomisedDetails>();
	private List<EvaluateCapitalInvestment> evalCapitalInvList = new LinkedList<>();
	@Autowired
	public DICController(ApplicantDetailsService appDtlService, ApplicantDocumentService appDocService,
			InvestmentDetailsService invDtlService, PhaseWiseInvestmentDetailsService pwInvDtlService,
			BreakupCostService brkupService, MeansOfFinanceService mofService,
			ProposedEmploymentDetailsService emplDtlService, BusinessEntityDetailsService businessEntityDetailsService,
			BusinessEntityDetailsService businessService, ProprietorDetailsService proprietorService,
			IncentiveDetailsService incentiveDetailsService, AvailCustmisedDetailsService availCustmisedDetailsService,
			ProjectService projectService) {
		super();
		this.appDtlService = appDtlService;
		this.appDocService = appDocService;
		this.invDtlService = invDtlService;
		this.pwInvDtlService = pwInvDtlService;
		this.brkupService = brkupService;
		this.mofService = mofService;
		this.emplDtlService = emplDtlService;
		this.businessEntityDetailsService = businessEntityDetailsService;
		this.businessService = businessService;
		this.proprietorService = proprietorService;
		this.incentiveDetailsService = incentiveDetailsService;
		this.availCustmisedDetailsService = availCustmisedDetailsService;
		this.projectService = projectService;
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

	String businId = "";
	String projId = "";
	String investId = "";
	String propId = "";
	String isfId = "";

	@GetMapping(value = "/viewDICApplicationDetails")
	public ModelAndView viewDICApplicationDetails(@RequestParam(value = "applicantId", required = false) String applId,
			HttpSession session, Model model) {
		applicantId = applId;
		session.setAttribute("appId", applicantId);

		String appStr = applId.substring(0, 14);
		businId = appStr + "B1";
		projId = appStr + "P1";
		investId = appStr + "I1";
		propId = appStr + "PE1";
		isfId = appStr + "INC1";

		model.addAttribute("concernDepartment", new ConcernDepartment());
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(applId);
		ApplicantDocument applicantDocument = fileStorageService.getApplicantDocumentByDocAppId(applId);
		if (applicantDetail != null) {
			model.addAttribute("appFirstName", applicantDetail.getAppFirstName());
			model.addAttribute("appMiddleName", applicantDetail.getAppMiddleName());
			model.addAttribute("appLastName", applicantDetail.getAppLastName());
			model.addAttribute("appEmailId", applicantDetail.getAppEmailId());
			model.addAttribute("appMobileNo", applicantDetail.getAppMobileNo());
			model.addAttribute("appPhoneNo", applicantDetail.getAppPhoneNo());
			model.addAttribute("appDesignation", applicantDetail.getAppDesignation());
			model.addAttribute("gender", applicantDetail.getGender());
			model.addAttribute("appAadharNo", applicantDetail.getAppAadharNo());
			model.addAttribute("appPancardNo", applicantDetail.getAppPancardNo());
			model.addAttribute("appAddressLine1", applicantDetail.getAppAddressLine1());
			model.addAttribute("appAddressLine2", applicantDetail.getAppAddressLine2());
			model.addAttribute("appCountry", applicantDetail.getAppCountry());
			model.addAttribute("appState", applicantDetail.getAppState());
			model.addAttribute("appDistrict", applicantDetail.getAppDistrict());
			model.addAttribute("appPinCode", applicantDetail.getAppPinCode());

			if (applicantDocument != null) {

				byte[] encodeBase64 = Base64.encodeBase64(applicantDocument.getData());
				String base64Encoded = null;
				try {
					if (applicantDocument.getFileName() != null && applicantDocument.getFileName() != ""
							&& !applicantDocument.getFileName().isEmpty()) {
						base64Encoded = new String(encodeBase64, "UTF-8");
					}

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				applicantDetail.setBase64imageFile(base64Encoded);
				applicantDetail.setFileName(applicantDocument.getFileName());
			}
			model.addAttribute("applicantDetail", applicantDetail);
		}

		/* Business Entity Details */

		BusinessEntityDetails businessEntityDetails = businessService.getBusinDetById(businId);
		if (businessEntityDetails != null) {

			model.addAttribute("businessEntityName", businessEntityDetails.getBusinessEntityName());
			model.addAttribute("businessEntityType", businessEntityDetails.getBusinessEntityType());
			model.addAttribute("authorisedSignatoryName", businessEntityDetails.getAuthorisedSignatoryName());
			model.addAttribute("businessDesignation", businessEntityDetails.getBusinessDesignation());
			model.addAttribute("emailId", businessEntityDetails.getEmailId());
			model.addAttribute("mobileNumber", businessEntityDetails.getMobileNumber());
			model.addAttribute("phoneNo", businessEntityDetails.getPhoneNo());
			model.addAttribute("fax", businessEntityDetails.getFax());
			model.addAttribute("businessAddress", businessEntityDetails.getBusinessAddress());
			model.addAttribute("businessCountryName", businessEntityDetails.getBusinessCountryName());
			StateDetails stateDetails = stateDetailsService
					.getStateBystateCode(Long.valueOf(businessEntityDetails.getBusinessStateName()));
			model.addAttribute("businessStateName", stateDetails.getStateName());
			model.addAttribute("businessDistrictName", businessEntityDetails.getBusinessDistrictName());
			model.addAttribute("PinCode", businessEntityDetails.getPinCode());
			model.addAttribute("yearEstablishment", businessEntityDetails.getYearEstablishment());
			model.addAttribute("gstin", businessEntityDetails.getGstin());
			model.addAttribute("cin", businessEntityDetails.getCin());
			model.addAttribute("companyPanNo", businessEntityDetails.getCompanyPanNo());
			model.addAttribute("moaDocFname", businessEntityDetails.getMoaDocFname());
			model.addAttribute("regisAttacDocFname", businessEntityDetails.getRegisAttacDocFname());
			model.addAttribute("bodDocFname", businessEntityDetails.getBodDocFname());
			model.addAttribute("indusAffidaDocFname", businessEntityDetails.getIndusAffidaDocFname());
			model.addAttribute("annexureiaformat", businessEntityDetails.getAnnexureiaformat());
			List<ProprietorDetails> proprietorDetailsList = proprietorService.findAllByBusinessId(businId);
			model.addAttribute("ProprietorDetailsList", proprietorDetailsList);

		}

		/* Project Details */

		
		
            ProjectDetails ProjectDetail = projectService.getProjDetById(projId);
            
           
		
		
		
		
		if (ProjectDetail != null) {
			model.addAttribute("contactPersonName", ProjectDetail.getContactPersonName());
			model.addAttribute("mobileNo", ProjectDetail.getMobileNo());
			model.addAttribute("designation", ProjectDetail.getDesignation());
			model.addAttribute("projectDescription", ProjectDetail.getProjectDescription());
			model.addAttribute("webSiteName", ProjectDetail.getWebSiteName());
			model.addAttribute("fullAddress", ProjectDetail.getFullAddress());
			model.addAttribute("registeredAddr", ProjectDetail.getRegisteredAddr());
			model.addAttribute("districtName", ProjectDetail.getDistrictName());
			model.addAttribute("mandalName", ProjectDetail.getMandalName());
			model.addAttribute("resionName", ProjectDetail.getResionName());
			model.addAttribute("pinCode", ProjectDetail.getPinCode());
			model.addAttribute("regiOrLicense", ProjectDetail.getRegiOrLicense());
			model.addAttribute("regiOrLicenseFileName", ProjectDetail.getRegiOrLicenseFileName());

			model.addAttribute("natureOfProject", ProjectDetail.getNatureOfProject());
			model.addAttribute("expansion", ProjectDetail.getExpansion());
			model.addAttribute("diversification", ProjectDetail.getDiversification());
			
			  model.addAttribute("ExistingProducts", ProjectDetail.getExistingProducts());
			  model.addAttribute("existingInstalledCapacity",
			  ProjectDetail.getExistingInstalledCapacity());
			  model.addAttribute("proposedProducts", ProjectDetail.getProposedProducts());
			  model.addAttribute("proposedInstalledCapacity",
			  ProjectDetail.getProposedInstalledCapacity());
			  model.addAttribute("existingGrossBlock",
			  ProjectDetail.getExistingGrossBlock());
			  model.addAttribute("proposedGrossBlock",
			  ProjectDetail.getProposedGrossBlock());
			  model.addAttribute("enclDetProRepFileName",
			 ProjectDetail.getEnclDetProRepFileName());
			  model.addAttribute("caCertificateFileName",
			  ProjectDetail.getCaCertificateFileName());
			 model.addAttribute("charatEngFileName",
			 ProjectDetail.getCharatEngFileName());
			 
		}

		/* Investment Details */

		InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(investId);
		if (invdtlFromDb != null) {
			model.addAttribute("categorytype", invdtlFromDb.getInvIndType());
			model.addAttribute("invFci", invdtlFromDb.getInvFci());
			model.addAttribute("invTotalProjCost", invdtlFromDb.getInvTotalProjCost());
			model.addAttribute("invLandCost", invdtlFromDb.getInvLandCost());
			model.addAttribute("invBuildingCost", invdtlFromDb.getInvBuildingCost());
			model.addAttribute("invPlantAndMachCost", invdtlFromDb.getInvPlantAndMachCost());
			model.addAttribute("invOtherCost", invdtlFromDb.getInvOtherCost());
			model.addAttribute("invCommenceDate", invdtlFromDb.getInvCommenceDate());
			model.addAttribute("propCommProdDate", invdtlFromDb.getPropCommProdDate());
			model.addAttribute("invFileName", invdtlFromDb.getInvFilename());
			List<PhaseWiseInvestmentDetails> pwInvListDromDb = pwInvestDs
					.getPwInvDetailListById(invdtlFromDb.getInvId());
			if (pwInvListDromDb.size() > 0) {
				model.addAttribute("pwApply", "Yes");
			} else {
				model.addAttribute("pwApply", "No");
			}

			model.addAttribute("pwInvList", pwInvListDromDb);
		}

		/* Proposed Employment Details */

		ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService.getPropEmpById(propId);
		if (proposedEmploymentDetail != null) {

			if (proposedEmploymentDetail != null) {
				List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = proposedEmploymentDetail
						.getSkilledUnSkilledEmployemnt();
				totalSkilledAndUnSkilledEmploment(model, skilledUnSkilledEmployemntslist);
				model.addAttribute("proposedEmploymentDetails", skilledUnSkilledEmployemntslist);
			}
		}
		
		


		/* Incentive Specific Details */
		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
		if (IncentiveDetail != null) {
			model.addAttribute("ISF_Claim_Reim", IncentiveDetail.getISF_Claim_Reim());
			model.addAttribute("ISF_Reim_SCST", IncentiveDetail.getISF_Reim_SCST());
			model.addAttribute("ISF_Reim_FW", IncentiveDetail.getISF_Reim_FW());
			model.addAttribute("ISF_Reim_BPLW", IncentiveDetail.getISF_Reim_BPLW());
			model.addAttribute("ISF_Ttl_SGST_Reim", IncentiveDetail.getISF_Ttl_SGST_Reim());

			model.addAttribute("ISF_Stamp_Duty_EX", IncentiveDetail.getISF_Stamp_Duty_EX());
			model.addAttribute("ISF_Additonal_Stamp_Duty_EX", IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
			model.addAttribute("ISF_Amt_Stamp_Duty_Reim", IncentiveDetail.getISF_Amt_Stamp_Duty_Reim());
			model.addAttribute("ISF_Ttl_Stamp_Duty_EX", IncentiveDetail.getISF_Ttl_Stamp_Duty_EX());

			model.addAttribute("ISF_Epf_Reim_UW", IncentiveDetail.getISF_Epf_Reim_UW());
			model.addAttribute("ISF_Add_Epf_Reim_SkUkW", IncentiveDetail.getISF_Add_Epf_Reim_SkUkW());
			model.addAttribute("ISF_Add_Epf_Reim_DIVSCSTF", IncentiveDetail.getISF_Add_Epf_Reim_DIVSCSTF());
			model.addAttribute("ISF_Ttl_EPF_Reim", IncentiveDetail.getISF_Ttl_EPF_Reim());

			model.addAttribute("ISF_Cis", IncentiveDetail.getISF_Cis());
			model.addAttribute("ISF_ACI_Subsidy_Indus", IncentiveDetail.getISF_ACI_Subsidy_Indus());
			model.addAttribute("ISF_Infra_Int_Subsidy", IncentiveDetail.getISF_Infra_Int_Subsidy());
			model.addAttribute("ISF_AII_Subsidy_DIVSCSTF", IncentiveDetail.getISF_AII_Subsidy_DIVSCSTF());
			model.addAttribute("ISF_Loan_Subsidy", IncentiveDetail.getISF_Loan_Subsidy());
			model.addAttribute("ISF_Total_Int_Subsidy", IncentiveDetail.getISF_Total_Int_Subsidy());

			model.addAttribute("ISF_Tax_Credit_Reim", IncentiveDetail.getISF_Tax_Credit_Reim());
			model.addAttribute("ISF_EX_E_Duty", IncentiveDetail.getISF_EX_E_Duty());
			model.addAttribute("ISF_EX_E_Duty_PC", IncentiveDetail.getISF_EX_E_Duty_PC());
			model.addAttribute("ISF_EX_Mandee_Fee", IncentiveDetail.getISF_EX_Mandee_Fee());
			model.addAttribute("ISF_Indus_Payroll_Asst", IncentiveDetail.getISF_Indus_Payroll_Asst());
			model.addAttribute("Total_Other_Incentive", IncentiveDetail.getTotal_Other_Incentive());
			model.addAttribute("isfCustIncDocName", IncentiveDetail.getIsfCustIncDocName());
			model.addAttribute("othAddRequest1", IncentiveDetail.getOthAddRequest1());
			
			try {
				Long isf_Claim_Reim = IncentiveDetail.getISF_Ttl_SGST_Reim() == null ? 0
						: IncentiveDetail.getISF_Ttl_SGST_Reim();
				Long isf_Reim_SCST = IncentiveDetail.getISF_Ttl_Stamp_Duty_EX() == null ? 0
						: IncentiveDetail.getISF_Ttl_Stamp_Duty_EX();
				Long isf_EX_E_Duty_PC = IncentiveDetail.getISF_EX_E_Duty_PC() == null ? 0
						: IncentiveDetail.getISF_EX_E_Duty_PC();
				Long isf_EX_Mandee_Fee = IncentiveDetail.getISF_Ttl_EPF_Reim() == null ? 0
						: IncentiveDetail.getISF_Ttl_EPF_Reim();
				Long isf_Reim_BPLW = IncentiveDetail.getISF_Total_Int_Subsidy() == null ? 0
						: IncentiveDetail.getISF_Total_Int_Subsidy();
				Long isf_Reim_FW = IncentiveDetail.getTotal_Other_Incentive() == null ? 0
						: IncentiveDetail.getTotal_Other_Incentive();
				Long total = isf_Claim_Reim + isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW
						+ isf_Reim_FW;
				model.addAttribute("total", total);
			} catch (Exception e) {
				logger.error("total  exception **** " + e.getMessage());
				e.printStackTrace();
			}

			if (invdtlFromDb.getInvIndType() != null && !invdtlFromDb.getInvIndType().isEmpty()) {
				if (invdtlFromDb.getInvIndType().equalsIgnoreCase("Super Mega")) {
					model.addAttribute("hideCustamList", "");
				} else {
					model.addAttribute("hideCustamList", "hideCustamList");
				}
			}

			List<AvailCustomisedDetails> availCustomisedDetailsList = availCustmisedDetailsService
					.findAllByAvaCustId(IncentiveDetail.getId());
			if (availCustomisedDetailsList.size() > 0) {
				model.addAttribute("ISF_Cstm_Inc_Status", "Yes");
			} else {
				model.addAttribute("ISF_Cstm_Inc_Status", "No");
			}
			model.addAttribute("department", new Department());
			// model.addAttribute("aggregatequantumbenifit", aggregatequantumbenifit);
			model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList);

		}

		return new ModelAndView("view-all-application-details-dic");

	}
	
	

	@GetMapping(value = "/viewApplicationDetailsSMEDIC")
	public ModelAndView viewApplicationDetailsSMEDIC(
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session, Model model) {
		applicantId = applId;
		session.setAttribute("appId", applicantId);

		String appStr = applId.substring(0, 14);
		businId = appStr + "B1";
		projId = appStr + "P1";
		investId = appStr + "I1";
		propId = appStr + "PE1";
		isfId = appStr + "INC1";

		model.addAttribute("concernDepartment", new ConcernDepartment());
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(applId);
		ApplicantDocument applicantDocument = fileStorageService.getApplicantDocumentByDocAppId(applId);
		if (applicantDetail != null) {
			model.addAttribute("appFirstName", applicantDetail.getAppFirstName());
			model.addAttribute("appMiddleName", applicantDetail.getAppMiddleName());
			model.addAttribute("appLastName", applicantDetail.getAppLastName());
			model.addAttribute("appEmailId", applicantDetail.getAppEmailId());
			model.addAttribute("appMobileNo", applicantDetail.getAppMobileNo());
			model.addAttribute("appPhoneNo", applicantDetail.getAppPhoneNo());
			model.addAttribute("appDesignation", applicantDetail.getAppDesignation());
			model.addAttribute("gender", applicantDetail.getGender());
			model.addAttribute("appAadharNo", applicantDetail.getAppAadharNo());
			model.addAttribute("appPancardNo", applicantDetail.getAppPancardNo());
			model.addAttribute("appAddressLine1", applicantDetail.getAppAddressLine1());
			model.addAttribute("appAddressLine2", applicantDetail.getAppAddressLine2());
			model.addAttribute("appCountry", applicantDetail.getAppCountry());
			model.addAttribute("appState", applicantDetail.getAppState());
			model.addAttribute("appDistrict", applicantDetail.getAppDistrict());
			model.addAttribute("appPinCode", applicantDetail.getAppPinCode());

			if (applicantDocument != null) {

				byte[] encodeBase64 = Base64.encodeBase64(applicantDocument.getData());
				String base64Encoded = null;
				try {
					if (applicantDocument.getFileName() != null && applicantDocument.getFileName() != ""
							&& !applicantDocument.getFileName().isEmpty()) {
						base64Encoded = new String(encodeBase64, "UTF-8");
					}

				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				applicantDetail.setBase64imageFile(base64Encoded);
				applicantDetail.setFileName(applicantDocument.getFileName());
			}
			model.addAttribute("applicantDetail", applicantDetail);
		}

		/* Business Entity Details */

		BusinessEntityDetails businessEntityDetails = businessService.getBusinDetById(businId);
		if (businessEntityDetails != null) {

			model.addAttribute("businessEntityName", businessEntityDetails.getBusinessEntityName());
			model.addAttribute("businessEntityType", businessEntityDetails.getBusinessEntityType());
			model.addAttribute("authorisedSignatoryName", businessEntityDetails.getAuthorisedSignatoryName());
			model.addAttribute("businessDesignation", businessEntityDetails.getBusinessDesignation());
			model.addAttribute("emailId", businessEntityDetails.getEmailId());
			model.addAttribute("mobileNumber", businessEntityDetails.getMobileNumber());
			model.addAttribute("phoneNo", businessEntityDetails.getPhoneNo());
			model.addAttribute("fax", businessEntityDetails.getFax());
			model.addAttribute("businessAddress", businessEntityDetails.getBusinessAddress());
			model.addAttribute("businessCountryName", businessEntityDetails.getBusinessCountryName());
			StateDetails stateDetails = stateDetailsService
					.getStateBystateCode(Long.valueOf(businessEntityDetails.getBusinessStateName()));
			model.addAttribute("businessStateName", stateDetails.getStateName());
			model.addAttribute("businessDistrictName", businessEntityDetails.getBusinessDistrictName());
			model.addAttribute("PinCode", businessEntityDetails.getPinCode());
			model.addAttribute("yearEstablishment", businessEntityDetails.getYearEstablishment());
			model.addAttribute("gstin", businessEntityDetails.getGstin());
			model.addAttribute("cin", businessEntityDetails.getCin());
			model.addAttribute("companyPanNo", businessEntityDetails.getCompanyPanNo());
			model.addAttribute("moaDocFname", businessEntityDetails.getMoaDocFname());
			model.addAttribute("regisAttacDocFname", businessEntityDetails.getRegisAttacDocFname());
			model.addAttribute("bodDocFname", businessEntityDetails.getBodDocFname());
			model.addAttribute("indusAffidaDocFname", businessEntityDetails.getIndusAffidaDocFname());
			model.addAttribute("annexureiaformat", businessEntityDetails.getAnnexureiaformat());
			List<ProprietorDetails> proprietorDetailsList = proprietorService.findAllByBusinessId(businId);
			model.addAttribute("ProprietorDetailsList", proprietorDetailsList);

		}

		/* Project Details */

		;
		
		
		 ProjectDetails ProjectDetail = projectService.getProjDetById(projId);
			
			// To retrieve ExistingProjectDetailsList from table on projectId basis
					if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
						existProjList = existProjectService.getExistProjListById(projId);
						model.addAttribute(EXIST_PROJ_LIST, existProjList);
					} else {
						existProjList.clear();
					}
			
		if (ProjectDetail != null) {
			model.addAttribute("contactPersonName", ProjectDetail.getContactPersonName());
			model.addAttribute("mobileNo", ProjectDetail.getMobileNo());
			model.addAttribute("designation", ProjectDetail.getDesignation());
			model.addAttribute("projectDescription", ProjectDetail.getProjectDescription());
			model.addAttribute("webSiteName", ProjectDetail.getWebSiteName());
			model.addAttribute("fullAddress", ProjectDetail.getFullAddress());
			model.addAttribute("registeredAddr", ProjectDetail.getRegisteredAddr());
			model.addAttribute("districtName", ProjectDetail.getDistrictName());
			model.addAttribute("mandalName", ProjectDetail.getMandalName());
			model.addAttribute("resionName", ProjectDetail.getResionName());
			model.addAttribute("pinCode", ProjectDetail.getPinCode());

			model.addAttribute("natureOfProject", ProjectDetail.getNatureOfProject());
			model.addAttribute("expansion", ProjectDetail.getExpansion());
			model.addAttribute("diversification", ProjectDetail.getDiversification());
			/*
			 * model.addAttribute("ExistingProducts", ProjectDetail.getExistingProducts());
			 * model.addAttribute("existingInstalledCapacity",
			 * ProjectDetail.getExistingInstalledCapacity());
			 * model.addAttribute("proposedProducts", ProjectDetail.getProposedProducts());
			 * model.addAttribute("proposedInstalledCapacity",
			 * ProjectDetail.getProposedInstalledCapacity());
			 * model.addAttribute("existingGrossBlock",
			 * ProjectDetail.getExistingGrossBlock());
			 * model.addAttribute("proposedGrossBlock",
			 * ProjectDetail.getProposedGrossBlock());
			 * model.addAttribute("enclDetProRepFileName",
			 * ProjectDetail.getEnclDetProRepFileName());
			 * model.addAttribute("caCertificateFileName",
			 * ProjectDetail.getCaCertificateFileName());
			 * model.addAttribute("charatEngFileName",
			 * ProjectDetail.getCharatEngFileName());
			 */
		}

		/* Investment Details */

		InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(investId);
		if (invdtlFromDb != null) {
			model.addAttribute("categorytype", invdtlFromDb.getInvIndType());
			model.addAttribute("invFci", invdtlFromDb.getInvFci());
			model.addAttribute("invTotalProjCost", invdtlFromDb.getInvTotalProjCost());
			model.addAttribute("invLandCost", invdtlFromDb.getInvLandCost());
			model.addAttribute("invBuildingCost", invdtlFromDb.getInvBuildingCost());
			model.addAttribute("invPlantAndMachCost", invdtlFromDb.getInvPlantAndMachCost());
			model.addAttribute("invOtherCost", invdtlFromDb.getInvOtherCost());
			model.addAttribute("invCommenceDate", invdtlFromDb.getInvCommenceDate());
			model.addAttribute("propCommProdDate", invdtlFromDb.getPropCommProdDate());
			model.addAttribute("invFileName", invdtlFromDb.getInvFilename());
			model.addAttribute("regiOrLicense", invdtlFromDb.getRegiOrLicense());
			model.addAttribute("regiOrLicenseFileName", invdtlFromDb.getRegiOrLicenseFileName());
			List<PhaseWiseInvestmentDetails> pwInvListDromDb = pwInvestDs
					.getPwInvDetailListById(invdtlFromDb.getInvId());
			if (pwInvListDromDb.size() > 0) {
				model.addAttribute("pwApply", "Yes");
			} else {
				model.addAttribute("pwApply", "No");
			}

			model.addAttribute("pwInvList", pwInvListDromDb);
		}

		/* Proposed Employment Details */

		ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService.getPropEmpById(propId);
		if (proposedEmploymentDetail != null) {

			if (proposedEmploymentDetail != null) {
				List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = proposedEmploymentDetail
						.getSkilledUnSkilledEmployemnt();
				totalSkilledAndUnSkilledEmploment(model, skilledUnSkilledEmployemntslist);
				model.addAttribute("proposedEmploymentDetails", skilledUnSkilledEmployemntslist);
			}
		}

		/* Incentive Specific Details */
		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
		if (IncentiveDetail != null) {
			model.addAttribute("ISF_Claim_Reim", IncentiveDetail.getISF_Claim_Reim());
			model.addAttribute("ISF_Reim_SCST", IncentiveDetail.getISF_Reim_SCST());
			model.addAttribute("ISF_Reim_FW", IncentiveDetail.getISF_Reim_FW());
			model.addAttribute("ISF_Reim_BPLW", IncentiveDetail.getISF_Reim_BPLW());
			model.addAttribute("ISF_Ttl_SGST_Reim", IncentiveDetail.getISF_Ttl_SGST_Reim());

			model.addAttribute("ISF_Stamp_Duty_EX", IncentiveDetail.getISF_Stamp_Duty_EX());
			model.addAttribute("ISF_Additonal_Stamp_Duty_EX", IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
			model.addAttribute("ISF_Amt_Stamp_Duty_Reim", IncentiveDetail.getISF_Amt_Stamp_Duty_Reim());
			model.addAttribute("ISF_Ttl_Stamp_Duty_EX", IncentiveDetail.getISF_Ttl_Stamp_Duty_EX());

			model.addAttribute("ISF_Epf_Reim_UW", IncentiveDetail.getISF_Epf_Reim_UW());
			model.addAttribute("ISF_Add_Epf_Reim_SkUkW", IncentiveDetail.getISF_Add_Epf_Reim_SkUkW());
			model.addAttribute("ISF_Add_Epf_Reim_DIVSCSTF", IncentiveDetail.getISF_Add_Epf_Reim_DIVSCSTF());
			model.addAttribute("ISF_Ttl_EPF_Reim", IncentiveDetail.getISF_Ttl_EPF_Reim());

			model.addAttribute("ISF_Cis", IncentiveDetail.getISF_Cis());
			model.addAttribute("ISF_ACI_Subsidy_Indus", IncentiveDetail.getISF_ACI_Subsidy_Indus());
			model.addAttribute("ISF_Infra_Int_Subsidy", IncentiveDetail.getISF_Infra_Int_Subsidy());
			model.addAttribute("ISF_AII_Subsidy_DIVSCSTF", IncentiveDetail.getISF_AII_Subsidy_DIVSCSTF());
			model.addAttribute("ISF_Loan_Subsidy", IncentiveDetail.getISF_Loan_Subsidy());
			model.addAttribute("ISF_Total_Int_Subsidy", IncentiveDetail.getISF_Total_Int_Subsidy());

			model.addAttribute("ISF_Tax_Credit_Reim", IncentiveDetail.getISF_Tax_Credit_Reim());
			model.addAttribute("ISF_EX_E_Duty", IncentiveDetail.getISF_EX_E_Duty());
			model.addAttribute("ISF_EX_E_Duty_PC", IncentiveDetail.getISF_EX_E_Duty_PC());
			model.addAttribute("ISF_EX_Mandee_Fee", IncentiveDetail.getISF_EX_Mandee_Fee());
			model.addAttribute("ISF_Indus_Payroll_Asst", IncentiveDetail.getISF_Indus_Payroll_Asst());
			model.addAttribute("Total_Other_Incentive", IncentiveDetail.getTotal_Other_Incentive());
			model.addAttribute("isfCustIncDocName", IncentiveDetail.getIsfCustIncDocName());
			model.addAttribute("othAddRequest1", IncentiveDetail.getOthAddRequest1());

			try {
				Long isf_Claim_Reim = IncentiveDetail.getISF_Ttl_SGST_Reim() == null ? 0
						: IncentiveDetail.getISF_Ttl_SGST_Reim();
				Long isf_Reim_SCST = IncentiveDetail.getISF_Ttl_Stamp_Duty_EX() == null ? 0
						: IncentiveDetail.getISF_Ttl_Stamp_Duty_EX();
				Long isf_EX_E_Duty_PC = IncentiveDetail.getISF_EX_E_Duty_PC() == null ? 0
						: IncentiveDetail.getISF_EX_E_Duty_PC();
				Long isf_EX_Mandee_Fee = IncentiveDetail.getISF_Ttl_EPF_Reim() == null ? 0
						: IncentiveDetail.getISF_Ttl_EPF_Reim();
				Long isf_Reim_BPLW = IncentiveDetail.getISF_Total_Int_Subsidy() == null ? 0
						: IncentiveDetail.getISF_Total_Int_Subsidy();
				Long isf_Reim_FW = IncentiveDetail.getTotal_Other_Incentive() == null ? 0
						: IncentiveDetail.getTotal_Other_Incentive();
				Long total = isf_Claim_Reim + isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW
						+ isf_Reim_FW;
				model.addAttribute("total", total);
			} catch (Exception e) {
				logger.error("total  exception **** " + e.getMessage());
				e.printStackTrace();
			}

			if(invdtlFromDb!=null)
 			if (invdtlFromDb.getInvIndType() != null && !invdtlFromDb.getInvIndType().isEmpty()) {
				if (invdtlFromDb.getInvIndType().equalsIgnoreCase("Super Mega")) {
					model.addAttribute("hideCustamList", "");
				} else {
					model.addAttribute("hideCustamList", "hideCustamList");
				}
			}

			List<AvailCustomisedDetails> availCustomisedDetailsList = availCustmisedDetailsService
					.findAllByAvaCustId(IncentiveDetail.getId());
			if (availCustomisedDetailsList.size() > 0) {
				model.addAttribute("ISF_Cstm_Inc_Status", "Yes");
			} else {
				model.addAttribute("ISF_Cstm_Inc_Status", "No");
			}
			model.addAttribute("department", new Department());
			// model.addAttribute("aggregatequantumbenifit", aggregatequantumbenifit);
			model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList);

		}

		return new ModelAndView("view_all_application_detailsSMEDIC");

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

	private void totalSkilledAndUnSkilledEmploment(Model model,
			List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist) {
		long skilledEmploymentMale = 0;
		long skilledEmploymentFemale = 0;
		long totalSkilledEmployment = 0;
		long unSkilledEmploymentMale = 0;
		long unSkilledEmploymentFemale = 0;
		long totalUnSkilledEmployment = 0;
		if (skilledUnSkilledEmployemntslist.size() > 0) {

			for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntslist) {
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
		model.addAttribute("grossTotalMaleEmployment", (skilledEmploymentMale + unSkilledEmploymentMale));
		model.addAttribute("grossTotalFemaleEmployment", (skilledEmploymentFemale + unSkilledEmploymentFemale));
		model.addAttribute("grossTotalMaleandFemaleEmployment", ((skilledEmploymentFemale + unSkilledEmploymentFemale)
				+ (skilledEmploymentMale + unSkilledEmploymentMale)));
	}

	@GetMapping(value = "/dicApplicationForLoc")
	public String applicationForLocSMEdic(ModelMap model, HttpSession session) {
		logger.debug("Applications SME For LOC");

		List<Object> investmentDetailsList = investRepository.getAllDetailsSMEByAppId();

		model.addAttribute("investmentDetailsList", investmentDetailsList);

		return "applicationsForLocSMEDIC";
	}

	@GetMapping(value = "/viewDICApplication")
	public String viewDICApplication(Model model) {
		logger.debug("View And Evaluate DIC Policy");
		return "viewAndEvaluateDIC";
	}

	@GetMapping(value = "/facilitiesReliefSoughtxx")
	public ModelAndView getFacilitiesReliefxx(ModelMap model) {

		String isfId = incentiveDetailsService.getIsfIdByApplId(applicantId);

		IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
		if (IncentiveDetail != null) {
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
		return new ModelAndView("evaluation-view-department");

	}

	@GetMapping(value = "/dicgenerateLoc")
	public String generateLocdic(ModelMap model, HttpSession session) {
		logger.debug("generateLoc For DIC");

		List<PrepareAgendaNotes> prepareAgendaNotesLists = new ArrayList<PrepareAgendaNotes>();
		String userid = (String) session.getAttribute("userId");
		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
				.findPrepAgendaNotesListByUserId(userid);
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Submitted To Committee")) {
					prepareAgendaNotesLists.add(list);
				}
			}
		}
		model.addAttribute("prepareAgendaNotesLists", prepareAgendaNotesLists);

		return "generateLocDIC";
	}

	@RequestMapping(value = "/savegenerateLocDIC", method = RequestMethod.GET)
	public ModelAndView savegenerateLocDIC(Model model, HttpSession session,
			@RequestParam(value = "applicantId", required = false) String applicantId) {

		PrepareAgendaNotes prepareAgendaNotes = new PrepareAgendaNotes();
		// String appId = (String) session.getAttribute("appId");
		PrepareAgendaNotes prepareAgendaNote = prepareAgendaNoteService.getPrepareByAppliId(applicantId);
		prepareAgendaNotes.setAppliId(prepareAgendaNote.getAppliId());
		prepareAgendaNotes.setCompanyName(prepareAgendaNote.getCompanyName());
		prepareAgendaNotes.setId(prepareAgendaNote.getId());
		prepareAgendaNotes.setInvestment(prepareAgendaNote.getInvestment());
		prepareAgendaNotes.setCategoryIndsType(prepareAgendaNote.getCategoryIndsType());
		prepareAgendaNotes.setNotes(prepareAgendaNote.getNotes());
		prepareAgendaNotes.setCreatedBY(prepareAgendaNote.getCreatedBY());
		prepareAgendaNotes.setDistrict(prepareAgendaNote.getDistrict());
		prepareAgendaNotes.setRegion(prepareAgendaNote.getRegion());
		prepareAgendaNotes.setStatus("LOC Generated");
		prepareAgendaNotes.setUserId(prepareAgendaNote.getUserId());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNotes);

		model.addAttribute("msg", "Succefully Complete Agenda Notes");

		return new ModelAndView("redirect:/generateLocEvaluationDIC?applicantId=" + applicantId);
	}

	@RequestMapping(value = "/rejectLocDIC", method = RequestMethod.GET)
	public ModelAndView saverejectLocdic(Model model, HttpSession session) {

		PrepareAgendaNotes prepareAgendaNotes = new PrepareAgendaNotes();
		String appId = (String) session.getAttribute("appId");
		PrepareAgendaNotes prepareAgendaNote = prepareAgendaNoteService.getPrepareByAppliId(appId);
		prepareAgendaNotes.setAppliId(prepareAgendaNote.getAppliId());
		prepareAgendaNotes.setCompanyName(prepareAgendaNote.getCompanyName());
		prepareAgendaNotes.setId(prepareAgendaNote.getId());
		prepareAgendaNotes.setInvestment(prepareAgendaNote.getInvestment());
		prepareAgendaNotes.setCategoryIndsType(prepareAgendaNote.getCategoryIndsType());
		prepareAgendaNotes.setNotes(prepareAgendaNote.getNotes());
		prepareAgendaNotes.setCreatedBY(prepareAgendaNote.getCreatedBY());
		prepareAgendaNotes.setDistrict(prepareAgendaNote.getDistrict());
		prepareAgendaNotes.setRegion(prepareAgendaNote.getRegion());
		prepareAgendaNotes.setStatus("LOC Rejected");
		prepareAgendaNotes.setUserId(prepareAgendaNote.getUserId());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNotes);

		model.addAttribute("msg", "Succefully Reject Notes");

		return new ModelAndView("redirect:/dicgenerateLoc");
	}

	@GetMapping(value = "/generateLocEvaluationDIC")
	public ModelAndView generateLocEvaluationDIC(@RequestParam(value = "applicantId", required = false) String applId,
			HttpSession session, Model model) {
		String investId = null;
		String propEmplId = null;
		investId = invDtlService.getInvDetailIdByapplId(applId);
		propEmplId = emplDtlService.getPropEmplIdByapplId(applId);
		applicantId = applId;
		session.setAttribute("appId", applicantId);
		try {
			// Authorised Signatory Details view
			try {
				ApplicantDetails appDtlFromDb = appDtlService.getApplicantDetailsByAppId(applId);
				ApplicantDocument applDoc = null;
				if (appDocService.getApplicantDocumentByDocAppId(applId) != null) {
					applDoc = appDocService.getApplicantDocumentByDocAppId(applId);
				}

				if (appDtlFromDb != null) {
					if (applDoc != null) {
						try {
							byte[] encodeBase64 = Base64.encodeBase64(applDoc.getData());
							String base64Encoded = null;
							if (applDoc.getFileName() != null && applDoc.getFileName() != ""
									&& !applDoc.getFileName().isEmpty()) {
								base64Encoded = new String(encodeBase64, "UTF-8");
							}
							appDtlFromDb.setBase64imageFile(base64Encoded);
							appDtlFromDb.setFileName(applDoc.getFileName());
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
				model.addAttribute("viewSignatoryDetail", appDtlFromDb);
			} catch (Exception e) {
				logger.error("viewSignatoryDetail exception &&&&& " + e.getMessage());
				e.printStackTrace();
			}

			/* Business Entity Details */

			BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(applId);
			if (businessEntityDetails != null) {

				model.addAttribute("businessEntityName", businessEntityDetails.getBusinessEntityName());
				model.addAttribute("businessEntityType", businessEntityDetails.getBusinessEntityType());
				model.addAttribute("authorisedSignatoryName", businessEntityDetails.getAuthorisedSignatoryName());
				model.addAttribute("businessDesignation", businessEntityDetails.getBusinessDesignation());
				model.addAttribute("emailId", businessEntityDetails.getEmailId());
				model.addAttribute("mobileNumber", businessEntityDetails.getMobileNumber());
				model.addAttribute("fax", businessEntityDetails.getFax());
				model.addAttribute("businessAddress", businessEntityDetails.getBusinessAddress());
				model.addAttribute("businessCountryName", businessEntityDetails.getBusinessCountryName());
				StateDetails stateDetails = stateDetailsService
						.getStateBystateCode(Long.valueOf(businessEntityDetails.getBusinessStateName()));
				model.addAttribute("businessStateName", stateDetails.getStateName());
				// model.addAttribute("businessStateName",
				// businessEntityDetails.getBusinessStateName());
				model.addAttribute("businessDistrictName", businessEntityDetails.getBusinessDistrictName());
				model.addAttribute("PinCode", businessEntityDetails.getPinCode());
				model.addAttribute("yearEstablishment", businessEntityDetails.getYearEstablishment());
				model.addAttribute("gstin", businessEntityDetails.getGstin());
				model.addAttribute("cin", businessEntityDetails.getCin());
				model.addAttribute("companyPanNo", businessEntityDetails.getCompanyPanNo());
				model.addAttribute("moaDocFname", businessEntityDetails.getMoaDocFname());
				model.addAttribute("regisAttacDocFname", businessEntityDetails.getRegisAttacDocFname());
				model.addAttribute("bodDocFname", businessEntityDetails.getBodDocFname());
				model.addAttribute("indusAffidaDocFname", businessEntityDetails.getIndusAffidaDocFname());
				model.addAttribute("annexureiaformat", businessEntityDetails.getAnnexureiaformat());
				List<ProprietorDetails> proprietorDetailsList = proprietorService
						.findAllByBusinessId(businessEntityDetails.getId());
				model.addAttribute("ProprietorDetailsList", proprietorDetailsList);

			}

			/* Project Details */

			ProjectDetails ProjectDetail = projectService.getProjectByapplicantDetailId(applId);
			if (ProjectDetail != null) {
				model.addAttribute("contactPersonName", ProjectDetail.getContactPersonName());
				model.addAttribute("mobileNo", ProjectDetail.getMobileNo());
				model.addAttribute("designation", ProjectDetail.getDesignation());
				model.addAttribute("projectDescription", ProjectDetail.getProjectDescription());
				model.addAttribute("webSiteName", ProjectDetail.getWebSiteName());
				model.addAttribute("fullAddress", ProjectDetail.getFullAddress());
				model.addAttribute("registeredAddr", ProjectDetail.getRegisteredAddr());
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
			}

			// Investment Details view
			try {

				InvestmentDetails invDetailFromDb = invDtlService.getInvestmentDetailsById(investId);
				if (invDetailFromDb != null) {
					if ((!invDetailFromDb.getApplId().isEmpty() && invDetailFromDb.getApplId() != null)) {
						List<InvBreakupCost> brkupViewList = brkupService.getInvBreakupCostListById(investId);
						List<InvMeansOfFinance> mofViewList = mofService.getInvMeansOfFinanceListById(investId);
						List<PhaseWiseInvestmentDetails> pwInvViewList = pwInvDtlService
								.getPwInvDetailListById(investId);

						int sum = 0;

						// Loop through the array to calculate sum of elements
						for (int i = 0; i < 1; i++) {
							for (PhaseWiseInvestmentDetails phaseWiseInvestmentDetails : pwInvViewList) {
								Long pwFci = phaseWiseInvestmentDetails.getPwFci();
								sum = (int) (sum + pwFci);
								model.addAttribute("PWFCI", sum);
								model.addAttribute("PWpropsdtcoprd", phaseWiseInvestmentDetails.getPwPropProductDate());
							}
						}

						String categorytype = invDetailFromDb.getInvIndType();
						Long pnm = invDetailFromDb.getInvPlantAndMachCost();
						Long fci = invDetailFromDb.getInvFci();

						model.addAttribute("pnm", pnm);
						model.addAttribute("fci", fci);
						Float fci1 = (float) (fci / 10000000);

						model.addAttribute("fci1", fci1);
						model.addAttribute("categorytype", categorytype);

						model.addAttribute("brkupList", brkupViewList);
						model.addAttribute("mofList", mofViewList);
						model.addAttribute("pwInvList", pwInvViewList);
						model.addAttribute("viewInvestDetails", invDetailFromDb);
						model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());

					}

				} else {
					model.addAttribute("viewInvestDetails", new InvestmentDetails());
					model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
				}
			} catch (Exception e) {
				logger.error("InvestmentDetails exception #### " + e.getMessage());
				e.printStackTrace();
			}

			// Proposed Employment Details view
			try {
				if (!propEmplId.isEmpty()) {
					ProposedEmploymentDetails emplDetail = emplDtlService.getPropEmpById(propEmplId);
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
				}

			} catch (Exception e) {
				logger.error("PropEmplDetails exception @@@@@ " + e.getMessage());
				e.printStackTrace();

			}

			/* Incentive Specific Details */
			IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveByisfapcId(applId);
			if (IncentiveDetail != null) {
				model.addAttribute("ISF_Claim_Reim", IncentiveDetail.getISF_Claim_Reim());
				model.addAttribute("ISF_Reim_SCST", IncentiveDetail.getISF_Reim_SCST());
				model.addAttribute("ISF_Reim_FW", IncentiveDetail.getISF_Reim_FW());
				model.addAttribute("ISF_Reim_BPLW", IncentiveDetail.getISF_Reim_BPLW());
				model.addAttribute("ISF_Ttl_SGST_Reim", IncentiveDetail.getISF_Ttl_SGST_Reim());

				model.addAttribute("ISF_Stamp_Duty_EX", IncentiveDetail.getISF_Stamp_Duty_EX());
				model.addAttribute("ISF_Additonal_Stamp_Duty_EX", IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
				model.addAttribute("ISF_Amt_Stamp_Duty_Reim", IncentiveDetail.getISF_Amt_Stamp_Duty_Reim());
				model.addAttribute("ISF_Ttl_Stamp_Duty_EX", IncentiveDetail.getISF_Ttl_Stamp_Duty_EX());

				model.addAttribute("ISF_Epf_Reim_UW", IncentiveDetail.getISF_Epf_Reim_UW());
				model.addAttribute("ISF_Add_Epf_Reim_SkUkW", IncentiveDetail.getISF_Add_Epf_Reim_SkUkW());
				model.addAttribute("ISF_Add_Epf_Reim_DIVSCSTF", IncentiveDetail.getISF_Add_Epf_Reim_DIVSCSTF());
				model.addAttribute("ISF_Ttl_EPF_Reim", IncentiveDetail.getISF_Ttl_EPF_Reim());

				model.addAttribute("ISF_Cis", IncentiveDetail.getISF_Cis());
				model.addAttribute("ISF_ACI_Subsidy_Indus", IncentiveDetail.getISF_ACI_Subsidy_Indus());
				model.addAttribute("ISF_Infra_Int_Subsidy", IncentiveDetail.getISF_Infra_Int_Subsidy());
				model.addAttribute("ISF_AII_Subsidy_DIVSCSTF", IncentiveDetail.getISF_AII_Subsidy_DIVSCSTF());
				model.addAttribute("ISF_Loan_Subsidy", IncentiveDetail.getISF_Loan_Subsidy());
				model.addAttribute("ISF_Total_Int_Subsidy", IncentiveDetail.getISF_Total_Int_Subsidy());

				model.addAttribute("ISF_Tax_Credit_Reim", IncentiveDetail.getISF_Tax_Credit_Reim());
				model.addAttribute("ISF_EX_E_Duty", IncentiveDetail.getISF_EX_E_Duty());
				model.addAttribute("ISF_EX_E_Duty_PC", IncentiveDetail.getISF_EX_E_Duty_PC());
				model.addAttribute("ISF_EX_Mandee_Fee", IncentiveDetail.getISF_EX_Mandee_Fee());
				model.addAttribute("ISF_Indus_Payroll_Asst", IncentiveDetail.getISF_Indus_Payroll_Asst());
				model.addAttribute("Total_Other_Incentive", IncentiveDetail.getTotal_Other_Incentive());
				model.addAttribute("isfCustIncDocName", IncentiveDetail.getIsfCustIncDocName());

				model.addAttribute("createdate", IncentiveDetail.getCreateDate());
				List<AvailCustomisedDetails> availCustomisedDetailsList = availCustmisedDetailsService
						.findAllByAvaCustId(IncentiveDetail.getId());
				if (availCustomisedDetailsList.size() > 0) {
					model.addAttribute("ISF_Cstm_Inc_Status", "Yes");
				} else {
					model.addAttribute("ISF_Cstm_Inc_Status", "No");
				}
				model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList);

				Long tsgst = IncentiveDetail.getISF_Ttl_SGST_Reim() == null ? 0
						: IncentiveDetail.getISF_Ttl_SGST_Reim();
				Long tstam = IncentiveDetail.getISF_Stamp_Duty_EX() == null ? 0
						: IncentiveDetail.getISF_Stamp_Duty_EX();
				Long taddist = IncentiveDetail.getISF_Additonal_Stamp_Duty_EX() == null ? 0
						: IncentiveDetail.getISF_Additonal_Stamp_Duty_EX();
				Long tepf = IncentiveDetail.getISF_Ttl_EPF_Reim() == null ? 0 : IncentiveDetail.getISF_Ttl_EPF_Reim();
				Long tis = IncentiveDetail.getISF_Total_Int_Subsidy() == null ? 0
						: IncentiveDetail.getISF_Total_Int_Subsidy();
				Long toth = IncentiveDetail.getTotal_Other_Incentive() == null ? 0
						: IncentiveDetail.getTotal_Other_Incentive();

				long aggregatequantumbenifit = tsgst + tstam + taddist + tepf + tis + toth;

				model.addAttribute("aggregatequantumbenifit", aggregatequantumbenifit);
			}

		} catch (Exception e) {
			logger.error(" exception **** " + e.getMessage());
			e.printStackTrace();
		}

		return new ModelAndView("generateLocEvaluationDIC");

	}

	@GetMapping(value = "/meetingscheduledic")
	public String meetingscheduledic(ModelMap model, HttpSession session) {
		logger.debug("Meeting Schedule Start");

		return "meetingscheduledic";
	}

	@GetMapping(value = "/momgodic")
	public String momgodic(ModelMap model, HttpSession session) {
		logger.debug("Meeting of Meeting");

		return "momgo";
	}

	@RequestMapping(value = "/SendtoConcernDepartmentdic", method = RequestMethod.POST)
	public ModelAndView SendConcernDepartmentdic(
			@ModelAttribute("SendtoConcernDepartment") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {
		String appId = (String) session.getAttribute("appId");
		String userId = (String) session.getAttribute("userId");

		return new ModelAndView("redirect:/viewApplicationDetails");
	}
	
	// vinay common method 
	
	public void common(Model model, HttpServletRequest request, HttpSession session) {
		String appId = (String) session.getAttribute("appId");

		String businId = "";
		String appStr = appId.substring(0, appId.length() - 2);
		businId = appStr + "B1";

		PrepareAgendaNotes prepareAgendaNotesList = prepareAgendaNotesService.findPrepAgendaNotesByAppliId(appId);
		if (prepareAgendaNotesList != null) {
			model.addAttribute("enableIncludeAgenda", "enableIncludeAgenda");
		} else {
			model.addAttribute("enableIncludeAgenda", "");
		}
		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);
		if (evaluateProjectDetails != null) {

			model.addAttribute("newProject", evaluateProjectDetails.getNewProject());
			model.addAttribute("region", evaluateProjectDetails.getResionName());
			model.addAttribute("propsProdtDetailObserv", evaluateProjectDetails.getPropsProdtDetailObserv());

			String expension = evaluateProjectDetails.getExpansion() != null ? evaluateProjectDetails.getExpansion()
					: "";
			String diversification = evaluateProjectDetails.getDiversification() != null
					? evaluateProjectDetails.getDiversification()
					: "";

			if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
				// fetch documents from MongoDBevaluateApplication
				applicantViewController.existProjDocsFromMongoDB(evaluateProjectDetails.getId(), model);

				model.addAttribute("natureOfProject", expension);
				if (!expension.isEmpty() && !diversification.isEmpty()) {
					model.addAttribute("natureOfProject", expension + "/ " + diversification);
				} else if (!diversification.isEmpty()) {
					model.addAttribute("natureOfProject", diversification);
				}
				List<EvaluateExistNewProjDetails> evaluateExistNewProjDetailsList = evaluateExistNewProjDetailsRepository
						.getEvalExistProjListByepdProjDtlId(evaluateProjectDetails.getId());
				model.addAttribute("evaluateExistNewProjDetailsList", evaluateExistNewProjDetailsList);
			} else {
				// fetch documents from MongoDB
				applicantViewController.newProjDocMongoDB(evaluateProjectDetails.getId(), model);

				model.addAttribute("natureOfProject", evaluateProjectDetails.getNatureOfProject());
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

		// Propriter Details
		List<ProprietorDetails> proprietorDetailsList = propriterService.findAllByBusinessId(businId);
		model.addAttribute("ProprietorDetailsList", proprietorDetailsList);

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
			model.addAttribute("invBuildingIIEPP", evaluateInvestMentDetails.getBuildingIIEPP());
			model.addAttribute("invPlantAndMachIIEPP", evaluateInvestMentDetails.getPlantAndMachIIEPP());
			model.addAttribute("invFixedAssetIIEPP", evaluateInvestMentDetails.getFixedAssetIIEPP());
			model.addAttribute("invcatundtakObserv", evaluateInvestMentDetails.getCatIndusUndtObserv());
			model.addAttribute("propsPlntMcnryCostObserv", evaluateInvestMentDetails.getPropsPlntMcnryCostObserv());
			model.addAttribute("optcutofdateObserv", evaluateInvestMentDetails.getOptcutofdateobserv());
			model.addAttribute("dateofcumProdObserv", evaluateInvestMentDetails.getDateofComProdObserv());
			model.addAttribute("projreportObserv", evaluateInvestMentDetails.getDetailProjReportObserv());
			model.addAttribute("propCapInvObserv", evaluateInvestMentDetails.getPropCapInvObserv());
			model.addAttribute("totlcostprojObserv", evaluateInvestMentDetails.getTotlCostProjObserv());

			model.addAttribute("mofObserv", evaluateInvestMentDetails.getMofObserv());
			model.addAttribute("IemRegObserv", evaluateInvestMentDetails.getIemRegObserv());
			model.addAttribute("IndusUndrtkObserv", evaluateInvestMentDetails.getIndusUntkObserv());

			model.addAttribute("eligblInvPerdLargeObserv", evaluateInvestMentDetails.getEligblInvPerdLargeObserv());
			model.addAttribute("eligblInvPerdMegaObserv", evaluateInvestMentDetails.getEligblInvPerdMegaObserv());
			model.addAttribute("eligblInvPerdSupermegaObserv",
					evaluateInvestMentDetails.getEligblInvPerdSupermegaObserv());
			model.addAttribute("eligblCapInvObserv", evaluateInvestMentDetails.getEligblCapInvObserv());
			model.addAttribute("projPhasesObserv", evaluateInvestMentDetails.getProjPhasesObserv());

			model.addAttribute("caStatutoryObserv", evaluateInvestMentDetails.getCaStatutoryObserv());
			model.addAttribute("authSignatoryObserv", evaluateInvestMentDetails.getAuthSignatoryObserv());
			model.addAttribute("appformatObserv", evaluateInvestMentDetails.getAppformatObserv());

			if (evaluateInvestMentDetails.getInvCAStatutoryDate() != null) {
				try {
					DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
					// parse the date string into Date object
					Date date = srcDf.parse(evaluateInvestMentDetails.getInvCAStatutoryDate().toString());
					DateFormat destDf = new SimpleDateFormat("dd/MM/yyyy");
					// format the date into another format
					String dateStr = destDf.format(date);
					model.addAttribute("invCAStatutoryDate", dateStr);
				} catch (ParseException e) {
					logger.error(String.format("8888 dateFormat exception 8888 %s", e.getMessage()));
				}
			} else {
				model.addAttribute("invCAStatutoryDate", evaluateInvestMentDetails.getInvCAStatutoryDate());
			}

			// EvaluateApplication Breakup of proposed capital Investment Remarks
			model.addAttribute("invFixedAssetRemarks", evaluateInvestMentDetails.getFixedAssetRemarks());
			model.addAttribute("invPlantAndMachRemarks", evaluateInvestMentDetails.getPlantAndMachRemarks());
			model.addAttribute("invBuildingRemarks", evaluateInvestMentDetails.getBuildingRemarks());
			model.addAttribute("invLandcostRemarks", evaluateInvestMentDetails.getLandcostRemarks());

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
			model.addAttribute("isPresFormat", deptBusEntFromDb.getPresFormat());
			model.addAttribute("isDocAuthorized", deptBusEntFromDb.getDocAuthorized());
			model.addAttribute("dirDetailObserv", deptBusEntFromDb.getDirDetailsObserv());

			model.addAttribute("supprtdocObserv", deptBusEntFromDb.getSupprtdocObserv());
			model.addAttribute("gstinObserv", deptBusEntFromDb.getGstinObserv());
			model.addAttribute("panObserv", deptBusEntFromDb.getPanObserv());
			
			
			// PropsedEmpDetails
			DeptProposedEmploymentDetails deptProEmploymentDetails = deptPrpsEmplRepository.findByappId(appId);
			model.addAttribute("totalEmpDetailObserv", deptProEmploymentDetails.getTotalDetailObserv());

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

				model.addAttribute("onlineSubmitDate", deptIncDetails.getCreatedDate());
				model.addAttribute("subDateAppObserv", deptIncDetails.getSubDateAppObserv());
				
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

			List<Object> evalObjList = investRepository.getEvalDetailsByApplId(appId);
			model.addAttribute("evalObjList", evalObjList);

			model.addAttribute("appId", appId);
			model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);
			model.addAttribute("EvaluateProjectDetails", evaluateProjectDetails);
		} catch (Exception e) {
			logger.error(String.format("^^^^ evaluateApplication exception ^^^^^ %s", e.getMessage()));
		}

	}
	
	
	
	// vinay common method end 
	

	

	@RequestMapping(value = "/evaluateApplicationDIC", method = RequestMethod.GET)
	public String evaluateApplicationDIC(@RequestParam(value = "applicantId", required = false) String applId,
			Model model, HttpServletRequest request, HttpSession session) {
		logger.debug("Evaluate Application Start");

		final String SMALL = "Small";
		final String MEDIUM = "Medium";
		final String LARGE = "Large";
		final String MEGA = "Mega";
		final String MEGAPLUS = "Mega Plus";
		final String SUPERMEGA = "Super Mega";

		String appId = (String) session.getAttribute("appId");
		PrepareAgendaNotes prepareAgendaNotesList = prepareAgendaNotesService.findPrepAgendaNotesByAppliId(appId);
		if (prepareAgendaNotesList != null) {
			model.addAttribute("enableIncludeAgenda", "enableIncludeAgenda");
		} else {
			model.addAttribute("enableIncludeAgenda", "");
		}

		// ProjectDetails evaluateProjectDetails =
		// projectService.getProjectByapplicantDetailId(appId);
		List<Object> evalObjList = investRepository.getEvalDetailsByApplId(appId);
		model.addAttribute("evalObjList", evalObjList);

		String beId=appId.substring(0, appId.length()-2)+"B1";
		List<DeptProprietorDetails> deptProprietorDetailsList=	deptPropRepository.findByBusinessEntityDetails(beId);
		model.addAttribute("deptProprietorDetailsList", deptProprietorDetailsList);
	
		/*
		 * List<DeptProprietorDetails> deptproperiterlist =
		 * deptIncRepository.findById(appId); model.addAttribute("deptproperiterlist",
		 * deptproperiterlist);
		 */
		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);
		if (evaluateProjectDetails != null) {

			
			
			model.addAttribute("newProject", evaluateProjectDetails.getNewProject());
			model.addAttribute("region", evaluateProjectDetails.getResionName().trim());
			model.addAttribute("district", evaluateProjectDetails.getDistrictName().trim());
			model.addAttribute("propsProdtDetailObserv", evaluateProjectDetails.getPropsProdtDetailObserv());
		
			/*
			 * model.addAttribute("contactPersonName",
			 * ProjectDetail.getContactPersonName()); model.addAttribute("mobileNo",
			 * ProjectDetail.getMobileNo()); model.addAttribute("designation",
			 * ProjectDetail.getDesignation()); model.addAttribute("projectDescription",
			 * ProjectDetail.getProjectDescription()); model.addAttribute("webSiteName",
			 * ProjectDetail.getWebSiteName()); model.addAttribute("fullAddress",
			 * ProjectDetail.getFullAddress()); model.addAttribute("districtName",
			 * ProjectDetail.getDistrictName()); model.addAttribute("mandalName",
			 * ProjectDetail.getMandalName()); model.addAttribute("resionName",
			 * ProjectDetail.getResionName()); model.addAttribute("pinCode",
			 * ProjectDetail.getPinCode()); model.addAttribute("regiOrLicense",
			 * ProjectDetail.getRegiOrLicense());
			 * model.addAttribute("regiOrLicenseFileName",
			 * ProjectDetail.getRegiOrLicenseFileName()); model.addAttribute("newProject",
			 * ProjectDetail.getNewProject()); model.addAttribute("expansion",
			 * ProjectDetail.getExpansion()); model.addAttribute("diversification",
			 * ProjectDetail.getDiversification()); model.addAttribute("ExistingProducts",
			 * ProjectDetail.getExistingProducts());
			 * model.addAttribute("existingInstalledCapacity",
			 * ProjectDetail.getExistingInstalledCapacity());
			 * model.addAttribute("proposedProducts", ProjectDetail.getProposedProducts());
			 * model.addAttribute("proposedInstalledCapacity",
			 * ProjectDetail.getProposedInstalledCapacity());
			 * model.addAttribute("existingGrossBlock",
			 * ProjectDetail.getExistingGrossBlock());
			 * model.addAttribute("proposedGrossBlock",
			 * ProjectDetail.getProposedGrossBlock());
			 * model.addAttribute("enclDetProRepFileName",
			 * ProjectDetail.getEnclDetProRepFileName());
			 * model.addAttribute("caCertificateFileName",
			 * ProjectDetail.getCaCertificateFileName());
			 * model.addAttribute("charatEngFileName",
			 * ProjectDetail.getCharatEngFileName()); model.addAttribute("natureOfProject",
			 * ProjectDetail.getNatureOfProject());
			 */
			model.addAttribute("EvaluateProjectDetails", evaluateProjectDetails);
			model.addAttribute("natureOfProject", evaluateProjectDetails.getNatureOfProject());
			String pergrosblock = additionalInterest.getgrossBlock(appId);
			model.addAttribute("pergrosblock", pergrosblock);

			String regorlic = additionalInterest.getregiOrLicense(appId);
			model.addAttribute("regorlic", regorlic);
			model.addAttribute("raiseQuery", new RaiseQuery());
			String userId = (String) session.getAttribute("userId");
			List<EvaluateExistNewProjDetails> evaluateExistNewProjDetailsList = evaluateExistNewProjDetailsRepository
					.getEvalExistProjListByepdProjDtlId(evaluateProjectDetails.getId());
			model.addAttribute("evaluateExistNewProjDetailsList", evaluateExistNewProjDetailsList);
			List<EvaluationAuditTrail> EvaluationAuditSMETrailList = evaluateAuditTrialRepository
					.getEvaluAudTraByAppliIdUserId(appId, userId);
			model.addAttribute("EvaluationAuditSMETrailList", EvaluationAuditSMETrailList);
		}
	
	
		// Propriter Details	
	List<ProprietorDetails> proprietorDetailsList = propriterService.findAllByBusinessId(businId);	
	model.addAttribute("ProprietorDetailsList", proprietorDetailsList);
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
		
		EvaluateInvestmentDetails evaluateInvestMentDetails = null;
		
		  if (evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId) != null)
		  { evaluateInvestMentDetails =
		  evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId); }
		 
	
		

		try {
			
			InvestmentDetails invDetailFromDb = invDtlService.getInvestmentDetailsByapplId(appId);

			model.addAttribute("InvFCI", invDetailFromDb.getInvFci());
			model.addAttribute("optedcufoffdate", evaluateInvestMentDetails.getInvCommenceDate());
			model.addAttribute("invPropCommProdDate", evaluateInvestMentDetails.getPropCommProdDate());
			model.addAttribute("category", invDetailFromDb.getInvIndType());
			model.addAttribute("InvTPC", invDetailFromDb.getInvTotalProjCost());
			model.addAttribute("InvLC", invDetailFromDb.getInvLandCost());
			model.addAttribute("InvBuiildC", invDetailFromDb.getInvBuildingCost());
			model.addAttribute("invPlantAndMachCost", invDetailFromDb.getInvPlantAndMachCost());
			model.addAttribute("invMisFixCost", invDetailFromDb.getInvOtherCost());
			model.addAttribute("invMisFixCost", invDetailFromDb.getInvOtherCost());
			evaluateProjectDetails.setInvFci(evaluateInvestMentDetails.getInvFci());
			evaluateProjectDetails.setInvTotalProjCost(evaluateInvestMentDetails.getInvTotalProjCost());
			evaluateProjectDetails.setInvIndType(evaluateInvestMentDetails.getInvIndType());

			
	
			model.addAttribute("invEqShCapital", invDetailFromDb.getInvEqShCapital());
			model.addAttribute("invEqIntCashAccrl", invDetailFromDb.getInvEqIntCashAccrl());
			model.addAttribute("invEqIntFreeUnsecLoan", invDetailFromDb.getInvEqIntFreeUnsecLoan());
			model.addAttribute("invEqSecDept", invDetailFromDb.getInvEqSecDept());
			model.addAttribute("invEqAdvDealer", invDetailFromDb.getInvEqAdvDealer());
			model.addAttribute("invDebtFi", invDetailFromDb.getInvDebtFi());
			model.addAttribute("invDebtBank", invDetailFromDb.getInvDebtBank());
			model.addAttribute("invcatundtakObserv", evaluateInvestMentDetails.getCatIndusUndtObserv());
			model.addAttribute("optcutofdateObserv", evaluateInvestMentDetails.getOptcutofdateobserv());
			model.addAttribute("dateofcumProdObserv", evaluateInvestMentDetails.getDateofComProdObserv());
			model.addAttribute("projreportObserv", evaluateInvestMentDetails.getDetailProjReportObserv());
			model.addAttribute("propCapInvObserv", evaluateInvestMentDetails.getPropCapInvObserv());
			model.addAttribute("totlcostprojObserv", evaluateInvestMentDetails.getTotlCostProjObserv());
			model.addAttribute("mofObserv", evaluateInvestMentDetails.getMofObserv());
			model.addAttribute("IemRegObserv", evaluateInvestMentDetails.getIemRegObserv());
			model.addAttribute("IndusUndrtkObserv", evaluateInvestMentDetails.getIndusUntkObserv());

			model.addAttribute("eligblInvPerdLargeObserv", evaluateInvestMentDetails.getEligblInvPerdLargeObserv());
			model.addAttribute("eligblCapInvObserv", evaluateInvestMentDetails.getEligblCapInvObserv());
			model.addAttribute("projPhasesObserv", evaluateInvestMentDetails.getProjPhasesObserv());
			model.addAttribute("caStatutoryObserv", evaluateInvestMentDetails.getCaStatutoryObserv());
			model.addAttribute("authSignatoryObserv", evaluateInvestMentDetails.getAuthSignatoryObserv());
			model.addAttribute("appformatObserv", evaluateInvestMentDetails.getAppformatObserv());
	
			model.addAttribute("invFixedAssetRemarks", evaluateInvestMentDetails.getFixedAssetRemarks());
			model.addAttribute("invPlantAndMachRemarks", evaluateInvestMentDetails.getPlantAndMachRemarks());
			model.addAttribute("invBuildingRemarks", evaluateInvestMentDetails.getBuildingRemarks());
			model.addAttribute("invLandcostRemarks", evaluateInvestMentDetails.getLandcostRemarks());
			model.addAttribute("eligblInvPerdLargeObserv", evaluateInvestMentDetails.getEligblInvPerdLargeObserv());
			model.addAttribute("eligblInvPerdMegaObserv", evaluateInvestMentDetails.getEligblInvPerdMegaObserv());
			model.addAttribute("eligblInvPerdSupermegaObserv",
					evaluateInvestMentDetails.getEligblInvPerdSupermegaObserv());
			model.addAttribute("eligblInvPerdLargeObserv", evaluateInvestMentDetails.getEligblInvPerdLargeObserv());
			model.addAttribute("eligblCapInvObserv", evaluateInvestMentDetails.getEligblCapInvObserv());
			model.addAttribute("projPhasesObserv", evaluateInvestMentDetails.getProjPhasesObserv());
			model.addAttribute("caStatutoryObserv", evaluateInvestMentDetails.getCaStatutoryObserv());
			model.addAttribute("authSignatoryObserv", evaluateInvestMentDetails.getAuthSignatoryObserv());
			model.addAttribute("appformatObserv", evaluateInvestMentDetails.getAppformatObserv());
			
			if (evaluateInvestMentDetails.getInvCAStatutoryDate() != null) {
				try {
					DateFormat srcDf = new SimpleDateFormat("yyyy-MM-dd");
					// parse the date string into Date object
					Date date = srcDf.parse(evaluateInvestMentDetails.getInvCAStatutoryDate().toString());
					DateFormat destDf = new SimpleDateFormat("dd/MM/yyyy");
					// format the date into another format
					String dateStr = destDf.format(date);
					model.addAttribute("invCAStatutoryDate", dateStr);
				} catch (ParseException e) {
					logger.error(String.format("8888 dateFormat exception 8888 %s", e.getMessage()));
				}
			} else {
				model.addAttribute("invCAStatutoryDate", evaluateInvestMentDetails.getInvCAStatutoryDate());
			}
			// EvaluateApplication Breakup of proposed capital Investment Remarks
			model.addAttribute("invFixedAssetRemarks", evaluateInvestMentDetails.getFixedAssetRemarks());
			model.addAttribute("invPlantAndMachRemarks", evaluateInvestMentDetails.getPlantAndMachRemarks());
			model.addAttribute("invBuildingRemarks", evaluateInvestMentDetails.getBuildingRemarks());
			model.addAttribute("invLandcostRemarks", evaluateInvestMentDetails.getLandcostRemarks());

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
			
			
			

			DeptBusinessEntityDetails deptBusEntFromDb = deptBusEntRepository.findByApplicantDetailId(appId);
			model.addAttribute("authSignName", deptBusEntFromDb.getAuthorisedSignatoryName());
			model.addAttribute("GSTno", deptBusEntFromDb.getGstin());
			model.addAttribute("companyPANno", deptBusEntFromDb.getCompanyPanNo());
			model.addAttribute("isPresFormat", deptBusEntFromDb.getPresFormat());
			model.addAttribute("isDocAuthorized", deptBusEntFromDb.getDocAuthorized());
			model.addAttribute("dirDetailObserv", deptBusEntFromDb.getDirDetailsObserv());
			model.addAttribute("supprtdocObserv", deptBusEntFromDb.getSupprtdocObserv());
			model.addAttribute("gstinObserv", deptBusEntFromDb.getGstinObserv());
			model.addAttribute("panObserv", deptBusEntFromDb.getPanObserv());

			Object LCasperIEPP = additionalInterest.getProposedFCI(appId);
			model.addAttribute("LCasperIEPP", LCasperIEPP);

			DeptProposedEmploymentDetails deptProEmploymentDetails = deptPrpsEmplRepository.findByappId(appId);
			model.addAttribute("totalEmpDetailObserv", deptProEmploymentDetails.getTotalDetailObserv());
			
			
			String isfId = incentiveDetailsService.getIsfIdByApplId(appId);

			IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
			IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
			DeptIncentiveDetails deptIncDetails = deptIncRepository.findById(isfId);
			if (IncentiveDetail != null) {
				
				// pic up remarks 
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
				model.addAttribute("subDateAppObserv", deptIncDetails.getSubDateAppObserv());
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
			logger.error("InvestmentDetails exception #### " + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("Evaluate Application End ");

		return "evaluateApplicationDIC";
	}

	@RequestMapping(value = "/dicApplicationAgendaNote", method = RequestMethod.POST)
	public ModelAndView saveapplicationAgendaNotedic(
			@ModelAttribute("applicationAgendaNote") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();

		String appId = (String) session.getAttribute("appId");
		prepareAgendaNote.setAppliId(appId);
		BusinessEntityDetails businessEntityDetails = businessEntityDetailsService
				.getBusinessEntityByapplicantDetailId(appId);
		prepareAgendaNote.setCompanyName(businessEntityDetails.getBusinessEntityName());
		prepareAgendaNote.setId(appId + "AGN");
		InvestmentDetails invdtlFromDb = invDtlService.getInvestmentDetailsByapplId(appId);
		prepareAgendaNote.setInvestment(String.valueOf(invdtlFromDb.getInvFci()));
		prepareAgendaNote.setCategoryIndsType(invdtlFromDb.getInvIndType());
		ProjectDetails projectDetails = projectService.getProjectByapplicantDetailId(appId);
		prepareAgendaNote.setDistrict(projectDetails.getDistrictName());
		prepareAgendaNote.setRegion(projectDetails.getResionName());
		prepareAgendaNote.setNotes("");
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setStatus("Application Included In Agenda Note");

		String userId = (String) session.getAttribute("userId");
		try {

			if (userId != null && userId != "") {
				Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
				prepareAgendaNote.setUserId(String.valueOf(loginUser.get().getid()));// gopal
			}
			prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);
		} catch (Exception e) {
			e.getMessage();
		}

		model.addAttribute("msg", "Succefully Add Agenda Notes");
		return new ModelAndView("redirect:/evaluateApplicationDIC");
	}

	@RequestMapping(value = "/EvaluateSMEProjectDetails", method = RequestMethod.POST)
	public ModelAndView updateEvaluateProjectDetails(
			@ModelAttribute("EvaluateProjectDetails") @Validated EvaluateProjectDetails evaluateProjectDetails,
			@ModelAttribute("incentiveDeatilsData") IncentiveDetails incentiveDetails, 
			BindingResult result, Model model, HttpSession session,HttpServletRequest request) {
		List<EvaluationAuditTrail> evaluationAuditTrailList = new ArrayList<EvaluationAuditTrail>();
		String userId = (String) session.getAttribute("userId");
		EvaluateProjectDetails evaluateProjectDetailExisiting = new EvaluateProjectDetails();
		
		
	
		
		if (evaluateProjectDetails.getId() != null && !evaluateProjectDetails.getId().isEmpty()) {
			EvaluateProjectDetails evaluateProjectDetail = evaluateProjectDetailsService
					.getProjectDetailsById(evaluateProjectDetails.getId());
			if (evaluateProjectDetail.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
				/*
				 * if (!evaluateProjectDetail.getExistingProducts()
				 * .equalsIgnoreCase(evaluateProjectDetails.getExistingProducts())) {
				 * EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
				 * evaluationAuditTrail .setId(evaluateProjectDetails.getId() + "EAT" +
				 * atomicInteger.getAndIncrement());
				 * evaluationAuditTrail.setFieldsName("ExistingProducts");
				 * evaluationAuditTrail.setOldDetails(evaluateProjectDetail.getExistingProducts(
				 * ));
				 * evaluationAuditTrail.setNewDetails(evaluateProjectDetails.getExistingProducts
				 * ());
				 * evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId())
				 * ; evaluationAuditTrail.setProjId(evaluateProjectDetails.getId()); if (userId
				 * != null && !userId.isEmpty()) { Login loginUser =
				 * loginService.getLoginIdById(userId);
				 * evaluationAuditTrail.setUserId(loginUser.getId());
				 * evaluationAuditTrail.setCreatedBY(loginUser.getFirstName() + " " +
				 * loginUser.getLastName());
				 * evaluationAuditTrail.setModifyedBy(loginUser.getFirstName() + " " +
				 * loginUser.getLastName()); } evaluationAuditTrail.setModifyedDate(new Date());
				 * evaluationAuditTrail.setCreatedDate(new Date());
				 * evaluationAuditTrail.setStatus("Active");
				 * evaluationAuditTrailList.add(evaluationAuditTrail); }
				 */
				/*
				 * if (!evaluateProjectDetail.getExistingInstalledCapacity()
				 * .equalsIgnoreCase(evaluateProjectDetails.getExistingInstalledCapacity())) {
				 * EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
				 * evaluationAuditTrail .setId(evaluateProjectDetails.getId() + "EAT" +
				 * atomicInteger.getAndIncrement());
				 * evaluationAuditTrail.setFieldsName("ExistingInstalledCapacity");
				 * evaluationAuditTrail.setOldDetails(evaluateProjectDetail.
				 * getExistingInstalledCapacity());
				 * evaluationAuditTrail.setNewDetails(evaluateProjectDetails.
				 * getExistingInstalledCapacity());
				 * evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId())
				 * ; evaluationAuditTrail.setProjId(evaluateProjectDetails.getId()); if (userId
				 * != null && !userId.isEmpty()) { Login loginUser =
				 * loginService.getLoginIdById(userId);
				 * evaluationAuditTrail.setUserId(loginUser.getId());
				 * evaluationAuditTrail.setCreatedBY(loginUser.getFirstName() + " " +
				 * loginUser.getLastName());
				 * evaluationAuditTrail.setModifyedBy(loginUser.getFirstName() + " " +
				 * loginUser.getLastName()); } evaluationAuditTrail.setModifyedDate(new Date());
				 * evaluationAuditTrail.setCreatedDate(new Date());
				 * evaluationAuditTrail.setStatus("Active");
				 * evaluationAuditTrailList.add(evaluationAuditTrail); } if
				 * (!evaluateProjectDetail.getProposedProducts()
				 * .equalsIgnoreCase(evaluateProjectDetails.getProposedProducts())) {
				 * EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
				 * evaluationAuditTrail .setId(evaluateProjectDetails.getId() + "EAT" +
				 * atomicInteger.getAndIncrement());
				 * evaluationAuditTrail.setFieldsName("ProposedProducts");
				 * evaluationAuditTrail.setOldDetails(evaluateProjectDetail.getProposedProducts(
				 * ));
				 * evaluationAuditTrail.setNewDetails(evaluateProjectDetails.getProposedProducts
				 * ());
				 * evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId())
				 * ; evaluationAuditTrail.setProjId(evaluateProjectDetails.getId()); if (userId
				 * != null && !userId.isEmpty()) { Login loginUser =
				 * loginService.getLoginIdById(userId);
				 * evaluationAuditTrail.setUserId(loginUser.getId());
				 * evaluationAuditTrail.setCreatedBY(loginUser.getFirstName() + " " +
				 * loginUser.getLastName());
				 * evaluationAuditTrail.setModifyedBy(loginUser.getFirstName() + " " +
				 * loginUser.getLastName()); } evaluationAuditTrail.setModifyedDate(new Date());
				 * evaluationAuditTrail.setCreatedDate(new Date());
				 * evaluationAuditTrail.setStatus("Active");
				 * evaluationAuditTrailList.add(evaluationAuditTrail); } if
				 * (!evaluateProjectDetail.getProposedInstalledCapacity()
				 * .equalsIgnoreCase(evaluateProjectDetails.getProposedInstalledCapacity())) {
				 * EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
				 * evaluationAuditTrail .setId(evaluateProjectDetails.getId() + "EAT" +
				 * atomicInteger.getAndIncrement());
				 * evaluationAuditTrail.setFieldsName("ProposedInstalledCapacity");
				 * evaluationAuditTrail.setOldDetails(evaluateProjectDetail.
				 * getProposedInstalledCapacity());
				 * evaluationAuditTrail.setNewDetails(evaluateProjectDetails.
				 * getProposedInstalledCapacity());
				 * evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId())
				 * ; evaluationAuditTrail.setProjId(evaluateProjectDetails.getId()); if (userId
				 * != null && !userId.isEmpty()) { Login loginUser =
				 * loginService.getLoginIdById(userId);
				 * evaluationAuditTrail.setUserId(loginUser.getId());
				 * evaluationAuditTrail.setCreatedBY(loginUser.getFirstName() + " " +
				 * loginUser.getLastName());
				 * evaluationAuditTrail.setModifyedBy(loginUser.getFirstName() + " " +
				 * loginUser.getLastName()); } evaluationAuditTrail.setModifyedDate(new Date());
				 * evaluationAuditTrail.setCreatedDate(new Date());
				 * evaluationAuditTrail.setStatus("Active");
				 * evaluationAuditTrailList.add(evaluationAuditTrail); } if
				 * (!String.valueOf(evaluateProjectDetail.getExistingGrossBlock())
				 * .equalsIgnoreCase(String.valueOf(evaluateProjectDetails.getExistingGrossBlock
				 * ()))) { EvaluationAuditTrail evaluationAuditTrail = new
				 * EvaluationAuditTrail(); evaluationAuditTrail
				 * .setId(evaluateProjectDetails.getId() + "EAT" +
				 * atomicInteger.getAndIncrement());
				 * evaluationAuditTrail.setFieldsName("ExistingGrossBlock");
				 * evaluationAuditTrail.setOldDetails(String.valueOf(evaluateProjectDetail.
				 * getExistingGrossBlock()));
				 * evaluationAuditTrail.setNewDetails(String.valueOf(evaluateProjectDetails.
				 * getExistingGrossBlock()));
				 * evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId())
				 * ; evaluationAuditTrail.setProjId(evaluateProjectDetails.getId()); if (userId
				 * != null && !userId.isEmpty()) { Login loginUser =
				 * loginService.getLoginIdById(userId);
				 * evaluationAuditTrail.setUserId(loginUser.getId());
				 * evaluationAuditTrail.setCreatedBY(loginUser.getFirstName() + " " +
				 * loginUser.getLastName());
				 * evaluationAuditTrail.setModifyedBy(loginUser.getFirstName() + " " +
				 * loginUser.getLastName()); } evaluationAuditTrail.setModifyedDate(new Date());
				 * evaluationAuditTrail.setCreatedDate(new Date());
				 * evaluationAuditTrail.setStatus("Active");
				 * evaluationAuditTrailList.add(evaluationAuditTrail); } if
				 * (!String.valueOf(evaluateProjectDetail.getProposedGrossBlock())
				 * .equalsIgnoreCase(String.valueOf(evaluateProjectDetails.getProposedGrossBlock
				 * ()))) { EvaluationAuditTrail evaluationAuditTrail = new
				 * EvaluationAuditTrail(); evaluationAuditTrail
				 * .setId(evaluateProjectDetails.getId() + "EAT" +
				 * atomicInteger.getAndIncrement());
				 * evaluationAuditTrail.setFieldsName("ProposedGrossBlock");
				 * evaluationAuditTrail.setOldDetails(String.valueOf(evaluateProjectDetail.
				 * getProposedGrossBlock()));
				 * evaluationAuditTrail.setNewDetails(String.valueOf(evaluateProjectDetails.
				 * getProposedGrossBlock()));
				 * evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId())
				 * ; evaluationAuditTrail.setProjId(evaluateProjectDetails.getId()); if (userId
				 * != null && !userId.isEmpty()) { Login loginUser =
				 * loginService.getLoginIdById(userId);
				 * evaluationAuditTrail.setUserId(loginUser.getId());
				 * evaluationAuditTrail.setCreatedBY(loginUser.getFirstName() + " " +
				 * loginUser.getLastName());
				 * evaluationAuditTrail.setModifyedBy(loginUser.getFirstName() + " " +
				 * loginUser.getLastName()); } evaluationAuditTrail.setModifyedDate(new Date());
				 * evaluationAuditTrail.setCreatedDate(new Date());
				 * evaluationAuditTrail.setStatus("Active");
				 * evaluationAuditTrailList.add(evaluationAuditTrail); }
				 */
			}
			if (!evaluateProjectDetail.getFullAddress().equalsIgnoreCase(evaluateProjectDetails.getFullAddress())) {
				EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
				evaluationAuditTrail.setId(evaluateProjectDetails.getId() + "EAT" + atomicInteger.getAndIncrement());
				evaluationAuditTrail.setFieldsName("FullAddress");
				evaluationAuditTrail.setOldDetails(evaluateProjectDetail.getFullAddress());
				evaluationAuditTrail.setNewDetails(evaluateProjectDetails.getRegisteredAddr());
				evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId());
				evaluationAuditTrail.setProjId(evaluateProjectDetails.getId());
				if (userId != null && !userId.isEmpty()) {
					Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
					// gopal
					evaluationAuditTrail.setUserId(String.valueOf(loginUser.get().getid()));
					evaluationAuditTrail.setCreatedBY(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
					evaluationAuditTrail.setModifyedBy(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
				}
				evaluationAuditTrail.setModifyedDate(new Date());
				evaluationAuditTrail.setCreatedDate(new Date());
				evaluationAuditTrail.setStatus("Active");
				evaluationAuditTrailList.add(evaluationAuditTrail);
			}
			if (!evaluateProjectDetail.getDistrictName().equalsIgnoreCase(evaluateProjectDetails.getDistrictName())) {
				EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
				evaluationAuditTrail.setId(evaluateProjectDetails.getId() + "EAT" + atomicInteger.getAndIncrement());
				evaluationAuditTrail.setFieldsName("DistrictName");
				evaluationAuditTrail.setOldDetails(String.valueOf(evaluateProjectDetail.getDistrictName()));
				evaluationAuditTrail.setNewDetails(String.valueOf(evaluateProjectDetails.getDistrictName()));
				evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId());
				evaluationAuditTrail.setProjId(evaluateProjectDetails.getId());
				if (userId != null && !userId.isEmpty()) {
					Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
					// sac
					evaluationAuditTrail.setUserId(String.valueOf(loginUser.get().getid()));
					evaluationAuditTrail.setCreatedBY(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
					evaluationAuditTrail.setModifyedBy(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
				}
				evaluationAuditTrail.setModifyedDate(new Date());
				evaluationAuditTrail.setCreatedDate(new Date());
				evaluationAuditTrail.setStatus("Active");
				evaluationAuditTrailList.add(evaluationAuditTrail);
			}

			if (!evaluateProjectDetail.getResionName().equalsIgnoreCase(evaluateProjectDetails.getResionName())) {
				EvaluationAuditTrail evaluationAuditTrail = new EvaluationAuditTrail();
				evaluationAuditTrail.setId(evaluateProjectDetails.getId() + "EAT" + atomicInteger.getAndIncrement());
				evaluationAuditTrail.setFieldsName("ResionName");
				evaluationAuditTrail.setOldDetails(String.valueOf(evaluateProjectDetail.getResionName()));
				evaluationAuditTrail.setNewDetails(String.valueOf(evaluateProjectDetails.getResionName()));
				evaluationAuditTrail.setAppliId(evaluateProjectDetail.getApplicantDetailId());
				evaluationAuditTrail.setProjId(evaluateProjectDetails.getId());
				if (userId != null && !userId.isEmpty()) {
					Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);

					evaluationAuditTrail.setUserId(String.valueOf(loginUser.get().getid()));// gopal
					evaluationAuditTrail.setCreatedBY(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
					evaluationAuditTrail.setModifyedBy(loginUser.get().getFirstName() + " " + loginUser.get().getLastName());
				}
				evaluationAuditTrail.setModifyedDate(new Date());
				evaluationAuditTrail.setCreatedDate(new Date());
				evaluationAuditTrail.setStatus("Active");
				evaluationAuditTrailList.add(evaluationAuditTrail);
			}
			for (EvaluationAuditTrail bean : evaluationAuditTrailList) {
				evaluationAuditTrailService.saveEvaluationAuditTrail(bean);
			}

			evaluateProjectDetailExisiting.setId(evaluateProjectDetail.getId());
			evaluateProjectDetailExisiting.setApplicantDetailId(evaluateProjectDetail.getApplicantDetailId());
			evaluateProjectDetailExisiting.setContactPersonName(evaluateProjectDetail.getContactPersonName());
			evaluateProjectDetailExisiting.setDesignation(evaluateProjectDetail.getDesignation());
			evaluateProjectDetailExisiting.setProjectDescription(evaluateProjectDetail.getProjectDescription());
			evaluateProjectDetailExisiting.setMobileNo((evaluateProjectDetail.getMobileNo()));
			evaluateProjectDetailExisiting.setWebSiteName(evaluateProjectDetail.getWebSiteName());

			evaluateProjectDetailExisiting.setFullAddress(evaluateProjectDetails.getFullAddress());
			evaluateProjectDetailExisiting.setRegisteredAddr(evaluateProjectDetails.getRegisteredAddr());
			evaluateProjectDetailExisiting.setDistrictName(evaluateProjectDetails.getDistrictName());
			evaluateProjectDetailExisiting.setResionName(evaluateProjectDetails.getResionName());

			evaluateProjectDetailExisiting.setMandalName(evaluateProjectDetail.getMandalName());
			evaluateProjectDetailExisiting.setPinCode(evaluateProjectDetail.getPinCode());
			// evaluateProjectDetailExisiting.setRegiOrLicense(evaluateProjectDetail.getRegiOrLicense());
			evaluateProjectDetailExisiting.setNatureOfProject(evaluateProjectDetail.getNatureOfProject());

			// evaluateProjectDetailExisiting.setRegiOrLicenseFileName(evaluateProjectDetail.getRegiOrLicenseFileName());
			evaluateProjectDetailExisiting.setRegiOrLicenseFileData(evaluateProjectDetail.getRegiOrLicenseFileData());
			evaluateProjectDetailExisiting.setEnclDetProRepFileName(evaluateProjectDetail.getEnclDetProRepFileName());
			evaluateProjectDetailExisiting.setEnclDetProRepFileData(evaluateProjectDetail.getEnclDetProRepFileData());
			evaluateProjectDetailExisiting.setCaCertificateFileName(evaluateProjectDetail.getCaCertificateFileName());
			evaluateProjectDetailExisiting.setCaCertificateFileData(evaluateProjectDetail.getCaCertificateFileData());
			evaluateProjectDetailExisiting.setCharatEngFileName(evaluateProjectDetail.getCharatEngFileName());
			evaluateProjectDetailExisiting.setCharatEngFileData(evaluateProjectDetail.getCharatEngFileData());

			if (evaluateProjectDetail.getNatureOfProject().equalsIgnoreCase("NewProject")) {
				evaluateProjectDetailExisiting.setNewProject("NewProject");
				/*
				 * evaluateProjectDetailExisiting.setExistingProducts(null);
				 * evaluateProjectDetailExisiting.setExistingInstalledCapacity(null);
				 * evaluateProjectDetailExisiting.setProposedProducts(null);
				 * evaluateProjectDetailExisiting.setProposedInstalledCapacity(null);
				 * evaluateProjectDetailExisiting.setExistingGrossBlock(null);
				 * evaluateProjectDetailExisiting.setProposedGrossBlock(null);
				 */
				evaluateProjectDetailExisiting.setCaCertificateFileName(null);
				evaluateProjectDetailExisiting.setCharatEngFileName(null);
			} else {
				evaluateProjectDetailExisiting.setExpansion(evaluateProjectDetail.getExpansion());
				evaluateProjectDetailExisiting.setDiversification(evaluateProjectDetail.getDiversification());
				/*
				 * evaluateProjectDetailExisiting.setExistingProducts(evaluateProjectDetails.
				 * getExistingProducts()); evaluateProjectDetailExisiting
				 * .setExistingInstalledCapacity(evaluateProjectDetails.
				 * getExistingInstalledCapacity());
				 * evaluateProjectDetailExisiting.setProposedProducts(evaluateProjectDetails.
				 * getProposedProducts()); evaluateProjectDetailExisiting
				 * .setProposedInstalledCapacity(evaluateProjectDetails.
				 * getProposedInstalledCapacity());
				 * evaluateProjectDetailExisiting.setExistingGrossBlock(evaluateProjectDetails.
				 * getExistingGrossBlock());
				 * evaluateProjectDetailExisiting.setProposedGrossBlock(evaluateProjectDetails.
				 * getProposedGrossBlock());
				 */
			}
			evaluateProjectDetailExisiting.setCreatedBy("AdminUser");
			evaluateProjectDetailExisiting.setStatus("active");
			
			
		
			evaluateProjectDetailsService.saveEvaluationProjectDetails(evaluateProjectDetailExisiting);
			for (EvaluationAuditTrail bean : evaluationAuditTrailList) {
				evaluationAuditTrailService.saveEvaluationAuditTrail(bean);
			}
			IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
			saveEvaluateProjectDetails(evaluateProjectDetail, evaluateProjectDetails);

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

			

		}
		return new ModelAndView("redirect:/evaluateApplicationDIC");
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
	evaluateInvestMentDetails.setInvCommenceDate(evaluateProjectDetails.getInvCommenceDate());
	evaluateInvestMentDetails.setPropCommProdDate(evaluateProjectDetails.getPropCommProdDate());
	
	evaluateInvestMentDetailsService.saveEvaluateInvestMentDetails(evaluateInvestMentDetails);

}

/**
 * This method is responsible to save ProjectDetails records in
 * Dept_Project_Details table.
 */
public void saveEvaluateProjectDetails(EvaluateProjectDetails evaluateProjectDetail,
		EvaluateProjectDetails evaluateProjectDetails) {
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

	if (evaluateProjectDetail.getNatureOfProject().equalsIgnoreCase("NewProject")) {
		newEvalProjDetail.setNewProject("NewProject");
		newEvalProjDetail.setProductDetails(evaluateProjectDetails.getProductDetails());
		newEvalProjDetail.setProjectObserv(evaluateProjectDetails.getProjectObserv());
		newEvalProjDetail.setProdDetailObserv(evaluateProjectDetails.getProdDetailObserv());
		newEvalProjDetail.setRegisteredAddr(evaluateProjectDetails.getRegisteredAddr());
	} else {
		newEvalProjDetail.setExpansion(evaluateProjectDetail.getExpansion());
		newEvalProjDetail.setDiversification(evaluateProjectDetail.getDiversification());
		newEvalProjDetail.setProjectObserv(evaluateProjectDetails.getProjectObserv());
		newEvalProjDetail.setRegisteredAddr(evaluateProjectDetails.getRegisteredAddr());
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
@PostMapping("/addEvaluateCapInvestdic")
public ModelAndView addEvaluateCapInvest(
		@ModelAttribute("EvaluateProjectDetails") @Validated EvaluateProjectDetails evalProjDetails,
		BindingResult result, Model model, HttpSession session, HttpServletRequest request) {

	try {
		String appId = (String) session.getAttribute("appId");
		String evalCapInvId = appId.substring(0, appId.length() - 2) + "ECI" + atomicInteger.getAndIncrement();

		EvaluateCapitalInvestment newEvalCapInv = new EvaluateCapitalInvestment();
		newEvalCapInv.setEciItem(evalProjDetails.getEciItem());
		newEvalCapInv.setEciIsFci(evalProjDetails.getEciIsFci());
		newEvalCapInv.setEciDPRInvest(evalProjDetails.getEciDPRInvest());
		newEvalCapInv.setEciIIEPPInvest(evalProjDetails.getEciIIEPPInvest());
		newEvalCapInv.setEciPICUP_Remarks(evalProjDetails.getEciPICUP_Remarks());

		newEvalCapInv.setEciCreatedBy("ECI User");
		newEvalCapInv.setEciModifiyDate(new Date());
		newEvalCapInv.setEciCreatedDate(LocalDate.now());
		newEvalCapInv.setEciApplcId(appId);
		newEvalCapInv.setEciId(evalCapInvId);

		evalCapitalInvList.add(newEvalCapInv);

		model.addAttribute("EvaluateProjectDetails", evalProjDetails);

	} catch (Exception e) {
		logger.error(String.format("#### addEvaluateCapInvest exception $$$ %s", e.getMessage()));
	}
	common(model, request, session);
	model.addAttribute("evalCapitalInvList", evalCapitalInvList);
	return new ModelAndView("evaluateApplicationDIC");
}

/**
 * This method is responsible to delete row from the list as well as table.
 */
@PostMapping("/deleteEvaluateCapInvestdic")
public ModelAndView deleteEvaluateCapInvest(
		@ModelAttribute("EvaluateProjectDetails") @Validated EvaluateProjectDetails evalProjDetails,
		BindingResult result, Model model, @RequestParam(value = "selectedRow", required = false) int index,
		HttpSession session, HttpServletRequest request) {

	try {
		if (!evalCapitalInvList.isEmpty() && evalCapitalInvList.get(index) != null) {
			EvaluateCapitalInvestment evalCapInv = evalCapitalInvList.get(index);
			try {
				//evalCapInvService.deleteEvalCapInvById(evalCapInv.getEciId());
			} catch (Exception e) {

			}
			evalCapitalInvList.remove(index);
		}
	} catch (Exception e) {
		logger.error(String.format("#### deleteEvaluateCapInvest exception $$$ %s", e.getMessage()));
	}
	common(model, request, session);
	model.addAttribute("evalCapitalInvList", evalCapitalInvList);
	return new ModelAndView("evaluateApplicationDIC");

}
}
