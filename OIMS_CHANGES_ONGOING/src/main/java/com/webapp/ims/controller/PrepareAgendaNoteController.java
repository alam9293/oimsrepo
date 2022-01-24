package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.EXIST_PROJ_LIST;
import static com.webapp.ims.exception.GlobalConstants.INVALID_PATH_SEQUENCE;
import static com.webapp.ims.exception.GlobalConstants.LARGE;
import static com.webapp.ims.exception.GlobalConstants.MEDIUM;
import static com.webapp.ims.exception.GlobalConstants.MEGA;
import static com.webapp.ims.exception.GlobalConstants.MEGAPLUS;
import static com.webapp.ims.exception.GlobalConstants.PREPARE_AGENDA_NOTES;
import static com.webapp.ims.exception.GlobalConstants.SMALL;
import static com.webapp.ims.exception.GlobalConstants.SUPERMEGA;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.exception.FileStorageException;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicantDocument;
import com.webapp.ims.model.ApplicationFwdEntity;
import com.webapp.ims.model.AvailCustomisedDetails;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.CirculateToDepartment;
import com.webapp.ims.model.DSCPdfUploadEntity;
import com.webapp.ims.model.Department;
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
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.RaiseQuery;
//import com.webapp.ims.model.RoleMasterEntity;
import com.webapp.ims.uam.model.TblRole;
import com.webapp.ims.uam.repository.TblRoleServiceRepository;
import com.webapp.ims.model.ScrutinyDocument;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.model.StateDetails;
import com.webapp.ims.repository.ApplicationFwdRepository;
import com.webapp.ims.repository.CirculateToDepartmentRepository;
import com.webapp.ims.repository.DeptBusinessEntityDetailsRepository;
import com.webapp.ims.repository.DeptIncentiveDetailsRepository;
import com.webapp.ims.repository.DeptPhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.repository.DeptProposedEmploymentDetailsRepository;
import com.webapp.ims.repository.EvaluateAuditTrialRepository;
import com.webapp.ims.repository.EvaluateExistNewProjDetailsRepository;
import com.webapp.ims.repository.ExistingProjectDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.RoleMasterRepository;
import com.webapp.ims.repository.ScrutinyDocumentRepo;
import com.webapp.ims.service.AgendaReportService;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.CirculateToDepartmentService;
import com.webapp.ims.service.DepartmentService;
import com.webapp.ims.service.EvaluateCapInvestService;
import com.webapp.ims.service.EvaluateInvestMentDetailsService;
import com.webapp.ims.service.EvaluateMeanofFinanceService;
import com.webapp.ims.service.EvaluateProjectDetailsService;
import com.webapp.ims.service.ExistingProjectDetailsService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;
import com.webapp.ims.service.PrepareAgendaNoteService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;
import com.webapp.ims.service.ScrutinyDocumentService;
import com.webapp.ims.service.StateDetailsService;
import com.webapp.ims.service.impl.AdditionalInterest;
import com.webapp.ims.service.impl.GlobalServiceUtil;

@Scope("session")
@Controller
public class PrepareAgendaNoteController {
	private final Logger logger = LoggerFactory.getLogger(PrepareAgendaNoteController.class);

	@Autowired
	private EvaluateProjectDetailsService evaluateProjectDetailsService;

	@Autowired
	public ApplicationDetailsViewController applicantViewController;

	@Autowired
	public EvaluateExistNewProjDetailsRepository evaluateExistNewProjDetailsRepository;

	@Autowired
	public EvaluateAuditTrialRepository evaluateAuditTrialRepository;
	@Autowired
	GlobalServiceUtil globalServiceUtil;
	@Autowired
	ScrutinyDocumentRepo scrutinyDocumentRepo;
	@Autowired
	DeptPhaseWiseInvestmentDetailsRepository deptPwInvRepository;

	@Autowired
	public EvaluateInvestMentDetailsService evaluateInvestMentDetailsService;

	@Autowired
	private DeptBusinessEntityDetailsRepository deptBusEntRepository;

	@Autowired
	private DeptProposedEmploymentDetailsRepository deptPrpsEmplRepository;

	@Autowired
	private DeptIncentiveDetailsRepository deptIncRepository;

	@Autowired
	private ExistingProjectDetailsRepository existProjRepository;

	@Autowired
	private EvaluateCapInvestService evalCapInvService;

	@Autowired
	InvestmentDetailsRepository investRepository;
	@Autowired
	private ApplicantDetailsService applicantDetailsService;
	@Autowired
	private ApplicantDocumentService fileStorageService;
	@Autowired
	BusinessEntityDetailsService businessService;
	@Autowired
	ProprietorDetailsService proprietorService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private ProposedEmploymentDetailsService proposedEmploymentDetailsService;
	@Autowired
	private InvestmentDetailsService investDs;
	@Autowired
	private PhaseWiseInvestmentDetailsService pwInvestDs;
	@Autowired
	IncentiveDetailsService incentiveDetailsService;
	@Autowired
	AvailCustmisedDetailsService availCustmisedDetailsService;
	@Autowired
	private PrepareAgendaNotesService prepareAgendaNotesService;
	@Autowired
	private ProposedEmploymentDetailsService emplDtlService;

	@Autowired
	AdditionalInterest additionalInterest;

	@Autowired
	InvestmentDetailsService investmentDetailsService;
	@Autowired
	PrepareAgendaNoteService prepareAgendaNoteService;

	@Autowired
	ScrutinyDocumentService scrutinyDocumentService;

	@Autowired
	private EvaluateMeanofFinanceService evalMOf;

	public List<ExistingProjectDetails> existProjList = new ArrayList<>();
	@Autowired
	private ExistingProjectDetailsService existProjectService;

	@Autowired
	private StateDetailsService stateDetailsService;

	@Autowired
	private TblRoleServiceRepository roleMasterRepository;

	@Autowired
	private ApplicationFwdRepository applicationFwdRepository;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	CirculateToDepartmentService circulateToDepartmentService;

	@Autowired
	CirculateToDepartmentRepository circulateToDepartmentRepository;

	@Autowired
	AgendaReportService agendaReportService;

	private AtomicInteger atomicInteger = new AtomicInteger();

	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();
	private List<EvaluateCapitalInvestment> evalCapitalInvList = new LinkedList<>();
	private List<EvaluateMeanOfFinance> evalMeanofFinanceList = new LinkedList<>();

	@GetMapping(value = "/agendaNode")
	public ModelAndView agendaNode(Model model, HttpSession session) throws IOException {

		String userid = (String) session.getAttribute("userId");

		try {
			// for comment jmd

			List<PrepareAgendaNotes> prepareAgendaNotesList1 = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			/* List for Mega,Super Mega and Mega Plus List with Ready Status */
			List<PrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

			/* List for Large with Ready Status */
			List<PrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

			if (!prepareAgendaNotesList1.isEmpty()) {

				for (PrepareAgendaNotes list : prepareAgendaNotesList1) {
					if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
							|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
							|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
						prepareAgendaNotesMegaMdPiCupLists.add(list);
					} else if (list.getCategoryIndsType().trim().equalsIgnoreCase("Large")) {
						prearepAgendaNotesLargeMdPiCupLists.add(list);
					}
				}
			}
			model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
			model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
			model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);

			// end jmd comment
			/* List for Mega,Super Mega and Mega Plus List with Ready Status */
			List<PrepareAgendaNotes> prepareAgendaNotesMegaLists = new ArrayList<PrepareAgendaNotes>();

			/* List for Large with Ready Status */
			List<PrepareAgendaNotes> prearepAgendaNotesLargeLists = new ArrayList<PrepareAgendaNotes>();

			/* All Lists of Large ,Mega, Super Mega and Mega Plus with Prepared Status */
			List<PrepareAgendaNotes> prepareAgendaNotesAllPreparedLists = new ArrayList<PrepareAgendaNotes>();

			/* List for Mega,Super Mega and Mega Plus List with Approved by MDPICUP */
			List<PrepareAgendaNotes> prepareAgendaNotesApprovedMegaByMDPICUPLists = new ArrayList<PrepareAgendaNotes>();

			/* List for Large with Approved by MDPICUP */
			List<PrepareAgendaNotes> prepareAgendaNotesApprovedLargeByMDPICUPListsLists = new ArrayList<PrepareAgendaNotes>();

			List<PrepareAgendaNotes> prepareAgendaNotescommentList = prepareAgendaNoteService
					.getPrepareAgendaNote(session);

			// List<PrepareAgendaNotes> prepareAgendaNotesList =
			// prepareAgendaNoteService.getPrepareAgendaNoteBySendforApproval(session);
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPreAgendaNote(session);

			// List<PrepareAgendaNotes> prepareAgendaNotesList =
			// prepareAgendaNoteService.findPrepAgendaNotesListByUserId(userid);

			if (prepareAgendaNotesList.size() > 0) {
				for (PrepareAgendaNotes list : prepareAgendaNotesList) {
					if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
							|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
							|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
						list.setScrutinyDocumentList(
								scrutinyDocumentService.getAllScrutinyDocByAppId(list.getAppliId()));
						prepareAgendaNotesMegaLists.add(list);
					} else if (list.getCategoryIndsType().equalsIgnoreCase("Large")) {
						list.setScrutinyDocumentList(
								scrutinyDocumentService.getAllScrutinyDocByAppId(list.getAppliId()));
						prearepAgendaNotesLargeLists.add(list);
					}
				}
			}
			prepareAgendaNotesList = prepareAgendaNoteService.getPrepareViewAgendaNoteList(session);
			if (!prepareAgendaNotesList.isEmpty()) {
				for (PrepareAgendaNotes list : prepareAgendaNotesList) {
					if (list.getAppliId() != null) {
						prepareAgendaNotesAllPreparedLists.add(list);
					}
				}
			}
			prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNoteSubmit(session);
			if (prepareAgendaNotesList != null) {
				for (PrepareAgendaNotes list : prepareAgendaNotesList) {
					if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
							|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
							|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {

						prepareAgendaNotesApprovedMegaByMDPICUPLists.add(list);

					} else if (list.getCategoryIndsType().equalsIgnoreCase("Large")) {

						prepareAgendaNotesApprovedLargeByMDPICUPListsLists.add(list);
					}
				}
			}

			List<PrepareAgendaNotes> prepareAgendaNotesCommentsAll = prepareAgendaNoteService.getAllPrepareAgendaNote();
			List<PrepareAgendaNotes> prepareAgendaNotesListACSComment = new ArrayList<PrepareAgendaNotes>();

			List<PrepareAgendaNotes> prepareAgendaNotesListMDPICUPComment = new ArrayList<PrepareAgendaNotes>();

			List<PrepareAgendaNotes> prepareAgendaNotesListJMDComment = new ArrayList<PrepareAgendaNotes>();

			for (PrepareAgendaNotes list : prepareAgendaNotesCommentsAll) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Large"))
						&& list.getMdScrutinyDetail() != null) {
					prepareAgendaNotesListMDPICUPComment.add(list);
				}
			}

			for (PrepareAgendaNotes list : prepareAgendaNotesCommentsAll) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))
						&& list.getAcsScrutinyDetail() != null) {
					prepareAgendaNotesListACSComment.add(list);
				}
			}

			for (PrepareAgendaNotes list : prepareAgendaNotesCommentsAll) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getComments() != null) {
					prepareAgendaNotesListJMDComment.add(list);
				}
			}

			model.addAttribute(PREPARE_AGENDA_NOTES, new PrepareAgendaNotes());

			model.addAttribute("prepareAgendaNotesMegaLists", prepareAgendaNotesMegaLists);
			model.addAttribute("prearepAgendaNotesLargeLists", prearepAgendaNotesLargeLists);

			model.addAttribute("prepareAgendaNotesAllPreparedLists", prepareAgendaNotesAllPreparedLists);

			model.addAttribute("prepareAgendaNotesApprovedMegaLists", prepareAgendaNotesApprovedMegaByMDPICUPLists);

			model.addAttribute("prepareAgendaNotesApprovedLargeLists",
					prepareAgendaNotesApprovedLargeByMDPICUPListsLists);

			model.addAttribute("prepareAgendaNotesListJMDComment", prepareAgendaNotesListJMDComment);
			model.addAttribute("prepareAgendaNotesListMDPICUPComment", prepareAgendaNotesListMDPICUPComment);
			model.addAttribute("prepareAgendaNotesListACSComment", prepareAgendaNotesListACSComment);

			// Added by Pramod solankee

			if (prepareAgendaNotesApprovedMegaByMDPICUPLists != null
					&& prepareAgendaNotesApprovedMegaByMDPICUPLists.size() > 0) {
				session.setAttribute("Mega", prepareAgendaNotesApprovedMegaByMDPICUPLists);
			}
			if (prepareAgendaNotesApprovedLargeByMDPICUPListsLists != null
					&& prepareAgendaNotesApprovedLargeByMDPICUPListsLists.size() > 0) {

				session.setAttribute("Large", prepareAgendaNotesApprovedLargeByMDPICUPListsLists);
			}
		} catch (Exception ex) {
			ex.printStackTrace();

		}
		model.addAttribute("CirculateToDepartment", new CirculateToDepartment());
		Iterable<Department> list = departmentService.getDepartmentList();
		model.addAttribute("concernDepartment", list);
		model.addAttribute("concernDepartmentMega", list);
		return new ModelAndView("prepareAgendaNote");

	}

	@GetMapping(value = "/viewAgendaDetails")
	public ModelAndView viewAgendaDetails(Model model,
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session) {

		if (applId != null && applId != "") {
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
							base64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
						}

					} catch (Exception e) {
						e.printStackTrace();
					}
					applicantDetail.setBase64imageFile(base64Encoded);
					applicantDetail.setFileName(applicantDocument.getFileName());
				}
				model.addAttribute("applicantDetail", applicantDetail);
			}
			BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(applId);
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
				List<ProprietorDetails> proprietorDetailsList = proprietorService
						.findAllByBusinessId(businessEntityDetails.getId());
				model.addAttribute("ProprietorDetailsList", proprietorDetailsList);

			}
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

				model.addAttribute("natureOfProject", ProjectDetail.getNatureOfProject());
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

			InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsByapplId(applId);
			if (invdtlFromDb != null) {
				model.addAttribute("invIndType", invdtlFromDb.getInvIndType());
				model.addAttribute("invFci", invdtlFromDb.getInvFci());
				model.addAttribute("invTotalProjCost", invdtlFromDb.getInvTotalProjCost());
				model.addAttribute("invLandCost", invdtlFromDb.getInvLandCost());
				model.addAttribute("invBuildingCost", invdtlFromDb.getInvBuildingCost());
				model.addAttribute("invPlantAndMachCost", invdtlFromDb.getInvPlantAndMachCost());
				model.addAttribute("invOtherCost", invdtlFromDb.getInvOtherCost());
				model.addAttribute("invCommenceDate", invdtlFromDb.getInvCommenceDate());
				model.addAttribute("propCommProdDate", invdtlFromDb.getPropCommProdDate());
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

			ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService
					.getProposedEmploymentDetailsByappId(applId);
			if (proposedEmploymentDetail != null) {

				if (proposedEmploymentDetail != null) {
					List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = proposedEmploymentDetail
							.getSkilledUnSkilledEmployemnt();
					totalSkilledAndUnSkilledEmploment(model, skilledUnSkilledEmployemntslist);
					model.addAttribute("proposedEmploymentDetails", skilledUnSkilledEmployemntslist);
				}
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
				List<AvailCustomisedDetails> availCustomisedDetailsList = availCustmisedDetailsService
						.findAllByAvaCustId(IncentiveDetail.getId());
				if (availCustomisedDetailsList.size() > 0) {
					model.addAttribute("ISF_Cstm_Inc_Status", "Yes");
				} else {
					model.addAttribute("ISF_Cstm_Inc_Status", "No");
				}
				model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList);
			}
		}
		session.setAttribute("appId", applId);
		return new ModelAndView("view_agenda_note_application_details");
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

	@PostMapping(value = "/saveNotes")
	public ModelAndView saveNotes(
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes,
			BindingResult result, Model model, HttpSession session) {

		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		prepareAgendaNote.setId(prepareAgendaNotes1.getId());
		prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
		prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
		prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());
		prepareAgendaNote.setUserId(prepareAgendaNotes1.getUserId());
		prepareAgendaNote.setNotes(prepareAgendaNotes.getNotes());
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setStatus(prepareAgendaNotes1.getStatus());
		prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());
		prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
		prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
		prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());

		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);
		String userid = (String) session.getAttribute("userId");
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeLists = new ArrayList<PrepareAgendaNotes>();

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
				.findPrepAgendaNotesListByUserId(userid);
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaLists.add(list);
				} else if (list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeLists.add(list);
				}
			}
		}

		// panDocService.insertPANDocList(multipartFiles, session); // save
		// PreparedAgendaNote doc list in MongoDB

		model.addAttribute(PREPARE_AGENDA_NOTES, new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaLists", prepareAgendaNotesMegaLists);
		model.addAttribute("prearepAgendaNotesLargeLists", prearepAgendaNotesLargeLists);
		return new ModelAndView("prepareAgendaNote");

	}

	@GetMapping(value = "/downloadFiles")
	public void downloadFiles(@RequestParam(value = "fileName", required = false) String fileName,
			@RequestParam(value = "appId") String appId, HttpSession session, HttpServletResponse response) {
		// String appId = (String) session.getAttribute("appId");
		String ScruDocId = appId + "SCRU";

		List<ScrutinyDocument> scrutinyDocumentList = scrutinyDocumentRepo.getListByScruDocId(ScruDocId);
		if (!scrutinyDocumentList.isEmpty()) {
			for (ScrutinyDocument scruDoc : scrutinyDocumentList) {
				if (fileName.equals(scruDoc.getFileName())) {
					String scruDocName = scruDoc.getFileName();
					byte[] scruDocData = scruDoc.getData();
					globalServiceUtil.downloadMongoDBDoc(scruDocName, scruDocData, response);
					break;
				}
			}
		}
	}

	@PostMapping(value = "/SaveScrutinyDocuments")
	public ModelAndView SaveScrutinyDetailDocuments(
			@ModelAttribute("ScrutinyDocument") ScrutinyDocument scrutinyDocument,
			@RequestParam("scrutinyFilename") MultipartFile[] multipartFileArr,
			@RequestParam("scrutinyFilenameSanction") MultipartFile[] multipartFileArr2,BindingResult result, Model model,
			HttpSession session) throws IOException {
		try {
			List<ScrutinyDocument> multipartFileList = new LinkedList<ScrutinyDocument>();
			//String appId = scrutinyDocument.getAppId();
			String appId = (String) session.getAttribute("appId");
			System.out.println("Appid"+ appId);

			appId = appId.replace(",", "");
			System.out.println("scrutinyDocument.appID" + appId);
			if(multipartFileArr.length>0)
			{
			for (MultipartFile multipartFile : multipartFileArr) {
				ScrutinyDocument dbFile = new ScrutinyDocument();
				if ((!multipartFile.getOriginalFilename().isEmpty() && multipartFile.getOriginalFilename() != null)
						&& multipartFile.getBytes() != null) {

					String fileName = multipartFile.getOriginalFilename();
					if (fileName.contains("..")) {
						throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
					}
					dbFile = new ScrutinyDocument(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
							multipartFile.getBytes());

					dbFile.setDocCreatedBy("User");
					dbFile.setFieldName("ScrutinyDoc");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					multipartFileList.add(dbFile);
				}
			}
			
			}
			else
			{
				if(multipartFileArr2.length>0)
				{
				for (MultipartFile multipartFile : multipartFileArr2) {
					ScrutinyDocument dbFile = new ScrutinyDocument();
					if ((!multipartFile.getOriginalFilename().isEmpty() && multipartFile.getOriginalFilename() != null)
							&& multipartFile.getBytes() != null) {

						String fileName = multipartFile.getOriginalFilename();
						if (fileName.contains("..")) {
							throw new FileStorageException(INVALID_PATH_SEQUENCE + fileName);
						}
						dbFile = new ScrutinyDocument(multipartFile.getOriginalFilename(), multipartFile.getContentType(),
								multipartFile.getBytes());

						dbFile.setDocCreatedBy("User");
						dbFile.setFieldName("ScrutinyDoc");
						dbFile.setDocUpdateDate(new Date());
						dbFile.setDocCreatedDate(new Date());
						multipartFileList.add(dbFile);
					}
				}
				
				}
				
			}
			scrutinyDocumentService.saveAndUpdateMultipleFiles(multipartFileList, appId);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}

		/*
		 * List for Mega,Super Mega and Mega Plus List with Ready Status
		 * List<PrepareAgendaNotes> prepareAgendaNotesMegaLists = new
		 * ArrayList<PrepareAgendaNotes>();
		 * 
		 * List for Large with Ready Status List<PrepareAgendaNotes>
		 * prearepAgendaNotesLargeLists = new ArrayList<PrepareAgendaNotes>();
		 * 
		 * List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
		 * .findPrepAgendaNotesListByUserId(scrutinyDocument.getAppId()); if
		 * (prepareAgendaNotesList.size() > 0) { for (PrepareAgendaNotes list :
		 * prepareAgendaNotesList) { if
		 * (list.getStatus().equalsIgnoreCase("Application Included In Agenda Note") &&
		 * (list.getCategoryIndsType().equalsIgnoreCase("Mega") ||
		 * list.getCategoryIndsType().equalsIgnoreCase("Mega Plus") ||
		 * list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
		 * prepareAgendaNotesMegaLists.add(list); } else if
		 * (list.getStatus().equalsIgnoreCase("Application Included In Agenda Note") &&
		 * list.getCategoryIndsType().equalsIgnoreCase("Large")) {
		 * 
		 * prearepAgendaNotesLargeLists.add(list); } } }
		 * 
		 * // panDocService.insertPANDocList(multipartFiles, session); // save //
		 * PreparedAgendaNote doc list in MongoDB
		 * 
		 * model.addAttribute(PREPARE_AGENDA_NOTES, new PrepareAgendaNotes());
		 * model.addAttribute("prepareAgendaNotesMegaLists",
		 * prepareAgendaNotesMegaLists);
		 * model.addAttribute("prearepAgendaNotesLargeLists",
		 * prearepAgendaNotesLargeLists);
		 * 
		 */

		String userid = (String) session.getAttribute("userId");

		// for comment jmd

		List<PrepareAgendaNotes> prepareAgendaNotesList1 = prepareAgendaNoteService
				.findPrepAgendaNotesListByUserId(userid);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

		if (!prepareAgendaNotesList1.isEmpty()) {

			for (PrepareAgendaNotes list : prepareAgendaNotesList1) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getCategoryIndsType().trim().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeMdPiCupLists.add(list);
				}
			}
		}
		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);

		// end jmd comment
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeLists = new ArrayList<PrepareAgendaNotes>();

		/* All Lists of Large ,Mega, Super Mega and Mega Plus with Prepared Status */
		List<PrepareAgendaNotes> prepareAgendaNotesAllPreparedLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Mega,Super Mega and Mega Plus List with Approved by MDPICUP */
		List<PrepareAgendaNotes> prepareAgendaNotesApprovedMegaByMDPICUPLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Approved by MDPICUP */
		List<PrepareAgendaNotes> prepareAgendaNotesApprovedLargeByMDPICUPListsLists = new ArrayList<PrepareAgendaNotes>();

		List<PrepareAgendaNotes> prepareAgendaNotescommentList = prepareAgendaNoteService.getPrepareAgendaNote(session);

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPreAgendaNote(session);

		// List<PrepareAgendaNotes> prepareAgendaNotesList =
		// prepareAgendaNoteService.findPrepAgendaNotesListByUserId(userid);

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					list.setScrutinyDocumentList(scrutinyDocumentService.getAllScrutinyDocByAppId(list.getAppliId()));
					prepareAgendaNotesMegaLists.add(list);
				} else if (list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					list.setScrutinyDocumentList(scrutinyDocumentService.getAllScrutinyDocByAppId(list.getAppliId()));
					prearepAgendaNotesLargeLists.add(list);
				}
			}
		}

		prepareAgendaNotesList = prepareAgendaNoteService.getPrepareViewAgendaNoteList(session);
		if (!prepareAgendaNotesList.isEmpty()) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {

				prepareAgendaNotesAllPreparedLists.add(list);

			}
		}
		if (!prepareAgendaNotesList.isEmpty()) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {

					prepareAgendaNotesApprovedMegaByMDPICUPLists.add(list);
				} else if (list.getCategoryIndsType().equalsIgnoreCase("Large")) {

					prepareAgendaNotesApprovedLargeByMDPICUPListsLists.add(list);
				}
			}
		}

		List<PrepareAgendaNotes> prepareAgendaNotesListACSComment = new ArrayList<PrepareAgendaNotes>();

		List<PrepareAgendaNotes> prepareAgendaNotesListMDPICUPComment = new ArrayList<PrepareAgendaNotes>();

		List<PrepareAgendaNotes> prepareAgendaNotesListJMDComment = new ArrayList<PrepareAgendaNotes>();

		for (PrepareAgendaNotes list : prepareAgendaNotescommentList) {
			if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
					|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getMdScrutinyDetail() != null) {
				prepareAgendaNotesListMDPICUPComment.add(list);
			}
		}

		for (PrepareAgendaNotes list : prepareAgendaNotescommentList) {
			if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
					|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))
					&& list.getAcsScrutinyDetail() != null) {
				prepareAgendaNotesListACSComment.add(list);
			}
		}

		for (PrepareAgendaNotes list : prepareAgendaNotescommentList) {
			if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
					|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getComments() != null) {
				prepareAgendaNotesListJMDComment.add(list);
			}
		}

		model.addAttribute(PREPARE_AGENDA_NOTES, new PrepareAgendaNotes());

		model.addAttribute("prepareAgendaNotesMegaLists", prepareAgendaNotesMegaLists);
		model.addAttribute("prearepAgendaNotesLargeLists", prearepAgendaNotesLargeLists);

		model.addAttribute("prepareAgendaNotesAllPreparedLists", prepareAgendaNotesAllPreparedLists);

		model.addAttribute("prepareAgendaNotesApprovedMegaLists", prepareAgendaNotesApprovedMegaByMDPICUPLists);

		model.addAttribute("prepareAgendaNotesApprovedLargeLists", prepareAgendaNotesApprovedLargeByMDPICUPListsLists);

		model.addAttribute("prepareAgendaNotesListJMDComment", prepareAgendaNotesListJMDComment);
		model.addAttribute("prepareAgendaNotesListMDPICUPComment", prepareAgendaNotesListMDPICUPComment);
		model.addAttribute("prepareAgendaNotesListACSComment", prepareAgendaNotesListACSComment);
		
		model.addAttribute("CirculateToDepartment", new CirculateToDepartment());

		// Added by Pramod solankee

		if (prepareAgendaNotesApprovedMegaByMDPICUPLists != null
				&& prepareAgendaNotesApprovedMegaByMDPICUPLists.size() > 0) {
			session.setAttribute("Mega", prepareAgendaNotesApprovedMegaByMDPICUPLists);
		}
		if (prepareAgendaNotesApprovedLargeByMDPICUPListsLists != null
				&& prepareAgendaNotesApprovedLargeByMDPICUPListsLists.size() > 0) {

			session.setAttribute("Large", prepareAgendaNotesApprovedLargeByMDPICUPListsLists);
		}

		return new ModelAndView("prepareAgendaNote");

	}

	@PostMapping(value = "/SaveScrutinyDetails")
	public ModelAndView SaveScrutinyDetails(
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes,
			BindingResult result, Model model, HttpSession session,
			@RequestParam("pkupFilename") MultipartFile multipartFile) {

		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		prepareAgendaNote.setId(prepareAgendaNotes1.getId());
		prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
		prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
		prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());
		prepareAgendaNote.setUserId(prepareAgendaNotes1.getUserId());
		prepareAgendaNote.setNotes(prepareAgendaNotes1.getNotes());
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setStatus(prepareAgendaNotes1.getStatus());
		prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
		prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());
		prepareAgendaNote.setSubjectName(prepareAgendaNotes1.getSubjectName());
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes.getPkupScrutinyDetail());
		prepareAgendaNote.setJmdScrutinyDetail(prepareAgendaNotes1.getJmdScrutinyDetail());
		prepareAgendaNote.setMdScrutinyDetail(prepareAgendaNotes1.getMdScrutinyDetail());
		prepareAgendaNote.setAcsScrutinyDetail(prepareAgendaNotes1.getAcsScrutinyDetail());
		prepareAgendaNote.setPkupFilename(multipartFile.getOriginalFilename());
		try {
			prepareAgendaNote.setPkupFiledata(multipartFile.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);
		String userid = (String) session.getAttribute("userId");
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeLists = new ArrayList<PrepareAgendaNotes>();

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
				.findPrepAgendaNotesListByUserId(userid);
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Application Included In Agenda Note")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Application Included In Agenda Note")
						&& list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeLists.add(list);
				}
			}
		}

		// panDocService.insertPANDocList(multipartFiles, session); // save
		// PreparedAgendaNote doc list in MongoDB

		model.addAttribute(PREPARE_AGENDA_NOTES, new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaLists", prepareAgendaNotesMegaLists);
		model.addAttribute("prearepAgendaNotesLargeLists", prearepAgendaNotesLargeLists);
		return new ModelAndView("prepareAgendaNote");

	}

	@PostMapping(value = "/prepareAgendaNote")
	public ModelAndView prepareAgendaNote(
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session, HttpServletRequest request) {

		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		String userid = (String) session.getAttribute("userId");
		for (String applId : applicantList) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)) {
					investmentDetailsmixedList.add(list);
				}
			}
			session.setAttribute("appId", applId);
		}
		String appId = (String) session.getAttribute("appId");
		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);
		common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute(PREPARE_AGENDA_NOTES, prepareAgendaNotes);
		model.addAttribute("flag", "false");

		return new ModelAndView("agenda_note_evaluation_view");
	}

	@GetMapping(value = "/dispalyAgendaNote")
	public String dispalyAgendaNote(@RequestParam(value = "appliId", required = false) String appliId,
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session, HttpServletRequest request) {

		String projId = appliId.substring(0, appliId.length() - 2) + "P1";

		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);

		// To retrieve ExistingProjectDetailsList from table on projectId basis
		if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
			existProjList = existProjectService.getExistProjListById(projId);
			model.addAttribute(EXIST_PROJ_LIST, existProjList);
		} else {
			existProjList.clear();
		}

		ProjectDetails projectDetails = projectService.getProjectByapplicantDetailId(appliId);
		model.addAttribute("natureOfProject", projectDetails.getNatureOfProject());
		model.addAttribute("ExistingProducts", projectDetails.getExistingProducts());
		model.addAttribute("existingInstalledCapacity", projectDetails.getExistingInstalledCapacity());
		model.addAttribute("proposedProducts", projectDetails.getProposedProducts());
		model.addAttribute("proposedInstalledCapacity", projectDetails.getProposedInstalledCapacity());
		model.addAttribute("existingGrossBlock", projectDetails.getExistingGrossBlock());
		model.addAttribute("proposedGrossBlock", projectDetails.getProposedGrossBlock());

		model.addAttribute("fullAddress", projectDetails.getFullAddress());
		model.addAttribute("districtName", projectDetails.getDistrictName());
		model.addAttribute("resionName", projectDetails.getResionName());
		String pergrosblock = additionalInterest.getgrossBlock(appliId);
		model.addAttribute("pergrosblock", pergrosblock);
		Object LCasperIEPP = additionalInterest.getProposedFCI(appliId);
		model.addAttribute("LCasperIEPP", LCasperIEPP);
		String regorlic = additionalInterest.getregiOrLicense(appliId);
		model.addAttribute("regorlic", regorlic);

		String userid = (String) session.getAttribute("userId");
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

			InvestmentDetails invDetailFromDb = investmentDetailsService.getInvestmentDetailsByapplId(appliId);

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
		} catch (Exception e) {
			logger.error(String.format("^^^^ dispalyAgendaNote exception ^^^ %s", e.getMessage()));
		}

		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveByisfapcId(appliId);
		if (IncentiveDetail != null) {
			model.addAttribute("ISF_Claim_Reim", IncentiveDetail.getISF_Claim_Reim());
			model.addAttribute("ISF_Reim_SCST", IncentiveDetail.getISF_Reim_SCST());
			model.addAttribute("ISF_Reim_FW", IncentiveDetail.getISF_Reim_FW());
			model.addAttribute("ISF_Reim_BPLW", IncentiveDetail.getISF_Reim_BPLW());
			model.addAttribute("ISF_EX_E_Duty_PC", IncentiveDetail.getISF_EX_E_Duty_PC());
			model.addAttribute("ISF_EX_Mandee_Fee", IncentiveDetail.getISF_EX_Mandee_Fee());

			model.addAttribute("ISF_SGST_Comment", IncentiveDetail.getIsfSgstComment());
			model.addAttribute("ISF_SCST_Comment", IncentiveDetail.getIsfScstComment());
			model.addAttribute("ISF_FW_Comment", IncentiveDetail.getIsffwComment());
			model.addAttribute("ISF_BPL_Comment", IncentiveDetail.getIsfBplComment());
			model.addAttribute("ISF_Elec_Duty_Comment", IncentiveDetail.getIsfElecdutyComment());
			model.addAttribute("ISF_Mandi_Comment", IncentiveDetail.getIsfMandiComment());

			try {
				Long isf_Claim_Reim = IncentiveDetail.getISF_Claim_Reim() == null ? 0
						: IncentiveDetail.getISF_Claim_Reim();
				Long isf_Reim_SCST = IncentiveDetail.getISF_Reim_SCST() == null ? 0
						: IncentiveDetail.getISF_Reim_SCST();
				Long isf_EX_E_Duty_PC = IncentiveDetail.getISF_EX_E_Duty_PC() == null ? 0
						: IncentiveDetail.getISF_EX_E_Duty_PC();
				Long isf_EX_Mandee_Fee = IncentiveDetail.getISF_EX_Mandee_Fee() == null ? 0
						: IncentiveDetail.getISF_EX_Mandee_Fee();
				Long isf_Reim_BPLW = IncentiveDetail.getISF_Reim_BPLW() == null ? 0
						: IncentiveDetail.getISF_Reim_BPLW();
				Long isf_Reim_FW = IncentiveDetail.getISF_Reim_FW() == null ? 0 : IncentiveDetail.getISF_Reim_FW();
				Long total = isf_Claim_Reim + isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW
						+ isf_Reim_FW;
				model.addAttribute("total", total);
			} catch (Exception e) {
				logger.error(String.format(":::: dispalyAgendaNote exception ::::: %s", e.getMessage()));
			}
		}
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		prepareAgendaNote.setAppliId(appliId);
		model.addAttribute("flag", "true");
		if (projectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			model.addAttribute("flag1", "false1");
		}
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		model.addAttribute("prepAgenNotes", prepareAgendaNotes1.getNotes());
		model.addAttribute("scrtnyComment", prepareAgendaNotes1.getPkupScrutinyDetail());
		model.addAttribute("JMDComment", prepareAgendaNotes1.getJmdcomments());
		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Application Included In Agenda Note")) {
					investmentDetailsmixedList.add(list);
				}
			}

		}

		common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute(PREPARE_AGENDA_NOTES, prepareAgendaNotes);
		return ("agenda_note_evaluation_view");
	}

	@GetMapping(value = "/dispalyAgendaNoteView")
	public String dispalyAgendaNoteView(@RequestParam(value = "appliId", required = false) String appliId,
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session, HttpServletRequest request) {

		String projId = appliId.substring(0, appliId.length() - 2) + "P1";

		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);

		// To retrieve ExistingProjectDetailsList from table on projectId basis
		if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
			existProjList = existProjectService.getExistProjListById(projId);
			model.addAttribute(EXIST_PROJ_LIST, existProjList);
		} else {
			existProjList.clear();
		}

		ProjectDetails projectDetails = projectService.getProjectByapplicantDetailId(appliId);
		model.addAttribute("natureOfProject", projectDetails.getNatureOfProject());
		model.addAttribute("ExistingProducts", projectDetails.getExistingProducts());
		model.addAttribute("existingInstalledCapacity", projectDetails.getExistingInstalledCapacity());
		model.addAttribute("proposedProducts", projectDetails.getProposedProducts());
		model.addAttribute("proposedInstalledCapacity", projectDetails.getProposedInstalledCapacity());
		model.addAttribute("existingGrossBlock", projectDetails.getExistingGrossBlock());
		model.addAttribute("proposedGrossBlock", projectDetails.getProposedGrossBlock());

		model.addAttribute("fullAddress", projectDetails.getFullAddress());
		model.addAttribute("districtName", projectDetails.getDistrictName());
		model.addAttribute("resionName", projectDetails.getResionName());
		String pergrosblock = additionalInterest.getgrossBlock(appliId);
		model.addAttribute("pergrosblock", pergrosblock);
		Object LCasperIEPP = additionalInterest.getProposedFCI(appliId);
		model.addAttribute("LCasperIEPP", LCasperIEPP);
		String regorlic = additionalInterest.getregiOrLicense(appliId);
		model.addAttribute("regorlic", regorlic);

		String userid = (String) session.getAttribute("userId");
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

			InvestmentDetails invDetailFromDb = investmentDetailsService.getInvestmentDetailsByapplId(appliId);

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
		} catch (Exception e) {
			logger.error(String.format("^^^^ dispalyAgendaNote exception ^^^ %s", e.getMessage()));
		}

		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveByisfapcId(appliId);
		if (IncentiveDetail != null) {
			model.addAttribute("ISF_Claim_Reim", IncentiveDetail.getISF_Claim_Reim());
			model.addAttribute("ISF_Reim_SCST", IncentiveDetail.getISF_Reim_SCST());
			model.addAttribute("ISF_Reim_FW", IncentiveDetail.getISF_Reim_FW());
			model.addAttribute("ISF_Reim_BPLW", IncentiveDetail.getISF_Reim_BPLW());
			model.addAttribute("ISF_EX_E_Duty_PC", IncentiveDetail.getISF_EX_E_Duty_PC());
			model.addAttribute("ISF_EX_Mandee_Fee", IncentiveDetail.getISF_EX_Mandee_Fee());

			model.addAttribute("ISF_SGST_Comment", IncentiveDetail.getIsfSgstComment());
			model.addAttribute("ISF_SCST_Comment", IncentiveDetail.getIsfScstComment());
			model.addAttribute("ISF_FW_Comment", IncentiveDetail.getIsffwComment());
			model.addAttribute("ISF_BPL_Comment", IncentiveDetail.getIsfBplComment());
			model.addAttribute("ISF_Elec_Duty_Comment", IncentiveDetail.getIsfElecdutyComment());
			model.addAttribute("ISF_Mandi_Comment", IncentiveDetail.getIsfMandiComment());

			try {
				Long isf_Claim_Reim = IncentiveDetail.getISF_Claim_Reim() == null ? 0
						: IncentiveDetail.getISF_Claim_Reim();
				Long isf_Reim_SCST = IncentiveDetail.getISF_Reim_SCST() == null ? 0
						: IncentiveDetail.getISF_Reim_SCST();
				Long isf_EX_E_Duty_PC = IncentiveDetail.getISF_EX_E_Duty_PC() == null ? 0
						: IncentiveDetail.getISF_EX_E_Duty_PC();
				Long isf_EX_Mandee_Fee = IncentiveDetail.getISF_EX_Mandee_Fee() == null ? 0
						: IncentiveDetail.getISF_EX_Mandee_Fee();
				Long isf_Reim_BPLW = IncentiveDetail.getISF_Reim_BPLW() == null ? 0
						: IncentiveDetail.getISF_Reim_BPLW();
				Long isf_Reim_FW = IncentiveDetail.getISF_Reim_FW() == null ? 0 : IncentiveDetail.getISF_Reim_FW();
				Long total = isf_Claim_Reim + isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW
						+ isf_Reim_FW;
				model.addAttribute("total", total);
			} catch (Exception e) {
				logger.error(String.format(":::: dispalyAgendaNote exception ::::: %s", e.getMessage()));
			}
		}
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		prepareAgendaNote.setAppliId(appliId);
		model.addAttribute("flag", "true");
		if (projectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			model.addAttribute("flag1", "false1");
		}
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		model.addAttribute("prepAgenNotes", prepareAgendaNotes1.getNotes());
		model.addAttribute("scrtnyComment", prepareAgendaNotes1.getPkupScrutinyDetail());
		model.addAttribute("JMDComment", prepareAgendaNotes1.getJmdcomments());
		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Agenda Note Prepared")) {
					investmentDetailsmixedList.add(list);
				}
			}

		}

		common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute(PREPARE_AGENDA_NOTES, prepareAgendaNotes);
		return ("agenda_note_evaluation_view");
	}

	public void common(Model model, HttpServletRequest request, HttpSession session) {

		String appId = (String) session.getAttribute("appId");
		String businId = "";
		String appStr = appId.substring(0, appId.length() - 2);
		businId = appStr + "B1";
		String projId = appStr + "P1";

		if (appId != null) {
			PrepareAgendaNotes prepareAgendaNotesList = prepareAgendaNotesService.findPrepAgendaNotesByAppliId(appId);
			if (prepareAgendaNotesList != null) {
				model.addAttribute("enableIncludeAgenda", "enableIncludeAgenda");
			} else {
				model.addAttribute("enableIncludeAgenda", "");
			}

		}
		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);
		if (evaluateProjectDetails != null) {

			model.addAttribute("newProject", evaluateProjectDetails.getNewProject());
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
			model.addAttribute("solarCaptivePower", evaluateProjectDetails.getSolarCaptivePower());
			model.addAttribute("solarCaptivePowerObserv", evaluateProjectDetails.getSolarCaptivePowerObserv());

			String expension = evaluateProjectDetails.getExpansion() != null ? evaluateProjectDetails.getExpansion()
					: "";
			String diversification = evaluateProjectDetails.getDiversification() != null
					? evaluateProjectDetails.getDiversification()
					: "";

			if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
				// fetch documents from MongoDB
				applicantViewController.existProjDocsFromMongoDB(evaluateProjectDetails.getId(), model);
				model.addAttribute("newProjProdDetail", evaluateProjectDetails.getProductDetails());
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

		// Propriter Details
		List<ProprietorDetails> proprietorDetailsList = proprietorService.findAllByBusinessId(businId);
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

			List<Object[]> productsList = existProjRepository.findProductsByProjId(projId);
			StringBuilder products = new StringBuilder();
			if (!productsList.isEmpty()) {
				for (Object[] prodArr : productsList) {
					if (prodArr.length > 0) {
						for (int k = 0; k < prodArr.length; k++) {
							products = products.append(prodArr[k].toString()).append(", ");
						}
					}
				}
				model.addAttribute("products", products.toString().substring(0, products.toString().lastIndexOf(',')));
			}
			model.addAttribute("products", "Dont have Product");
			List<Object> evalObjList = investRepository.getEvalDetailsByApplId(appId);
			model.addAttribute("evalObjList", evalObjList);

			model.addAttribute("appId", appId);
			model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);
			model.addAttribute("EvaluateProjectDetails", evaluateProjectDetails);
		} catch (Exception e) {
			logger.error(String.format("^^^^common evaluateApplication exception ^^^^^ %s", e.getMessage()));
		}

		List<Object[]> productsList = existProjRepository.findProductsByProjId(projId);
		StringBuilder products = new StringBuilder();
		if (!productsList.isEmpty()) {
			for (Object[] prodArr : productsList) {
				if (prodArr.length > 0) {
					for (int k = 0; k < prodArr.length; k++) {
						products = products.append(prodArr[k].toString()).append(", ");
					}
				}
			}
			model.addAttribute("products", products.toString().substring(0, products.toString().lastIndexOf(',')));
		}

		evalCapitalInvList = evalCapInvService.getEvalCapitalInvListByApplId(appId);
		model.addAttribute("evalCapitalInvList", evalCapitalInvList);

		evalMeanofFinanceList = evalMOf.getEvalMeanofFinanceListByApplId(appId);
		model.addAttribute("evalMeanofFinanceList", evalMeanofFinanceList);

		List<EvaluateExistNewProjDetails> evaluateExistNewProjDetailsList = evaluateExistNewProjDetailsRepository
				.getEvalExistProjListByepdProjDtlId(evaluateProjectDetails.getId());
		model.addAttribute("evaluateExistNewProjDetailsList", evaluateExistNewProjDetailsList);

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

	@PostMapping(value = "/preparedAgendaNote")
	public ModelAndView preparedAgendaNote(
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session, HttpServletRequest request) {
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		prepareAgendaNote.setId(prepareAgendaNotes1.getId());
		prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
		prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
		prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());
		prepareAgendaNote.setUserId(prepareAgendaNotes1.getUserId());
		prepareAgendaNote.setNotes(prepareAgendaNotes1.getNotes());
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setStatus("Agenda Note Prepared");
		prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());
		prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
		prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
		prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
		prepareAgendaNote.setNotes(prepareAgendaNotes.getNotes());
		prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes.getPkupScrutinyDetail());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);

		String userid = (String) session.getAttribute("userId");
		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Application Included In Agenda Note")) {
					investmentDetailsmixedList.add(list);
				}
			}

		}

		ApplicationFwdEntity applicationFwdEntity = new ApplicationFwdEntity();
		List<ApplicationFwdEntity> applicationFwdEntityList = new ArrayList<>();
		applicationFwdEntityList = applicationFwdRepository.findByAppId(prepareAgendaNotes1.getAppliId());
		for (ApplicationFwdEntity applicationFwd : applicationFwdEntityList) {
			applicationFwd.setStatus(false);
			applicationFwdRepository.save(applicationFwd);
		}

		{
			applicationFwdEntity = new ApplicationFwdEntity();

			applicationFwdEntity.setAppId(prepareAgendaNotes1.getAppliId());
			Timestamp currentDate = new Timestamp(System.currentTimeMillis());
			applicationFwdEntity.setFwddate(currentDate);
			applicationFwdEntity.setDepartment("Send for Approval");
			applicationFwdEntity.setName("Send for Approval");
			applicationFwdEntity.setRolname("Send for Approval");
//			applicationFwdEntity.setDepartment((String) session.getAttribute("userd"));
//			applicationFwdEntity.setName("Send for Approval");
//			applicationFwdEntity.setRolname((String) session.getAttribute("userr"));
			applicationFwdEntity.setStatus(true);
			applicationFwdRepository.save(applicationFwdEntity);

		}
		common(model, request, session);
		model.addAttribute("flag", "false");
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		return new ModelAndView("agenda_note_evaluation_view");
	}

	@PostMapping(value = "/approvingAgendaNote")
	public ModelAndView approvingAgendaNote(
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			@RequestParam(value = "appliId", required = false) String[] appliId, HttpSession session,
			HttpServletRequest request) {
		ArrayList<String> roleArr = new ArrayList<>();
		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = appliId;
		List<TblRole> roleList = roleMasterRepository.findAll();;
		String userid = (String) session.getAttribute("userId");
		for (String applId : applicantList) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);

			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Agenda Note Prepared")) {

					
//					list.setRoleMasterList(roleList);
					investmentDetailsmixedList.add(list);
				}

			}
			session.setAttribute("appId", applId);

		}
		common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute(PREPARE_AGENDA_NOTES, prepareAgendaNotes);
		model.addAttribute("flag", "false");
		System.out.println(roleList);
		for (TblRole roleMasterEntity : roleList) {
			roleArr.add(roleMasterEntity.getRole());
			// System.out.println(roleMasterEntity.getRoleName());
		}
		model.addAttribute("roleMasterList", roleArr);

		return new ModelAndView("agendanotesubmitforapproval");
	}

	@GetMapping(value = "/viewPreparedAgendaDetails")
	public ModelAndView viewPreparedAgendaDetails(Model model,
			@RequestParam(value = "appliId", required = false) String id,
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes, HttpSession session,
			HttpServletRequest request) {
		List<TblRole> roleList = null;
		ArrayList<String> roleArr = new ArrayList<>();
		ProjectDetails projectDetails = projectService.getProjectByapplicantDetailId(id);
		model.addAttribute("natureOfProject", projectDetails.getNatureOfProject());
		model.addAttribute("ExistingProducts", projectDetails.getExistingProducts());
		model.addAttribute("existingInstalledCapacity", projectDetails.getExistingInstalledCapacity());
		model.addAttribute("proposedProducts", projectDetails.getProposedProducts());
		model.addAttribute("proposedInstalledCapacity", projectDetails.getProposedInstalledCapacity());
		model.addAttribute("existingGrossBlock", projectDetails.getExistingGrossBlock());
		model.addAttribute("proposedGrossBlock", projectDetails.getProposedGrossBlock());

		model.addAttribute("fullAddress", projectDetails.getFullAddress());
		model.addAttribute("districtName", projectDetails.getDistrictName());
		model.addAttribute("resionName", projectDetails.getResionName());
		String pergrosblock = additionalInterest.getgrossBlock(id);
		model.addAttribute("pergrosblock", pergrosblock);
		Object LCasperIEPP = additionalInterest.getProposedFCI(id);
		model.addAttribute("LCasperIEPP", LCasperIEPP);
		String regorlic = additionalInterest.getregiOrLicense(id);
		model.addAttribute("regorlic", regorlic);
		try {
			ProposedEmploymentDetails emplDetail = emplDtlService.getProposedEmploymentDetailsByappId(id);
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
			logger.error(String.format(":::: viewPreparedAgendaDetails exception ::::: %s", e.getMessage()));
		}
		try {

			InvestmentDetails invDetailFromDb = investmentDetailsService.getInvestmentDetailsByapplId(id);

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
		} catch (Exception e) {
			logger.error(String.format("1111 viewPreparedAgendaDetails exception 1111 %s", e.getMessage()));
		}

		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveByisfapcId(id);
		if (IncentiveDetail != null) {
			model.addAttribute("ISF_Claim_Reim", IncentiveDetail.getISF_Claim_Reim());
			model.addAttribute("ISF_Reim_SCST", IncentiveDetail.getISF_Reim_SCST());
			model.addAttribute("ISF_Reim_FW", IncentiveDetail.getISF_Reim_FW());
			model.addAttribute("ISF_Reim_BPLW", IncentiveDetail.getISF_Reim_BPLW());
			model.addAttribute("ISF_EX_E_Duty_PC", IncentiveDetail.getISF_EX_E_Duty_PC());
			model.addAttribute("ISF_EX_Mandee_Fee", IncentiveDetail.getISF_EX_Mandee_Fee());
			try {
				Long isf_Claim_Reim = IncentiveDetail.getISF_Claim_Reim() == null ? 0
						: IncentiveDetail.getISF_Claim_Reim();
				Long isf_Reim_SCST = IncentiveDetail.getISF_Reim_SCST() == null ? 0
						: IncentiveDetail.getISF_Reim_SCST();
				Long isf_EX_E_Duty_PC = IncentiveDetail.getISF_EX_E_Duty_PC() == null ? 0
						: IncentiveDetail.getISF_EX_E_Duty_PC();
				Long isf_EX_Mandee_Fee = IncentiveDetail.getISF_EX_Mandee_Fee() == null ? 0
						: IncentiveDetail.getISF_EX_Mandee_Fee();
				Long isf_Reim_BPLW = IncentiveDetail.getISF_Reim_BPLW() == null ? 0
						: IncentiveDetail.getISF_Reim_BPLW();
				Long isf_Reim_FW = IncentiveDetail.getISF_Reim_FW() == null ? 0 : IncentiveDetail.getISF_Reim_FW();
				Long total = isf_Claim_Reim + isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW
						+ isf_Reim_FW;
				model.addAttribute("total", total);
			} catch (Exception e) {
				logger.error(String.format("2222 viewPreparedAgendaDetails exception 2222 %s", e.getMessage()));
			}
		}
		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();

		String[] applicantList = { id };
		prepareAgendaNotes.getPrepareAgendaNote();
		String userid = (String) session.getAttribute("userId");
		for (String applId : applicantList) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Agenda Note Prepared")) {
					roleList = roleMasterRepository.findAll();
					investmentDetailsmixedList.add(list);
				}
			}

		}
		common(model, request, session);

		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);

		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		prepareAgendaNote.setAppliId(id);
		prepareAgendaNote.setPrepareAgendaNote(applicantList);
		model.addAttribute(PREPARE_AGENDA_NOTES, prepareAgendaNote);
		model.addAttribute("flag", "true");
		if (projectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			model.addAttribute("flag1", "false1");
		}
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		model.addAttribute("prepAgenNotes", prepareAgendaNotes1.getNotes());
		model.addAttribute("scrtnyComment", prepareAgendaNotes1.getPkupScrutinyDetail());

		for (TblRole roleMasterEntity : roleList) {
			roleArr.add(roleMasterEntity.getRole());
			// System.out.println(roleMasterEntity.getRoleName());
		}
		model.addAttribute("roleMasterList", roleArr);
		return new ModelAndView("agendanotesubmitforapproval");

	}

	@PostMapping(value = "/approvedAgendaNote")
	public ModelAndView approvedAgendaNote(
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		prepareAgendaNote.setId(prepareAgendaNotes1.getId());
		prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
		prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
		prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());
		prepareAgendaNote.setUserId(prepareAgendaNotes1.getUserId());
		prepareAgendaNote.setNotes(prepareAgendaNotes1.getNotes());
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setStatus("Submitted For Approval");
		prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);
		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();

		String userid = (String) session.getAttribute("userId");
		for (String applId : applicantList) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Agenda Note Prepared")) {
					investmentDetailsmixedList.add(list);
				}
			}

		}
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("flag", "false");
		return new ModelAndView("agendanotesubmitforapproval");
	}

	@PostMapping(value = "/circulateAgendaNote")
	public ModelAndView circulateAgendaNote(
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session, HttpServletRequest request) {

		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		String userid = (String) session.getAttribute("userId");
		for (String applId : applicantList) {
			// List<PrepareAgendaNotes> prepareAgendaNotesList =
			// prepareAgendaNoteService.findPrepAgendaNotesListByUserId(userid);
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Approved By MD PICUP")) {
					investmentDetailsmixedList.add(list);
				}
			}
			session.setAttribute("appId", applId);
		}
		prepareAgendaNoteService.common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute(PREPARE_AGENDA_NOTES, prepareAgendaNotes);
		model.addAttribute("flag", "false");
		return new ModelAndView("agendanotesubmittocomplete");
	}

	@GetMapping(value = "/viewApprovedAgendaDetails")
	public ModelAndView viewApprovedAgendaDetails(Model model,
			@RequestParam(value = "appliId", required = false) String id,
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes, HttpSession session,
			HttpServletRequest request) {

		ProjectDetails projectDetails = projectService.getProjectByapplicantDetailId(id);
		model.addAttribute("natureOfProject", projectDetails.getNatureOfProject());
		model.addAttribute("ExistingProducts", projectDetails.getExistingProducts());
		model.addAttribute("existingInstalledCapacity", projectDetails.getExistingInstalledCapacity());
		model.addAttribute("proposedProducts", projectDetails.getProposedProducts());
		model.addAttribute("proposedInstalledCapacity", projectDetails.getProposedInstalledCapacity());
		model.addAttribute("existingGrossBlock", projectDetails.getExistingGrossBlock());
		model.addAttribute("proposedGrossBlock", projectDetails.getProposedGrossBlock());

		model.addAttribute("fullAddress", projectDetails.getFullAddress());
		model.addAttribute("districtName", projectDetails.getDistrictName());
		model.addAttribute("resionName", projectDetails.getResionName());
		String pergrosblock = additionalInterest.getgrossBlock(id);
		model.addAttribute("pergrosblock", pergrosblock);
		Object LCasperIEPP = additionalInterest.getProposedFCI(id);
		model.addAttribute("LCasperIEPP", LCasperIEPP);
		String regorlic = additionalInterest.getregiOrLicense(id);
		model.addAttribute("regorlic", regorlic);

		try {
			ProposedEmploymentDetails emplDetail = emplDtlService.getProposedEmploymentDetailsByappId(id);
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
			logger.error(String.format(":::: viewApprovedAgendaDetails exception ::::: %s", e.getMessage()));
		}
		try {

			InvestmentDetails invDetailFromDb = investmentDetailsService.getInvestmentDetailsByapplId(id);

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
		} catch (Exception e) {
			logger.error(String.format("&&&& viewApprovedAgendaDetails exception &&&& %s", e.getMessage()));
		}

		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveByisfapcId(id);
		if (IncentiveDetail != null) {
			model.addAttribute("ISF_Claim_Reim", IncentiveDetail.getISF_Claim_Reim());
			model.addAttribute("ISF_Reim_SCST", IncentiveDetail.getISF_Reim_SCST());
			model.addAttribute("ISF_Reim_FW", IncentiveDetail.getISF_Reim_FW());
			model.addAttribute("ISF_Reim_BPLW", IncentiveDetail.getISF_Reim_BPLW());
			model.addAttribute("ISF_EX_E_Duty_PC", IncentiveDetail.getISF_EX_E_Duty_PC());
			model.addAttribute("ISF_EX_Mandee_Fee", IncentiveDetail.getISF_EX_Mandee_Fee());
			try {
				Long isf_Claim_Reim = IncentiveDetail.getISF_Claim_Reim() == null ? 0
						: IncentiveDetail.getISF_Claim_Reim();
				Long isf_Reim_SCST = IncentiveDetail.getISF_Reim_SCST() == null ? 0
						: IncentiveDetail.getISF_Reim_SCST();
				Long isf_EX_E_Duty_PC = IncentiveDetail.getISF_EX_E_Duty_PC() == null ? 0
						: IncentiveDetail.getISF_EX_E_Duty_PC();
				Long isf_EX_Mandee_Fee = IncentiveDetail.getISF_EX_Mandee_Fee() == null ? 0
						: IncentiveDetail.getISF_EX_Mandee_Fee();
				Long isf_Reim_BPLW = IncentiveDetail.getISF_Reim_BPLW() == null ? 0
						: IncentiveDetail.getISF_Reim_BPLW();
				Long isf_Reim_FW = IncentiveDetail.getISF_Reim_FW() == null ? 0 : IncentiveDetail.getISF_Reim_FW();
				Long total = isf_Claim_Reim + isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW
						+ isf_Reim_FW;
				model.addAttribute("total", total);
			} catch (Exception e) {
				logger.error(String.format("3333 viewApprovedAgendaDetails exception 33333 %s", e.getMessage()));
			}
		}
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		model.addAttribute("prepAgenNotes", prepareAgendaNotes1.getNotes());
		model.addAttribute("scrtnyComment", prepareAgendaNotes1.getPkupScrutinyDetail());
		model.addAttribute("JMDComment", prepareAgendaNotes1.getComments());
		model.addAttribute("MDComment", prepareAgendaNotes1.getMdScrutinyDetail());
		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();

		String userid = (String) session.getAttribute("userId");
		for (String applId : applicantList) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Approved By MD PICUP")) {
					investmentDetailsmixedList.add(list);
				}
			}
			session.setAttribute("appId", applId);
		}
		prepareAgendaNoteService.common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		prepareAgendaNote.setAppliId(id);
		prepareAgendaNote.setPrepareAgendaNote(applicantList);
		model.addAttribute(PREPARE_AGENDA_NOTES, prepareAgendaNote);
		model.addAttribute("flag", "true");
		if (projectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			model.addAttribute("flag1", "false1");
		}
		return new ModelAndView("agendanotesubmittocomplete");

	}

	@GetMapping(value = "/submitToComitee")
	public ModelAndView submitToComitee(Model model, @RequestParam(value = "applicantId", required = false) String id,
			HttpSession session) {
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService.getPrepareByAppliId(id);
		prepareAgendaNote.setId(prepareAgendaNotes1.getId());
		prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
		prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
		prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());
		prepareAgendaNote.setUserId(prepareAgendaNotes1.getUserId());
		prepareAgendaNote.setNotes(prepareAgendaNotes1.getNotes());
		prepareAgendaNote.setCreateDate(prepareAgendaNotes1.getCreateDate());
		prepareAgendaNote.setCreatedBY(prepareAgendaNotes1.getCreatedBY());
		prepareAgendaNote.setModifyDate(new Timestamp(System.currentTimeMillis()));
		prepareAgendaNote.setCreatedBY(prepareAgendaNotes1.getCreatedBY());
		prepareAgendaNote.setStatus("Submitted To Committee");
		prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());
		prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
		prepareAgendaNote.setJmdScrutinyDetail(prepareAgendaNotes1.getJmdScrutinyDetail());
		prepareAgendaNote.setMdScrutinyDetail(prepareAgendaNotes1.getMdScrutinyDetail());
		prepareAgendaNote.setAcsScrutinyDetail(prepareAgendaNotes1.getAcsScrutinyDetail());
		prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());

		prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
		prepareAgendaNote.setApprovalDate(prepareAgendaNotes1.getApprovalDate());
		prepareAgendaNote.setAcsapprovalDate(prepareAgendaNotes1.getAcsapprovalDate());

		ApplicantDetails applicantDetails = applicantDetailsService
				.getApplicantDetailsByAppId(prepareAgendaNotes1.getAppliId());
		prepareAgendaNote.setAppliacntEmailId(applicantDetails.getAppEmailId());

		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);

		ApplicationFwdEntity applicationFwdEntity = new ApplicationFwdEntity();
		List<ApplicationFwdEntity> applicationFwdEntityList = new ArrayList<>();
		applicationFwdEntityList = applicationFwdRepository.findByAppId(prepareAgendaNotes1.getAppliId());
		for (ApplicationFwdEntity applicationFwd : applicationFwdEntityList) {
			applicationFwd.setStatus(false);
			applicationFwdRepository.save(applicationFwd);
		}

		applicationFwdEntity = new ApplicationFwdEntity();

		applicationFwdEntity.setAppId(prepareAgendaNotes1.getAppliId());
		Timestamp currentDate = new Timestamp(System.currentTimeMillis());
		applicationFwdEntity.setFwddate(currentDate);
		applicationFwdEntity.setDepartment("PICUP Processing Officer");
		applicationFwdEntity.setName("PICUP Processing Officer");
		applicationFwdEntity.setRolname("Submitted To Committee");
		
//		applicationFwdEntity.setDepartment((String) session.getAttribute("userd"));
//		applicationFwdEntity.setName("PICUP Processing Officer");
//		applicationFwdEntity.setRolname((String) session.getAttribute("userr"));
		
		applicationFwdEntity.setStatus(true);
		applicationFwdRepository.save(applicationFwdEntity);

		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeLists = new ArrayList<PrepareAgendaNotes>();

		/* All Lists of Large ,Mega, Super Mega and Mega Plus with Prepared Status */
		List<PrepareAgendaNotes> prepareAgendaNotesAllPreparedLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Mega,Super Mega and Mega Plus List with Approval Status */
		List<PrepareAgendaNotes> prepareAgendaNotesApprovedMegaLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Approval Status */
		List<PrepareAgendaNotes> prepareAgendaNotesApprovedLargeLists = new ArrayList<PrepareAgendaNotes>();

		String userid = (String) session.getAttribute("userId");

//		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
//				.findPrepAgendaNotesListByUserId(userid);

//		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
//		.findPrepAgendaNotesListByUserId(userid);
		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNoteSubmit(session);
		if (!prepareAgendaNotesList.isEmpty()) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaLists.add(list);
				} else if (list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeLists.add(list);
				}
			}
		}
		if (!prepareAgendaNotesList.isEmpty()) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {

				prepareAgendaNotesAllPreparedLists.add(list);

			}
		}

		if (!prepareAgendaNotesList.isEmpty()) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesApprovedMegaLists.add(list);
				} else if (list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					prepareAgendaNotesApprovedLargeLists.add(list);
				}
			}
		}
		model.addAttribute(PREPARE_AGENDA_NOTES, new PrepareAgendaNotes());
		model.addAttribute("CirculateToDepartment", new CirculateToDepartment());

		model.addAttribute("prepareAgendaNotesMegaLists", prepareAgendaNotesMegaLists);
		model.addAttribute("prearepAgendaNotesLargeLists", prearepAgendaNotesLargeLists);

		model.addAttribute("prepareAgendaNotesAllPreparedLists", prepareAgendaNotesAllPreparedLists);

		model.addAttribute("prepareAgendaNotesApprovedMegaLists", prepareAgendaNotesApprovedMegaLists);

		model.addAttribute("prepareAgendaNotesApprovedLargeLists", prepareAgendaNotesApprovedLargeLists);
		return new ModelAndView("prepareAgendaNote");
	}

	// Mega
	@PostMapping(value = "/viewAgendaNoteByMega")
	public ModelAndView viewAgendaNoteByMega(
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {

		List<PrepareAgendaNotes> investmentDetailMegaList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		String userid = (String) session.getAttribute("userId");
		for (String applId : applicantList) {
			// List<PrepareAgendaNotes> prepareAgendaNotesList =
			// prepareAgendaNoteService.findPrepAgendaNotesListByUserId(userid);
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)) {
					investmentDetailMegaList.add(list);
				}
			}

		}
		model.addAttribute("investmentDetailMegaList", investmentDetailMegaList);
		model.addAttribute(PREPARE_AGENDA_NOTES, prepareAgendaNotes);
		session.setAttribute(userid + "##IDML", investmentDetailMegaList);
		session.setAttribute(userid + "##PAN", prepareAgendaNotes);
		model.addAttribute("flag", "false");
		return new ModelAndView("agendanoteviewMegaByPicup");
	}

	@PostMapping(value = "/viewCommonAgendaPrint")
	public ModelAndView viewCommonAgendaPrint(@RequestParam("agendaNote") String agendaNote, Model model,
			HttpSession session) {
		String userid = (String) session.getAttribute("userId");
		List<PrepareAgendaNotes> investmentDetailMegaList = (List<PrepareAgendaNotes>) session
				.getAttribute(userid + "##IDML");
		PrepareAgendaNotes prepareAgendaNotes = (PrepareAgendaNotes) session.getAttribute(userid + "##PAN");

		ProjectDetails projectDetails = null;
		ProposedEmploymentDetails emplDetail = null;
		InvestmentDetails invDetailFromDb = null;
		IncentiveDetails IncentiveDetail = null;

		List<ProjectDetails> plist = new ArrayList<ProjectDetails>();

		List<ProposedEmploymentDetails> elist = new ArrayList<ProposedEmploymentDetails>();
		List<InvestmentDetails> ilist = new ArrayList<InvestmentDetails>();
		List<IncentiveDetails> inctvlist = new ArrayList<IncentiveDetails>();

		model.addAttribute("investmentDetailMegaList", investmentDetailMegaList);
		model.addAttribute(PREPARE_AGENDA_NOTES, prepareAgendaNotes);
		model.addAttribute("agendaNote", agendaNote);
		// int size = investmentDetailMegaList.size();
		for (PrepareAgendaNotes pan : investmentDetailMegaList) {
			String id = pan.getAppliId();

			// vinay

			String projId = id.substring(0, id.length() - 2) + "P1";

			ProjectDetails ProjectDetail = projectService.getProjDetById(projId);

			// To retrieve ExistingProjectDetailsList from table on projectId basis
			if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
				existProjList = existProjectService.getExistProjListById(projId);
				model.addAttribute(EXIST_PROJ_LIST, existProjList);
			} else {
				existProjList.clear();
			}

			BusinessEntityDetails business = businessService.getBusinessEntityByapplicantDetailId(id);
			business.getCompanyPanNo();
			model.addAttribute("gst", business.getGstin());
			model.addAttribute("pan", business.getCompanyPanNo());

			projectDetails = projectService.getProjectByapplicantDetailId(id);
			model.addAttribute("natureOfProject", projectDetails.getNatureOfProject());
			model.addAttribute("ExistingProducts", projectDetails.getExistingProducts());
			model.addAttribute("existingInstalledCapacity", projectDetails.getExistingInstalledCapacity());
			model.addAttribute("proposedProducts", projectDetails.getProposedProducts());
			model.addAttribute("proposedInstalledCapacity", projectDetails.getProposedInstalledCapacity());
			model.addAttribute("existingGrossBlock", projectDetails.getExistingGrossBlock());
			model.addAttribute("proposedGrossBlock", projectDetails.getProposedGrossBlock());

			model.addAttribute("fullAddress", projectDetails.getFullAddress());
			model.addAttribute("districtName", projectDetails.getDistrictName());
			model.addAttribute("resionName", projectDetails.getResionName());
			String pergrosblock = additionalInterest.getgrossBlock(id);
			model.addAttribute("pergrosblock", pergrosblock);
			Object LCasperIEPP = additionalInterest.getProposedFCI(id);
			model.addAttribute("LCasperIEPP", LCasperIEPP);
			String regorlic = additionalInterest.getregiOrLicense(id);
			model.addAttribute("regorlic", regorlic);
			try {
				emplDetail = emplDtlService.getProposedEmploymentDetailsByappId(id);
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
				invDetailFromDb = investmentDetailsService.getInvestmentDetailsByapplId(id);

				model.addAttribute("InvFCI", invDetailFromDb.getInvFci());
				model.addAttribute("category", invDetailFromDb.getInvIndType());
				model.addAttribute("InvTPC", invDetailFromDb.getInvTotalProjCost());
				model.addAttribute("InvLC", invDetailFromDb.getInvLandCost());
				model.addAttribute("InvBuiildC", invDetailFromDb.getInvBuildingCost());
				model.addAttribute("invPlantAndMachCost", invDetailFromDb.getInvPlantAndMachCost());
				model.addAttribute("invMisFixCost", invDetailFromDb.getInvOtherCost());
				model.addAttribute("invMisFixCost", invDetailFromDb.getInvOtherCost());

				model.addAttribute("invCommenceDate", invDetailFromDb.getInvCommenceDate());
				model.addAttribute("propCommProdDate", invDetailFromDb.getPropCommProdDate());

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
			} catch (Exception e) {
				e.printStackTrace();
			}

			String isfId = incentiveDetailsService.getIsfIdByApplId(id);

			IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
			IncentiveDetails IncentiveDetail1 = incentiveDetailsService.getIncentiveisfById(isfId);
			if (IncentiveDetail1 != null) {
				model.addAttribute("ISF_Claim_Reim", IncentiveDetail1.getISF_Claim_Reim());

				// incentiveSpecificDetails.setISF_Claim_Reim(IncentiveDetail1.getISF_Claim_Reim());
				incentiveSpecificDetails.setISF_Reim_SCST(IncentiveDetail1.getISF_Reim_SCST());
				incentiveSpecificDetails.setISF_Reim_FW(IncentiveDetail1.getISF_Reim_FW());
				incentiveSpecificDetails.setISF_Reim_BPLW(IncentiveDetail1.getISF_Reim_BPLW());
				incentiveSpecificDetails.setISF_Ttl_SGST_Reim(IncentiveDetail1.getISF_Ttl_SGST_Reim());

				incentiveSpecificDetails.setISF_Stamp_Duty_EX(IncentiveDetail1.getISF_Stamp_Duty_EX());
				incentiveSpecificDetails
						.setISF_Additonal_Stamp_Duty_EX(IncentiveDetail1.getISF_Additonal_Stamp_Duty_EX());
				incentiveSpecificDetails.setISF_Amt_Stamp_Duty_Reim(IncentiveDetail1.getISF_Amt_Stamp_Duty_Reim());
				incentiveSpecificDetails.setISF_Ttl_Stamp_Duty_EX(IncentiveDetail1.getISF_Ttl_Stamp_Duty_EX());

				incentiveSpecificDetails.setISF_Epf_Reim_UW(IncentiveDetail1.getISF_Epf_Reim_UW());
				incentiveSpecificDetails.setISF_Add_Epf_Reim_SkUkW(IncentiveDetail1.getISF_Add_Epf_Reim_SkUkW());
				incentiveSpecificDetails.setISF_Add_Epf_Reim_DIVSCSTF(IncentiveDetail1.getISF_Add_Epf_Reim_DIVSCSTF());
				incentiveSpecificDetails.setISF_Ttl_EPF_Reim(IncentiveDetail1.getISF_Ttl_EPF_Reim());// 1

				incentiveSpecificDetails.setISF_Cis(IncentiveDetail1.getISF_Cis());
				incentiveSpecificDetails.setISF_ACI_Subsidy_Indus(IncentiveDetail1.getISF_ACI_Subsidy_Indus());
				incentiveSpecificDetails.setISF_Infra_Int_Subsidy(IncentiveDetail1.getISF_Infra_Int_Subsidy());
				incentiveSpecificDetails.setISF_AII_Subsidy_DIVSCSTF(IncentiveDetail1.getISF_AII_Subsidy_DIVSCSTF());
				incentiveSpecificDetails.setISF_Loan_Subsidy(IncentiveDetail1.getISF_Loan_Subsidy());
				incentiveSpecificDetails.setISF_Total_Int_Subsidy(IncentiveDetail1.getISF_Total_Int_Subsidy());

				incentiveSpecificDetails.setISF_Tax_Credit_Reim(IncentiveDetail1.getISF_Tax_Credit_Reim());
				incentiveSpecificDetails.setISF_EX_E_Duty(IncentiveDetail1.getISF_EX_E_Duty());
				incentiveSpecificDetails.setISF_EX_E_Duty_PC(IncentiveDetail1.getISF_EX_E_Duty_PC());
				incentiveSpecificDetails.setISF_EX_Mandee_Fee(IncentiveDetail1.getISF_EX_Mandee_Fee());
				incentiveSpecificDetails.setISF_Indus_Payroll_Asst(IncentiveDetail1.getISF_Indus_Payroll_Asst());
				incentiveSpecificDetails.setTotal_Other_Incentive(IncentiveDetail1.getTotal_Other_Incentive());

				incentiveSpecificDetails.setISF_Cstm_Inc_Status(IncentiveDetail1.getISF_Cstm_Inc_Status());
				incentiveSpecificDetails.setIsfCustIncDocName(IncentiveDetail1.getIsfCustIncDocName());
				incentiveSpecificDetails.setIsfCustIncDoc(IncentiveDetail1.getIsfCustIncDocName().getBytes());

				incentiveSpecificDetails.setIsfSgstComment(IncentiveDetail1.getIsfSgstComment());
				incentiveSpecificDetails.setIsfScstComment(IncentiveDetail1.getIsfScstComment());
				incentiveSpecificDetails.setIsffwComment(IncentiveDetail1.getIsffwComment());
				incentiveSpecificDetails.setIsfBplComment(IncentiveDetail1.getIsfBplComment());
				incentiveSpecificDetails.setIsfElecdutyComment(IncentiveDetail1.getIsfElecdutyComment());
				incentiveSpecificDetails.setIsfMandiComment(IncentiveDetail1.getIsfMandiComment());

				incentiveSpecificDetails.setIsfStampComment(IncentiveDetail1.getIsfStampComment());
				incentiveSpecificDetails.setIsfStampremComment(IncentiveDetail1.getIsfStampremComment());
				incentiveSpecificDetails.setIsfStampscstComment(IncentiveDetail1.getIsfStampscstComment());
				incentiveSpecificDetails.setIsfepfComment(IncentiveDetail1.getIsfepfComment());
				incentiveSpecificDetails.setIsfepfaddComment(IncentiveDetail1.getIsfepfaddComment());
				incentiveSpecificDetails.setIsfepfscComment(IncentiveDetail1.getIsfepfscComment());
				incentiveSpecificDetails.setIsfcapisComment(IncentiveDetail1.getIsfcapisComment());
				incentiveSpecificDetails.setIsfcapisaComment(IncentiveDetail1.getIsfcapisaComment());
				incentiveSpecificDetails.setIsfinfComment(IncentiveDetail1.getIsfinfComment());
				incentiveSpecificDetails.setIsfinfaComment(IncentiveDetail1.getIsfinfaComment());
				incentiveSpecificDetails.setIsfloanComment(IncentiveDetail1.getIsfloanComment());
				incentiveSpecificDetails.setIsfdisComment(IncentiveDetail1.getIsfdisComment());
				incentiveSpecificDetails.setIsfelepodownComment(IncentiveDetail1.getIsfelepodownComment());
				incentiveSpecificDetails.setIsfdifferabilComment(IncentiveDetail1.getIsfdifferabilComment());

				incentiveSpecificDetails.setOthAddRequest1(IncentiveDetail1.getOthAddRequest1());

				incentiveSpecificDetails.setId(IncentiveDetail1.getId());

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

			model.addAttribute("appId", id);
			model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);

			if (projectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
				model.addAttribute("flag1", "false1");
			}

			plist.add(projectDetails);
			elist.add(emplDetail);
			ilist.add(invDetailFromDb);
			inctvlist.add(IncentiveDetail1);

		}

		model.addAttribute("plist", plist);
		model.addAttribute("elist", elist);
		model.addAttribute("ilist", ilist);
		model.addAttribute("inctvlist", inctvlist);
		return new ModelAndView("agenda-note-submit-to-committee-all-View");
	}

	@GetMapping(value = "/viewApprovedAgendaDetailsMegaByPicup")
	public ModelAndView viewApprovedAgendaDetailsMegaByPicup(Model model,
			@RequestParam(value = "appliId", required = false) String id,
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes,
			HttpSession session) {

		ProjectDetails projectDetails = projectService.getProjectByapplicantDetailId(id);
		model.addAttribute("natureOfProject", projectDetails.getNatureOfProject());
		model.addAttribute("ExistingProducts", projectDetails.getExistingProducts());
		model.addAttribute("existingInstalledCapacity", projectDetails.getExistingInstalledCapacity());
		model.addAttribute("proposedProducts", projectDetails.getProposedProducts());
		model.addAttribute("proposedInstalledCapacity", projectDetails.getProposedInstalledCapacity());
		model.addAttribute("existingGrossBlock", projectDetails.getExistingGrossBlock());
		model.addAttribute("proposedGrossBlock", projectDetails.getProposedGrossBlock());

		model.addAttribute("fullAddress", projectDetails.getFullAddress());
		model.addAttribute("districtName", projectDetails.getDistrictName());
		model.addAttribute("resionName", projectDetails.getResionName());
		String pergrosblock = additionalInterest.getgrossBlock(id);
		model.addAttribute("pergrosblock", pergrosblock);
		Object LCasperIEPP = additionalInterest.getProposedFCI(id);
		model.addAttribute("LCasperIEPP", LCasperIEPP);
		String regorlic = additionalInterest.getregiOrLicense(id);
		model.addAttribute("regorlic", regorlic);
		try {
			ProposedEmploymentDetails emplDetail = emplDtlService.getProposedEmploymentDetailsByappId(id);
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
			InvestmentDetails invDetailFromDb = investmentDetailsService.getInvestmentDetailsByapplId(id);

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
		} catch (Exception e) {
			e.printStackTrace();
		}

		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveByisfapcId(id);
		if (IncentiveDetail != null) {
			model.addAttribute("ISF_Claim_Reim", IncentiveDetail.getISF_Claim_Reim());
			model.addAttribute("ISF_Reim_SCST", IncentiveDetail.getISF_Reim_SCST());
			model.addAttribute("ISF_Reim_FW", IncentiveDetail.getISF_Reim_FW());
			model.addAttribute("ISF_Reim_BPLW", IncentiveDetail.getISF_Reim_BPLW());
			model.addAttribute("ISF_EX_E_Duty_PC", IncentiveDetail.getISF_EX_E_Duty_PC());
			model.addAttribute("ISF_EX_Mandee_Fee", IncentiveDetail.getISF_EX_Mandee_Fee());
			try {
				Long isf_Claim_Reim = IncentiveDetail.getISF_Claim_Reim() == null ? 0
						: IncentiveDetail.getISF_Claim_Reim();
				Long isf_Reim_SCST = IncentiveDetail.getISF_Reim_SCST() == null ? 0
						: IncentiveDetail.getISF_Reim_SCST();
				Long isf_EX_E_Duty_PC = IncentiveDetail.getISF_EX_E_Duty_PC() == null ? 0
						: IncentiveDetail.getISF_EX_E_Duty_PC();
				Long isf_EX_Mandee_Fee = IncentiveDetail.getISF_EX_Mandee_Fee() == null ? 0
						: IncentiveDetail.getISF_EX_Mandee_Fee();
				Long isf_Reim_BPLW = IncentiveDetail.getISF_Reim_BPLW() == null ? 0
						: IncentiveDetail.getISF_Reim_BPLW();
				Long isf_Reim_FW = IncentiveDetail.getISF_Reim_FW() == null ? 0 : IncentiveDetail.getISF_Reim_FW();
				Long total = isf_Claim_Reim + isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW
						+ isf_Reim_FW;
				model.addAttribute("total", total);
			} catch (Exception e) {

				e.printStackTrace();
			}
		}
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		model.addAttribute("prepAgenNotes", prepareAgendaNotes1.getNotes());
		model.addAttribute("prepAgenNotes", prepareAgendaNotes1.getNotes());
		model.addAttribute("prepAgenNotes", prepareAgendaNotes1.getNotes());
		model.addAttribute("scrtnyComment", prepareAgendaNotes1.getPkupScrutinyDetail());
		model.addAttribute("JMDComment", prepareAgendaNotes1.getComments());
		model.addAttribute("MDComment", prepareAgendaNotes1.getMdScrutinyDetail());
		List<PrepareAgendaNotes> investmentDetailMegaList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();

		String userid = (String) session.getAttribute("userId");
		for (String applId : applicantList) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId) && list.getStatus().equalsIgnoreCase("ApprovedByHOD")) {
					investmentDetailMegaList.add(list);
				}
			}

		}
		model.addAttribute("investmentDetailMegaList", investmentDetailMegaList);
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		prepareAgendaNote.setAppliId(id);
		prepareAgendaNote.setPrepareAgendaNote(applicantList);
		model.addAttribute(PREPARE_AGENDA_NOTES, prepareAgendaNote);
		model.addAttribute("flag", "true");
		if (projectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			model.addAttribute("flag1", "false1");
		}
		return new ModelAndView("agendanoteviewMegaByPicup");

	}

	@GetMapping(value = "/viewCommonAgenda")
	public ModelAndView viewCommonAgenda(Model model, @RequestParam(value = "appliId", required = false) String id,
			@ModelAttribute(PREPARE_AGENDA_NOTES) @Validated PrepareAgendaNotes prepareAgendaNotes,
			HttpSession session) {
		return new ModelAndView("agenda-note-submit-to-committee-all-View");

	}

	@PostMapping("/circulateAgendaReport")
	public ModelAndView circulateAgendaReport(
			@ModelAttribute("CirculateToDepartment") CirculateToDepartment circulateToDepartment, BindingResult result,
			HttpSession session, Model model) {

		try {
			List<String> newlist = new ArrayList<>();

			String str = circulateToDepartment.getDeptName().toString();

			String deptName = "";

			List<String> aalist = new ArrayList<>();

			String[] strarr = str.split(",");
			for (String a : strarr) {
				if (a.contains("@")) {
					deptName += a;

					newlist.add(a);
				} else if (a.contains("|")) {
					deptName += a;
					aalist.add(deptName);
					deptName = "";
				} else {
					deptName += a;

				}

				if (deptName != "") {
					deptName += ",";
				}

			}
			List<CirculateToDepartment> ciDepartmentsList = new ArrayList<>();
			for (String aa : aalist) {
				String data[] = aa.split(",");
				CirculateToDepartment circulateToDepartment1 = new CirculateToDepartment();

				if (data[0] != null) {
					circulateToDepartment1.setDeptEmail(data[0]);
				}
				if (data[1] != null) {
					circulateToDepartment1.setDeptName(data[1]);
				}
				if (data[2] != null) {
					circulateToDepartment1.setDeptId(data[2].replaceAll("[^a-zA-Z0-9]", ""));
				}
				circulateToDepartment1.setCirculateId("CD" + atomicInteger.getAndIncrement());
				circulateToDepartment1.setAppId(circulateToDepartment.getAppId());
				circulateToDepartment1.setNoteReportStatus("Circulate Agenda Report");

				List<CirculateToDepartment> circulateToDepartment3 = circulateToDepartmentRepository
						.SearchByAppIdAnddeptId(circulateToDepartment1.getAppId(), circulateToDepartment1.getDeptId());

				if (circulateToDepartment3.size() > 0) {
					for (CirculateToDepartment cr : circulateToDepartment3) {
						if (cr.getAppId().equals(circulateToDepartment1.getAppId())
								|| cr.getDeptId().equals(circulateToDepartment1.getDeptId())) {

						}
					}
				} else {
					CirculateToDepartment circulateToDepartment2 = circulateToDepartmentService
							.save(circulateToDepartment1);
					ciDepartmentsList.add(circulateToDepartment2);
				}
			}
			if (!ciDepartmentsList.isEmpty()) {
				String appID = circulateToDepartment.getAppId();
				if (!appID.isEmpty()) {
					DSCPdfUploadEntity disPdfUploadEntity = agendaReportService.sendAgendaReportListLarge(model,
							session, appID);

					for (CirculateToDepartment cirList : ciDepartmentsList) {
						cirList.setFileId(disPdfUploadEntity.getId());
						circulateToDepartmentService.save(cirList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/agendaNode");

	}

	@PostMapping("/circulateAgendaReportMega")
	public ModelAndView circulateAgendaReportMega(
			@ModelAttribute("CirculateToDepartment") CirculateToDepartment circulateToDepartment, BindingResult result,
			HttpSession session, Model model) {

		try {
			List<String> newlist = new ArrayList<>();

			String str = circulateToDepartment.getDeptName().toString();

			String deptName = "";

			List<String> aalist = new ArrayList<>();

			String[] strarr = str.split(",");
			for (String a : strarr) {
				if (a.contains("@")) {
					deptName += a;

					newlist.add(a);
				} else if (a.contains("|")) {
					deptName += a;
					aalist.add(deptName);
					deptName = "";
				} else {
					deptName += a;

				}

				if (deptName != "") {
					deptName += ",";
				}

			}
			List<CirculateToDepartment> ciDepartmentsList = new ArrayList<>();
			for (String aa : aalist) {
				String data[] = aa.split(",");
				CirculateToDepartment circulateToDepartment1 = new CirculateToDepartment();

				if (data[0] != null) {
					circulateToDepartment1.setDeptEmail(data[0]);
				}
				if (data[1] != null) {
					circulateToDepartment1.setDeptName(data[1]);
				}
				if (data[2] != null) {
					circulateToDepartment1.setDeptId(data[2].replaceAll("[^a-zA-Z0-9]", ""));
				}
				circulateToDepartment1.setCirculateId("CD" + atomicInteger.getAndIncrement());
				circulateToDepartment1.setAppId(circulateToDepartment.getAppId());
				circulateToDepartment1.setNoteReportStatus("Circulate Agenda Report");

				List<CirculateToDepartment> circulateToDepartment3 = circulateToDepartmentRepository
						.SearchByAppIdAnddeptId(circulateToDepartment1.getAppId(), circulateToDepartment1.getDeptId());

				if (circulateToDepartment3.size() > 0) {
					for (CirculateToDepartment cr : circulateToDepartment3) {
						if (cr.getAppId().equals(circulateToDepartment1.getAppId())
								|| cr.getDeptId().equals(circulateToDepartment1.getDeptId())) {

						}
					}
				} else {
					CirculateToDepartment circulateToDepartment2 = circulateToDepartmentService
							.save(circulateToDepartment1);
					ciDepartmentsList.add(circulateToDepartment2);
				}
			}

			if (!ciDepartmentsList.isEmpty()) {
				String appID = circulateToDepartment.getAppId();
				if (!appID.isEmpty()) {
					DSCPdfUploadEntity disPdfUploadEntity = agendaReportService.sendAgendaReportListMega(model, session,
							appID);

					for (CirculateToDepartment cirList : ciDepartmentsList) {
						cirList.setFileId(disPdfUploadEntity.getId());
						circulateToDepartmentService.save(cirList);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/agendaNode");

	}
}
