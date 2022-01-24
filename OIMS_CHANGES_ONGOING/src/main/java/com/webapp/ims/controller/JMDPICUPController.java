package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.PREPARE_AGENDA_NOTES;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
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
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.itextpdf.text.DocumentException;
import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.dis.model.MinutesOfMeetingDis;
import com.webapp.ims.dis.model.MomCirculateNoteDis;
import com.webapp.ims.dis.model.MomUploadDocumentsDis;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.dis.repository.DisMinutesOfMeetingRepository;
import com.webapp.ims.dis.repository.DisMomUploadDocumentsRepository;
import com.webapp.ims.dis.service.DisMinutesOfMeetingService;
import com.webapp.ims.dis.service.DisPrepareAgendaNoteService;
import com.webapp.ims.uam.service.TblUsersService;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicationFwdEntity;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.EvaluateCapitalInvestment;
import com.webapp.ims.model.EvaluateMeanOfFinance;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.MinutesOfMeeting;
import com.webapp.ims.model.MomCirculateNote;
import com.webapp.ims.model.MomUploadDocuments;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
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
import com.webapp.ims.repository.IncentiveDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.MinutesOfMeetingRepository;
import com.webapp.ims.repository.MomUploadDocumentsRepository;
import com.webapp.ims.repository.ProjectRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BreakupCostService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.DigitalSignatureService;
import com.webapp.ims.service.EvaluateCapInvestService;
import com.webapp.ims.service.EvaluateInvestMentDetailsService;
import com.webapp.ims.service.EvaluateMeanofFinanceService;
import com.webapp.ims.service.EvaluateProjectDetailsService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.MeansOfFinanceService;
import com.webapp.ims.service.MinutesOfMeetingService;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;
import com.webapp.ims.service.PrepareAgendaNoteService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;
import com.webapp.ims.service.StateDetailsService;
import com.webapp.ims.service.WReturnCUSIDSTATUSBeanService;
import com.webapp.ims.service.impl.AdditionalInterest;

@Controller
public class JMDPICUPController {
	private final Logger logger = LoggerFactory.getLogger(JMDPICUPController.class);
	private ApplicantDetailsService appDtlService;
	private ApplicantDocumentService appDocService;
	private InvestmentDetailsService invDtlService;
	private PhaseWiseInvestmentDetailsService pwInvDtlService;
	private BreakupCostService brkupService;
	private MeansOfFinanceService mofService;
	private ProposedEmploymentDetailsService emplDtlService;
	private ProprietorDetailsService proprietorService;	
	private IncentiveDetailsService incentiveDetailsService;
	private ProjectService projectService;
	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();
	private String applicantId = null;

	List<PrepareAgendaNotes> panList = new ArrayList<>();
	AdditionalInterest additionalInterest;
	InvestmentDetailsRepository investRepository;	
	PrepareAgendaNoteService prepareAgendaNoteService;
	private StateDetailsService stateDetailsService;
	@Autowired	
	private MinutesOfMeetingService minutesOfMeetingService;
	@Autowired
	private PrepareAgendaNotesService prepareAgendaNotesService;
	@Autowired
	InvestmentDetailsService investmentDetailsService;
	@Autowired
	private MomUploadDocumentsRepository momUploadDocumentsRepository;
	@Autowired
	private TblUsersService loginService;
	@Autowired
	MinutesOfMeetingRepository minutesOfMeetingRepository;
	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	InvestmentDetailsRepository investmentDetailsRepository;
	
	@Autowired
	ApplicantDetailsRepository applicantDetailsRepository;
	
	@Autowired
	WReturnCUSIDSTATUSBeanService wReturnCUSIDSTATUSBeanService;
	
	@Autowired
	DisMinutesOfMeetingService disMinutesOfMeetingService;
	
	@Autowired
	DisMinutesOfMeetingRepository disMinutesOfMeetingRepository;

	@Autowired
	IncentiveDetailsRepository incentiveDetailsRepository;

	@Autowired
	BusinessEntityDetailsService businessEntityDetailsService;
	
	@Autowired
	DisPrepareAgendaNoteService disPrepareAgendaNoteService;
 
	@Autowired
	ApplicationDetailsViewController applicationDetailsViewController;
	@Autowired
	DigitalSignatureService digitalSignatureService;

	@Autowired
	ApplicationFwdRepository applicationFwdRepository;
	
	@Autowired
	DISPrepareAgendaNotesRepository dISPrepareAgendaNotesRepository;

	@Autowired
	public JMDPICUPController(ApplicantDetailsService appDtlService, ApplicantDocumentService appDocService,
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
		this.proprietorService = proprietorService;
		this.incentiveDetailsService = incentiveDetailsService;
		this.projectService = projectService;
		this.additionalInterest = additionalInterest;
		this.investRepository = investRepository;
		this.prepareAgendaNoteService = prepareAgendaNoteService;
		this.stateDetailsService = stateDetailsService;
	}

	@Autowired
	DashboardController dashboardController;

	@Autowired
	private EvaluateProjectDetailsService evaluateProjectDetailsService;

	@Autowired
	public ApplicationDetailsViewController applicantViewController;

	@Autowired
	public EvaluateExistNewProjDetailsRepository evaluateExistNewProjDetailsRepository;

	@Autowired
	public EvaluateAuditTrialRepository evaluateAuditTrialRepository;
	
	@Autowired
	DISPrepareAgendaNotesRepository disprepareAgendaRepos;

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
	private EvaluateMeanofFinanceService evalMOf;
	
	@Autowired
	private DisMomUploadDocumentsRepository dismomUploadDocumentsRepository;

	private List<EvaluateCapitalInvestment> evalCapitalInvList = new LinkedList<>();
	private List<EvaluateMeanOfFinance> evalMeanofFinanceList = new LinkedList<>();

	@GetMapping(value = "/dashboardJMDPICUP")
	public ModelAndView dashboardJMDPICUP(Model model, HttpSession sessio) {
		dashboardController.dashboard(model, sessio);
		return new ModelAndView("dashboard-JMDPICUP");
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

	@GetMapping(value = "/viewJMDPICUPApplicationDetails")
	public ModelAndView viewJMDPICUPApplicationDetails(
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session, Model model) {

		applicationDetailsViewController.commonViewApplicationDetails(applId, session, model);

		return new ModelAndView("view-all-application-details-JMDPICUP");

	}

	/*
	 * @GetMapping(value = "/viewSMEJMDPICUPApplications") public ModelAndView
	 * viewSMEJMDPICUPApplications() { return new
	 * ModelAndView("select-policy-SME-JMDPICUP"); }
	 */

	@GetMapping(value = "/viewJMDPICUPSMEApplicationDetails")
	public ModelAndView viewJMDPICUPSMEApplicationDetails(
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session, Model model) {

		applicationDetailsViewController.commonViewApplicationDetails(applId, session, model);
		return new ModelAndView("view-all-application-details-SME-JMDPICUP");

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

	@GetMapping(value = "/JMDPICUPApplicationForLoc")
	public String JMDPICUPApplicationForLoc(ModelMap model) {
		List<Object> investmentDetailsList = investRepository.getAllDetailsByAppId();
		model.addAttribute("investmentDetailsList", investmentDetailsList);
		return "view-incentive-applications-JMDPICUP";
	}

	@GetMapping(value = "/JMDPICUPSMEApplicationForLoc")
	public String JMDPICUPSMEApplicationForLoc(ModelMap model) {
		logger.debug("Applications SME For LOC");
		List<Object> investmentDetailsList = investRepository.getAllDetailsSMEByAppId();
		model.addAttribute("investmentDetailsList", investmentDetailsList);
		return "view-incentive-applications-SME-JMDPICUP";
	}

	@GetMapping(value = "/selectPolicyJMDPICUP")
	public String selectPolicyJMDPICUP(Model model) {
		return "select-policy-JMDPICUP";
	}

	// vinay aproval agenda

	@PostMapping(value = "/approvedAgendaNoteJmd")
	public ModelAndView approvedAgendaNoteJmd(
			@ModelAttribute(PREPARE_AGENDA_NOTES) PrepareAgendaNotes prepareAgendaNotes, Model model,
			@RequestParam(value = "multiDept", required = false) ArrayList<String> multiDept, HttpSession session) {

		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		if (prepareAgendaNotes1 != null) {
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
			prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());
			prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
			prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
			prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
			prepareAgendaNote.setSubmissionDate(new Date());

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

			ApplicationFwdEntity applicationFwdEntity = new ApplicationFwdEntity();
			List<ApplicationFwdEntity> applicationFwdEntityList = new ArrayList<>();
			applicationFwdEntityList = applicationFwdRepository.findByAppId(prepareAgendaNotes1.getAppliId());
			for (ApplicationFwdEntity applicationFwd : applicationFwdEntityList) {
				applicationFwd.setStatus(false);
				applicationFwdRepository.save(applicationFwd);
			}

			if (multiDept != null) {
				for (String dept : multiDept) {
					applicationFwdEntity = new ApplicationFwdEntity();

					applicationFwdEntity.setAppId(prepareAgendaNotes1.getAppliId());
					Timestamp currentDate = new Timestamp(System.currentTimeMillis());
					applicationFwdEntity.setFwddate(currentDate);
					applicationFwdEntity.setDepartment(dept);
					applicationFwdEntity.setName(dept);
					applicationFwdEntity.setRolname(dept);
					applicationFwdEntity.setStatus(true);
					applicationFwdRepository.save(applicationFwdEntity);
				}
			}

			model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
			model.addAttribute("flag", "false");
			
			ApplicantDetails applicantDetails =applicantDetailsRepository.getApplicantDetailsByAppId(prepareAgendaNotes.getAppliId());
			System.out.println(prepareAgendaNotes.getAppliId());
			if (applicantDetails != null) {
				applicantDetails.setStatusCode("05");
				applicantDetails.setRemarks("Application Form Submitted for Approval");
				applicantDetails.setPendancyLevel("pending on Dept");
 
				wReturnCUSIDSTATUSBeanService.WReturn_CUSID_STATUS(applicantDetails);
			}
		}
		
		

		return new ModelAndView("agendanotesubmitforapprovaljmd");
	}
// vinay forward to mdpicup

	@RequestMapping(value = "/saveForwardedToMdpicup", method = RequestMethod.GET)
	public ModelAndView saveForwardedToMdpicup(
			@RequestParam(value = "applicantId", required = false) String applicantId,
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes,
			BindingResult result, Model model, HttpSession session)
			throws IOException, GeneralSecurityException, DocumentException {
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();

		/*
		 * byte[] signature =
		 * digitalSignatureService.checkDigitalSingleItem(applicantId, model); if
		 * (signature == null) { model.addAttribute("jmdsignaturekeyMsg",
		 * " Please Insert USB Digital key"); List<PrepareAgendaNotes>
		 * prepareAgendaNotesList = prepareAgendaNoteService.getAllPrepareAgendaNote();
		 * List for Mega,Super Mega and Mega Plus List with Ready Status
		 * List<PrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new
		 * ArrayList<PrepareAgendaNotes>();
		 * 
		 * List for Large with Ready Status List<PrepareAgendaNotes>
		 * prearepAgendaNotesLargeMdPiCupLists = new ArrayList<PrepareAgendaNotes>();
		 * 
		 * if (prepareAgendaNotesList.size() > 0) { for (PrepareAgendaNotes list :
		 * prepareAgendaNotesList) { if
		 * (list.getStatus().equalsIgnoreCase("Submitted For Approval") &&
		 * (list.getCategoryIndsType().equalsIgnoreCase("Mega") ||
		 * list.getCategoryIndsType().equalsIgnoreCase("Mega Plus") ||
		 * list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
		 * prepareAgendaNotesMegaMdPiCupLists.add(list); } else if
		 * (list.getStatus().equalsIgnoreCase("Submitted For Approval") &&
		 * list.getCategoryIndsType().equalsIgnoreCase("Large")) {
		 * prearepAgendaNotesLargeMdPiCupLists.add(list); }
		 * 
		 * } }
		 * 
		 * List<PrepareAgendaNotes> prepareAgendaNotesListComment = new
		 * ArrayList<PrepareAgendaNotes>();
		 * 
		 * if (prepareAgendaNotesList.size() > 0) { for (PrepareAgendaNotes list :
		 * prepareAgendaNotesList) { if
		 * ((list.getCategoryIndsType().equalsIgnoreCase("Mega") ||
		 * list.getCategoryIndsType().equalsIgnoreCase("Mega Plus") ||
		 * list.getCategoryIndsType().equalsIgnoreCase("Super Mega") ||
		 * list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getComments()
		 * != null) { prepareAgendaNotesListComment.add(list); } } }
		 * 
		 * model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		 * model.addAttribute("prepareAgendaNotesMegaMdPiCupLists",
		 * prepareAgendaNotesMegaMdPiCupLists);
		 * model.addAttribute("prearepAgendaNotesLargeMdPiCupLists",
		 * prearepAgendaNotesLargeMdPiCupLists);
		 * 
		 * model.addAttribute("prepareAgendaNotesListComment",
		 * prepareAgendaNotesListComment);
		 * 
		 * return new ModelAndView("jmdPiCupDepartAgendaNote");
		 * 
		 * } else {
		 */
		if (applicantId != null && !applicantId.isEmpty()) {
			PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService.getPrepareByAppliId(applicantId);
			prepareAgendaNote.setId(prepareAgendaNotes1.getId());
			prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
			prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
			prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());
			prepareAgendaNote.setUserId(prepareAgendaNotes1.getUserId());
			prepareAgendaNote.setNotes(prepareAgendaNotes1.getNotes());
			prepareAgendaNote.setCreatedBY("AdminUser");
			prepareAgendaNote.setStatus("Forward to MDPICUP");// change
			prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
			prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
			prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
			prepareAgendaNote.setSubjectName(prepareAgendaNotes1.getSubjectName());
			prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());
			prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
			
			prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
			prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
			prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
			prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);
		}
		
		System.out.println("prepareAgendaNotes.getAppliId() : " + applicantId);
		ApplicantDetails applicantDetails =applicantDetailsRepository.getApplicantDetailsByAppId(applicantId);
		if (applicantDetails != null) {
			applicantDetails.setStatusCode("05");
			applicantDetails.setRemarks("Application Form Submitted for Approval");
			applicantDetails.setPendancyLevel("Pending on MD");
 
			wReturnCUSIDSTATUSBeanService.WReturn_CUSID_STATUS(applicantDetails);
		}
		ApplicationFwdEntity applicationFwdEntity = new ApplicationFwdEntity();
		List<ApplicationFwdEntity> applicationFwdEntityList = new ArrayList<>();
		boolean status = true;
		applicationFwdEntityList = applicationFwdRepository.findByAppId(prepareAgendaNote.getAppliId());
		for (ApplicationFwdEntity applicationFwd : applicationFwdEntityList) {
			applicationFwd.setStatus(false);
			applicationFwdRepository.save(applicationFwd);
		}

		applicationFwdEntity = new ApplicationFwdEntity();

		applicationFwdEntity.setAppId(prepareAgendaNote.getAppliId());
		Timestamp currentDate = new Timestamp(System.currentTimeMillis());
		applicationFwdEntity.setFwddate(currentDate);
		applicationFwdEntity.setDepartment("MD");
		applicationFwdEntity.setName("MD");
		applicationFwdEntity.setRolname("MD");
		applicationFwdEntity.setStatus(true);
		applicationFwdEntity = applicationFwdRepository.save(applicationFwdEntity);

		// }

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<PrepareAgendaNotes>();
		List<PrepareAgendaNotes> prepareAgendaNotesjmdCommentsAll = prepareAgendaNoteService.getAllPrepareAgendaNote();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeMdPiCupLists.add(list);
				}
			}
		}

		List<PrepareAgendaNotes> prepareAgendaNotesListComment = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesjmdCommentsAll.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesjmdCommentsAll) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getComments() != null) {
					prepareAgendaNotesListComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);
		model.addAttribute("prepareAgendaNotesListComment", prepareAgendaNotesListComment);
		return new ModelAndView("jmdPiCupDepartAgendaNote");
	}

	// vinay md agenda

	@RequestMapping(value = "/jmdPiCupAgendaNote", method = RequestMethod.GET)
	public ModelAndView JmdPiCupAgendaNote(Model model, HttpSession session) {

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<PrepareAgendaNotes>();
		
		
		
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ( (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeMdPiCupLists.add(list);
				}

			}
		}

		
		/*
		 * if (prepareAgendaNotesList.size() > 0) { for (PrepareAgendaNotes list :
		 * prepareAgendaNotesList) { if
		 * (list.getStatus().equalsIgnoreCase("Submitted For Approval") &&
		 * (list.getCategoryIndsType().equalsIgnoreCase("Mega") ||
		 * list.getCategoryIndsType().equalsIgnoreCase("Mega Plus") ||
		 * list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
		 * prepareAgendaNotesMegaMdPiCupLists.add(list); } else if
		 * (list.getStatus().equalsIgnoreCase("Submitted For Approval") &&
		 * list.getCategoryIndsType().equalsIgnoreCase("Large")) {
		 * prearepAgendaNotesLargeMdPiCupLists.add(list); }
		 * 
		 * } }
		 */
		List<PrepareAgendaNotes> prepareAgendaNotesListComment = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getComments() != null) {
					prepareAgendaNotesListComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);

		model.addAttribute("prepareAgendaNotesListComment", prepareAgendaNotesListComment);

		return new ModelAndView("jmdPiCupDepartAgendaNote");

	}

	// JMD Agenda Note DIS get List and show
	@RequestMapping(value = "/jmdPiCupAgendaNoteDis", method = RequestMethod.GET)
	public ModelAndView JmdPiCupAgendaNoteDis(Model model, HttpSession session) {

		List<DISPrepareAgendaNotes> prepareAgendaNotesList = disPrepareAgendaNoteService.getAllDisPrepareAgendaNote();
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<DISPrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<DISPrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Submitted to JMD for Comments")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					
					if(list.getSubmissionDate() != null)
					{
					 Timestamp megaSubmissionDt=list.getSubmissionDate();
					 java.text.DateFormat dfl = new SimpleDateFormat("dd-MM-yyyy");
					 String requiredDate = dfl.format(megaSubmissionDt);
					model.addAttribute("megaSubmissionDate", requiredDate);
					}
					
					prepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Submitted to JMD for Comments")
						&& list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					
					if(list.getSubmissionDate() != null)
					{
					 Timestamp megaSubmissionDt=list.getSubmissionDate();
					 DateFormat dfl = new SimpleDateFormat("dd-MM-yyyy");
					 String requiredDate = dfl.format(megaSubmissionDt);
					model.addAttribute("largeSubmissionDate", requiredDate);
					}
					
					prearepAgendaNotesLargeMdPiCupLists.add(list);
				}

			}
		}

		List<DISPrepareAgendaNotes> prepareAgendaNotesListComment = new ArrayList<DISPrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getJmdComment() != null) {
					prepareAgendaNotesListComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);

		model.addAttribute("prepareAgendaNotesListComment", prepareAgendaNotesListComment);

		return new ModelAndView("jmdPiCupDepartAgendaNoteDis");

	}

	// Start DIS comment section of jmdpicup by PAnkaj
	@RequestMapping(value = "/saveCommenJMdPicupDepartmentDis", method = RequestMethod.POST)
	public ModelAndView saveCommenJMdPicupDepartmentDis(
			@ModelAttribute("PrepareAgendaNotesDis") @Validated DISPrepareAgendaNotes prepareAgendaNotes,
			BindingResult result, Model model, HttpSession session) {
		String userid = (String) session.getAttribute("userId");
		DISPrepareAgendaNotes prepareAgendaNote = new DISPrepareAgendaNotes();
		DISPrepareAgendaNotes prepareAgendaNotes1 = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByAppliId(prepareAgendaNotes.getAppliId());
		prepareAgendaNote.setId(prepareAgendaNotes1.getId());
		prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
		prepareAgendaNote.setUserId(userid);
		prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
		prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());
		prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		prepareAgendaNote.setStatus(prepareAgendaNotes1.getStatus());
		prepareAgendaNote.setCreatedBY(prepareAgendaNotes1.getCreatedBY());
		prepareAgendaNote.setCreateDate(new Timestamp(System.currentTimeMillis()));
		prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
		prepareAgendaNote.setNote(prepareAgendaNotes1.getNote());
		prepareAgendaNote.setNodalAgency(prepareAgendaNotes1.getNodalAgency());
		prepareAgendaNote.setJmdComment(prepareAgendaNotes.getJmdComment());
		// prepareAgendaNote.setNotes(prepareAgendaNotes1.getNotes());
		// prepareAgendaNote.setSubjectName(prepareAgendaNotes1.getSubjectName());
		// prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
		// prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
		// prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
		// prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
		disPrepareAgendaNoteService.savePrepareAgendaNotes(prepareAgendaNote);

		List<DISPrepareAgendaNotes> prepareAgendaNotesList = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByUserId(userid);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<DISPrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<DISPrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		if (!prepareAgendaNotesList.isEmpty()) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Submitted to JMD for Comments")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Submitted to JMD for Comments")
						&& list.getCategoryIndsType().trim().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeMdPiCupLists.add(list);
				}
			}
		}

		List<DISPrepareAgendaNotes> prepareAgendaNotesListComment = new ArrayList<DISPrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Submitted to JMD for Comments")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
								|| list.getCategoryIndsType().trim().equalsIgnoreCase("Large"))
						&& list.getJmdComment() != null) {
					prepareAgendaNotesListComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);

		model.addAttribute("prepareAgendaNotesListComment", prepareAgendaNotesListComment);

		return new ModelAndView("jmdPiCupDepartAgendaNoteDis");
	}

	// end DIS comment section by Pankaj

	// Start DIS Save Forward To MDPICUP by Pankaj
	@RequestMapping(value = "/saveForwardedToMdpicupDis", method = RequestMethod.GET)
	public ModelAndView saveForwardedToMdpicupDis(
			@RequestParam(value = "applicantId", required = false) String applicantId,
			@ModelAttribute("PrepareAgendaNotesDis") DISPrepareAgendaNotes disprepareAgendaNotes, BindingResult result,
			Model model, HttpSession session) throws IOException, GeneralSecurityException, DocumentException {
		DISPrepareAgendaNotes prepareAgendaNote = new DISPrepareAgendaNotes();
		byte[] signature = digitalSignatureService.checkDigitalSingleItem(applicantId, model);
		if (applicantId != null && !applicantId.isEmpty()) {
			DISPrepareAgendaNotes prepareAgendaNotes1 = disPrepareAgendaNoteService
					.findDisPrepAgendaNotesByAppliId(applicantId);
			prepareAgendaNote.setId(prepareAgendaNotes1.getId());
			prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
			prepareAgendaNote.setUserId(prepareAgendaNotes1.getUserId());
			prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
			prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());
			prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
			prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
			prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
			prepareAgendaNote.setStatus("Agenda Forwarded to MDPICUP for Approval");
			prepareAgendaNote.setCreatedBY(prepareAgendaNotes1.getCreatedBY());
			prepareAgendaNote.setCreateDate(new Timestamp(System.currentTimeMillis()));
			prepareAgendaNote.setNote(prepareAgendaNotes1.getNote());
			prepareAgendaNote.setNodalAgency(prepareAgendaNotes1.getNodalAgency());
			prepareAgendaNote.setJmdComment(prepareAgendaNotes1.getJmdComment());
			prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
			// prepareAgendaNote.setNotes(prepareAgendaNotes1.getNotes());
			// prepareAgendaNote.setSubjectName(prepareAgendaNotes1.getSubjectName());
			// prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
			// prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
			// prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
			// prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
			disPrepareAgendaNoteService.savePrepareAgendaNotes(prepareAgendaNote);
		}

		List<DISPrepareAgendaNotes> prepareAgendaNotesList = disPrepareAgendaNoteService.getAllDisPrepareAgendaNote();
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<DISPrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<DISPrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Submitted to JMD for Comments")// change
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Submitted to JMD for Comments")
						&& list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeMdPiCupLists.add(list);
				}
			}
		}

		List<DISPrepareAgendaNotes> prepareAgendaNotesListComment = new ArrayList<DISPrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getJmdComment() != null) {
					prepareAgendaNotesListComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);
		model.addAttribute("prepareAgendaNotesListComment", prepareAgendaNotesListComment);
		return new ModelAndView("jmdPiCupDepartAgendaNoteDis");
	}
	// End DIS Save Forward To MDPICUP by Pankaj

	// minutes of meeting
	@GetMapping(value = "/momgobyJMDPICUP")
	public ModelAndView listbyJMDPICUP(ModelMap model, HttpSession session) {
		logger.debug("MINUTES OF MEETING BY DIC..................");
		String userId = (String) session.getAttribute("userId");

		if (userId != null && !userId.isEmpty()) {
			List<MomCirculateNote> minutesOfMeetingEmpowerCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingSactionCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingGosCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingList = new ArrayList<>();
			
			List<Object> minutesOfMeetingObjectList = minutesOfMeetingRepository.findAllMOMbyStatus("JMD");

			Object[] row;
			ObjectMapper mapper = new ObjectMapper();
			MomCirculateNote momNote = new MomCirculateNote();
			for (Object object : minutesOfMeetingObjectList) {
				momNote = new MomCirculateNote();
				row = (Object[]) object;
				
				HashMap<String, Object> map = new HashMap<>();
				

				if (row[0] != null)
					map.put("id", row[0].toString());
				if (row[1] != null)
					map.put("gosAppID", row[1].toString());
				if (row[2] != null)
					map.put("minutesOfMeetingOrGos", row[2]);
				if (row[3] != null)
					map.put("committeeDepartments", row[3].toString());
				if (row[4] != null)
					map.put("dateOfMeeting", row[4]);
				if (row[5] != null)
					map.put("committeeName", row[5].toString());
				if (row[6] != null)
					map.put("gosName", row[6].toString());
				if (row[7] != null)
					map.put("deptName", row[7].toString());
				if (row[8] != null)
					map.put("noteReportStatus", row[8].toString());
				if (row[9] != null)
					map.put("gosNo", row[9].toString());
				momNote = mapper.convertValue(map, MomCirculateNote.class);
				minutesOfMeetingList.add(momNote);
			}

			if (minutesOfMeetingList.size() > 0) {
				for (MomCirculateNote list : minutesOfMeetingList) {
					if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee")
							&& list.getMinutesOfMeetingOrGos().equalsIgnoreCase("MinutesofMeeting")
							&& list.getNoteReportStatus().equalsIgnoreCase("MOM Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository
								.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null
								&& !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingEmpowerCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee")
							&& list.getNoteReportStatus().equalsIgnoreCase("MOMs Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository
								.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null
								&& !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingSactionCommitList.add(list);
					} else if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("GOs")
							&& list.getNoteReportStatus().equalsIgnoreCase("Go Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository
								.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null
								&& !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadGosFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingGosCommitList.add(list);
					}
				}
			}

			model.addAttribute("minutesOfMeetingEmpowerCommitList", minutesOfMeetingEmpowerCommitList);
			model.addAttribute("minutesOfMeetingSactionCommitList", minutesOfMeetingSactionCommitList);
			model.addAttribute("minutesOfMeetingGosCommitList", minutesOfMeetingGosCommitList);

		}
		model.addAttribute("minutesOfMeeting", new MinutesOfMeeting());
		return new ModelAndView("minutesOfMeetingsbyJMDPICUP");
	}

	@GetMapping(value = "/disMomgobyJMDPICUP")
	public ModelAndView listbyJMDPICUPDis(ModelMap model, HttpSession session) {
		logger.debug("MINUTES OF MEETING BY DIS JMD..................");
		String userId = (String) session.getAttribute("userId");

		if (userId != null && !userId.isEmpty()) {
			List<MomCirculateNoteDis> minutesOfMeetingEmpowerCommitList = new ArrayList<MomCirculateNoteDis>();
			List<MomCirculateNoteDis> minutesOfMeetingSactionCommitList = new ArrayList<MomCirculateNoteDis>();
			List<MomCirculateNoteDis> minutesOfMeetingGosCommitList = new ArrayList<MomCirculateNoteDis>();
			List<MomCirculateNoteDis> minutesOfMeetingCabinetCommitList = new ArrayList<MomCirculateNoteDis>();
			List<MomCirculateNoteDis> minutesOfMeetingjmdList = new ArrayList<MomCirculateNoteDis>();
			List<Object> minutesOfMeetingObjectList = disMinutesOfMeetingRepository.findAllDisMOMbyStatus("JMD");

			Object[] row;
			ObjectMapper mapper = new ObjectMapper();
			MomCirculateNoteDis momNote = new MomCirculateNoteDis();
			for (Object object : minutesOfMeetingObjectList) {
				momNote = new MomCirculateNoteDis();
				row = (Object[]) object;
				/* System.out.println(row.length); */
				HashMap<String, Object> map = new HashMap<>();
				/*
				 * System.out.println("Element " + Arrays.toString(row));
				 */
				if (row[0] != null)
					map.put("id", row[0].toString());
				if (row[1] != null)
					map.put("gosAppID", row[1].toString());
				if (row[2] != null)
					map.put("minutesOfMeetingOrGos", row[2]);
				if (row[3] != null)
					map.put("committeeDepartments", row[3].toString());
				if (row[4] != null)
					map.put("dateOfMeeting", row[4]);
				if (row[5] != null)
					map.put("committeeName", row[5].toString());
				if (row[6] != null)
					map.put("gosName", row[6].toString());
				if (row[7] != null)
					map.put("deptName", row[7].toString());
				if (row[8] != null)
					map.put("noteReportStatus", row[8].toString());
				if (row[9] != null)
					map.put("gosNo", row[9].toString());


				momNote = mapper.convertValue(map, MomCirculateNoteDis.class);
				minutesOfMeetingjmdList.add(momNote);
			}

			// List<MinutesOfMeetingDis> minutesOfMeetingList =
			// disMinutesOfMeetingService.getMinutesOfMeetinguserId(userId);
			if (minutesOfMeetingjmdList.size() > 0) {
				for (MomCirculateNoteDis list : minutesOfMeetingjmdList) {

					if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee")
							&& list.getMinutesOfMeetingOrGos().equalsIgnoreCase("MinutesofMeeting")
							&& list.getNoteReportStatus().equalsIgnoreCase("MOM Report")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository
								.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null
								&& !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingEmpowerCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee")
							&& list.getMinutesOfMeetingOrGos().equalsIgnoreCase("MinutesofMeeting")
							&& list.getNoteReportStatus().equalsIgnoreCase("MOMs Report")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository
								.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null
								&& !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingSactionCommitList.add(list);
					} else if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("GOs")
							&& list.getNoteReportStatus().equalsIgnoreCase("GOs Report")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository
								.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null
								&& !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadGosFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingGosCommitList.add(list);
					} else if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("CabinetNote")
							&& list.getNoteReportStatus().equalsIgnoreCase("Cabinet Report")) {
						MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository
								.getMomByMomId(list.getId());
						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null
								&& !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadCabinetFile(momUploadDocuments.getFileName());
						}
						minutesOfMeetingCabinetCommitList.add(list);
					}

				}
			}
			
			model.addAttribute("minutesOfMeetingEmpowerCommitList", minutesOfMeetingEmpowerCommitList);
			model.addAttribute("minutesOfMeetingSactionCommitList", minutesOfMeetingSactionCommitList);
			model.addAttribute("minutesOfMeetingGosCommitList", minutesOfMeetingGosCommitList);
			model.addAttribute("minutesOfMeetingCabinetCommitList", minutesOfMeetingCabinetCommitList);
	
		}
		model.addAttribute("minutesOfMeeting", new MinutesOfMeetingDis());
		return new ModelAndView("/Disbursement/disMinutesOfMeetingsbyJMDPICUP");
	}

	// vinay new method

	@RequestMapping(value = "/viewAgendaDetailsByJMdPiCupDepart", method = RequestMethod.GET)
	public ModelAndView viewAgendaDetailsByJMdPiCupDepart(Model model,
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session,
			HttpServletRequest request) {

		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();

		if (applId != null && !applId.isEmpty()) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)) {
					investmentDetailsmixedList.add(list);
				}
				session.setAttribute("appId", applId);
			}
		}
		prepareAgendaNoteService.common(model, request, session);

		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("flag", "false");
		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		return new ModelAndView("prepareAgendaNoteByJMdPicup");
	}

// end 

	@RequestMapping(value = "/viewAgendaDetailsByJMdPiCupDepartDis", method = RequestMethod.GET)
	public ModelAndView viewAgendaDetailsByJMdPiCupDepartDis(Model model,
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session,
			HttpServletRequest request) {

		List<DISPrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<DISPrepareAgendaNotes>();

		if (applId != null && !applId.isEmpty()) {
			List<DISPrepareAgendaNotes> prepareAgendaNotesList = disPrepareAgendaNoteService
					.getAllDisPrepareAgendaNote();
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)) {
					investmentDetailsmixedList.add(list);
				}
				session.setAttribute("appId", applId);
			}
		}
		disPrepareAgendaNoteService.common(model, request, session);

		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("flag", "false");
		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		return new ModelAndView("/Disbursement/prepareAgendaNoteByJMdPicupDis");
	}

	@RequestMapping(value = "/displayAgendaDetailsByJMdPiCupDepartDis", method = RequestMethod.POST)
	public ModelAndView displayAgendaDetailsByJMdPiCupDepartDis(Model model,
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session,
			HttpServletRequest request) {

		List<DISPrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<DISPrepareAgendaNotes>();

		if (applId != null && !applId.isEmpty()) {
			List<DISPrepareAgendaNotes> prepareAgendaNotesList = disPrepareAgendaNoteService
					.getAllDisPrepareAgendaNote();
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)) {
					investmentDetailsmixedList.add(list);
				}
				session.setAttribute("appId", applId);
			}
		}
		disPrepareAgendaNoteService.common(model, request, session);

		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("flag", "true");
		String applid = (String) session.getAttribute("appId");
		DISPrepareAgendaNotes prepareAgendaNotesdis1 = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByAppliId(applid);
		model.addAttribute("disprepAgendaNotes", prepareAgendaNotesdis1.getNote());
		model.addAttribute("nodalAgencyNm", prepareAgendaNotesdis1.getNodalAgency());
		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		return new ModelAndView("/Disbursement/prepareAgendaNoteByJMdPicupDis");
	}

	// comment section of jmdpicup
	@RequestMapping(value = "/saveCommenSubByJMdPicupDepartment", method = RequestMethod.POST)
	public ModelAndView saveCommenSubByJMdPicupDepartment(
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes,
			BindingResult result, Model model, HttpSession session) {
		String userid = (String) session.getAttribute("userId");
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		prepareAgendaNote.setId(prepareAgendaNotes1.getId());
		prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
		prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
		prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());

		prepareAgendaNote.setUserId(userid);
		prepareAgendaNote.setNotes(prepareAgendaNotes1.getNotes());
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setStatus(prepareAgendaNotes1.getStatus());
		prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		prepareAgendaNote.setSubjectName(prepareAgendaNotes.getSubjectName());
		prepareAgendaNote.setComments(prepareAgendaNotes.getComments());
		prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
		prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
		prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
		prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);

		
		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
				.findPrepAgendaNotesListByUserId(userid);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

		if (!prepareAgendaNotesList.isEmpty()) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Submitted For Approval")
						&& list.getCategoryIndsType().trim().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeMdPiCupLists.add(list);
				}
			}
		}

		List<PrepareAgendaNotes> prepareAgendaNotesListComment = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ( (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
								|| list.getCategoryIndsType().trim().equalsIgnoreCase("Large"))
						&& list.getComments() != null) {
					prepareAgendaNotesListComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);

		model.addAttribute("prepareAgendaNotesListComment", prepareAgendaNotesListComment);

		return new ModelAndView("jmdPiCupDepartAgendaNote");
	}

	// comment section end

	// vinay md agenda end

	@GetMapping(value = "JMDPICUPEvaluateApplication")
	public String JMDPICUPEvaluateApplication(@RequestParam(value = "applicantId", required = false) String applId,
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
		return "evaluation-view-JMDPICUP";
	}

	// dispay agenda detail

	@RequestMapping(value = "/dispalyAgendaNoteByJMdPiCupDepart", method = RequestMethod.GET)
	public ModelAndView dispalyAgendaNoteByJMdPiCupDepart(
			@RequestParam(value = "appliId", required = false) String appliId,
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session, HttpServletRequest request) {

		ProjectDetails projectDetails = projectRepository.getProjectByapplicantDetailId(appliId);
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
		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		if (appliId != null && !appliId.isEmpty()) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(appliId)) {
					investmentDetailsmixedList.add(list);
				}
			}
		}

		prepareAgendaNoteService.common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("PrepareAgendaNotes", prepareAgendaNotes);
		return new ModelAndView("prepareAgendaNoteByJMdPicup");
	}

// end display agenda 
	@RequestMapping(value = "/downloadSupportJMD", method = RequestMethod.POST)
	public void downloadSupportJMD(
			@ModelAttribute("downloadSupportJMD") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session, HttpServletResponse response) {
		String appId = (String) session.getAttribute("appId");
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
			e.printStackTrace();
		}

	}

	@RequestMapping(value = "/downloadDocSctnyComt", method = RequestMethod.GET)
	public void downloadDocSctnyComt(@RequestParam(value = "applicantId", required = false) String applId, Model model,
			HttpServletResponse response, HttpSession session) {

		PrepareAgendaNotes prepareAgendaNotes = prepareAgendaNotesService.findPrepAgendaNotesByAppliId(applId);
		String fileDocName = prepareAgendaNotes.getPkupFilename();
		byte[] fileDocData = prepareAgendaNotes.getPkupFiledata();
		response.setHeader("Content-Disposition", "attachment; filename=" + fileDocName + "");
		response.setHeader("Content-Type", "application/pdf");
		InputStream is = new ByteArrayInputStream(fileDocData);
		try {
			IOUtils.copy(is, response.getOutputStream());
		} catch (IOException e) {

			e.printStackTrace();
		}

		try {
			is.close();
		} catch (IOException e) {

			e.printStackTrace();
		}
	}

}
