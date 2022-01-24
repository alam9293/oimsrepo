package com.webapp.ims.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
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
import com.webapp.ims.login.model.Login;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicationFwdEntity;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.MinutesOfMeeting;
import com.webapp.ims.model.MomCirculateNote;
import com.webapp.ims.model.MomUploadDocuments;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.repository.ApplicationFwdRepository;
import com.webapp.ims.repository.LoginRepository;
import com.webapp.ims.repository.MinutesOfMeetingRepository;
import com.webapp.ims.repository.MomUploadDocumentsRepository;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.DigitalSignatureService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.MinutesOfMeetingService;
import com.webapp.ims.service.PrepareAgendaNoteService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;
import com.webapp.ims.service.SendingEmail;
import com.webapp.ims.service.WReturnCUSIDSTATUSBeanService;
import com.webapp.ims.service.impl.AdditionalInterest;

@Controller
public class HeadNodalDepartmentController {

	@Autowired
	PrepareAgendaNoteService prepareAgendaNoteService;
	
	@Autowired
	MinutesOfMeetingRepository minutesOfMeetingRepository;
	
	@Autowired
	DISPrepareAgendaNotesRepository disprepareAgendaRepos;
	
	@Autowired
	DisMinutesOfMeetingRepository disMinutesOfMeetingRepository;

	

	@Autowired
	private DisMomUploadDocumentsRepository dismomUploadDocumentsRepository;

	@Autowired
	DisMinutesOfMeetingService disMinutesOfMeetingService;
	
	@Autowired
	DisPrepareAgendaNoteService disPrepareAgendaNoteService;
	
	@Autowired
	MinutesOfMeetingService minutesOfMeetingService;
	@Autowired
	MomUploadDocumentsRepository momUploadDocumentsRepository;
	@Autowired
	DigitalSignatureService digitalSignatureService;

	@Autowired
	BusinessEntityDetailsService businessService;
	@Autowired
	ProprietorDetailsService proprietorService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	AdditionalInterest additionalInterest;
	@Autowired
	private ProposedEmploymentDetailsService emplDtlService;
	@Autowired
	InvestmentDetailsService investmentDetailsService;
	@Autowired
	IncentiveDetailsService incentiveDetailsService;
	@Autowired
	private PrepareAgendaNotesService prepareAgendaNotesService;
	@Autowired
	private SendingEmail sendingEmail;
	@Autowired
	LoginRepository loginRepository;

	@Autowired
	WReturnCUSIDSTATUSBeanService wReturnCUSIDSTATUSBeanService;
	
	@Autowired
	ApplicantDetailsRepository applicantDetailsRepository;
	
	@Autowired
	ApplicationFwdRepository applicationFwdRepository;

	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();

	@RequestMapping(value = "/preAgenNoteheadNodDepart", method = RequestMethod.GET)
	public ModelAndView preAgenNoteheadNodDepart(Model model, HttpSession session) {
		
		List<PrepareAgendaNotes> prepareAgendaNotesList2=prepareAgendaNoteService.getAllPrepareAgendaNote();
		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
	
		/* List for Mega,Super Mega and Mega Plus List with forwardToASC Status */
		List<PrepareAgendaNotes> preAgenNoteHeadNodalDepartLists = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					preAgenNoteHeadNodalDepartLists.add(list);
				}
			}
		}

		List<PrepareAgendaNotes> prepareAgendaNotesListACSComment = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList2.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList2) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))
						&& list.getAcsScrutinyDetail() != null) {
					prepareAgendaNotesListACSComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("preAgenNoteHeadNodalDepartLists", preAgenNoteHeadNodalDepartLists);
		model.addAttribute("prepareAgendaNotesListACSComment", prepareAgendaNotesListACSComment);
		return new ModelAndView("preAganNoteHeadNodalDepart");

	}

	// DIS Agenda Note by Pankaj
	@RequestMapping(value = "/preAgenNoteheadNodDepartDis", method = RequestMethod.GET)
	public ModelAndView preAgenNoteheadNodDepartDis(Model model, HttpSession session) {

		List<DISPrepareAgendaNotes> prepareAgendaNotesList = disPrepareAgendaNoteService.getAllDisPrepareAgendaNote();

		/* List for Mega,Super Mega and Mega Plus List with forwardToASC Status */
		List<DISPrepareAgendaNotes> preAgenNoteHeadNodalDepartLists = new ArrayList<DISPrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to ACS for Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					
					if(list.getSubmissionDate() != null)
					{
					 Timestamp megaSubmissionDt=list.getSubmissionDate();
					 DateFormat dfl = new SimpleDateFormat("dd-MM-yyyy");
					 String requiredDate = dfl.format(megaSubmissionDt);
					model.addAttribute("megaSubmissionDate", requiredDate);
					}
					
					preAgenNoteHeadNodalDepartLists.add(list);
				}

			}
		}

		List<DISPrepareAgendaNotes> prepareAgendaNotesListACSComment = new ArrayList<DISPrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))
						&& list.getAcsComments() != null) {
					prepareAgendaNotesListACSComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		model.addAttribute("preAgenNoteHeadNodalDepartLists", preAgenNoteHeadNodalDepartLists);
		model.addAttribute("prepareAgendaNotesListACSComment", prepareAgendaNotesListACSComment);
		return new ModelAndView("preAganNoteHeadNodalDepartDis");

	}

	@RequestMapping(value = "/viewAgendaHeadNodalDepart", method = RequestMethod.GET)
	public ModelAndView viewAgendaHeadNodalDepart(Model model,
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session,
			HttpServletRequest request) {

		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();

		if (applId != null && !applId.isEmpty()) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)) {
					investmentDetailsmixedList.add(list);
				}
			}
			session.setAttribute("appId", applId);
		}
		prepareAgendaNoteService.common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("flag", "false");
		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		return new ModelAndView("viewAganHeadNodalDepart");

	}

	// DIS View Agebda by Pankaj
	@RequestMapping(value = "/viewAgendaHeadNodalDepartDis", method = RequestMethod.GET)
	public ModelAndView viewAgendaHeadNodalDepartDis(Model model,
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
		return new ModelAndView("/Disbursement/prepareAgendaNoteByACSDis");
	}

	@RequestMapping(value = "/displayAgendaHeadNodalDepartDis", method = RequestMethod.POST)
	public ModelAndView displayAgendaHeadNodalDepartDis(Model model,
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
	
		return new ModelAndView("/Disbursement/prepareAgendaNoteByACSDis");
	}

	@RequestMapping(value = "/dispalyAgendaNoteByHeadNodalDepart", method = RequestMethod.GET)
	public ModelAndView dispalyAgendaNoteByHeadNodalDepart(
			@RequestParam(value = "appliId", required = false) String appliId,
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session, HttpServletRequest request) {

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
			final String SMALL = "Small";
			final String MEDIUM = "Medium";
			final String LARGE = "Large";
			final String MEGA = "Mega";
			final String MEGAPLUS = "Mega Plus";
			final String SUPERMEGA = "Super Mega";
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
			e.printStackTrace();
		}

		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveByisfapcId(appliId);
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
		model.addAttribute("JMDComment", prepareAgendaNotes1.getComments());
		model.addAttribute("MDComment", prepareAgendaNotes1.getMdScrutinyDetail());

		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		if (appliId != null && !appliId.isEmpty()) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(appliId)) {
					investmentDetailsmixedList.add(list);
				}
			}
			session.setAttribute("appId", appliId);
		}
		prepareAgendaNoteService.common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("PrepareAgendaNotes", prepareAgendaNotes);
		return new ModelAndView("viewAganHeadNodalDepart");
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

	// 1262: circulate agenda note
	@RequestMapping(value = "/sendEmailConcernDepartment", method = RequestMethod.POST)
	public String sendEmailConcernDepartment(@RequestParam("val") String val) {
		String[] str = val.split(",");
		List<String> depList = new ArrayList<>(Arrays.asList(str));
		List<Login> temp = loginRepository.findByDepartmentIn(depList);
		temp.forEach(x -> sendingEmail.sentEmail("Subject", "message", x.getUserName()));
		return "redirect:/preAgenNoteheadNodDepart";
		// 1262: circulate agenda note
	}

	@RequestMapping(value = "/saveCommenSubByHeadNodalDepartment", method = RequestMethod.POST)
	public ModelAndView saveCommenSubByHeadNodalDepartment(
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

		prepareAgendaNote.setAcsScrutinyDetail(prepareAgendaNotes.getAcsScrutinyDetail());

		prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
		prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
		prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
		prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());
		prepareAgendaNote.setMdScrutinyDetail(prepareAgendaNotes1.getMdScrutinyDetail());
		prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> preAgenNoteHeadNodalDepartLists = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to ACS for Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					preAgenNoteHeadNodalDepartLists.add(list);
				}

			}
		}

		List<PrepareAgendaNotes> prepareAgendaNotesListACSComment = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to ACS for Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))
						&& list.getAcsScrutinyDetail() != null) {
					prepareAgendaNotesListACSComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("preAgenNoteHeadNodalDepartLists", preAgenNoteHeadNodalDepartLists);
		model.addAttribute("prepareAgendaNotesListACSComment", prepareAgendaNotesListACSComment);
		return new ModelAndView("preAganNoteHeadNodalDepart");
	}

	// DIS ACS Comments
	@RequestMapping(value = "/saveCommentHeadNodalDepartmentDis", method = RequestMethod.POST)
	public ModelAndView saveCommentHeadNodalDepartmentDis(
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

		prepareAgendaNote.setNote(prepareAgendaNotes1.getNote());
		prepareAgendaNote.setNodalAgency(prepareAgendaNotes1.getNodalAgency());
		prepareAgendaNote.setJmdComment(prepareAgendaNotes1.getJmdComment());
		prepareAgendaNote.setMdComments(prepareAgendaNotes1.getMdComments());
		prepareAgendaNote.setAcsComments(prepareAgendaNotes.getAcsComments());
		// prepareAgendaNote.setSubjectName(prepareAgendaNotes1.getSubjectName());
		// prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
		// prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
		// prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
		// prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
		disPrepareAgendaNoteService.savePrepareAgendaNotes(prepareAgendaNote);

		List<DISPrepareAgendaNotes> prepareAgendaNotesList = disPrepareAgendaNoteService.getAllDisPrepareAgendaNote();
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<DISPrepareAgendaNotes> preAgenNoteHeadNodalDepartLists = new ArrayList<DISPrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to ACS for Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					preAgenNoteHeadNodalDepartLists.add(list);
				}

			}
		}

		List<DISPrepareAgendaNotes> prepareAgendaNotesListACSComment = new ArrayList<DISPrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to ACS for Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))
						&& list.getAcsComments() != null) {
					prepareAgendaNotesListACSComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		model.addAttribute("preAgenNoteHeadNodalDepartLists", preAgenNoteHeadNodalDepartLists);
		model.addAttribute("prepareAgendaNotesListACSComment", prepareAgendaNotesListACSComment);
		return new ModelAndView("preAganNoteHeadNodalDepartDis");
	}

	// Approved by ACS
	@RequestMapping(value = "/savePreAganByHeadNodalDepart", method = RequestMethod.GET)
	public ModelAndView savePreAganByHeadNodalDepart(
			@RequestParam(value = "applicantId", required = false) String applicantId,
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes,
			BindingResult result, Model model, HttpSession session) {
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		if (applicantId != null && !applicantId.isEmpty()) {
			PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService.getPrepareByAppliId(applicantId);
			prepareAgendaNote.setId(prepareAgendaNotes1.getId());
			prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
			prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
			prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());
			prepareAgendaNote.setUserId(prepareAgendaNotes1.getUserId());
			prepareAgendaNote.setNotes(prepareAgendaNotes1.getNotes());
			prepareAgendaNote.setCreatedBY("AdminUser");
			prepareAgendaNote.setStatus("Approved BY ACS");
			prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
			prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
			prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());

			prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
			prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
			prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
			prepareAgendaNote.setNotes(prepareAgendaNotes1.getNotes());
			prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
			prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());
			prepareAgendaNote.setMdScrutinyDetail(prepareAgendaNotes1.getMdScrutinyDetail());
			prepareAgendaNote.setAcsScrutinyDetail(prepareAgendaNotes1.getAcsScrutinyDetail());
			prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
			prepareAgendaNote.setAcsapprovalDate(new Date());
			prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);

		}

		ApplicationFwdEntity applicationFwdEntity = new ApplicationFwdEntity();
		List<ApplicationFwdEntity> applicationFwdEntityList = new ArrayList<>();
		applicationFwdEntityList = applicationFwdRepository.findByAppId(prepareAgendaNote.getAppliId());
		for (ApplicationFwdEntity applicationFwd : applicationFwdEntityList) {
			applicationFwd.setStatus(false);
			applicationFwdRepository.save(applicationFwd);
		}

		ApplicantDetails applicantDetails =applicantDetailsRepository.getApplicantDetailsByAppId(applicantId);
		if (applicantDetails != null) {
			applicantDetails.setStatusCode("06");
			applicantDetails.setRemarks("Application Approval");
			applicantDetails.setPendancyLevel("Approved by ACS");
 
			wReturnCUSIDSTATUSBeanService.WReturn_CUSID_STATUS(applicantDetails);
		}
		applicationFwdEntity = new ApplicationFwdEntity();

		applicationFwdEntity.setAppId(prepareAgendaNote.getAppliId());
		Timestamp currentDate = new Timestamp(System.currentTimeMillis());
		applicationFwdEntity.setFwddate(currentDate);
		applicationFwdEntity.setDepartment("Pickup");
		applicationFwdEntity.setName("Submit");
		applicationFwdEntity.setRolname("Submit");
		applicationFwdEntity.setStatus(true);
		applicationFwdRepository.save(applicationFwdEntity);

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> preAgenNoteHeadNodalDepartLists = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to ACS for Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					preAgenNoteHeadNodalDepartLists.add(list);
				}

			}
		}
		List<PrepareAgendaNotes> prepareAgendaNotesacsCommentsAll=prepareAgendaNoteService.getAllPrepareAgendaNote();
		List<PrepareAgendaNotes> prepareAgendaNotesListACSComment = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesacsCommentsAll.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesacsCommentsAll) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))
						&& list.getAcsScrutinyDetail() != null) {
					prepareAgendaNotesListACSComment.add(list);
				}
			}
		}

		model.addAttribute("prepareAgendaNotesListACSComment", prepareAgendaNotesListACSComment);

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("preAgenNoteHeadNodalDepartLists", preAgenNoteHeadNodalDepartLists);
		return new ModelAndView("preAganNoteHeadNodalDepart");

	}

	// DIS Approved by ACS
	@RequestMapping(value = "/savePreAganByHeadNodalDepartDis", method = RequestMethod.GET)
	public ModelAndView savePreAganByHeadNodalDepartDis(
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
			prepareAgendaNote.setStatus("Approved BY ACS Industries");
			prepareAgendaNote.setCreatedBY(prepareAgendaNotes1.getCreatedBY());
			prepareAgendaNote.setCreateDate(new Timestamp(System.currentTimeMillis()));
			prepareAgendaNote.setNote(prepareAgendaNotes1.getNote());
			prepareAgendaNote.setNodalAgency(prepareAgendaNotes1.getNodalAgency());
			prepareAgendaNote.setJmdComment(prepareAgendaNotes1.getJmdComment());
			prepareAgendaNote.setMdComments(prepareAgendaNotes1.getMdComments());
			prepareAgendaNote.setAcsComments(prepareAgendaNotes1.getAcsComments());
			prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
			prepareAgendaNote.setApprovalDate(prepareAgendaNotes1.getApprovalDate());
			prepareAgendaNote.setAcsapprovalDate(new Timestamp(System.currentTimeMillis()));

			disPrepareAgendaNoteService.savePrepareAgendaNotes(prepareAgendaNote);
		}
		List<DISPrepareAgendaNotes> prepareAgendaNotesList = disPrepareAgendaNoteService.getAllDisPrepareAgendaNote();
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<DISPrepareAgendaNotes> preAgenNoteHeadNodalDepartLists = new ArrayList<DISPrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to ACS for Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					preAgenNoteHeadNodalDepartLists.add(list);
				}

			}
		}

		List<DISPrepareAgendaNotes> prepareAgendaNotesListACSComment = new ArrayList<DISPrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))
						&& list.getAcsComments() != null) {
					prepareAgendaNotesListACSComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		model.addAttribute("preAgenNoteHeadNodalDepartLists", preAgenNoteHeadNodalDepartLists);
		model.addAttribute("prepareAgendaNotesListACSComment", prepareAgendaNotesListACSComment);
		return new ModelAndView("preAganNoteHeadNodalDepartDis");
	}
	
	@GetMapping(value = "/nodalmomgo")
	public String nodalmomgo(ModelMap model, HttpSession session) {
		String userId = (String) session.getAttribute("userId");
		if (userId != null && !userId.isEmpty()) {
			List<MomCirculateNote> minutesOfMeetingEmpowerCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingSactionCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingGosCommitList = new ArrayList<>();
			List<MomCirculateNote> minutesOfMeetingList = new ArrayList<>();
			// List<MinutesOfMeeting> minutesOfMeetingList =
			// minutesOfMeetingService.getMinutesOfMeetinguserId(userId);
			// List<MinutesOfMeeting> minutesOfMeetingList = minutesOfMeetingService.getAlluserId();
			List<Object> minutesOfMeetingObjectList = minutesOfMeetingRepository.findAllMOMbyStatus("ACS");

			Object[] row;
			ObjectMapper mapper = new ObjectMapper();
			MomCirculateNote momNote = new MomCirculateNote();
			for (Object object : minutesOfMeetingObjectList) {
				momNote = new MomCirculateNote();
				row = (Object[]) object;
				/* System.out.println(row.length); */
				HashMap<String, Object> map = new HashMap<>();
				/* System.out.println("Element " + Arrays.toString(row)); */

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
					if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee") && list.getMinutesOfMeetingOrGos().equalsIgnoreCase("MinutesofMeeting")
							&& list.getNoteReportStatus().equalsIgnoreCase("MOM Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingEmpowerCommitList.add(list);
					} else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee") && list.getNoteReportStatus().equalsIgnoreCase("MOMs Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
							list.setUploadMomFile(momUploadDocuments.getFileName());
						}

						minutesOfMeetingSactionCommitList.add(list);
					} else if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("GOs") && list.getNoteReportStatus().equalsIgnoreCase("Go Report")) {
						MomUploadDocuments momUploadDocuments = momUploadDocumentsRepository.getMomByMomId(list.getId());

						if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
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
		return "nodalMomgo";
		
	}


@GetMapping(value = "/nodalmomgoDis")
public ModelAndView momgoDislistDis(ModelMap model, HttpSession session) {
	
	String userId = (String) session.getAttribute("userId");

	if (userId != null && !userId.isEmpty()) {
		List<MomCirculateNoteDis> minutesOfMeetingEmpowerCommitList = new ArrayList<MomCirculateNoteDis>();
		List<MomCirculateNoteDis> minutesOfMeetingSactionCommitList = new ArrayList<MomCirculateNoteDis>();
		List<MomCirculateNoteDis> minutesOfMeetingGosCommitList = new ArrayList<MomCirculateNoteDis>();
		List<MomCirculateNoteDis> minutesOfMeetingCabinetCommitList = new ArrayList<MomCirculateNoteDis>();

		List<DISPrepareAgendaNotes> listpreAgendaNotesMega = disprepareAgendaRepos.findAllPrepareAgendaNotebyStatus();
		List<DISPrepareAgendaNotes> listpreAgendaNotesLarge = disprepareAgendaRepos.findLargePrepareAgendaNotebyStatus();
		
		List<MomCirculateNoteDis> minutesOfMeetingNodalList = new ArrayList<MomCirculateNoteDis>();
		
		List<Object> minutesOfMeetingObjectList = disMinutesOfMeetingRepository.findAllDisMOMbyStatus("ACS");
		
		Object[] row;
		ObjectMapper mapper = new ObjectMapper();
		MomCirculateNoteDis momNote = new MomCirculateNoteDis();
		for (Object object : minutesOfMeetingObjectList) {
			momNote = new MomCirculateNoteDis();
			row = (Object[]) object;
			/* System.out.println(row.length) */;
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
			minutesOfMeetingNodalList.add(momNote);
		}

		//List<MinutesOfMeetingDis> minutesOfMeetingList = disMinutesOfMeetingService.getMinutesOfMeetinguserId(userId);
		if (minutesOfMeetingNodalList.size() > 0) {
			for (MomCirculateNoteDis list : minutesOfMeetingNodalList) {

				if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("CabinetNote") && list.getNoteReportStatus().equalsIgnoreCase("Cabinet Report")) {
					MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());
					if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
						list.setUploadCabinetFile(momUploadDocuments.getFileName());
					}
					minutesOfMeetingCabinetCommitList.add(list);
				}

				if (list.getCommitteeDepartments().equalsIgnoreCase("Empowered Committee") && list.getMinutesOfMeetingOrGos().equalsIgnoreCase("MinutesofMeeting")
						&& list.getNoteReportStatus().equalsIgnoreCase("MOM Report")) {
					MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());

					if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
						list.setUploadMomFile(momUploadDocuments.getFileName());
					}

					minutesOfMeetingEmpowerCommitList.add(list);
				} else if (list.getCommitteeDepartments().equalsIgnoreCase("Sanction Committee") && list.getNoteReportStatus().equalsIgnoreCase("MOMs Report")) {
					MomUploadDocumentsDis momUploadDocuments = dismomUploadDocumentsRepository.getMomByMomId(list.getId());

					if (momUploadDocuments != null && momUploadDocuments.getFileName() != null && !momUploadDocuments.getFileName().isEmpty()) {
						list.setUploadMomFile(momUploadDocuments.getFileName());
					}

					minutesOfMeetingSactionCommitList.add(list);
				} 
					  else if (list.getMinutesOfMeetingOrGos().equalsIgnoreCase("GOs") &&
					  list.getNoteReportStatus().equalsIgnoreCase("GOs Report")) {
						  MomUploadDocumentsDis momUploadDocuments =
									  dismomUploadDocumentsRepository.getMomByMomId(list.getId()); if
									  (momUploadDocuments != null && momUploadDocuments.getFileName() != null &&
									  !momUploadDocuments.getFileName().isEmpty()) {
									  list.setUploadGosFile(momUploadDocuments.getFileName()); }
									  minutesOfMeetingGosCommitList.add(list); }
					 
				

			}
		}

		model.addAttribute("listpreAgendaNotesMega", listpreAgendaNotesMega);
		model.addAttribute("listpreAgendaNotesLarge", listpreAgendaNotesLarge);
		model.addAttribute("minutesOfMeetingEmpowerCommitList", minutesOfMeetingEmpowerCommitList);
		model.addAttribute("minutesOfMeetingSactionCommitList", minutesOfMeetingSactionCommitList);
		model.addAttribute("minutesOfMeetingGosCommitList", minutesOfMeetingGosCommitList);
		model.addAttribute("minutesOfMeetingCabinetCommitList", minutesOfMeetingCabinetCommitList);
	}

	model.addAttribute("minutesOfMeeting", new MinutesOfMeetingDis());
	return new ModelAndView("/Disbursement/disNodalMomgo");
}

}
