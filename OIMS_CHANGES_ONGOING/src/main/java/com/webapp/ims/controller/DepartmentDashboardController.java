package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.EXIST_PROJ_LIST;
import static com.webapp.ims.exception.GlobalConstants.PROPOSED_EMPLOYMENT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.PW_INVESTMENT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.PW_INVESTMENT_LIST;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.webapp.ims.dis.model.PrepAgendaCirculateNoteDis;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicantDocument;
import com.webapp.ims.model.AvailCustomisedDetails;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.ConcernDepartment;
import com.webapp.ims.model.Department;
import com.webapp.ims.model.DeptIncentiveDetails;
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.InvBreakupCost;
import com.webapp.ims.model.InvMeansOfFinance;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.MinutesOfMeeting;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PrepAgendaCirculateNote;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.model.StateDetails;
import com.webapp.ims.repository.CirculateToDepartmentRepository;
import com.webapp.ims.repository.DPTAgendaRepository;
import com.webapp.ims.repository.DeptIncentiveDetailsRepository;
import com.webapp.ims.repository.IncentiveDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.MinutesOfMeetingRepository;
import com.webapp.ims.repository.PrepareAgendaNoteRepository;
import com.webapp.ims.repository.ProjectRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BreakupCostService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.ExistingProjectDetailsService;
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
import com.webapp.ims.service.impl.GlobalServiceUtil;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;

import net.sf.jasperreports.engine.JRException;

@Controller
public class DepartmentDashboardController {
	private final Logger logger = LoggerFactory.getLogger(DepartmentDashboardController.class);
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
	public List<ExistingProjectDetails> existProjList = new ArrayList<>();
	@Autowired
	private ExistingProjectDetailsService existProjectService;

	@Autowired
	AdditionalInterest additionalInterest;

	@Autowired
	InvestmentDetailsRepository investRepository;
	@Autowired
	MinutesOfMeetingRepository meetingRepos;
	@Autowired
	PrepareAgendaNoteService prepareAgendaNoteService;
	
	@Autowired
	private DeptIncentiveDetailsRepository deptIncRepository;

	@Autowired
	PrepareAgendaNoteRepository prepareAgendaNoteRepo;
	
	
	@Autowired
	DISPrepareAgendaNotesRepository disPrepareAgendaNotesRepository;

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
	CirculateToDepartmentRepository circulateToDepartmentRepository;

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
	DPTAgendaRepository dptAgendaRepository;

	private List<AvailCustomisedDetails> availCustomisedDetailsList = new ArrayList<AvailCustomisedDetails>();

	@Autowired
	public DepartmentDashboardController(ApplicantDetailsService appDtlService, ApplicantDocumentService appDocService,
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

	@GetMapping(value = "/viewDeptApplicationDetails")
	public ModelAndView viewDeptApplicationDetails(@RequestParam(value = "applicantId", required = false) String applId,
			HttpSession session, Model model) {
		applicantId = applId;
		session.setAttribute("appId", applicantId);

		String appStr = applId.substring(0, applId.length() - 2);
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
//			StateDetails stateDetails = stateDetailsService
//					.getStateBystateCode(Long.valueOf(businessEntityDetails.getBusinessStateName()));
			model.addAttribute("businessStateName", businessEntityDetails.getBusinessStateName());
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
			model.addAttribute("districtName", ProjectDetail.getDistrictName());
			model.addAttribute("mandalName", ProjectDetail.getMandalName());
			model.addAttribute("resionName", ProjectDetail.getResionName());
			model.addAttribute("pinCode", ProjectDetail.getPinCode());
			model.addAttribute("regiOrLicense", ProjectDetail.getRegiOrLicense());
			model.addAttribute("regiOrLicenseFileName", ProjectDetail.getRegiOrLicenseFileName());

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
		model.addAttribute("department", new Department());
		return new ModelAndView("view-all-application-details-department");

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


	@GetMapping(value = "/viewDeptApplication")
	public String viewDeptApplication(Model model) {
		logger.debug("viewDeptApplication Policy");
		return "select-policy-department";
	}
	

	@GetMapping(value = "/viewAggricultureDeptApplication")
	public String viewAggriDeptApplication(Model model) {
		logger.debug("viewDeptApplication Policy");
		return "select-Aggri_policy-department";
	}
	
	@GetMapping(value = "/viewElectricityDeptApplication")
	public String viewElectricityDeptApplication(Model model) {
		logger.debug("viewDeptApplication Policy");
		return "select-electricity_policy-department";
	}
	
	@GetMapping(value = "/viewStampDutyDeptApplication")
	public String viewStampDutyDeptApplication(Model model) {
		logger.debug("viewDeptApplication Policy");
		return "select-stampDuty_policy-department";
	}
	
	@GetMapping(value = "/viewLabourDeptApplication")
	public String viewLabourDeptApplication(Model model) {
		logger.debug("viewDeptApplication Policy");
		return "select-labour_policy-department";
	}
	

	@PostMapping(value = "/addIsfComments")
	public ModelAndView addIsfComments(@ModelAttribute("incentiveDeatilsData") @Validated IncentiveSpecificDetails isd,
			HttpSession session) {

		String role = (String) session.getAttribute("role");

		if (role.equalsIgnoreCase("ConDept0")) {

			incentiveDetailsService.updateCommentsConDept0(applicantId, isd.getIsfMandiComment());
		}

		if (role.equalsIgnoreCase("ConDept25")) {

			incentiveDetailsService.updateCommentsConDept25(applicantId, isd.getIsfElecdutyComment(),
					isd.getIsfelepodownComment());
		}
		if (role.equalsIgnoreCase("ConDept5")) {

			incentiveDetailsService.updateCommentsConDept5(applicantId, isd.getIsfStampComment(),
					isd.getIsfStampremComment(), isd.getIsfStampscstComment());

		}
		if (role.equalsIgnoreCase("ConDept4")) {

			incentiveDetailsService.updateCommentsConDept4(applicantId, isd.getIsfepfComment(),
					isd.getIsfepfaddComment(), isd.getIsfepfscComment(), isd.getIsfdifferabilComment());
		}
		if (role.equalsIgnoreCase("ConDept2")) {

			incentiveDetailsService.updateCommentsConDept2(applicantId, isd.getIsfSgstComment(),
					isd.getIsfScstComment(), isd.getIsffwComment(), isd.getIsfBplComment(), isd.getIsfcapisaComment(),
					isd.getIsfcapisComment(), isd.getIsfinfaComment(), isd.getIsfinfComment(), isd.getIsfloanComment(),
					isd.getIsfdisComment());

		}
		return new ModelAndView("redirect:/facilitiesReliefSought");

	}

	@GetMapping(value = "/facilitiesReliefSought")
	public ModelAndView facilitiesReliefSought(ModelMap model, HttpSession session) {

		String s = (String) session.getAttribute("role");
		model.addAttribute("role", s);
		String isfId = incentiveDetailsService.getIsfIdByApplId(applicantId);

		IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
		DeptIncentiveDetails deptIncDetails = deptIncRepository.findById(isfId);
		if (IncentiveDetail != null) {
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

	@GetMapping(value = "deptEvaluateApplication")
	public String deptEvaluateApplication(
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {
		logger.debug("Evaluate department Application Starts");

		final String SMALL = "Small";
		final String MEDIUM = "Medium";
		final String LARGE = "Large";
		final String MEGA = "Mega";
		final String MEGAPLUS = "Mega Plus";
		final String SUPERMEGA = "Super Mega";
		String appliId = applicantId;
		ProjectDetails ProjectDetail = projectService.getProjectByapplicantDetailId(appliId);
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

			String pergrosblock = additionalInterest.getgrossBlock(appliId);
			model.addAttribute("pergrosblock", pergrosblock);

			String regorlic = additionalInterest.getregiOrLicense(appliId);
			model.addAttribute("regorlic", regorlic);
			model.addAttribute("raiseQuery", new RaiseQuery());

		}

		// Proposed Employment Details view
		try {
			ProposedEmploymentDetails emplDetail = emplDtlService.getProposedEmploymentDetailsByappId(appliId);
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

			InvestmentDetails invDetailFromDb = invDtlService.getInvestmentDetailsByapplId(appliId);

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
				model.addAttribute("ctypeSMALL", "Yes");
			} else {
				model.addAttribute("ctypeSMALL", "No");
			}

			if (categorytype.equalsIgnoreCase(LARGE)) {
				model.addAttribute("ctypeLARGE", "Yes");
			} else {
				model.addAttribute("ctypeLARGE", "No");
			}

			if ((categorytype.equalsIgnoreCase(MEGA)) || (categorytype.equalsIgnoreCase(MEGAPLUS))) {
				model.addAttribute("ctypeMEGA", "Yes");
			} else {
				model.addAttribute("ctypeMEGA", "No");
			}

			if (categorytype.equalsIgnoreCase(SUPERMEGA)) {
				model.addAttribute("ctypeSUPERMEGA", "Yes");
			} else {
				model.addAttribute("ctypeSUPERMEGA", "No");
			}

			String phsWsAply = invDetailFromDb.getPwApply();

			if (phsWsAply.equalsIgnoreCase("Yes")) {
				model.addAttribute("phsWsAply", "Yes");
			} else {
				model.addAttribute("phsWsAply", "No");
			}

			Object LCasperIEPP = additionalInterest.getProposedFCI(appliId);
			model.addAttribute("LCasperIEPP", LCasperIEPP);

			String isfId = incentiveDetailsService.getIsfIdByApplId(appliId);

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
			logger.error("InvestmentDetails exception #### " + e.getMessage());
			e.printStackTrace();
		}
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		model.addAttribute("prepAgenNotes", prepareAgendaNotes1.getNotes());
		model.addAttribute("flag", "true");
		if (ProjectDetail.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			model.addAttribute("flag1", "false1");
		}
		List<PrepareAgendaNotes> prepareAgendaNotesCirculatedList = new ArrayList<PrepareAgendaNotes>();

		String userid = (String) session.getAttribute("userId");
		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
				.findPrepAgendaNotesListByUserId(userid);
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Circulated")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Large")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesCirculatedList.add(list);
				}

			}
		}

		model.addAttribute("prepareAgendaNotesCirculatedList", prepareAgendaNotesCirculatedList);

		logger.debug("deptEvaluateApplication End ");

		return "view-circulated-agenda-note";
	}

	@RequestMapping(value = "/deptApplicationAgendaNote", method = RequestMethod.POST)
	public ModelAndView deptApplicationAgendaNote(
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
		prepareAgendaNote.setStatus("Ready");

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
		return new ModelAndView("redirect:/evaluateApplication");
	}

	@RequestMapping(value = "/circulateViewAgendaNote", method = RequestMethod.GET)
	public ModelAndView circulateViewAgendaNote(Model model, HttpSession session) {

		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedMegaList = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedLargeList = new ArrayList<>();

		List<PrepAgendaCirculateNote> prepcircNoteMegaList = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepcircNoteLargeList = new ArrayList<>();

		List<Object> prepareAgendaNotesMegaList = prepareAgendaNoteRepo.findAllMegaPrepareAgendaNotebyStatus();

		Object[] row1;
		ObjectMapper mapper1 = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote1 = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesMegaList) {
			prepcircNote1 = new PrepAgendaCirculateNote();
			row1 = (Object[]) object;
			//System.out.println(row1.length);
			HashMap<String, Object> map = new HashMap<>();
			//System.out.println("Element " + Arrays.toString(row1));

			if (row1[0] != null)
				map.put("appliId", row1[0].toString());
			if (row1[1] != null)
				map.put("status", row1[1]);
			if (row1[2] != null)
				map.put("submissionDate", row1[2].toString());
			if (row1[3] != null)
				map.put("acsapprovalDate", row1[3].toString());
			if (row1[4] != null)
				map.put("deptName", row1[4].toString());
			if (row1[5] != null)
				map.put("noteReportStatus", row1[5].toString());
			if (row1[6] != null)
				map.put("fileId", row1[6].toString());

			prepcircNote1 = mapper1.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteMegaList.add(prepcircNote1);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteMegaList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Commercial Tax Department")) {
				prepareAgendaNotesCirculatedMegaList.add(list);
			}

		}

		List<Object> prepareAgendaNotesLargeList = prepareAgendaNoteRepo.findAllLargePrepareAgendaNotebyStatus();
		Object[] row;
		ObjectMapper mapper = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesLargeList) {
			prepcircNote = new PrepAgendaCirculateNote();
			row = (Object[]) object;
			//System.out.println(row.length);
			HashMap<String, Object> map = new HashMap<>();
		//	System.out.println("Element " + Arrays.toString(row));

			if (row[0] != null)
				map.put("appliId", row[0].toString());
			if (row[1] != null)
				map.put("status", row[1]);
			if (row[2] != null)
				map.put("submissionDate", row[2].toString());
			if (row[3] != null)
				map.put("approvalDate", row[3].toString());
			if (row[4] != null)
				map.put("deptName", row[4].toString());
			if (row[5] != null)
				map.put("noteReportStatus", row[5].toString());
			if (row[6] != null)
				map.put("fileId", row[6].toString());

			prepcircNote = mapper.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteLargeList.add(prepcircNote);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteLargeList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Commercial Tax Department")) {
				prepareAgendaNotesCirculatedLargeList.add(list);
			}

		}
		model.addAttribute("prepareAgendaNotesCirculatedMegaList", prepareAgendaNotesCirculatedMegaList);
		model.addAttribute("prepareAgendaNotesCirculatedLargeList", prepareAgendaNotesCirculatedLargeList);
		return new ModelAndView("view-circulated-agenda-note");
	}
	
	@RequestMapping(value = "/circulateViewAgendaNoteDis", method = RequestMethod.GET)
	public ModelAndView circulateViewAgendaNoteDisb(Model model, HttpSession session) {

		List<PrepAgendaCirculateNoteDis> prepareAgendaNotesCirculatedMegaListDis = new ArrayList<>();
		List<PrepAgendaCirculateNoteDis> prepareAgendaNotesCirculatedLargeListDis = new ArrayList<>();

		List<PrepAgendaCirculateNoteDis> prepcircNoteMegaListDis = new ArrayList<>();
		List<PrepAgendaCirculateNoteDis> prepcircNoteLargeListDis = new ArrayList<>();

		List<Object> prepareAgendaNotesMegaListDis = disPrepareAgendaNotesRepository.findAllDisMegaPrepareAgendaNotebyStatus();

		Object[] row1;
		ObjectMapper mapper1 = new ObjectMapper();
		PrepAgendaCirculateNoteDis prepcircNote1 = new PrepAgendaCirculateNoteDis();
		for (Object object : prepareAgendaNotesMegaListDis) {
			prepcircNote1 = new PrepAgendaCirculateNoteDis();
			row1 = (Object[]) object;
			//System.out.println(row1.length);
			HashMap<String, Object> map = new HashMap<>();
			//System.out.println("Element " + Arrays.toString(row1));

			if (row1[0] != null)
				map.put("appliId", row1[0].toString());
			if (row1[1] != null)
				map.put("status", row1[1]);
			if (row1[2] != null)
				map.put("submissionDate", row1[2].toString());
			if (row1[3] != null)
				map.put("acsapprovalDate", row1[3].toString());
			if (row1[4] != null)
				map.put("deptName", row1[4].toString());
			if (row1[5] != null)
				map.put("noteReportStatus", row1[5].toString());
			if (row1[6] != null)
				map.put("fileId", row1[6].toString());

			prepcircNote1 = mapper1.convertValue(map, PrepAgendaCirculateNoteDis.class);
			prepcircNoteMegaListDis.add(prepcircNote1);
		}

		for (PrepAgendaCirculateNoteDis list : prepcircNoteMegaListDis) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Commercial Tax Department")) {
				prepareAgendaNotesCirculatedMegaListDis.add(list);
			}

		}

		List<Object> prepareAgendaNotesLargeListDis = disPrepareAgendaNotesRepository.findAllDisLargePrepareAgendaNotebyStatus();
		Object[] row;
		ObjectMapper mapper = new ObjectMapper();
		PrepAgendaCirculateNoteDis prepcircNote = new PrepAgendaCirculateNoteDis();
		for (Object object : prepareAgendaNotesLargeListDis) {
			prepcircNote = new PrepAgendaCirculateNoteDis();
			row = (Object[]) object;
			//System.out.println(row.length);
			HashMap<String, Object> map = new HashMap<>();
		//	System.out.println("Element " + Arrays.toString(row));

			if (row[0] != null)
				map.put("appliId", row[0].toString());
			if (row[1] != null)
				map.put("status", row[1]);
			if (row[2] != null)
				map.put("submissionDate", row[2].toString());
			if (row[3] != null)
				map.put("approvalDate", row[3].toString());
			if (row[4] != null)
				map.put("deptName", row[4].toString());
			if (row[5] != null)
				map.put("noteReportStatus", row[5].toString());
			if (row[6] != null)
				map.put("fileId", row[6].toString());

			prepcircNote = mapper.convertValue(map, PrepAgendaCirculateNoteDis.class);
			prepcircNoteLargeListDis.add(prepcircNote);
		}

		for (PrepAgendaCirculateNoteDis list : prepcircNoteLargeListDis) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Commercial Tax Department")) {
				prepareAgendaNotesCirculatedLargeListDis.add(list);
			}

		}
		model.addAttribute("prepareAgendaNotesCirculatedMegaList", prepareAgendaNotesCirculatedMegaListDis);
		model.addAttribute("prepareAgendaNotesCirculatedLargeList", prepareAgendaNotesCirculatedLargeListDis);
		return new ModelAndView("/Disbursement/view-circulated-agenda-note_dis");
	}
	
	
	@RequestMapping(value = "/circulateAggricultureViewAgendaNote", method = RequestMethod.GET)
	public ModelAndView circulateAggriViewAgendaNote(Model model, HttpSession session) {

		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedMegaListAggriculture = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedLargeListAggriculture = new ArrayList<>();

		List<PrepAgendaCirculateNote> prepcircNoteMegaList = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepcircNoteLargeList = new ArrayList<>();

		List<Object> prepareAgendaNotesMegaListAggriculture = prepareAgendaNoteRepo.findAllMegaPrepareAgendaNotebyStatus();

		Object[] row1;
		ObjectMapper mapper1 = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote1 = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesMegaListAggriculture) {
			prepcircNote1 = new PrepAgendaCirculateNote();
			row1 = (Object[]) object;
			//System.out.println(row1.length);
			HashMap<String, Object> map = new HashMap<>();
			//System.out.println("Element " + Arrays.toString(row1));

			if (row1[0] != null)
				map.put("appliId", row1[0].toString());
			if (row1[1] != null)
				map.put("status", row1[1]);
			if (row1[2] != null)
				map.put("submissionDate", row1[2].toString());
			if (row1[3] != null)
				map.put("acsapprovalDate", row1[3].toString());
			if (row1[4] != null)
				map.put("deptName", row1[4].toString());
			if (row1[5] != null)
				map.put("noteReportStatus", row1[5].toString());
			if (row1[6] != null)
				map.put("fileId", row1[6].toString());

			prepcircNote1 = mapper1.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteMegaList.add(prepcircNote1);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteMegaList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Agricultural Department")) {
				prepareAgendaNotesCirculatedMegaListAggriculture.add(list);
			}

		}

		List<Object> prepareAgendaNotesLargeList = prepareAgendaNoteRepo.findAllLargePrepareAgendaNotebyStatus();
		Object[] row;
		ObjectMapper mapper = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesLargeList) {
			prepcircNote = new PrepAgendaCirculateNote();
			row = (Object[]) object;
			//System.out.println(row.length);
			HashMap<String, Object> map = new HashMap<>();
		//	System.out.println("Element " + Arrays.toString(row));

			if (row[0] != null)
				map.put("appliId", row[0].toString());
			if (row[1] != null)
				map.put("status", row[1]);
			if (row[2] != null)
				map.put("submissionDate", row[2].toString());
			if (row[3] != null)
				map.put("approvalDate", row[3].toString());
			if (row[4] != null)
				map.put("deptName", row[4].toString());
			if (row[5] != null)
				map.put("noteReportStatus", row[5].toString());
			if (row[6] != null)
				map.put("fileId", row[6].toString());

			prepcircNote = mapper.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteLargeList.add(prepcircNote);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteLargeList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Agricultural Department")) {
				prepareAgendaNotesCirculatedLargeListAggriculture.add(list);
			}

		}
		model.addAttribute("prepareAgendaNotesCirculatedMegaList", prepareAgendaNotesCirculatedMegaListAggriculture);
		model.addAttribute("prepareAgendaNotesCirculatedLargeList", prepareAgendaNotesCirculatedLargeListAggriculture);
		return new ModelAndView("view-aggriculture-circulated-agenda-note");
	}
	
	@RequestMapping(value = "/circulateAggricultureViewAgendaNoteDis", method = RequestMethod.GET)
	public ModelAndView circulateAggriViewAgendaNoteDis(Model model, HttpSession session) {

		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedMegaListAggriculture = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedLargeListAggriculture = new ArrayList<>();

		List<PrepAgendaCirculateNote> prepcircNoteMegaList = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepcircNoteLargeList = new ArrayList<>();

		List<Object> prepareAgendaNotesMegaListAggriculture = disPrepareAgendaNotesRepository.findAllDisMegaPrepareAgendaNotebyStatus();

		Object[] row1;
		ObjectMapper mapper1 = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote1 = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesMegaListAggriculture) {
			prepcircNote1 = new PrepAgendaCirculateNote();
			row1 = (Object[]) object;
			//System.out.println(row1.length);
			HashMap<String, Object> map = new HashMap<>();
			//System.out.println("Element " + Arrays.toString(row1));

			if (row1[0] != null)
				map.put("appliId", row1[0].toString());
			if (row1[1] != null)
				map.put("status", row1[1]);
			if (row1[2] != null)
				map.put("submissionDate", row1[2].toString());
			if (row1[3] != null)
				map.put("acsapprovalDate", row1[3].toString());
			if (row1[4] != null)
				map.put("deptName", row1[4].toString());
			if (row1[5] != null)
				map.put("noteReportStatus", row1[5].toString());
			if (row1[6] != null)
				map.put("fileId", row1[6].toString());

			prepcircNote1 = mapper1.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteMegaList.add(prepcircNote1);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteMegaList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Agricultural Department")) {
				prepareAgendaNotesCirculatedMegaListAggriculture.add(list);
			}

		}

		List<Object> prepareAgendaNotesLargeList = disPrepareAgendaNotesRepository.findAllDisLargePrepareAgendaNotebyStatus();
		Object[] row;
		ObjectMapper mapper = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesLargeList) {
			prepcircNote = new PrepAgendaCirculateNote();
			row = (Object[]) object;
			//System.out.println(row.length);
			HashMap<String, Object> map = new HashMap<>();
		//	System.out.println("Element " + Arrays.toString(row));

			if (row[0] != null)
				map.put("appliId", row[0].toString());
			if (row[1] != null)
				map.put("status", row[1]);
			if (row[2] != null)
				map.put("submissionDate", row[2].toString());
			if (row[3] != null)
				map.put("approvalDate", row[3].toString());
			if (row[4] != null)
				map.put("deptName", row[4].toString());
			if (row[5] != null)
				map.put("noteReportStatus", row[5].toString());
			if (row[6] != null)
				map.put("fileId", row[6].toString());

			prepcircNote = mapper.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteLargeList.add(prepcircNote);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteLargeList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Agricultural Department")) {
				prepareAgendaNotesCirculatedLargeListAggriculture.add(list);
			}

		}
		model.addAttribute("prepareAgendaNotesCirculatedMegaList", prepareAgendaNotesCirculatedMegaListAggriculture);
		model.addAttribute("prepareAgendaNotesCirculatedLargeList", prepareAgendaNotesCirculatedLargeListAggriculture);
		return new ModelAndView("/Disbursement/view-aggriculture-circulated-agenda-note_dis");
	}

	@RequestMapping(value = "/circulateElectricityViewAgendaNote", method = RequestMethod.GET)
	public ModelAndView circulateElectricityViewAgendaNote(Model model, HttpSession session) {

		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedMegaListElectricity = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedLargeListElectricity = new ArrayList<>();

		List<PrepAgendaCirculateNote> prepcircNoteMegaList = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepcircNoteLargeList = new ArrayList<>();

		List<Object> prepareAgendaNotesMegaListElectricity = prepareAgendaNoteRepo.findAllMegaPrepareAgendaNotebyStatus();

		Object[] row1;
		ObjectMapper mapper1 = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote1 = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesMegaListElectricity) {
			prepcircNote1 = new PrepAgendaCirculateNote();
			row1 = (Object[]) object;
			//System.out.println(row1.length);
			HashMap<String, Object> map = new HashMap<>();
			//System.out.println("Element " + Arrays.toString(row1));

			if (row1[0] != null)
				map.put("appliId", row1[0].toString());
			if (row1[1] != null)
				map.put("status", row1[1]);
			if (row1[2] != null)
				map.put("submissionDate", row1[2].toString());
			if (row1[3] != null)
				map.put("acsapprovalDate", row1[3].toString());
			if (row1[4] != null)
				map.put("deptName", row1[4].toString());
			if (row1[5] != null)
				map.put("noteReportStatus", row1[5].toString());
			if (row1[6] != null)
				map.put("fileId", row1[6].toString());

			prepcircNote1 = mapper1.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteMegaList.add(prepcircNote1);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteMegaList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Electricity")) {
				prepareAgendaNotesCirculatedMegaListElectricity.add(list);
			}

		}

		List<Object> prepareAgendaNotesLargeListElectricity = prepareAgendaNoteRepo.findAllLargePrepareAgendaNotebyStatus();
		Object[] row;
		ObjectMapper mapper = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesLargeListElectricity) {
			prepcircNote = new PrepAgendaCirculateNote();
			row = (Object[]) object;
			//System.out.println(row.length);
			HashMap<String, Object> map = new HashMap<>();
		//	System.out.println("Element " + Arrays.toString(row));

			if (row[0] != null)
				map.put("appliId", row[0].toString());
			if (row[1] != null)
				map.put("status", row[1]);
			if (row[2] != null)
				map.put("submissionDate", row[2].toString());
			if (row[3] != null)
				map.put("approvalDate", row[3].toString());
			if (row[4] != null)
				map.put("deptName", row[4].toString());
			if (row[5] != null)
				map.put("noteReportStatus", row[5].toString());
			if (row[6] != null)
				map.put("fileId", row[6].toString());

			prepcircNote = mapper.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteLargeList.add(prepcircNote);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteLargeList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Electricity")) {
				prepareAgendaNotesCirculatedLargeListElectricity.add(list);
			}

		}
		model.addAttribute("prepareAgendaNotesCirculatedMegaList", prepareAgendaNotesCirculatedMegaListElectricity);
		model.addAttribute("prepareAgendaNotesCirculatedLargeList", prepareAgendaNotesCirculatedLargeListElectricity);
		return new ModelAndView("view-electricity-circulated-agenda-note");
	}
	
	@RequestMapping(value = "/circulateElectricityViewAgendaNoteDis", method = RequestMethod.GET)
	public ModelAndView circulateElectricityViewAgendaNoteDis(Model model, HttpSession session) {

		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedMegaListElectricity = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedLargeListElectricity = new ArrayList<>();

		List<PrepAgendaCirculateNote> prepcircNoteMegaList = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepcircNoteLargeList = new ArrayList<>();

		List<Object> prepareAgendaNotesMegaListElectricity = disPrepareAgendaNotesRepository.findAllDisMegaPrepareAgendaNotebyStatus();

		Object[] row1;
		ObjectMapper mapper1 = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote1 = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesMegaListElectricity) {
			prepcircNote1 = new PrepAgendaCirculateNote();
			row1 = (Object[]) object;
			//System.out.println(row1.length);
			HashMap<String, Object> map = new HashMap<>();
			//System.out.println("Element " + Arrays.toString(row1));

			if (row1[0] != null)
				map.put("appliId", row1[0].toString());
			if (row1[1] != null)
				map.put("status", row1[1]);
			if (row1[2] != null)
				map.put("submissionDate", row1[2].toString());
			if (row1[3] != null)
				map.put("acsapprovalDate", row1[3].toString());
			if (row1[4] != null)
				map.put("deptName", row1[4].toString());
			if (row1[5] != null)
				map.put("noteReportStatus", row1[5].toString());
			if (row1[6] != null)
				map.put("fileId", row1[6].toString());

			prepcircNote1 = mapper1.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteMegaList.add(prepcircNote1);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteMegaList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Electricity")) {
				prepareAgendaNotesCirculatedMegaListElectricity.add(list);
			}

		}

		List<Object> prepareAgendaNotesLargeListElectricity = disPrepareAgendaNotesRepository.findAllDisLargePrepareAgendaNotebyStatus();
		Object[] row;
		ObjectMapper mapper = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesLargeListElectricity) {
			prepcircNote = new PrepAgendaCirculateNote();
			row = (Object[]) object;
			//System.out.println(row.length);
			HashMap<String, Object> map = new HashMap<>();
		//	System.out.println("Element " + Arrays.toString(row));

			if (row[0] != null)
				map.put("appliId", row[0].toString());
			if (row[1] != null)
				map.put("status", row[1]);
			if (row[2] != null)
				map.put("submissionDate", row[2].toString());
			if (row[3] != null)
				map.put("approvalDate", row[3].toString());
			if (row[4] != null)
				map.put("deptName", row[4].toString());
			if (row[5] != null)
				map.put("noteReportStatus", row[5].toString());
			if (row[6] != null)
				map.put("fileId", row[6].toString());

			prepcircNote = mapper.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteLargeList.add(prepcircNote);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteLargeList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Electricity")) {
				prepareAgendaNotesCirculatedLargeListElectricity.add(list);
			}

		}
		model.addAttribute("prepareAgendaNotesCirculatedMegaList", prepareAgendaNotesCirculatedMegaListElectricity);
		model.addAttribute("prepareAgendaNotesCirculatedLargeList", prepareAgendaNotesCirculatedLargeListElectricity);
		return new ModelAndView("/Disbursement/view-electricity-circulated-agenda-note_dis");
	}
	
	@RequestMapping(value = "/circulateStampDutyViewAgendaNote", method = RequestMethod.GET)
	public ModelAndView circulateStampDutyViewAgendaNote(Model model, HttpSession session) {

		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedMegaListStamp = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedLargeListStamp = new ArrayList<>();

		List<PrepAgendaCirculateNote> prepcircNoteMegaList = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepcircNoteLargeList = new ArrayList<>();

		List<Object> prepareAgendaNotesMegaListStamp = prepareAgendaNoteRepo.findAllMegaPrepareAgendaNotebyStatus();

		Object[] row1;
		ObjectMapper mapper1 = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote1 = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesMegaListStamp) {
			prepcircNote1 = new PrepAgendaCirculateNote();
			row1 = (Object[]) object;
			//System.out.println(row1.length);
			HashMap<String, Object> map = new HashMap<>();
			//System.out.println("Element " + Arrays.toString(row1));

			if (row1[0] != null)
				map.put("appliId", row1[0].toString());
			if (row1[1] != null)
				map.put("status", row1[1]);
			if (row1[2] != null)
				map.put("submissionDate", row1[2].toString());
			if (row1[3] != null)
				map.put("acsapprovalDate", row1[3].toString());
			if (row1[4] != null)
				map.put("deptName", row1[4].toString());
			if (row1[5] != null)
				map.put("noteReportStatus", row1[5].toString());
			if (row1[6] != null)
				map.put("fileId", row1[6].toString());

			prepcircNote1 = mapper1.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteMegaList.add(prepcircNote1);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteMegaList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Stamp Duty")) {
				prepareAgendaNotesCirculatedMegaListStamp.add(list);
			}

		}

		List<Object> prepareAgendaNotesLargeListStamp = prepareAgendaNoteRepo.findAllLargePrepareAgendaNotebyStatus();
		Object[] row;
		ObjectMapper mapper = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesLargeListStamp) {
			prepcircNote = new PrepAgendaCirculateNote();
			row = (Object[]) object;
			//System.out.println(row.length);
			HashMap<String, Object> map = new HashMap<>();
		//	System.out.println("Element " + Arrays.toString(row));

			if (row[0] != null)
				map.put("appliId", row[0].toString());
			if (row[1] != null)
				map.put("status", row[1]);
			if (row[2] != null)
				map.put("submissionDate", row[2].toString());
			if (row[3] != null)
				map.put("approvalDate", row[3].toString());
			if (row[4] != null)
				map.put("deptName", row[4].toString());
			if (row[5] != null)
				map.put("noteReportStatus", row[5].toString());
			if (row[6] != null)
				map.put("fileId", row[6].toString());

			prepcircNote = mapper.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteLargeList.add(prepcircNote);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteLargeList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Stamp Duty")) {
				prepareAgendaNotesCirculatedLargeListStamp.add(list);
			}

		}
		model.addAttribute("prepareAgendaNotesCirculatedMegaList", prepareAgendaNotesCirculatedMegaListStamp);
		model.addAttribute("prepareAgendaNotesCirculatedLargeList", prepareAgendaNotesCirculatedLargeListStamp);
		return new ModelAndView("view-stampDuty-circulated-agenda-note");
	}
	
	@RequestMapping(value = "/circulateStampDutyViewAgendaNoteDis", method = RequestMethod.GET)
	public ModelAndView circulateStampDutyViewAgendaNoteDis(Model model, HttpSession session) {

		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedMegaListStamp = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedLargeListStamp = new ArrayList<>();

		List<PrepAgendaCirculateNote> prepcircNoteMegaList = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepcircNoteLargeList = new ArrayList<>();

		List<Object> prepareAgendaNotesMegaListStamp = disPrepareAgendaNotesRepository.findAllDisMegaPrepareAgendaNotebyStatus();

		Object[] row1;
		ObjectMapper mapper1 = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote1 = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesMegaListStamp) {
			prepcircNote1 = new PrepAgendaCirculateNote();
			row1 = (Object[]) object;
			//System.out.println(row1.length);
			HashMap<String, Object> map = new HashMap<>();
			//System.out.println("Element " + Arrays.toString(row1));

			if (row1[0] != null)
				map.put("appliId", row1[0].toString());
			if (row1[1] != null)
				map.put("status", row1[1]);
			if (row1[2] != null)
				map.put("submissionDate", row1[2].toString());
			if (row1[3] != null)
				map.put("acsapprovalDate", row1[3].toString());
			if (row1[4] != null)
				map.put("deptName", row1[4].toString());
			if (row1[5] != null)
				map.put("noteReportStatus", row1[5].toString());
			if (row1[6] != null)
				map.put("fileId", row1[6].toString());

			prepcircNote1 = mapper1.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteMegaList.add(prepcircNote1);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteMegaList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Stamp Duty")) {
				prepareAgendaNotesCirculatedMegaListStamp.add(list);
			}

		}

		List<Object> prepareAgendaNotesLargeListStamp = disPrepareAgendaNotesRepository.findAllDisLargePrepareAgendaNotebyStatus();
		Object[] row;
		ObjectMapper mapper = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesLargeListStamp) {
			prepcircNote = new PrepAgendaCirculateNote();
			row = (Object[]) object;
			//System.out.println(row.length);
			HashMap<String, Object> map = new HashMap<>();
		//	System.out.println("Element " + Arrays.toString(row));

			if (row[0] != null)
				map.put("appliId", row[0].toString());
			if (row[1] != null)
				map.put("status", row[1]);
			if (row[2] != null)
				map.put("submissionDate", row[2].toString());
			if (row[3] != null)
				map.put("approvalDate", row[3].toString());
			if (row[4] != null)
				map.put("deptName", row[4].toString());
			if (row[5] != null)
				map.put("noteReportStatus", row[5].toString());
			if (row[6] != null)
				map.put("fileId", row[6].toString());

			prepcircNote = mapper.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteLargeList.add(prepcircNote);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteLargeList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Stamp Duty")) {
				prepareAgendaNotesCirculatedLargeListStamp.add(list);
			}

		}
		model.addAttribute("prepareAgendaNotesCirculatedMegaList", prepareAgendaNotesCirculatedMegaListStamp);
		model.addAttribute("prepareAgendaNotesCirculatedLargeList", prepareAgendaNotesCirculatedLargeListStamp);
		return new ModelAndView("/Disbursement/view-stampDuty-circulated-agenda-note_dis");
	}

	@RequestMapping(value = "/circulateLabourViewAgendaNote", method = RequestMethod.GET)
	public ModelAndView circulateLabourViewAgendaNote(Model model, HttpSession session) {

		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedMegaListLabour = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedLargeListLabour = new ArrayList<>();

		List<PrepAgendaCirculateNote> prepcircNoteMegaList = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepcircNoteLargeList = new ArrayList<>();

		List<Object> prepareAgendaNotesMegaListLabour = prepareAgendaNoteRepo.findAllMegaPrepareAgendaNotebyStatus();

		Object[] row1;
		ObjectMapper mapper1 = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote1 = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesMegaListLabour) {
			prepcircNote1 = new PrepAgendaCirculateNote();
			row1 = (Object[]) object;
			//System.out.println(row1.length);
			HashMap<String, Object> map = new HashMap<>();
			//System.out.println("Element " + Arrays.toString(row1));

			if (row1[0] != null)
				map.put("appliId", row1[0].toString());
			if (row1[1] != null)
				map.put("status", row1[1]);
			if (row1[2] != null)
				map.put("submissionDate", row1[2].toString());
			if (row1[3] != null)
				map.put("acsapprovalDate", row1[3].toString());
			if (row1[4] != null)
				map.put("deptName", row1[4].toString());
			if (row1[5] != null)
				map.put("noteReportStatus", row1[5].toString());
			if (row1[6] != null)
				map.put("fileId", row1[6].toString());

			prepcircNote1 = mapper1.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteMegaList.add(prepcircNote1);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteMegaList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Labour department")) {
				prepareAgendaNotesCirculatedMegaListLabour.add(list);
			}

		}

		List<Object> prepareAgendaNotesLargeListLabour = prepareAgendaNoteRepo.findAllLargePrepareAgendaNotebyStatus();
		Object[] row;
		ObjectMapper mapper = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesLargeListLabour) {
			prepcircNote = new PrepAgendaCirculateNote();
			row = (Object[]) object;
			//System.out.println(row.length);
			HashMap<String, Object> map = new HashMap<>();
		//	System.out.println("Element " + Arrays.toString(row));

			if (row[0] != null)
				map.put("appliId", row[0].toString());
			if (row[1] != null)
				map.put("status", row[1]);
			if (row[2] != null)
				map.put("submissionDate", row[2].toString());
			if (row[3] != null)
				map.put("approvalDate", row[3].toString());
			if (row[4] != null)
				map.put("deptName", row[4].toString());
			if (row[5] != null)
				map.put("noteReportStatus", row[5].toString());
			if (row[6] != null)
				map.put("fileId", row[6].toString());

			prepcircNote = mapper.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteLargeList.add(prepcircNote);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteLargeList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Labour department")) {
				prepareAgendaNotesCirculatedLargeListLabour.add(list);
			}

		}
		model.addAttribute("prepareAgendaNotesCirculatedMegaList", prepareAgendaNotesCirculatedMegaListLabour);
		model.addAttribute("prepareAgendaNotesCirculatedLargeList", prepareAgendaNotesCirculatedLargeListLabour);
		return new ModelAndView("view-labour-circulated-agenda-note");
	}
	
	@RequestMapping(value = "/circulateLabourViewAgendaNoteDis", method = RequestMethod.GET)
	public ModelAndView circulateLabourViewAgendaNoteDis(Model model, HttpSession session) {

		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedMegaListLabour = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepareAgendaNotesCirculatedLargeListLabour = new ArrayList<>();

		List<PrepAgendaCirculateNote> prepcircNoteMegaList = new ArrayList<>();
		List<PrepAgendaCirculateNote> prepcircNoteLargeList = new ArrayList<>();

		List<Object> prepareAgendaNotesMegaListLabour = disPrepareAgendaNotesRepository.findAllDisMegaPrepareAgendaNotebyStatus();

		Object[] row1;
		ObjectMapper mapper1 = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote1 = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesMegaListLabour) {
			prepcircNote1 = new PrepAgendaCirculateNote();
			row1 = (Object[]) object;
			//System.out.println(row1.length);
			HashMap<String, Object> map = new HashMap<>();
			//System.out.println("Element " + Arrays.toString(row1));

			if (row1[0] != null)
				map.put("appliId", row1[0].toString());
			if (row1[1] != null)
				map.put("status", row1[1]);
			if (row1[2] != null)
				map.put("submissionDate", row1[2].toString());
			if (row1[3] != null)
				map.put("acsapprovalDate", row1[3].toString());
			if (row1[4] != null)
				map.put("deptName", row1[4].toString());
			if (row1[5] != null)
				map.put("noteReportStatus", row1[5].toString());
			if (row1[6] != null)
				map.put("fileId", row1[6].toString());

			prepcircNote1 = mapper1.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteMegaList.add(prepcircNote1);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteMegaList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Labour department")) {
				prepareAgendaNotesCirculatedMegaListLabour.add(list);
			}

		}

		List<Object> prepareAgendaNotesLargeListLabour = disPrepareAgendaNotesRepository.findAllDisLargePrepareAgendaNotebyStatus();
		Object[] row;
		ObjectMapper mapper = new ObjectMapper();
		PrepAgendaCirculateNote prepcircNote = new PrepAgendaCirculateNote();
		for (Object object : prepareAgendaNotesLargeListLabour) {
			prepcircNote = new PrepAgendaCirculateNote();
			row = (Object[]) object;
			//System.out.println(row.length);
			HashMap<String, Object> map = new HashMap<>();
		//	System.out.println("Element " + Arrays.toString(row));

			if (row[0] != null)
				map.put("appliId", row[0].toString());
			if (row[1] != null)
				map.put("status", row[1]);
			if (row[2] != null)
				map.put("submissionDate", row[2].toString());
			if (row[3] != null)
				map.put("approvalDate", row[3].toString());
			if (row[4] != null)
				map.put("deptName", row[4].toString());
			if (row[5] != null)
				map.put("noteReportStatus", row[5].toString());
			if (row[6] != null)
				map.put("fileId", row[6].toString());

			prepcircNote = mapper.convertValue(map, PrepAgendaCirculateNote.class);
			prepcircNoteLargeList.add(prepcircNote);
		}

		for (PrepAgendaCirculateNote list : prepcircNoteLargeList) {
			if (list.getNoteReportStatus().equalsIgnoreCase("Circulate Agenda Report")
					&& list.getDeptName().equalsIgnoreCase("Labour department")) {
				prepareAgendaNotesCirculatedLargeListLabour.add(list);
			}

		}
		model.addAttribute("prepareAgendaNotesCirculatedMegaList", prepareAgendaNotesCirculatedMegaListLabour);
		model.addAttribute("prepareAgendaNotesCirculatedLargeList", prepareAgendaNotesCirculatedLargeListLabour);
		return new ModelAndView("/Disbursement/view-labour-circulated-agenda-note_dis");
	}
	
	@GetMapping(value = "/downloadAgendaReportLarge")
	public void downloadAgendaReportLarge(@RequestParam(value = "appId", required = false) String appId,
			HttpServletResponse response, Model model, HttpSession session) throws JRException, IOException {

		List<byte[]> output = circulateToDepartmentRepository.getAgendaReportData(appId);

		// byte[] output = agendareport.createAgendaReportListLarge(model, session);
		if (output != null) {
			downloadReport("report.pdf", output.get(0), response);
		}
	}

	@RequestMapping(value = "/downloadSupportCD", method = RequestMethod.POST)
	public void downloadSupportCD(@ModelAttribute("downloadSupportCD") @Validated PrepareAgendaNotes prepareAgendaNotes,
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

	public void downloadReport(String fileName, byte[] fileData, HttpServletResponse response) {

		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		response.setHeader("Content-Type", "application/pdf");

		// try-with-resources statement
		try (InputStream is = new ByteArrayInputStream(fileData)) {
			try {
				IOUtils.copy(is, response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
	@GetMapping("/viewLoc")
	public ModelAndView viewLoc(ModelMap model, HttpSession session) 
	{
		List<String[]> finalList = new ArrayList<>();

		List<Tuple> list = investRepository.getAllLocDetailsByAppIdTuple();
		for (Tuple tuple : list) {
			if (tuple.get(6).toString().equals("Commercial Tax Department")) {
				String[] str = new String[10];
				str[0] = tuple.get(0).toString();
				str[1] = tuple.get(1).toString();
				str[2] = tuple.get(2).toString();
				str[3] = tuple.get(3).toString();
				str[4] = tuple.get(4).toString();
				str[5] = tuple.get(5).toString();
				str[6] = tuple.get(6).toString();
				String temp = dptAgendaRepository.getStatusByDptActId(tuple.get(0).toString());
				str[7] = temp == null ? "Pending for Evaluation" : temp;
				finalList.add(str);
			}
		}
		model.addAttribute("investmentDetailsList", finalList);
		return new ModelAndView("viewloc");

	}
	@GetMapping("/viewLocAggriculture")
	public ModelAndView viewAggriLoc(ModelMap model, HttpSession session) 
	{
		List<String[]> finalList = new ArrayList<>();

		List<Tuple> list = investRepository.getAllLocDetailsByAppIdTuple();
		for (Tuple tuple : list) {
			if (tuple.get(6).toString().equals("Agricultural Department")) {
				String[] str = new String[10];
				str[0] = tuple.get(0).toString();
				str[1] = tuple.get(1).toString();
				str[2] = tuple.get(2).toString();
				str[3] = tuple.get(3).toString();
				str[4] = tuple.get(4).toString();
				str[5] = tuple.get(5).toString();
				str[6] = tuple.get(6).toString();
				String temp = dptAgendaRepository.getStatusByDptActId(tuple.get(0).toString());
				str[7] = temp == null ? "Pending for Evaluation" : temp;
				finalList.add(str);
			}
		}
		model.addAttribute("investmentDetailsList", finalList);
		return new ModelAndView("viewlocAggriculture");

	}
	
	@GetMapping("/viewLocLabour")
	public ModelAndView viewLocLabour(ModelMap model, HttpSession session) 
	{
		List<String[]> finalList = new ArrayList<>();

		List<Tuple> list = investRepository.getAllLocDetailsByAppIdTuple();
		for (Tuple tuple : list) {
			if (tuple.get(6).toString().equals("Labour department")) {
				String[] str = new String[10];
				str[0] = tuple.get(0).toString();
				str[1] = tuple.get(1).toString();
				str[2] = tuple.get(2).toString();
				str[3] = tuple.get(3).toString();
				str[4] = tuple.get(4).toString();
				str[5] = tuple.get(5).toString();
				str[6] = tuple.get(6).toString();
				String temp = dptAgendaRepository.getStatusByDptActId(tuple.get(0).toString());
				str[7] = temp == null ? "Pending for Evaluation" : temp;
				finalList.add(str);
			}
		}
		model.addAttribute("investmentDetailsList", finalList);
		return new ModelAndView("viewlocLabour");

	}

	@GetMapping("/viewLocElectricity")
	public ModelAndView viewElectricityLoc(ModelMap model, HttpSession session) 
	{
		List<String[]> finalList = new ArrayList<>();

		List<Tuple> list = investRepository.getAllLocDetailsByAppIdTuple();
		for (Tuple tuple : list) {
			if (tuple.get(6).toString().equals("Electricity")) {
				String[] str = new String[10];
				str[0] = tuple.get(0).toString();
				str[1] = tuple.get(1).toString();
				str[2] = tuple.get(2).toString();
				str[3] = tuple.get(3).toString();
				str[4] = tuple.get(4).toString();
				str[5] = tuple.get(5).toString();
				str[6] = tuple.get(6).toString();
				String temp = dptAgendaRepository.getStatusByDptActId(tuple.get(0).toString());
				str[7] = temp == null ? "Pending for Evaluation" : temp;
				finalList.add(str);
			}
		}
		model.addAttribute("investmentDetailsList", finalList);
		return new ModelAndView("viewlocElectricity");

	}
	
	@GetMapping("/viewLocStampDuty")
	public ModelAndView viewStampDutyLoc(ModelMap model, HttpSession session) 
	{
		List<String[]> finalList = new ArrayList<>();

		List<Tuple> list = investRepository.getAllLocDetailsByAppIdTuple();
		for (Tuple tuple : list) {
			if (tuple.get(6).toString().equals("Stamp Duty")) {
				String[] str = new String[10];
				str[0] = tuple.get(0).toString();
				str[1] = tuple.get(1).toString();
				str[2] = tuple.get(2).toString();
				str[3] = tuple.get(3).toString();
				str[4] = tuple.get(4).toString();
				str[5] = tuple.get(5).toString();
				str[6] = tuple.get(6).toString();
				String temp = dptAgendaRepository.getStatusByDptActId(tuple.get(0).toString());
				str[7] = temp == null ? "Pending for Evaluation" : temp;
				finalList.add(str);
			}
		}
		model.addAttribute("investmentDetailsList", finalList);
		return new ModelAndView("viewlocStampDuty");

	}

	
	
	@RequestMapping(value = "/savegenerateLoc1", method = RequestMethod.GET)
	public ModelAndView savegenerateLoc1(@RequestParam(value = "applicantId", required = false) String applId,
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
						model.addAttribute(PW_INVESTMENT_LIST, pwInvViewList);
						model.addAttribute("viewInvestDetails", invDetailFromDb);
						model.addAttribute(PW_INVESTMENT_DETAILS, new PhaseWiseInvestmentDetails());

					}

				} else {
					model.addAttribute("viewInvestDetails", new InvestmentDetails());
					model.addAttribute(PW_INVESTMENT_DETAILS, new PhaseWiseInvestmentDetails());
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

		// Go's No.
		List<MinutesOfMeeting> meeting = meetingRepos.getBygosAppID(applId);

		for (MinutesOfMeeting mom1 : meeting) {
			if (mom1.getGosNo() != null && !mom1.getGosNo().isEmpty())
				model.addAttribute("meeting", mom1.getGosNo());
		}

		return new ModelAndView("savedLoc");

	}
}
