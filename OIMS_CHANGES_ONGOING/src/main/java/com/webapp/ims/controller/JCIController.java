package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.EXIST_PROJ_LIST;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.uam.service.TblUsersService;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicantDocument;
import com.webapp.ims.model.AvailCustomisedDetails;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.ConcernDepartment;
import com.webapp.ims.model.Department;
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.model.StateDetails;
import com.webapp.ims.repository.IncentiveDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
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

@Controller
public class JCIController {
	private final Logger logger = LoggerFactory.getLogger(JCIController.class);
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

	private List<AvailCustomisedDetails> availCustomisedDetailsList = new ArrayList<AvailCustomisedDetails>();

	@Autowired
	public JCIController(ApplicantDetailsService appDtlService, ApplicantDocumentService appDocService,
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

	/*
	 * @GetMapping(value = "/viewDICApplicationDetails") public ModelAndView
	 * viewDICApplicationDetails(@RequestParam(value = "applicantId", required =
	 * false) String applId, HttpSession session, Model model) { applicantId =
	 * applId; session.setAttribute("appId", applicantId);
	 * 
	 * String appStr = applId.substring(0, 14); businId = appStr + "B1"; projId =
	 * appStr + "P1"; investId = appStr + "I1"; propId = appStr + "PE1"; isfId =
	 * appStr + "INC1";
	 * 
	 * model.addAttribute("concernDepartment", new ConcernDepartment());
	 * ApplicantDetails applicantDetail =
	 * applicantDetailsService.getApplicantDetailsByAppId(applId); ApplicantDocument
	 * applicantDocument =
	 * fileStorageService.getApplicantDocumentByDocAppId(applId); if
	 * (applicantDetail != null) { model.addAttribute("appFirstName",
	 * applicantDetail.getAppFirstName()); model.addAttribute("appMiddleName",
	 * applicantDetail.getAppMiddleName()); model.addAttribute("appLastName",
	 * applicantDetail.getAppLastName()); model.addAttribute("appEmailId",
	 * applicantDetail.getAppEmailId()); model.addAttribute("appMobileNo",
	 * applicantDetail.getAppMobileNo()); model.addAttribute("appPhoneNo",
	 * applicantDetail.getAppPhoneNo()); model.addAttribute("appDesignation",
	 * applicantDetail.getAppDesignation()); model.addAttribute("gender",
	 * applicantDetail.getGender()); model.addAttribute("appAadharNo",
	 * applicantDetail.getAppAadharNo()); model.addAttribute("appPancardNo",
	 * applicantDetail.getAppPancardNo()); model.addAttribute("appAddressLine1",
	 * applicantDetail.getAppAddressLine1()); model.addAttribute("appAddressLine2",
	 * applicantDetail.getAppAddressLine2()); model.addAttribute("appCountry",
	 * applicantDetail.getAppCountry()); model.addAttribute("appState",
	 * applicantDetail.getAppState()); model.addAttribute("appDistrict",
	 * applicantDetail.getAppDistrict()); model.addAttribute("appPinCode",
	 * applicantDetail.getAppPinCode());
	 * 
	 * if (applicantDocument != null) {
	 * 
	 * byte[] encodeBase64 = Base64.encodeBase64(applicantDocument.getData());
	 * String base64Encoded = null; try { if (applicantDocument.getFileName() !=
	 * null && applicantDocument.getFileName() != "" &&
	 * !applicantDocument.getFileName().isEmpty()) { base64Encoded = new
	 * String(encodeBase64, "UTF-8"); }
	 * 
	 * } catch (UnsupportedEncodingException e) { e.printStackTrace(); }
	 * applicantDetail.setBase64imageFile(base64Encoded);
	 * applicantDetail.setFileName(applicantDocument.getFileName()); }
	 * model.addAttribute("applicantDetail", applicantDetail); }
	 * 
	 * Business Entity Details
	 * 
	 * BusinessEntityDetails businessEntityDetails =
	 * businessService.getBusinDetById(businId); if (businessEntityDetails != null)
	 * {
	 * 
	 * model.addAttribute("businessEntityName",
	 * businessEntityDetails.getBusinessEntityName());
	 * model.addAttribute("businessEntityType",
	 * businessEntityDetails.getBusinessEntityType());
	 * model.addAttribute("authorisedSignatoryName",
	 * businessEntityDetails.getAuthorisedSignatoryName());
	 * model.addAttribute("businessDesignation",
	 * businessEntityDetails.getBusinessDesignation());
	 * model.addAttribute("emailId", businessEntityDetails.getEmailId());
	 * model.addAttribute("mobileNumber", businessEntityDetails.getMobileNumber());
	 * model.addAttribute("phoneNo", businessEntityDetails.getPhoneNo());
	 * model.addAttribute("fax", businessEntityDetails.getFax());
	 * model.addAttribute("businessAddress",
	 * businessEntityDetails.getBusinessAddress());
	 * model.addAttribute("businessCountryName",
	 * businessEntityDetails.getBusinessCountryName()); StateDetails stateDetails =
	 * stateDetailsService
	 * .getStateBystateCode(Long.valueOf(businessEntityDetails.getBusinessStateName(
	 * ))); model.addAttribute("businessStateName", stateDetails.getStateName());
	 * model.addAttribute("businessDistrictName",
	 * businessEntityDetails.getBusinessDistrictName());
	 * model.addAttribute("PinCode", businessEntityDetails.getPinCode());
	 * model.addAttribute("yearEstablishment",
	 * businessEntityDetails.getYearEstablishment()); model.addAttribute("gstin",
	 * businessEntityDetails.getGstin()); model.addAttribute("cin",
	 * businessEntityDetails.getCin()); model.addAttribute("companyPanNo",
	 * businessEntityDetails.getCompanyPanNo()); model.addAttribute("moaDocFname",
	 * businessEntityDetails.getMoaDocFname());
	 * model.addAttribute("regisAttacDocFname",
	 * businessEntityDetails.getRegisAttacDocFname());
	 * model.addAttribute("bodDocFname", businessEntityDetails.getBodDocFname());
	 * model.addAttribute("indusAffidaDocFname",
	 * businessEntityDetails.getIndusAffidaDocFname());
	 * model.addAttribute("annexureiaformat",
	 * businessEntityDetails.getAnnexureiaformat()); List<ProprietorDetails>
	 * proprietorDetailsList = proprietorService.findAllByBusinessId(businId);
	 * model.addAttribute("ProprietorDetailsList", proprietorDetailsList);
	 * 
	 * }
	 * 
	 * Project Details
	 * 
	 * ProjectDetails ProjectDetail = projectService.getProjDetById(projId); if
	 * (ProjectDetail != null) { model.addAttribute("contactPersonName",
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
	 * ProjectDetail.getRegiOrLicenseFileName());
	 * 
	 * model.addAttribute("natureOfProject", ProjectDetail.getNatureOfProject());
	 * model.addAttribute("expansion", ProjectDetail.getExpansion());
	 * model.addAttribute("diversification", ProjectDetail.getDiversification());
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
	 * ProjectDetail.getCharatEngFileName()); }
	 * 
	 * Investment Details
	 * 
	 * InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(investId);
	 * if (invdtlFromDb != null) { model.addAttribute("categorytype",
	 * invdtlFromDb.getInvIndType()); model.addAttribute("invFci",
	 * invdtlFromDb.getInvFci()); model.addAttribute("invTotalProjCost",
	 * invdtlFromDb.getInvTotalProjCost()); model.addAttribute("invLandCost",
	 * invdtlFromDb.getInvLandCost()); model.addAttribute("invBuildingCost",
	 * invdtlFromDb.getInvBuildingCost()); model.addAttribute("invPlantAndMachCost",
	 * invdtlFromDb.getInvPlantAndMachCost()); model.addAttribute("invOtherCost",
	 * invdtlFromDb.getInvOtherCost()); model.addAttribute("invCommenceDate",
	 * invdtlFromDb.getInvCommenceDate()); model.addAttribute("propCommProdDate",
	 * invdtlFromDb.getPropCommProdDate()); model.addAttribute("invFileName",
	 * invdtlFromDb.getInvFilename()); List<PhaseWiseInvestmentDetails>
	 * pwInvListDromDb = pwInvestDs
	 * .getPwInvDetailListById(invdtlFromDb.getInvId()); if (pwInvListDromDb.size()
	 * > 0) { model.addAttribute("pwApply", "Yes"); } else {
	 * model.addAttribute("pwApply", "No"); }
	 * 
	 * model.addAttribute("pwInvList", pwInvListDromDb); }
	 * 
	 * Proposed Employment Details
	 * 
	 * ProposedEmploymentDetails proposedEmploymentDetail =
	 * proposedEmploymentDetailsService.getPropEmpById(propId); if
	 * (proposedEmploymentDetail != null) {
	 * 
	 * if (proposedEmploymentDetail != null) { List<SkilledUnSkilledEmployemnt>
	 * skilledUnSkilledEmployemntslist = proposedEmploymentDetail
	 * .getSkilledUnSkilledEmployemnt(); totalSkilledAndUnSkilledEmploment(model,
	 * skilledUnSkilledEmployemntslist);
	 * model.addAttribute("proposedEmploymentDetails",
	 * skilledUnSkilledEmployemntslist); } }
	 * 
	 * Incentive Specific Details IncentiveDetails IncentiveDetail =
	 * incentiveDetailsService.getIncentiveisfById(isfId); if (IncentiveDetail !=
	 * null) { model.addAttribute("ISF_Claim_Reim",
	 * IncentiveDetail.getISF_Claim_Reim()); model.addAttribute("ISF_Reim_SCST",
	 * IncentiveDetail.getISF_Reim_SCST()); model.addAttribute("ISF_Reim_FW",
	 * IncentiveDetail.getISF_Reim_FW()); model.addAttribute("ISF_Reim_BPLW",
	 * IncentiveDetail.getISF_Reim_BPLW()); model.addAttribute("ISF_Ttl_SGST_Reim",
	 * IncentiveDetail.getISF_Ttl_SGST_Reim());
	 * 
	 * model.addAttribute("ISF_Stamp_Duty_EX",
	 * IncentiveDetail.getISF_Stamp_Duty_EX());
	 * model.addAttribute("ISF_Additonal_Stamp_Duty_EX",
	 * IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
	 * model.addAttribute("ISF_Amt_Stamp_Duty_Reim",
	 * IncentiveDetail.getISF_Amt_Stamp_Duty_Reim());
	 * model.addAttribute("ISF_Ttl_Stamp_Duty_EX",
	 * IncentiveDetail.getISF_Ttl_Stamp_Duty_EX());
	 * 
	 * model.addAttribute("ISF_Epf_Reim_UW", IncentiveDetail.getISF_Epf_Reim_UW());
	 * model.addAttribute("ISF_Add_Epf_Reim_SkUkW",
	 * IncentiveDetail.getISF_Add_Epf_Reim_SkUkW());
	 * model.addAttribute("ISF_Add_Epf_Reim_DIVSCSTF",
	 * IncentiveDetail.getISF_Add_Epf_Reim_DIVSCSTF());
	 * model.addAttribute("ISF_Ttl_EPF_Reim",
	 * IncentiveDetail.getISF_Ttl_EPF_Reim());
	 * 
	 * model.addAttribute("ISF_Cis", IncentiveDetail.getISF_Cis());
	 * model.addAttribute("ISF_ACI_Subsidy_Indus",
	 * IncentiveDetail.getISF_ACI_Subsidy_Indus());
	 * model.addAttribute("ISF_Infra_Int_Subsidy",
	 * IncentiveDetail.getISF_Infra_Int_Subsidy());
	 * model.addAttribute("ISF_AII_Subsidy_DIVSCSTF",
	 * IncentiveDetail.getISF_AII_Subsidy_DIVSCSTF());
	 * model.addAttribute("ISF_Loan_Subsidy",
	 * IncentiveDetail.getISF_Loan_Subsidy());
	 * model.addAttribute("ISF_Total_Int_Subsidy",
	 * IncentiveDetail.getISF_Total_Int_Subsidy());
	 * 
	 * model.addAttribute("ISF_Tax_Credit_Reim",
	 * IncentiveDetail.getISF_Tax_Credit_Reim());
	 * model.addAttribute("ISF_EX_E_Duty", IncentiveDetail.getISF_EX_E_Duty());
	 * model.addAttribute("ISF_EX_E_Duty_PC",
	 * IncentiveDetail.getISF_EX_E_Duty_PC());
	 * model.addAttribute("ISF_EX_Mandee_Fee",
	 * IncentiveDetail.getISF_EX_Mandee_Fee());
	 * model.addAttribute("ISF_Indus_Payroll_Asst",
	 * IncentiveDetail.getISF_Indus_Payroll_Asst());
	 * model.addAttribute("Total_Other_Incentive",
	 * IncentiveDetail.getTotal_Other_Incentive());
	 * model.addAttribute("isfCustIncDocName",
	 * IncentiveDetail.getIsfCustIncDocName()); model.addAttribute("othAddRequest1",
	 * IncentiveDetail.getOthAddRequest1());
	 * 
	 * try { Long isf_Claim_Reim = IncentiveDetail.getISF_Ttl_SGST_Reim() == null ?
	 * 0 : IncentiveDetail.getISF_Ttl_SGST_Reim(); Long isf_Reim_SCST =
	 * IncentiveDetail.getISF_Ttl_Stamp_Duty_EX() == null ? 0 :
	 * IncentiveDetail.getISF_Ttl_Stamp_Duty_EX(); Long isf_EX_E_Duty_PC =
	 * IncentiveDetail.getISF_EX_E_Duty_PC() == null ? 0 :
	 * IncentiveDetail.getISF_EX_E_Duty_PC(); Long isf_EX_Mandee_Fee =
	 * IncentiveDetail.getISF_Ttl_EPF_Reim() == null ? 0 :
	 * IncentiveDetail.getISF_Ttl_EPF_Reim(); Long isf_Reim_BPLW =
	 * IncentiveDetail.getISF_Total_Int_Subsidy() == null ? 0 :
	 * IncentiveDetail.getISF_Total_Int_Subsidy(); Long isf_Reim_FW =
	 * IncentiveDetail.getTotal_Other_Incentive() == null ? 0 :
	 * IncentiveDetail.getTotal_Other_Incentive(); Long total = isf_Claim_Reim +
	 * isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW +
	 * isf_Reim_FW; model.addAttribute("total", total); } catch (Exception e) {
	 * logger.error("total  exception **** " + e.getMessage()); e.printStackTrace();
	 * }
	 * 
	 * 
	 * if (invdtlFromDb.getInvIndType() != null &&
	 * !invdtlFromDb.getInvIndType().isEmpty()) { if
	 * (invdtlFromDb.getInvIndType().equalsIgnoreCase("Super Mega")) {
	 * model.addAttribute("hideCustamList", ""); } else {
	 * model.addAttribute("hideCustamList", "hideCustamList"); } }
	 * 
	 * List<AvailCustomisedDetails> availCustomisedDetailsList =
	 * availCustmisedDetailsService .findAllByAvaCustId(IncentiveDetail.getId()); if
	 * (availCustomisedDetailsList.size() > 0) {
	 * model.addAttribute("ISF_Cstm_Inc_Status", "Yes"); } else {
	 * model.addAttribute("ISF_Cstm_Inc_Status", "No"); }
	 * model.addAttribute("department", new Department()); //
	 * model.addAttribute("aggregatequantumbenifit", aggregatequantumbenifit);
	 * model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList);
	 * 
	 * }
	 * 
	 * return new ModelAndView("view-all-application-details-dic");
	 * 
	 * }
	 */

	@GetMapping(value = "/viewApplicationDetailsSMEJCI")
	public ModelAndView viewApplicationDetailsSMEJCI(
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

		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);
		
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
			/*
			 * List<PhaseWiseInvestmentDetails> pwInvListDromDb =
			 * pwInvestDs.getPwInvDetailListById(invdtlFromDb.getInvId()); if
			 * (pwInvListDromDb.size() > 0) { model.addAttribute("pwApply", "Yes"); } else {
			 * model.addAttribute("pwApply", "No"); }
			 * 
			 * model.addAttribute("pwInvList", pwInvListDromDb);
			 */
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
		return new ModelAndView("view_all_application_detailsSMEJCI");

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

	@GetMapping(value = "/JCIApplicationForLoc")
	public String applicationForLocSMEJCI(ModelMap model, HttpSession session) {
		logger.debug("Applications JCI SME For LOC");
		List<Object> investmentDetailsList = investRepository.getAllDetailsSMEByAppId();
		model.addAttribute("investmentDetailsList", investmentDetailsList);
		return "applicationsForLocSMEJCI";
	}

	@GetMapping(value = "/selectJCIApplicationPolicy")
	public String selectJCIApplicationPolicy(Model model) {
		logger.debug("Select JCI Policy");
		return "selectJCIPolicy";
	}

	/*
	 * @GetMapping(value = "/dicgenerateLoc") public String generateLocdic(ModelMap
	 * model, HttpSession session) { logger.debug("generateLoc For DIC");
	 * 
	 * List<PrepareAgendaNotes> prepareAgendaNotesLists = new
	 * ArrayList<PrepareAgendaNotes>(); String userid = (String)
	 * session.getAttribute("userId"); List<PrepareAgendaNotes>
	 * prepareAgendaNotesList = prepareAgendaNoteService
	 * .findPrepAgendaNotesListByUserId(userid); if (prepareAgendaNotesList.size() >
	 * 0) { for (PrepareAgendaNotes list : prepareAgendaNotesList) { if
	 * (list.getStatus().equalsIgnoreCase("CommitteeByDIC")) {
	 * prepareAgendaNotesLists.add(list); } } }
	 * model.addAttribute("prepareAgendaNotesLists", prepareAgendaNotesLists);
	 * 
	 * return "generateLocDIC"; }
	 */

	/*
	 * @RequestMapping(value = "/savegenerateLocDIC", method = RequestMethod.GET)
	 * public ModelAndView savegenerateLocDIC(Model model, HttpSession session,
	 * 
	 * @RequestParam(value = "applicantId", required = false) String applicantId) {
	 * 
	 * PrepareAgendaNotes prepareAgendaNotes = new PrepareAgendaNotes(); // String
	 * appId = (String) session.getAttribute("appId"); PrepareAgendaNotes
	 * prepareAgendaNote =
	 * prepareAgendaNoteService.getPrepareByAppliId(applicantId);
	 * prepareAgendaNotes.setAppliId(prepareAgendaNote.getAppliId());
	 * prepareAgendaNotes.setCompanyName(prepareAgendaNote.getCompanyName());
	 * prepareAgendaNotes.setId(prepareAgendaNote.getId());
	 * prepareAgendaNotes.setInvestment(prepareAgendaNote.getInvestment());
	 * prepareAgendaNotes.setCategoryIndsType(prepareAgendaNote.getCategoryIndsType(
	 * )); prepareAgendaNotes.setNotes(prepareAgendaNote.getNotes());
	 * prepareAgendaNotes.setCreatedBY(prepareAgendaNote.getCreatedBY());
	 * prepareAgendaNotes.setDistrict(prepareAgendaNote.getDistrict());
	 * prepareAgendaNotes.setRegion(prepareAgendaNote.getRegion());
	 * prepareAgendaNotes.setStatus("CompletedByDIC");
	 * prepareAgendaNotes.setUserId(prepareAgendaNote.getUserId());
	 * prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNotes);
	 * 
	 * model.addAttribute("msg", "Succefully Complete Agenda Notes");
	 * 
	 * return new ModelAndView("redirect:/generateLocEvaluationDIC?applicantId=" +
	 * applicantId); }
	 */

	/*
	 * @RequestMapping(value = "/rejectLocDIC", method = RequestMethod.GET) public
	 * ModelAndView saverejectLocdic(Model model, HttpSession session) {
	 * 
	 * PrepareAgendaNotes prepareAgendaNotes = new PrepareAgendaNotes(); String
	 * appId = (String) session.getAttribute("appId"); PrepareAgendaNotes
	 * prepareAgendaNote = prepareAgendaNoteService.getPrepareByAppliId(appId);
	 * prepareAgendaNotes.setAppliId(prepareAgendaNote.getAppliId());
	 * prepareAgendaNotes.setCompanyName(prepareAgendaNote.getCompanyName());
	 * prepareAgendaNotes.setId(prepareAgendaNote.getId());
	 * prepareAgendaNotes.setInvestment(prepareAgendaNote.getInvestment());
	 * prepareAgendaNotes.setCategoryIndsType(prepareAgendaNote.getCategoryIndsType(
	 * )); prepareAgendaNotes.setNotes(prepareAgendaNote.getNotes());
	 * prepareAgendaNotes.setCreatedBY(prepareAgendaNote.getCreatedBY());
	 * prepareAgendaNotes.setDistrict(prepareAgendaNote.getDistrict());
	 * prepareAgendaNotes.setRegion(prepareAgendaNote.getRegion());
	 * prepareAgendaNotes.setStatus("Reject");
	 * prepareAgendaNotes.setUserId(prepareAgendaNote.getUserId());
	 * prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNotes);
	 * 
	 * model.addAttribute("msg", "Succefully Reject Notes");
	 * 
	 * return new ModelAndView("redirect:/dicgenerateLoc"); }
	 */

	/*
	 * @GetMapping(value = "/generateLocEvaluationDIC") public ModelAndView
	 * generateLocEvaluationDIC(@RequestParam(value = "applicantId", required =
	 * false) String applId, HttpSession session, Model model) { String investId =
	 * null; String propEmplId = null; investId =
	 * invDtlService.getInvDetailIdByapplId(applId); propEmplId =
	 * emplDtlService.getPropEmplIdByapplId(applId); applicantId = applId;
	 * session.setAttribute("appId", applicantId); try { // Authorised Signatory
	 * Details view try { ApplicantDetails appDtlFromDb =
	 * appDtlService.getApplicantDetailsByAppId(applId); ApplicantDocument applDoc =
	 * null; if (appDocService.getApplicantDocumentByDocAppId(applId) != null) {
	 * applDoc = appDocService.getApplicantDocumentByDocAppId(applId); }
	 * 
	 * if (appDtlFromDb != null) { if (applDoc != null) { try { byte[] encodeBase64
	 * = Base64.encodeBase64(applDoc.getData()); String base64Encoded = null; if
	 * (applDoc.getFileName() != null && applDoc.getFileName() != "" &&
	 * !applDoc.getFileName().isEmpty()) { base64Encoded = new String(encodeBase64,
	 * "UTF-8"); } appDtlFromDb.setBase64imageFile(base64Encoded);
	 * appDtlFromDb.setFileName(applDoc.getFileName()); } catch
	 * (UnsupportedEncodingException e) { e.printStackTrace(); } } }
	 * model.addAttribute("viewSignatoryDetail", appDtlFromDb); } catch (Exception
	 * e) { logger.error("viewSignatoryDetail exception &&&&& " + e.getMessage());
	 * e.printStackTrace(); }
	 * 
	 * Business Entity Details
	 * 
	 * BusinessEntityDetails businessEntityDetails =
	 * businessService.getBusinessEntityByapplicantDetailId(applId); if
	 * (businessEntityDetails != null) {
	 * 
	 * model.addAttribute("businessEntityName",
	 * businessEntityDetails.getBusinessEntityName());
	 * model.addAttribute("businessEntityType",
	 * businessEntityDetails.getBusinessEntityType());
	 * model.addAttribute("authorisedSignatoryName",
	 * businessEntityDetails.getAuthorisedSignatoryName());
	 * model.addAttribute("businessDesignation",
	 * businessEntityDetails.getBusinessDesignation());
	 * model.addAttribute("emailId", businessEntityDetails.getEmailId());
	 * model.addAttribute("mobileNumber", businessEntityDetails.getMobileNumber());
	 * model.addAttribute("fax", businessEntityDetails.getFax());
	 * model.addAttribute("businessAddress",
	 * businessEntityDetails.getBusinessAddress());
	 * model.addAttribute("businessCountryName",
	 * businessEntityDetails.getBusinessCountryName()); StateDetails stateDetails =
	 * stateDetailsService
	 * .getStateBystateCode(Long.valueOf(businessEntityDetails.getBusinessStateName(
	 * ))); model.addAttribute("businessStateName", stateDetails.getStateName()); //
	 * model.addAttribute("businessStateName", //
	 * businessEntityDetails.getBusinessStateName());
	 * model.addAttribute("businessDistrictName",
	 * businessEntityDetails.getBusinessDistrictName());
	 * model.addAttribute("PinCode", businessEntityDetails.getPinCode());
	 * model.addAttribute("yearEstablishment",
	 * businessEntityDetails.getYearEstablishment()); model.addAttribute("gstin",
	 * businessEntityDetails.getGstin()); model.addAttribute("cin",
	 * businessEntityDetails.getCin()); model.addAttribute("companyPanNo",
	 * businessEntityDetails.getCompanyPanNo()); model.addAttribute("moaDocFname",
	 * businessEntityDetails.getMoaDocFname());
	 * model.addAttribute("regisAttacDocFname",
	 * businessEntityDetails.getRegisAttacDocFname());
	 * model.addAttribute("bodDocFname", businessEntityDetails.getBodDocFname());
	 * model.addAttribute("indusAffidaDocFname",
	 * businessEntityDetails.getIndusAffidaDocFname());
	 * model.addAttribute("annexureiaformat",
	 * businessEntityDetails.getAnnexureiaformat()); List<ProprietorDetails>
	 * proprietorDetailsList = proprietorService
	 * .findAllByBusinessId(businessEntityDetails.getId());
	 * model.addAttribute("ProprietorDetailsList", proprietorDetailsList);
	 * 
	 * }
	 * 
	 * Project Details
	 * 
	 * ProjectDetails ProjectDetail =
	 * projectService.getProjectByapplicantDetailId(applId); if (ProjectDetail !=
	 * null) { model.addAttribute("contactPersonName",
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
	 * ProjectDetail.getCharatEngFileName()); }
	 * 
	 * // Investment Details view try {
	 * 
	 * InvestmentDetails invDetailFromDb =
	 * invDtlService.getInvestmentDetailsById(investId); if (invDetailFromDb !=
	 * null) { if ((!invDetailFromDb.getApplId().isEmpty() &&
	 * invDetailFromDb.getApplId() != null)) { List<InvBreakupCost> brkupViewList =
	 * brkupService.getInvBreakupCostListById(investId); List<InvMeansOfFinance>
	 * mofViewList = mofService.getInvMeansOfFinanceListById(investId);
	 * List<PhaseWiseInvestmentDetails> pwInvViewList = pwInvDtlService
	 * .getPwInvDetailListById(investId);
	 * 
	 * int sum = 0;
	 * 
	 * // Loop through the array to calculate sum of elements for (int i = 0; i < 1;
	 * i++) { for (PhaseWiseInvestmentDetails phaseWiseInvestmentDetails :
	 * pwInvViewList) { Long pwFci = phaseWiseInvestmentDetails.getPwFci(); sum =
	 * (int) (sum + pwFci); model.addAttribute("PWFCI", sum);
	 * model.addAttribute("PWpropsdtcoprd",
	 * phaseWiseInvestmentDetails.getPwPropProductDate()); } }
	 * 
	 * String categorytype = invDetailFromDb.getInvIndType(); Long pnm =
	 * invDetailFromDb.getInvPlantAndMachCost(); Long fci =
	 * invDetailFromDb.getInvFci();
	 * 
	 * model.addAttribute("pnm", pnm); model.addAttribute("fci", fci); Float fci1 =
	 * (float) (fci / 10000000);
	 * 
	 * model.addAttribute("fci1", fci1); model.addAttribute("categorytype",
	 * categorytype);
	 * 
	 * model.addAttribute("brkupList", brkupViewList); model.addAttribute("mofList",
	 * mofViewList); model.addAttribute("pwInvList", pwInvViewList);
	 * model.addAttribute("viewInvestDetails", invDetailFromDb);
	 * model.addAttribute("phaseWiseInvestmentDetails", new
	 * PhaseWiseInvestmentDetails());
	 * 
	 * }
	 * 
	 * } else { model.addAttribute("viewInvestDetails", new InvestmentDetails());
	 * model.addAttribute("phaseWiseInvestmentDetails", new
	 * PhaseWiseInvestmentDetails()); } } catch (Exception e) {
	 * logger.error("InvestmentDetails exception #### " + e.getMessage());
	 * e.printStackTrace(); }
	 * 
	 * // Proposed Employment Details view try { if (!propEmplId.isEmpty()) {
	 * ProposedEmploymentDetails emplDetail =
	 * emplDtlService.getPropEmpById(propEmplId); if (emplDetail != null) {
	 * List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = emplDetail
	 * .getSkilledUnSkilledEmployemnt();
	 * model.addAttribute("proposedEmploymentDetails", emplDetail);
	 * skillUnskillEmplList.clear();
	 * skillUnskillEmplList.addAll(skilledUnSkilledEmployemntslist);
	 * model.addAttribute("skilledUnSkilledEmployemntList", skillUnskillEmplList);
	 * totalSkilledAndUnSkilledEmploment(model);
	 * model.addAttribute("proposedEmploymentDetails", new
	 * ProposedEmploymentDetails());
	 * model.addAttribute("skilledUnSkilledEmployemnt", new
	 * SkilledUnSkilledEmployemnt());
	 * 
	 * } else { skillUnskillEmplList.clear();
	 * model.addAttribute("proposedEmploymentDetails", new
	 * ProposedEmploymentDetails());
	 * model.addAttribute("skilledUnSkilledEmployemnt", new
	 * SkilledUnSkilledEmployemnt()); } }
	 * 
	 * } catch (Exception e) { logger.error("PropEmplDetails exception @@@@@ " +
	 * e.getMessage()); e.printStackTrace();
	 * 
	 * }
	 * 
	 * Incentive Specific Details IncentiveDetails IncentiveDetail =
	 * incentiveDetailsService.getIncentiveByisfapcId(applId); if (IncentiveDetail
	 * != null) { model.addAttribute("ISF_Claim_Reim",
	 * IncentiveDetail.getISF_Claim_Reim()); model.addAttribute("ISF_Reim_SCST",
	 * IncentiveDetail.getISF_Reim_SCST()); model.addAttribute("ISF_Reim_FW",
	 * IncentiveDetail.getISF_Reim_FW()); model.addAttribute("ISF_Reim_BPLW",
	 * IncentiveDetail.getISF_Reim_BPLW()); model.addAttribute("ISF_Ttl_SGST_Reim",
	 * IncentiveDetail.getISF_Ttl_SGST_Reim());
	 * 
	 * model.addAttribute("ISF_Stamp_Duty_EX",
	 * IncentiveDetail.getISF_Stamp_Duty_EX());
	 * model.addAttribute("ISF_Additonal_Stamp_Duty_EX",
	 * IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
	 * model.addAttribute("ISF_Amt_Stamp_Duty_Reim",
	 * IncentiveDetail.getISF_Amt_Stamp_Duty_Reim());
	 * model.addAttribute("ISF_Ttl_Stamp_Duty_EX",
	 * IncentiveDetail.getISF_Ttl_Stamp_Duty_EX());
	 * 
	 * model.addAttribute("ISF_Epf_Reim_UW", IncentiveDetail.getISF_Epf_Reim_UW());
	 * model.addAttribute("ISF_Add_Epf_Reim_SkUkW",
	 * IncentiveDetail.getISF_Add_Epf_Reim_SkUkW());
	 * model.addAttribute("ISF_Add_Epf_Reim_DIVSCSTF",
	 * IncentiveDetail.getISF_Add_Epf_Reim_DIVSCSTF());
	 * model.addAttribute("ISF_Ttl_EPF_Reim",
	 * IncentiveDetail.getISF_Ttl_EPF_Reim());
	 * 
	 * model.addAttribute("ISF_Cis", IncentiveDetail.getISF_Cis());
	 * model.addAttribute("ISF_ACI_Subsidy_Indus",
	 * IncentiveDetail.getISF_ACI_Subsidy_Indus());
	 * model.addAttribute("ISF_Infra_Int_Subsidy",
	 * IncentiveDetail.getISF_Infra_Int_Subsidy());
	 * model.addAttribute("ISF_AII_Subsidy_DIVSCSTF",
	 * IncentiveDetail.getISF_AII_Subsidy_DIVSCSTF());
	 * model.addAttribute("ISF_Loan_Subsidy",
	 * IncentiveDetail.getISF_Loan_Subsidy());
	 * model.addAttribute("ISF_Total_Int_Subsidy",
	 * IncentiveDetail.getISF_Total_Int_Subsidy());
	 * 
	 * model.addAttribute("ISF_Tax_Credit_Reim",
	 * IncentiveDetail.getISF_Tax_Credit_Reim());
	 * model.addAttribute("ISF_EX_E_Duty", IncentiveDetail.getISF_EX_E_Duty());
	 * model.addAttribute("ISF_EX_E_Duty_PC",
	 * IncentiveDetail.getISF_EX_E_Duty_PC());
	 * model.addAttribute("ISF_EX_Mandee_Fee",
	 * IncentiveDetail.getISF_EX_Mandee_Fee());
	 * model.addAttribute("ISF_Indus_Payroll_Asst",
	 * IncentiveDetail.getISF_Indus_Payroll_Asst());
	 * model.addAttribute("Total_Other_Incentive",
	 * IncentiveDetail.getTotal_Other_Incentive());
	 * model.addAttribute("isfCustIncDocName",
	 * IncentiveDetail.getIsfCustIncDocName());
	 * 
	 * model.addAttribute("createdate", IncentiveDetail.getCreateDate());
	 * List<AvailCustomisedDetails> availCustomisedDetailsList =
	 * availCustmisedDetailsService .findAllByAvaCustId(IncentiveDetail.getId()); if
	 * (availCustomisedDetailsList.size() > 0) {
	 * model.addAttribute("ISF_Cstm_Inc_Status", "Yes"); } else {
	 * model.addAttribute("ISF_Cstm_Inc_Status", "No"); }
	 * model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList);
	 * 
	 * Long tsgst = IncentiveDetail.getISF_Ttl_SGST_Reim() == null ? 0 :
	 * IncentiveDetail.getISF_Ttl_SGST_Reim(); Long tstam =
	 * IncentiveDetail.getISF_Stamp_Duty_EX() == null ? 0 :
	 * IncentiveDetail.getISF_Stamp_Duty_EX(); Long taddist =
	 * IncentiveDetail.getISF_Additonal_Stamp_Duty_EX() == null ? 0 :
	 * IncentiveDetail.getISF_Additonal_Stamp_Duty_EX(); Long tepf =
	 * IncentiveDetail.getISF_Ttl_EPF_Reim() == null ? 0 :
	 * IncentiveDetail.getISF_Ttl_EPF_Reim(); Long tis =
	 * IncentiveDetail.getISF_Total_Int_Subsidy() == null ? 0 :
	 * IncentiveDetail.getISF_Total_Int_Subsidy(); Long toth =
	 * IncentiveDetail.getTotal_Other_Incentive() == null ? 0 :
	 * IncentiveDetail.getTotal_Other_Incentive();
	 * 
	 * long aggregatequantumbenifit = tsgst + tstam + taddist + tepf + tis + toth;
	 * 
	 * model.addAttribute("aggregatequantumbenifit", aggregatequantumbenifit); }
	 * 
	 * } catch (Exception e) { logger.error(" exception **** " + e.getMessage());
	 * e.printStackTrace(); }
	 * 
	 * return new ModelAndView("generateLocEvaluationDIC");
	 * 
	 * }
	 */

	/*
	 * @GetMapping(value = "/meetingscheduledic") public String
	 * meetingscheduledic(ModelMap model, HttpSession session) {
	 * logger.debug("Meeting Schedule Start");
	 * 
	 * return "meetingscheduledic"; }
	 */

	/*
	 * @GetMapping(value = "/momgodic") public String momgodic(ModelMap model,
	 * HttpSession session) { logger.debug("Meeting of Meeting");
	 * 
	 * return "momgo"; }
	 */

	/*
	 * @RequestMapping(value = "/SendtoConcernDepartmentdic", method =
	 * RequestMethod.POST) public ModelAndView SendConcernDepartmentdic(
	 * 
	 * @ModelAttribute("SendtoConcernDepartment") @Validated PrepareAgendaNotes
	 * prepareAgendaNotes, Model model, HttpSession session) { String appId =
	 * (String) session.getAttribute("appId"); String userId = (String)
	 * session.getAttribute("userId");
	 * 
	 * return new ModelAndView("redirect:/viewApplicationDetails"); }
	 */

	@RequestMapping(value = "/evaluateApplicationJCI", method = RequestMethod.GET)
	public String evaluateApplicationJCI(@RequestParam(value = "applicantId", required = false) String applId,
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
		} catch (

		Exception e) {
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
			logger.error("InvestmentDetails exception #### " + e.getMessage());
			e.printStackTrace();
		}
		logger.debug("Evaluate Application End ");

		return "evaluateApplicationJCI";
	}

	/*
	 * @RequestMapping(value = "/dicApplicationAgendaNote", method =
	 * RequestMethod.POST) public ModelAndView saveapplicationAgendaNotedic(
	 * 
	 * @ModelAttribute("applicationAgendaNote") @Validated PrepareAgendaNotes
	 * prepareAgendaNotes, Model model, HttpSession session) { PrepareAgendaNotes
	 * prepareAgendaNote = new PrepareAgendaNotes();
	 * 
	 * String appId = (String) session.getAttribute("appId");
	 * prepareAgendaNote.setAppliId(appId); BusinessEntityDetails
	 * businessEntityDetails = businessEntityDetailsService
	 * .getBusinessEntityByapplicantDetailId(appId);
	 * prepareAgendaNote.setCompanyName(businessEntityDetails.getBusinessEntityName(
	 * )); prepareAgendaNote.setId(appId + "AGN"); InvestmentDetails invdtlFromDb =
	 * invDtlService.getInvestmentDetailsByapplId(appId);
	 * prepareAgendaNote.setInvestment(String.valueOf(invdtlFromDb.getInvFci()));
	 * prepareAgendaNote.setCategoryIndsType(invdtlFromDb.getInvIndType());
	 * ProjectDetails projectDetails =
	 * projectService.getProjectByapplicantDetailId(appId);
	 * prepareAgendaNote.setDistrict(projectDetails.getDistrictName());
	 * prepareAgendaNote.setRegion(projectDetails.getResionName());
	 * prepareAgendaNote.setNotes(""); prepareAgendaNote.setCreatedBY("AdminUser");
	 * prepareAgendaNote.setStatus("Ready");
	 * 
	 * String userId = (String) session.getAttribute("userId"); try {
	 * 
	 * if (userId != null && userId != "") { Login loginUser =
	 * loginService.getLoginIdById(userId);
	 * prepareAgendaNote.setUserId(loginUser.getId()); }
	 * prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote); } catch
	 * (Exception e) { e.getMessage(); }
	 * 
	 * model.addAttribute("msg", "Succefully Add Agenda Notes"); return new
	 * ModelAndView("redirect:/evaluateApplicationDIC"); }
	 */

}
