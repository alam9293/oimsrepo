/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.dis.controller;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.controller.ApplicationDetailsViewController;
import com.webapp.ims.controller.PrepareAgendaNoteController;
import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.dis.service.DisPrepareAgendaNoteService;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.EvaluateCapitalInvestment;
import com.webapp.ims.model.EvaluateMeanOfFinance;
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.repository.DeptBusinessEntityDetailsRepository;
import com.webapp.ims.repository.DeptIncentiveDetailsRepository;
import com.webapp.ims.repository.DeptPhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.repository.DeptProposedEmploymentDetailsRepository;
import com.webapp.ims.repository.EvaluateAuditTrialRepository;
import com.webapp.ims.repository.EvaluateExistNewProjDetailsRepository;
import com.webapp.ims.repository.ExistingProjectDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BusinessEntityDetailsService;
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
import com.webapp.ims.service.StateDetailsService;
import com.webapp.ims.service.impl.AdditionalInterest;

/**
 * @author dell
 *
 */
@Controller
public class DisPrepareAgendaNoteController {

	private final Logger logger = LoggerFactory.getLogger(PrepareAgendaNoteController.class);

	@Autowired
	private EvaluateProjectDetailsService evaluateProjectDetailsService;

	@Autowired
	public ApplicationDetailsViewController applicantViewController;

	@Autowired
	DisPrepareAgendaNoteService disPrepareAgendaNoteService;

	@Autowired
	public EvaluateExistNewProjDetailsRepository evaluateExistNewProjDetailsRepository;

	@Autowired
	public EvaluateAuditTrialRepository evaluateAuditTrialRepository;

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
	private EvaluateMeanofFinanceService evalMOf;

	public List<ExistingProjectDetails> existProjList = new ArrayList<>();
	@Autowired
	private ExistingProjectDetailsService existProjectService;

	@Autowired
	private StateDetailsService stateDetailsService;

	@Autowired
	DISPrepareAgendaNotesRepository dISPrepareAgendaNotesRepository;

	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();
	private List<EvaluateCapitalInvestment> evalCapitalInvList = new LinkedList<>();
	private List<EvaluateMeanOfFinance> evalMeanofFinanceList = new LinkedList<>();

	@GetMapping(value = "/agendaNodedis")
	public ModelAndView agendaNodedis(Model model, HttpSession session) {

		String userid = (String) session.getAttribute("userId");

		// for comment jmd

		List<DISPrepareAgendaNotes> prepareAgendaNotesList1 = dISPrepareAgendaNotesRepository
				.findDisPrepAgendaNotesByUserId(userid);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		ArrayList<DISPrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<DISPrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		if (!prepareAgendaNotesList1.isEmpty()) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList1) {
				if (list.getStatus().equalsIgnoreCase("Submitted For Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Submitted For Approval")
						&& list.getCategoryIndsType().trim().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeMdPiCupLists.add(list);
				}
			}
		}
		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);

		// end jmd comment
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<DISPrepareAgendaNotes> prepareAgendaNotesMegaLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<DISPrepareAgendaNotes> prearepAgendaNotesLargeLists = new ArrayList<DISPrepareAgendaNotes>();

		/* All Lists of Large ,Mega, Super Mega and Mega Plus with Prepared Status */
		List<DISPrepareAgendaNotes> prepareAgendaNotesAllPreparedLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Mega,Super Mega and Mega Plus List with Approved by MDPICUP */
		List<DISPrepareAgendaNotes> prepareAgendaNotesApprovedMegaByMDPICUPLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Large with Approved by MDPICUP */
		List<DISPrepareAgendaNotes> prepareAgendaNotesApprovedLargeByMDPICUPListsLists = new ArrayList<DISPrepareAgendaNotes>();

		List<DISPrepareAgendaNotes> prepareAgendaNotescommentList = disPrepareAgendaNoteService
				.getAllDisPrepareAgendaNote();

		List<DISPrepareAgendaNotes> prepareAgendaNotesList = dISPrepareAgendaNotesRepository.findAll();

		// List<PrepareAgendaNotes> prepareAgendaNotesList =
		// prepareAgendaNoteService.findPrepAgendaNotesListByUserId(userid);

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
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
		if (!prepareAgendaNotesList.isEmpty()) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Note Prepared")) {
					prepareAgendaNotesAllPreparedLists.add(list);
				}
			}
		}
		if (!prepareAgendaNotesList.isEmpty()) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				
				if (list.getStatus().equalsIgnoreCase("Approved BY ACS Industries")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					
					
					prepareAgendaNotesApprovedMegaByMDPICUPLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to ACS for Approval")
						&& list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					
					
					
					prepareAgendaNotesApprovedLargeByMDPICUPListsLists.add(list);
				}
			}
		}

		List<DISPrepareAgendaNotes> prepareAgendaNotesListACSComment = new ArrayList<DISPrepareAgendaNotes>();

		List<DISPrepareAgendaNotes> prepareAgendaNotesListMDPICUPComment = new ArrayList<DISPrepareAgendaNotes>();

		List<DISPrepareAgendaNotes> prepareAgendaNotesListJMDComment = new ArrayList<DISPrepareAgendaNotes>();

		for (DISPrepareAgendaNotes list : prepareAgendaNotescommentList) {
			if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
					|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getMdComments() != null) {
				prepareAgendaNotesListMDPICUPComment.add(list);
			}
		}

		for (DISPrepareAgendaNotes list : prepareAgendaNotescommentList) {
			if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
					|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")) && list.getAcsComments() != null) {
				prepareAgendaNotesListACSComment.add(list);
			}
		}

		for (DISPrepareAgendaNotes list : prepareAgendaNotescommentList) {
			if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
					|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getJmdComment() != null) {
				prepareAgendaNotesListJMDComment.add(list);
			}
		}

		// model.addAttribute(PREPARE_AGENDA_NOTES, new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaLists", prepareAgendaNotesMegaLists);
		model.addAttribute("prearepAgendaNotesLargeLists", prearepAgendaNotesLargeLists);

		model.addAttribute("prepareAgendaNotesAllPreparedLists", prepareAgendaNotesAllPreparedLists);

		model.addAttribute("prepareAgendaNotesApprovedMegaLists", prepareAgendaNotesApprovedMegaByMDPICUPLists);

		model.addAttribute("prepareAgendaNotesApprovedLargeLists", prepareAgendaNotesApprovedLargeByMDPICUPListsLists);

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

		return new ModelAndView("/Disbursement/agendaNodedis");

	}

	@PostMapping(value = "/prepareAgendaNoteDis")
	public ModelAndView prepareAgendaNoteDis(
			@ModelAttribute(value = "PrepareAgendaNotesDis") @Validated DISPrepareAgendaNotes dISPrepareAgendaNotes,
			Model model, HttpSession session, HttpServletRequest request) {

		String userid = (String) session.getAttribute("userId");
		List<DISPrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<DISPrepareAgendaNotes>();
		String[] applicantList = dISPrepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {
			List<DISPrepareAgendaNotes> disPrepareAgendaNotes = dISPrepareAgendaNotesRepository
					.findDisPrepAgendaNotesByUserId(userid);
			for (DISPrepareAgendaNotes list : disPrepareAgendaNotes) {
				if (list.getAppliId().equalsIgnoreCase(applId)) {
					investmentDetailsmixedList.add(list);
				}
			}
			session.setAttribute("appId", applId);
		}

		disPrepareAgendaNoteService.common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("disPrepareAgendaNotes", dISPrepareAgendaNotes);
		model.addAttribute("flag", "false");

		return new ModelAndView("/Disbursement/dis_agenda_note_evaluation_view");

	}

	@PostMapping(value = "/dispalyAgendaNoteDis")
	public ModelAndView displayAgendaNoteDis(@RequestParam(value = "appliId", required = false) String appliId,
			@ModelAttribute(value = "PrepareAgendaNotesDis") @Validated DISPrepareAgendaNotes dISPrepareAgendaNotes,
			Model model, HttpSession session, HttpServletRequest request) {

		String userid = (String) session.getAttribute("userId");
		List<DISPrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<DISPrepareAgendaNotes>();

		List<DISPrepareAgendaNotes> disPrepareAgendaNotes = dISPrepareAgendaNotesRepository
				.findDisPrepAgendaNotesByUserId(userid);
		for (DISPrepareAgendaNotes list : disPrepareAgendaNotes) {
			if (list.getAppliId().equalsIgnoreCase(appliId)) {
				investmentDetailsmixedList.add(list);

			}
			session.setAttribute("appId", appliId);
		}

		disPrepareAgendaNoteService.common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("disPrepareAgendaNotes", dISPrepareAgendaNotes);
		model.addAttribute("flag", "true");

		return new ModelAndView("/Disbursement/dis_agenda_note_evaluation_view");

	}

	@PostMapping(value = "/saveCommonEvaluteDis")
	public ModelAndView commonEvaluteDis(
			@ModelAttribute(value = "PrepareAgendaNotesDis") @Validated DISPrepareAgendaNotes dISPrepareAgendaNotes,
			Model model, HttpSession session, HttpServletRequest request) {
		String applid = (String) session.getAttribute("appId");
		DISPrepareAgendaNotes prepareAgendaNotedis = new DISPrepareAgendaNotes();
		DISPrepareAgendaNotes disprepareAgendaNotes1 = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByAppliId(applid);

		
		prepareAgendaNotedis.setId(disprepareAgendaNotes1.getId());
		prepareAgendaNotedis.setAppliId(disprepareAgendaNotes1.getAppliId());
		prepareAgendaNotedis.setCompanyName(disprepareAgendaNotes1.getCompanyName());
		prepareAgendaNotedis.setInvestment(disprepareAgendaNotes1.getInvestment());
		prepareAgendaNotedis.setUserId(disprepareAgendaNotes1.getUserId());
		prepareAgendaNotedis.setNote(dISPrepareAgendaNotes.getNote());
		prepareAgendaNotedis.setNodalAgency(dISPrepareAgendaNotes.getNodalAgency());
		prepareAgendaNotedis.setCreatedBY(disprepareAgendaNotes1.getCreatedBY());
		prepareAgendaNotedis.setStatus("Agenda Note Prepared");
		prepareAgendaNotedis.setCategoryIndsType(disprepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNotedis.setDistrict(disprepareAgendaNotes1.getDistrict());
		prepareAgendaNotedis.setRegion(disprepareAgendaNotes1.getRegion());
		prepareAgendaNotedis.setCreateDate(disprepareAgendaNotes1.getCreateDate());

		disPrepareAgendaNoteService.savePrepareAgendaNotes(prepareAgendaNotedis);

		String userid = (String) session.getAttribute("userId");
		List<DISPrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<DISPrepareAgendaNotes>();

		List<DISPrepareAgendaNotes> disprepareAgendaNotesList = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByUserId(userid);

		for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
			if (list.getAppliId().equalsIgnoreCase(applid)
					&& list.getStatus().equalsIgnoreCase("Application Included In Agenda Note")) {
				investmentDetailsmixedList.add(list);

			}

		}
		List<DISPrepareAgendaNotes> prepareAgendaNotesAllPreparedLists = new ArrayList<DISPrepareAgendaNotes>();
		if (!disprepareAgendaNotesList.isEmpty()) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Note Prepared")) {
					prepareAgendaNotesAllPreparedLists.add(list);
				}
			}
		}
		model.addAttribute("flag", "false");
		model.addAttribute("prepareAgendaNotesAllPreparedLists", prepareAgendaNotesAllPreparedLists);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		return new ModelAndView("/Disbursement/agendaNodedis");

	}

	@GetMapping(value = "/submitToComiteeDis")
	public ModelAndView submitToComiteeDis(Model model,
			@RequestParam(value = "applicantId", required = false) String id, HttpSession session) {
		String userid = (String) session.getAttribute("userId");
		DISPrepareAgendaNotes prepareAgendaNote = new DISPrepareAgendaNotes();
		DISPrepareAgendaNotes prepareAgendaNotes1 = disPrepareAgendaNoteService.findDisPrepAgendaNotesByAppliId(id);
		prepareAgendaNote.setId(prepareAgendaNotes1.getId());
		prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
		prepareAgendaNote.setUserId(userid);
		prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
		prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());
		prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		ApplicantDetails applicantDetails = applicantDetailsService
				.getApplicantDetailsByAppId(prepareAgendaNotes1.getAppliId());
		prepareAgendaNote.setAppliacntEmailId(applicantDetails.getAppEmailId());

		prepareAgendaNote.setStatus("Submitted To Committee");
		prepareAgendaNote.setCreatedBY(prepareAgendaNotes1.getCreatedBY());
		prepareAgendaNote.setCreateDate(new Timestamp(System.currentTimeMillis()));

		prepareAgendaNote.setNote(prepareAgendaNotes1.getNote());
		prepareAgendaNote.setNodalAgency(prepareAgendaNotes1.getNodalAgency());
		prepareAgendaNote.setJmdComment(prepareAgendaNotes1.getJmdComment());
		prepareAgendaNote.setAcsComments(prepareAgendaNotes1.getAcsComments());

		disPrepareAgendaNoteService.savePrepareAgendaNotes(prepareAgendaNote);

		List<DISPrepareAgendaNotes> prepareAgendaNotesList1 = dISPrepareAgendaNotesRepository
				.findDisPrepAgendaNotesByUserId(userid);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		ArrayList<DISPrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<DISPrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		if (!prepareAgendaNotesList1.isEmpty()) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList1) {
				if (list.getStatus().equalsIgnoreCase("Submitted For Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Submitted For Approval")
						&& list.getCategoryIndsType().trim().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeMdPiCupLists.add(list);
				}
			}
		}
		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);

		// end jmd comment
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<DISPrepareAgendaNotes> prepareAgendaNotesMegaLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<DISPrepareAgendaNotes> prearepAgendaNotesLargeLists = new ArrayList<DISPrepareAgendaNotes>();

		/* All Lists of Large ,Mega, Super Mega and Mega Plus with Prepared Status */
		List<DISPrepareAgendaNotes> prepareAgendaNotesAllPreparedLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Mega,Super Mega and Mega Plus List with Approved by MDPICUP */
		List<DISPrepareAgendaNotes> prepareAgendaNotesApprovedMegaByMDPICUPLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Large with Approved by MDPICUP */
		List<DISPrepareAgendaNotes> prepareAgendaNotesApprovedLargeByMDPICUPListsLists = new ArrayList<DISPrepareAgendaNotes>();

		List<DISPrepareAgendaNotes> prepareAgendaNotescommentList = disPrepareAgendaNoteService
				.getAllDisPrepareAgendaNote();

		List<DISPrepareAgendaNotes> prepareAgendaNotesList = dISPrepareAgendaNotesRepository.findAll();

		// List<PrepareAgendaNotes> prepareAgendaNotesList =
		// prepareAgendaNoteService.findPrepAgendaNotesListByUserId(userid);

		if (prepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
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
		if (!prepareAgendaNotesList.isEmpty()) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Note Prepared")) {
					prepareAgendaNotesAllPreparedLists.add(list);
				}
			}
		}
		if (!prepareAgendaNotesList.isEmpty()) {
			for (DISPrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Approved BY ACS Industries")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesApprovedMegaByMDPICUPLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Approved By MD PICUP")
						&& list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					prepareAgendaNotesApprovedLargeByMDPICUPListsLists.add(list);
				}
			}
		}

		List<DISPrepareAgendaNotes> prepareAgendaNotesListACSComment = new ArrayList<DISPrepareAgendaNotes>();

		List<DISPrepareAgendaNotes> prepareAgendaNotesListMDPICUPComment = new ArrayList<DISPrepareAgendaNotes>();

		List<DISPrepareAgendaNotes> prepareAgendaNotesListJMDComment = new ArrayList<DISPrepareAgendaNotes>();

		for (DISPrepareAgendaNotes list : prepareAgendaNotescommentList) {
			if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
					|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getJmdComment() != null) {
				prepareAgendaNotesListMDPICUPComment.add(list);
			}
		}

		for (DISPrepareAgendaNotes list : prepareAgendaNotescommentList) {
			if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
					|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")) && list.getAcsComments() != null) {
				prepareAgendaNotesListACSComment.add(list);
			}
		}

		for (DISPrepareAgendaNotes list : prepareAgendaNotescommentList) {
			if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
					|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
					|| list.getCategoryIndsType().equalsIgnoreCase("Large")) && list.getJmdComment() != null) {
				prepareAgendaNotesListJMDComment.add(list);
			}
		}

		model.addAttribute("prearepAgendaNotesLargeLists", prearepAgendaNotesLargeLists);

		model.addAttribute("prepareAgendaNotesAllPreparedLists", prepareAgendaNotesAllPreparedLists);

		model.addAttribute("prepareAgendaNotesApprovedMegaLists", prepareAgendaNotesApprovedMegaByMDPICUPLists);

		model.addAttribute("prepareAgendaNotesApprovedLargeLists", prepareAgendaNotesApprovedLargeByMDPICUPListsLists);

		model.addAttribute("prepareAgendaNotesListJMDComment", prepareAgendaNotesListJMDComment);
		model.addAttribute("prepareAgendaNotesListMDPICUPComment", prepareAgendaNotesListMDPICUPComment);
		model.addAttribute("prepareAgendaNotesListACSComment", prepareAgendaNotesListACSComment);

		return new ModelAndView("/Disbursement/agendaNodedis");
	}

	@PostMapping(value = "/approvingAgendaNoteDis")
	public ModelAndView approvingAgendaNoteDis(
			@ModelAttribute(value = "PrepareAgendaNotesDis") @Validated DISPrepareAgendaNotes dISPrepareAgendaNotes,
			Model model, HttpSession session, HttpServletRequest request) {

		String userid = (String) session.getAttribute("userId");
		List<DISPrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<DISPrepareAgendaNotes>();
		String[] applicantList = dISPrepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {
			List<DISPrepareAgendaNotes> disPrepareAgendaNotes = dISPrepareAgendaNotesRepository
					.findDisPrepAgendaNotesByUserId(userid);
			for (DISPrepareAgendaNotes list : disPrepareAgendaNotes) {
				if (list.getAppliId().equalsIgnoreCase(applId)) {
					investmentDetailsmixedList.add(list);
				}
			}
			session.setAttribute("appId", applId);
		}

		disPrepareAgendaNoteService.common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("disPrepareAgendaNotes", dISPrepareAgendaNotes);
		model.addAttribute("flag", "false");
		String applid = (String) session.getAttribute("appId");
		DISPrepareAgendaNotes prepareAgendaNotesdis1 = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByAppliId(applid);
		model.addAttribute("disprepAgendaNotes", prepareAgendaNotesdis1.getNote());
		model.addAttribute("nodalAgencyNm", prepareAgendaNotesdis1.getNodalAgency());
		return new ModelAndView("/Disbursement/disAgendaNoteSubmitForApproval");

	}

	@PostMapping(value = "/viewPreparedAgendaDetailsDis")
	public ModelAndView viewPreparedAgendaDetailsDis(@RequestParam(value = "appliId", required = false) String appliId,
			@ModelAttribute(value = "PrepareAgendaNotesDis") @Validated DISPrepareAgendaNotes dISPrepareAgendaNotes,
			Model model, HttpSession session, HttpServletRequest request) {

		String userid = (String) session.getAttribute("userId");
		List<DISPrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<DISPrepareAgendaNotes>();

		List<DISPrepareAgendaNotes> disPrepareAgendaNotes = dISPrepareAgendaNotesRepository
				.findDisPrepAgendaNotesByUserId(userid);
		for (DISPrepareAgendaNotes list : disPrepareAgendaNotes) {
			if (list.getAppliId().equalsIgnoreCase(appliId)) {
				investmentDetailsmixedList.add(list);
			}

			session.setAttribute("appId", appliId);
		}

		disPrepareAgendaNoteService.common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("disPrepareAgendaNotes", dISPrepareAgendaNotes);
		model.addAttribute("flag", "true");
		String applid = (String) session.getAttribute("appId");
		DISPrepareAgendaNotes prepareAgendaNotesdis1 = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByAppliId(applid);
		model.addAttribute("disprepAgendaNotes", prepareAgendaNotesdis1.getNote());
		model.addAttribute("nodalAgencyNm", prepareAgendaNotesdis1.getNodalAgency());
		return new ModelAndView("/Disbursement/disAgendaNoteSubmitForApproval");

	}

	@PostMapping(value = "/disApprovedAgendaNoteJmd")
	public ModelAndView disApprovedAgendaNoteJmd(
			@ModelAttribute(value = "PrepareAgendaNotesDis") @Validated DISPrepareAgendaNotes dISPrepareAgendaNotes,
			Model model, HttpSession session) {
		String applid = (String) session.getAttribute("appId");
		DISPrepareAgendaNotes prepareAgendaNotedis = new DISPrepareAgendaNotes();
		DISPrepareAgendaNotes disprepareAgendaNotes1 = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByAppliId(applid);

		prepareAgendaNotedis.setId(disprepareAgendaNotes1.getId());
		prepareAgendaNotedis.setAppliId(disprepareAgendaNotes1.getAppliId());
		prepareAgendaNotedis.setCompanyName(disprepareAgendaNotes1.getCompanyName());
		prepareAgendaNotedis.setInvestment(disprepareAgendaNotes1.getInvestment());
		prepareAgendaNotedis.setUserId(disprepareAgendaNotes1.getUserId());
		prepareAgendaNotedis.setNote(disprepareAgendaNotes1.getNote());
		prepareAgendaNotedis.setCreateDate(disprepareAgendaNotes1.getCreateDate());
		prepareAgendaNotedis.setNodalAgency(disprepareAgendaNotes1.getNodalAgency());
		prepareAgendaNotedis.setCreatedBY("AdminUser");
		prepareAgendaNotedis.setStatus("Submitted to JMD for comments");
		prepareAgendaNotedis.setCategoryIndsType(disprepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNotedis.setDistrict(disprepareAgendaNotes1.getDistrict());
		prepareAgendaNotedis.setRegion(disprepareAgendaNotes1.getRegion());
		// prepareAgendaNotedis.setComments(disprepareAgendaNotes1.getComments());
		/*
		 * prepareAgendaNote.setPkupScrutinyDetail(disprepareAgendaNotes1.
		 * getPkupScrutinyDetail());
		 * prepareAgendaNote.setPkupFilename(disprepareAgendaNotes1.getPkupFilename());
		 * prepareAgendaNote.setPkupFiledata(disprepareAgendaNotes1.getPkupFiledata());
		 */
		prepareAgendaNotedis.setSubmissionDate(new Timestamp(System.currentTimeMillis()));

		disPrepareAgendaNoteService.savePrepareAgendaNotes(prepareAgendaNotedis);
		List<DISPrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<DISPrepareAgendaNotes>();

		String userid = (String) session.getAttribute("userId");

		List<DISPrepareAgendaNotes> disprepareAgendaNotesList = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByUserId(userid);
		for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
			if (list.getAppliId().equalsIgnoreCase(applid)
					&& list.getStatus().equalsIgnoreCase("Agenda Note Prepared")) {
				investmentDetailsmixedList.add(list);

			}

		}
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("flag", "false");
		return new ModelAndView("/Disbursement/agendaNodedis");
	}

}
