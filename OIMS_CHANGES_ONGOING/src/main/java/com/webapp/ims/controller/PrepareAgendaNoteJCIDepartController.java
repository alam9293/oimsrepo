package com.webapp.ims.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.PrepareAgendaNoteService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;
import com.webapp.ims.service.impl.AdditionalInterest;

@Controller
public class PrepareAgendaNoteJCIDepartController {

	private final Logger logger = LoggerFactory.getLogger(PrepareAgendaNoteJCIDepartController.class);

	@Autowired
	BusinessEntityDetailsService businessService;
	@Autowired
	ProprietorDetailsService proprietorService;
	@Autowired
	private ProjectService projectService;

	@Autowired
	IncentiveDetailsService incentiveDetailsService;
	@Autowired
	AvailCustmisedDetailsService availCustmisedDetailsService;

	@Autowired
	private ProposedEmploymentDetailsService emplDtlService;

	@Autowired
	AdditionalInterest additionalInterest;

	@Autowired
	InvestmentDetailsService investmentDetailsService;
	@Autowired
	PrepareAgendaNoteService prepareAgendaNoteService;

	@Autowired
	private PrepareAgendaNotesService prepareAgendaNotesService;

	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();

	@RequestMapping(value = "/viewAjendaNoteByJCI", method = RequestMethod.GET)
	public String viewAjendaNoteByJCI(Model model, HttpSession session) {

		logger.debug("View Prepare Ajenda Not By JCI Department..");
		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
		/* List for Small and Medium List with Ready Status */
		List<PrepareAgendaNotes> preAgenNotesSmalMedListsByJCI = new ArrayList<PrepareAgendaNotes>();
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Submitted For Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					preAgenNotesSmalMedListsByJCI.add(list);
				}
			}
		}
		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("preAgenNotesSmalMedListsByJCI", preAgenNotesSmalMedListsByJCI);
		return ("prepareAgendaNoteByJCI");
	}

	@RequestMapping(value = "/viewAgendaDetailsByJCIDepart", method = RequestMethod.GET)
	public ModelAndView viewAgendaDetailsByJCIDepart(Model model,
			@RequestParam(value = "applicantId", required = false) String applicantId, HttpSession session) {

		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();

		if (applicantId != null && !applicantId.isEmpty()) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applicantId)) {
					investmentDetailsmixedList.add(list);
				}
			}
		}
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("flag", "false");
		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		return new ModelAndView("ViewEvaluationAgendaNote");

	}

	@RequestMapping(value = "/dispalyAgendaNoteByJICDepart", method = RequestMethod.GET)
	public ModelAndView dispalyAgendaNoteByJICDepart(@RequestParam(value = "appliId", required = false) String appliId,
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {

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
		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		if (appliId != null && !appliId.isEmpty()) {
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(appliId)) {
					investmentDetailsmixedList.add(list);
				}
			}
		}

		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("PrepareAgendaNotes", prepareAgendaNotes);
		return new ModelAndView("ViewEvaluationAgendaNote");
	}

	@RequestMapping(value = "/saveCommenSubByCJIDepartment", method = RequestMethod.POST)
	public ModelAndView saveCommenSubByCJIDepartment(
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes,
			BindingResult result, Model model, HttpSession session) {
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
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		prepareAgendaNote.setSubjectName(prepareAgendaNotes.getSubjectName());
		prepareAgendaNote.setComments(prepareAgendaNotes.getComments());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> preAgenNotesSmalMedListsByJCI = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Submitted For Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					preAgenNotesSmalMedListsByJCI.add(list);
				}
			}
		}
		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("preAgenNotesSmalMedListsByJCI", preAgenNotesSmalMedListsByJCI);

		return new ModelAndView("prepareAgendaNoteByJCI");
	}

	@RequestMapping(value = "/saveApprovedByJCIDepart", method = RequestMethod.GET)
	public ModelAndView saveApprovedByJCIDepart(
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
			prepareAgendaNote.setStatus("Approved By JCI");
			prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
			prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
			prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
			prepareAgendaNote.setSubjectName(prepareAgendaNotes1.getSubjectName());
			prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());
			prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);
		}

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> preAgenNotesSmalMedListsByJCI = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Submitted For Approval")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					preAgenNotesSmalMedListsByJCI.add(list);
				}
			}
		}
		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("preAgenNotesSmalMedListsByJCI", preAgenNotesSmalMedListsByJCI);

		return new ModelAndView("prepareAgendaNoteByJCI");
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

}
