package com.webapp.ims.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.dis.service.DisPrepareAgendaNoteService;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.repository.IncentiveDetailsRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.PrepareAgendaNoteService;

@Controller
public class DashboardController {

	private final Logger logger = LoggerFactory.getLogger(DashboardController.class);

	@Autowired
	private ApplicantDetailsService applicantDetailsService;

	@Autowired
	PrepareAgendaNoteService prepareAgendaNoteService;

	@Autowired
	DisPrepareAgendaNoteService disPrepareAgendaNoteService;

	@Autowired
	IncentiveDetailsService incentiveService;

	@Autowired
	IncentiveDetailsService incentiveDetailsService;

	@Autowired
	IncentiveDetailsRepository incentRepo;

	@RequestMapping(value = "/dashboard", method = RequestMethod.GET)
	public String dashboard(Model model, HttpSession session) {

		logger.debug("View Applicant Application");

		List<IncentiveDetails> incentiveLists = incentRepo.getAllIncentiveDetailsBystatus();
		List<IncentiveDetails> disincentiveLists = incentRepo.getAllDisIncentiveDetailsBystatus();

		List<PrepareAgendaNotes> prepareAgendaNotesLists = new ArrayList<PrepareAgendaNotes>();
		List<PrepareAgendaNotes> prepareAgendaNotesPreparedLists = new ArrayList<PrepareAgendaNotes>();
		List<PrepareAgendaNotes> prepareAgendaNotesApprovedByCommiteeLists = new ArrayList<PrepareAgendaNotes>();
		List<PrepareAgendaNotes> prepareAgendaNotesApprovalLists = new ArrayList<PrepareAgendaNotes>();
		List<PrepareAgendaNotes> prepareAgendaNotesPreparedListsReject = new ArrayList<PrepareAgendaNotes>();
		List<PrepareAgendaNotes> prepareAgendaNotesApprovedListsCompleted = new ArrayList<PrepareAgendaNotes>();
		List<PrepareAgendaNotes> prepareAgendaNotesPreparedListsQuery = new ArrayList<PrepareAgendaNotes>();
		List<PrepareAgendaNotes> prepareAgendaNotesListsReady = new ArrayList<PrepareAgendaNotes>();
		List<PrepareAgendaNotes> prepareAgendaNotesListsDeferred = new ArrayList<PrepareAgendaNotes>();

		List<IncentiveDetails> disprepareAgendaNotesListsIssuedDis = new ArrayList<IncentiveDetails>();
		List<DISPrepareAgendaNotes> disprepareAgendaNotesPreparedLists = new ArrayList<DISPrepareAgendaNotes>();
		List<DISPrepareAgendaNotes> disprepareAgendaNotesSubmittedToCommiteeLists = new ArrayList<DISPrepareAgendaNotes>();
		List<DISPrepareAgendaNotes> disprepareAgendaNotesApprovedByACSLists = new ArrayList<DISPrepareAgendaNotes>();
		List<DISPrepareAgendaNotes> disprepareAgendaNotesPreparedListsReject = new ArrayList<DISPrepareAgendaNotes>();
		List<DISPrepareAgendaNotes> disprepareAgendaNotesPreparedListsQuery = new ArrayList<DISPrepareAgendaNotes>();
		List<DISPrepareAgendaNotes> disprepareAgendaNotesListsInclude = new ArrayList<DISPrepareAgendaNotes>();
		List<DISPrepareAgendaNotes> disprepareAgendaNotesListsDeferred = new ArrayList<DISPrepareAgendaNotes>();

		String userid = (String) session.getAttribute("userId");
		String userName = (String) session.getAttribute("userName");
		model.addAttribute("userName", userName);
		List<DISPrepareAgendaNotes> disprepareAgendaNotesList = disPrepareAgendaNoteService
				.getAllDisPrepareAgendaNote();
		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getAllPrepareAgendaNote();

//		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);

		// count All Application
		int countAllApplication = incentiveLists.size();
		model.addAttribute("countAllApplication", countAllApplication);

		int countAllDisApplication = disincentiveLists.size();
		model.addAttribute("countAllDisApplication", countAllDisApplication);

//Disbursment Stage--------------	

		// count Complete
		List<IncentiveDetails> incentiveDetails2 = incentiveDetailsService.getAllIncentiveDetails();
		if (incentiveDetails2.size() > 0) {
			for (IncentiveDetails list : incentiveDetails2) {
				if (list.getDis_status() != null) {
					if (list.getDis_status().equalsIgnoreCase("Submit")) {
						disprepareAgendaNotesListsIssuedDis.add(list);
					}
				}
			}
		}
		int countIssuedDis = disprepareAgendaNotesListsIssuedDis.size();
		model.addAttribute("countIssuedDis", countIssuedDis);

		if (disprepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Submitted To Committee")) {
					disprepareAgendaNotesSubmittedToCommiteeLists.add(list);
				}
			}
		}
		int submitToCommiteeDis = disprepareAgendaNotesSubmittedToCommiteeLists.size();
		model.addAttribute("ApprovedByCommiteeDis", submitToCommiteeDis);

		if (disprepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Application Rejected")) {
					disprepareAgendaNotesPreparedListsReject.add(list);
				}
			}
		}
		int countRejectDis = disprepareAgendaNotesPreparedListsReject.size();
		model.addAttribute("countRejectDis", countRejectDis);

		// count Number of Applications on Which Query has been Raised
		if (disprepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Query Raised By Processing Officer")) {
					disprepareAgendaNotesPreparedListsQuery.add(list);
				}
			}
		}
		int countQueryDis = disprepareAgendaNotesPreparedListsQuery.size();
		model.addAttribute("countQueryDis", countQueryDis);

		// count Number of Applications Under Evaluation
		if (disprepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Pending for Evaluation")) {
					disprepareAgendaNotesListsInclude.add(list);
				}
			}
		}
		int countIncludeDis = disprepareAgendaNotesListsInclude.size();
		model.addAttribute("countIncludeDis", countIncludeDis);

		if (disprepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Deferred")) {
					disprepareAgendaNotesListsDeferred.add(list);
				}
			}
		}
		int countDeferDis = disprepareAgendaNotesListsDeferred.size();
		model.addAttribute("countDeferDis", countDeferDis);

		// count Number of Applications Ready for Sanctioning/Empowered Committee
		if (disprepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Note Prepared")) {
					disprepareAgendaNotesPreparedLists.add(list);
				}
			}
		}
		int countAgendaNotePreparedDis = disprepareAgendaNotesPreparedLists.size();
		model.addAttribute("countAgendaNotePreparedDis", countAgendaNotePreparedDis);

		// count Number of Applications Pending for Approval of Hon'ble Cabinet
		if (disprepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Approved BY ACS Industries")) {
					disprepareAgendaNotesApprovedByACSLists.add(list);
				}
			}
		}
		int countApprovedByAcsDis = disprepareAgendaNotesApprovedByACSLists.size();
		model.addAttribute("countApprovedByAcsDis", countApprovedByAcsDis);

//LOC Stage------------------------

		// count Complete
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("LOC Generated")) {
					prepareAgendaNotesApprovedListsCompleted.add(list);
				}
			}
		}
		int countCompleteLoc = prepareAgendaNotesApprovedListsCompleted.size();
		model.addAttribute("countCompleteLoc", countCompleteLoc);

		// count Number of Applications Rejected
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("LOC Rejected")) {
					prepareAgendaNotesPreparedListsReject.add(list);
				}
			}
		}
		int countRejectLoc = prepareAgendaNotesPreparedListsReject.size();
		model.addAttribute("countRejectLoc", countRejectLoc);

		// count Number of Applications on Which Query has been Raised
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Query Raised By Processing Officer")) {
					prepareAgendaNotesPreparedListsQuery.add(list);
				}
			}
		}
		int countQueryLoc = prepareAgendaNotesPreparedListsQuery.size();
		model.addAttribute("countQueryLoc", countQueryLoc);

		// count Approved by commitee
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Submitted To Committee")) {
					prepareAgendaNotesApprovedByCommiteeLists.add(list);
				}
			}
		}
		int ApprovedByCommitee = prepareAgendaNotesApprovedByCommiteeLists.size();
		model.addAttribute("ApprovedByCommitee", ApprovedByCommitee);

		// count Number of Applications Under Evaluation
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Application Included In Agenda Note")) {
					prepareAgendaNotesListsReady.add(list);
				}
			}
		}
		int countReadyLoc = prepareAgendaNotesListsReady.size();
		model.addAttribute("countReadyLoc", countReadyLoc);

		// count Number of Applications Deferred
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Application Deferred")) {
					prepareAgendaNotesListsDeferred.add(list);
				}
			}
		}
		int countDeferLoc = prepareAgendaNotesListsDeferred.size();
		model.addAttribute("countDeferLoc", countDeferLoc);

		// count Number of Applications Ready for Sanctioning/Empowered Committee
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Note Prepared")) {
					prepareAgendaNotesPreparedLists.add(list);
				}
			}
		}
		int countPreparedLoc = prepareAgendaNotesPreparedLists.size();
		model.addAttribute("countPreparedLoc", countPreparedLoc);

		// count Number of Applications Pending for Approval of Hon'ble Cabinet
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Approved BY ACS")
						|| list.getStatus().equalsIgnoreCase("Approved By MD PICUP")) {
					prepareAgendaNotesApprovalLists.add(list);
				}
			}
		}
		int countApprovalLoc = prepareAgendaNotesApprovalLists.size();
		model.addAttribute("countApprovalLoc", countApprovalLoc);

		return "dashboard";
	}

	@RequestMapping(value = "/departmentDashboard", method = RequestMethod.GET)
	public ModelAndView departmentDashboard(Model model, HttpSession session) {
		dashboard(model, session);
		return new ModelAndView("departmentDashboard");

	}
	
	@RequestMapping(value = "/aggricultureDashboard", method = RequestMethod.GET)
	public ModelAndView aggricultureDashboard(Model model, HttpSession session) {
		dashboard(model, session);
		return new ModelAndView("aggricultureDashboard");

	}
	
	@RequestMapping(value = "/stampDutyDashboard", method = RequestMethod.GET)
	public ModelAndView stampDutyDashboard(Model model, HttpSession session) {
		dashboard(model, session);
		return new ModelAndView("stampDutyDashboard");

	}
	
	
	@RequestMapping(value = "/labourDashboard", method = RequestMethod.GET)
	public ModelAndView labourDashboard(Model model, HttpSession session) {
		dashboard(model, session);
		return new ModelAndView("labourDashboard");

	}
	
	
	
	@RequestMapping(value = "/electricityDashboard", method = RequestMethod.GET)
	public ModelAndView electricityDashboard(Model model, HttpSession session) {
		dashboard(model, session);
		return new ModelAndView("electricityDashboard");

	}

	@GetMapping(value = "/nodalDeptHeadDashboard")
	public ModelAndView nodalDepartmentHeadDashboard(Model model, HttpSession session) {
		dashboard(model, session);
		return new ModelAndView("nodalDeptHeadDashboard");
	}

	@RequestMapping(value = "/dicDepartmentDashboard", method = RequestMethod.GET)
	public ModelAndView dicDepartmentDashboard(Model model, HttpSession session) {
		logger.debug("View Applicant Application");
		dashboard(model, session);
		return new ModelAndView("dicDepartmentDashboard");

	}

	@RequestMapping(value = "/mdPICUPDepartmentDashboard", method = RequestMethod.GET)
	public ModelAndView mdPICUPDepartmentDashboard(Model model, HttpSession session) {
		dashboard(model, session);
		return new ModelAndView("dicDepartmentDashboard");

	}

	// vinay

	@RequestMapping(value = "/jmdPICUPDepartmentDashboard", method = RequestMethod.GET)
	public ModelAndView jmdPICUPDepartmentDashboard(Model model, HttpSession session) {
		dashboard(model, session);
		return new ModelAndView("dicDepartmentDashboard");

	}

// vinay end
	@RequestMapping(value = "/dashboardCONCERJCIDEPARTMENT", method = RequestMethod.GET)
	public ModelAndView dashboardCONCERJCIDEPARTMENT(Model model, HttpSession session) {
		dashboard(model, session);
		return new ModelAndView("concerjciDepartmentDashboard");

	}

	@RequestMapping(value = "/dashboardPSI", method = RequestMethod.GET)
	public ModelAndView dashboardPSI(Model model, HttpSession session) {
		logger.debug("View Applicant Application");
		dashboard(model, session);
		return new ModelAndView("/Disbursement/dashboard_PSI");

	}

	@RequestMapping(value = "/dashboardCS", method = RequestMethod.GET)
	public ModelAndView dashboardCS(Model model, HttpSession session) {
		logger.debug("View Applicant Application");
		dashboard(model, session);
		return new ModelAndView("/Disbursement/dashboard_CS");

	}

	@RequestMapping(value = "/dashboardID6", method = RequestMethod.GET)
	public ModelAndView dashboardID6(Model model, HttpSession session) {
		logger.debug("View Applicant Application");
		dashboard(model, session);
		return new ModelAndView("/Disbursement/dashboard_ID6");
	}

}
