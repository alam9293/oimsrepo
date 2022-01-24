/**
 * Author:: Gyan
* Created on:: 
 */

package com.webapp.ims.dis.controller;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.dis.service.DisPrepareAgendaNoteService;
import com.webapp.ims.repository.ProjectRepository;

@Controller
public class DisMdPiCupDepartAgenNoteController {

	@Autowired
	DisPrepareAgendaNoteService disPrepareAgendaNoteService;

	@Autowired
	DISPrepareAgendaNotesRepository dISPrepareAgendaNotesRepository;

	@Autowired
	ProjectRepository projectRepository;

	@RequestMapping(value = "/mdPiCupAgendaNoteDis", method = RequestMethod.GET)
	public ModelAndView mdPiCupAgendaNoteDis(Model model, HttpSession session) {

		List<DISPrepareAgendaNotes> disprepareAgendaNotesList = disPrepareAgendaNoteService
				.getAllDisPrepareAgendaNote();
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<DISPrepareAgendaNotes> disprepareAgendaNotesMegaMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<DISPrepareAgendaNotes> disprearepAgendaNotesLargeMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		if (disprepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to MDPICUP for Approval")
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
					
					disprepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to MDPICUP for Approval")
						&& list.getCategoryIndsType().equalsIgnoreCase("Large")) {

					if(list.getSubmissionDate() != null)
					{
					 Timestamp megaSubmissionDt=list.getSubmissionDate();
					 DateFormat dfl = new SimpleDateFormat("dd-MM-yyyy");
					 String requiredDate = dfl.format(megaSubmissionDt);
					model.addAttribute("largeSubmissionDate", requiredDate);
					}
					
					disprearepAgendaNotesLargeMdPiCupLists.add(list);
				}

			}
		}

		List<DISPrepareAgendaNotes> disprepareAgendaNotesListComment = new ArrayList<DISPrepareAgendaNotes>();

		if (disprepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to ACS for Approval")
						|| list.getStatus().equalsIgnoreCase("Agenda Forwarded to MDPICUP for Approval")
								&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
										|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
										|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
										|| list.getCategoryIndsType().equalsIgnoreCase("Large"))) {
					disprepareAgendaNotesListComment.add(list);
				}
			}
		}
		

		List<DISPrepareAgendaNotes> prepareAgendaNotesListACSComment = new ArrayList<DISPrepareAgendaNotes>();

			if (disprepareAgendaNotesList.size() > 0) {
				for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
					if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
							|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
							|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))
							&& list.getAcsComments() != null) {
						prepareAgendaNotesListACSComment.add(list);
					}
				}
			}

			
		
		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", disprepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", disprearepAgendaNotesLargeMdPiCupLists);

		model.addAttribute("prepareAgendaNotesListComment", disprepareAgendaNotesListComment);
		model.addAttribute("prepareAgendaNotesListACSComment", prepareAgendaNotesListACSComment);

		return new ModelAndView("/Disbursement/mdPiCupDepartAgendaNoteDis");

	}

	@RequestMapping(value = "/saveCommenSubByMdPicupDepartmentDis", method = RequestMethod.POST)
	public ModelAndView saveCommenSubByMdPicupDepartmentDis(
			@ModelAttribute("PrepareAgendaNotesDis") @Validated DISPrepareAgendaNotes dISPrepareAgendaNotes,
			BindingResult result, Model model, HttpSession session) {
		String userid = (String) session.getAttribute("userId");
		DISPrepareAgendaNotes disprepareAgendaNote = new DISPrepareAgendaNotes();
		DISPrepareAgendaNotes disprepareAgendaNotes1 = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByAppliId(dISPrepareAgendaNotes.getAppliId());

		disprepareAgendaNote.setId(disprepareAgendaNotes1.getId());
		disprepareAgendaNote.setAppliId(disprepareAgendaNotes1.getAppliId());
		disprepareAgendaNote.setCompanyName(disprepareAgendaNotes1.getCompanyName());
		disprepareAgendaNote.setInvestment(disprepareAgendaNotes1.getInvestment());

		disprepareAgendaNote.setUserId(userid);
		disprepareAgendaNote.setNote(disprepareAgendaNotes1.getNote());
		disprepareAgendaNote.setNodalAgency(disprepareAgendaNotes1.getNodalAgency());
		disprepareAgendaNote.setJmdComment(disprepareAgendaNotes1.getJmdComment());
		disprepareAgendaNote.setCreatedBY("AdminUser");
		disprepareAgendaNote.setStatus(disprepareAgendaNotes1.getStatus());
		disprepareAgendaNote.setCategoryIndsType(disprepareAgendaNotes1.getCategoryIndsType());
		disprepareAgendaNote.setDistrict(disprepareAgendaNotes1.getDistrict());
		disprepareAgendaNote.setRegion(disprepareAgendaNotes1.getRegion());

		disprepareAgendaNote.setMdComments(dISPrepareAgendaNotes.getMdComments());

		disprepareAgendaNote.setCreateDate(disprepareAgendaNotes1.getCreateDate());
		disprepareAgendaNote.setSubmissionDate(disprepareAgendaNotes1.getSubmissionDate());
		disPrepareAgendaNoteService.savePrepareAgendaNotes(disprepareAgendaNote);

		List<DISPrepareAgendaNotes> disprepareAgendaNotesList = disPrepareAgendaNoteService
				.getAllDisPrepareAgendaNote();
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<DISPrepareAgendaNotes> disprepareAgendaNotesMegaMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<DISPrepareAgendaNotes> disprearepAgendaNotesLargeMdPiCupLists = new ArrayList<DISPrepareAgendaNotes>();

		if (disprepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to MDPICUP for Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					disprepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to MDPICUP for Approval")
						&& list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					disprearepAgendaNotesLargeMdPiCupLists.add(list);
				}
			}
		}

		List<DISPrepareAgendaNotes> disprepareAgendaNotesListComment = new ArrayList<DISPrepareAgendaNotes>();

		if (disprepareAgendaNotesList.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to ACS for Approval")
						|| list.getStatus().equalsIgnoreCase("Agenda Forwarded to MDPICUP for Approval")
								&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
										|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
										|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
										|| list.getCategoryIndsType().equalsIgnoreCase("Large"))) {
					disprepareAgendaNotesListComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", disprepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", disprearepAgendaNotesLargeMdPiCupLists);

		model.addAttribute("prepareAgendaNotesListComment", disprepareAgendaNotesListComment);

		return new ModelAndView("/Disbursement/mdPiCupDepartAgendaNoteDis");
	}

	@RequestMapping(value = "/viewAgendaDetailsByMdPiCupLargeDepartDis", method = RequestMethod.GET)
	public ModelAndView viewAgendaDetailsByMdPiCupLargeDepartDis(Model model,
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session,
			HttpServletRequest request) {

		List<DISPrepareAgendaNotes> disinvestmentDetailsmixedList = new ArrayList<DISPrepareAgendaNotes>();

		if (applId != null && !applId.isEmpty()) {
			List<DISPrepareAgendaNotes> disprepareAgendaNotesList = disPrepareAgendaNoteService
					.getAllDisPrepareAgendaNote();
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)) {
					disinvestmentDetailsmixedList.add(list);
				}
			}
			session.setAttribute("appId", applId);
		}
		disPrepareAgendaNoteService.common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", disinvestmentDetailsmixedList);
		model.addAttribute("flag", "false");
		model.addAttribute("PrepareAgendaNotesDis", new DISPrepareAgendaNotes());
		return new ModelAndView("/Disbursement/disPrepareAgendaNoteByMdLargePicupView");

	}

	@RequestMapping(value = "/dispalyAgendaNoteByMdPiCupDepartDis", method = RequestMethod.POST)
	public ModelAndView dispalyAgendaNoteByJMdPiCupDepart(Model model,
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session,
			HttpServletRequest request) {

		String userid = (String) session.getAttribute("userId");
		List<DISPrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<DISPrepareAgendaNotes>();

		List<DISPrepareAgendaNotes> disPrepareAgendaNotes = dISPrepareAgendaNotesRepository
				.findDisPrepAgendaNotesByUserId(userid);
		for (DISPrepareAgendaNotes list : disPrepareAgendaNotes) {
			if (list.getAppliId().equalsIgnoreCase(applId)) {
				investmentDetailsmixedList.add(list);
			}

			session.setAttribute("appId", applId);
		}

		disPrepareAgendaNoteService.common(model, request, session);
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		// model.addAttribute("disPrepareAgendaNotes", dISPrepareAgendaNotes);
		model.addAttribute("flag", "true");
		String applid = (String) session.getAttribute("appId");
		DISPrepareAgendaNotes prepareAgendaNotesdis1 = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByAppliId(applid);
		model.addAttribute("disprepAgendaNotes", prepareAgendaNotesdis1.getNote());
		model.addAttribute("nodalAgencyNm", prepareAgendaNotesdis1.getNodalAgency());
		model.addAttribute("JmdComments", prepareAgendaNotesdis1.getJmdComment());

		return new ModelAndView("/Disbursement/disPrepareAgendaNoteByMdLargePicupView");

	}

	@GetMapping(value = "/disApprovedAgendaNoteACS")
	public ModelAndView disApprovedAgendaNoteACS(@RequestParam(value = "applicantId", required = false) String applId,
			@ModelAttribute(value = "PrepareAgendaNotesDis") @Validated DISPrepareAgendaNotes dISPrepareAgendaNotes,
			Model model, HttpSession session) {

		DISPrepareAgendaNotes prepareAgendaNotedis = new DISPrepareAgendaNotes();
		DISPrepareAgendaNotes disprepareAgendaNotes1 = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByAppliId(applId);

		prepareAgendaNotedis.setId(disprepareAgendaNotes1.getId());
		prepareAgendaNotedis.setAppliId(disprepareAgendaNotes1.getAppliId());
		prepareAgendaNotedis.setCompanyName(disprepareAgendaNotes1.getCompanyName());
		prepareAgendaNotedis.setInvestment(disprepareAgendaNotes1.getInvestment());
		prepareAgendaNotedis.setUserId(disprepareAgendaNotes1.getUserId());
		prepareAgendaNotedis.setNote(disprepareAgendaNotes1.getNote());
		prepareAgendaNotedis.setCreateDate(disprepareAgendaNotes1.getCreateDate());
		prepareAgendaNotedis.setNodalAgency(disprepareAgendaNotes1.getNodalAgency());
		prepareAgendaNotedis.setCreatedBY("AdminUser");
		prepareAgendaNotedis.setStatus("Agenda Forwarded to ACS for Approval");
		prepareAgendaNotedis.setCategoryIndsType(disprepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNotedis.setDistrict(disprepareAgendaNotes1.getDistrict());
		prepareAgendaNotedis.setRegion(disprepareAgendaNotes1.getRegion());
		prepareAgendaNotedis.setMdComments(disprepareAgendaNotes1.getMdComments());
		prepareAgendaNotedis.setJmdComment(disprepareAgendaNotes1.getJmdComment());
		prepareAgendaNotedis.setSubmissionDate(new Timestamp(System.currentTimeMillis()));
		prepareAgendaNotedis.setApprovalDate(new Timestamp(System.currentTimeMillis()));

		disPrepareAgendaNoteService.savePrepareAgendaNotes(prepareAgendaNotedis);
		List<DISPrepareAgendaNotes> disinvestmentDetailsmixedList = new ArrayList<DISPrepareAgendaNotes>();

		String userid = (String) session.getAttribute("userId");

		List<DISPrepareAgendaNotes> disprepareAgendaNotesList = disPrepareAgendaNoteService
				.findDisPrepAgendaNotesByUserId(userid);
		for (DISPrepareAgendaNotes list : disprepareAgendaNotesList) {
			if (list.getAppliId().equalsIgnoreCase(applId) && list.getStatus().equalsIgnoreCase("Forward to MDPICUP")) {
				disinvestmentDetailsmixedList.add(list);

			}

		}
		List<DISPrepareAgendaNotes> disprepareAgendaNotesList1 = disPrepareAgendaNoteService
				.getAllDisPrepareAgendaNote();
		List<DISPrepareAgendaNotes> disprepareAgendaNotesListComment = new ArrayList<DISPrepareAgendaNotes>();

		if (disprepareAgendaNotesList1.size() > 0) {
			for (DISPrepareAgendaNotes list : disprepareAgendaNotesList1) {
				if (list.getStatus().equalsIgnoreCase("Agenda Forwarded to ACS for Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
								|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
								|| list.getCategoryIndsType().equalsIgnoreCase("Large"))) {
					disprepareAgendaNotesListComment.add(list);
				}
			}
		}
		model.addAttribute("prepareAgendaNotesListComment", disprepareAgendaNotesListComment);
		model.addAttribute("investmentDetailsmixedList", disinvestmentDetailsmixedList);
		model.addAttribute("flag", "false");
		return new ModelAndView("/Disbursement/mdPiCupDepartAgendaNoteDis");
	}

}
