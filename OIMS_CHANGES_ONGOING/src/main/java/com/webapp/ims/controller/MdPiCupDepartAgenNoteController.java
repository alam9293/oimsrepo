package com.webapp.ims.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

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

import com.itextpdf.text.DocumentException;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicationFwdEntity;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.repository.ApplicationFwdRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.PrepareAgendaNoteRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.DigitalSignatureService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
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
public class MdPiCupDepartAgenNoteController {

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
	PrepareAgendaNoteRepository prepAgendaNoteRepository;
	@Autowired
	InvestmentDetailsService investmentDetailsService;
	@Autowired
	PrepareAgendaNoteService prepareAgendaNoteService;

	@Autowired
	private StateDetailsService stateDetailsService;

	@Autowired
	DigitalSignatureService digitalSignatureService;

	@Autowired
	ApplicantDetailsRepository applicantDetailsRepository;

	@Autowired
	ApplicationFwdRepository applicationFwdRepository;
	@Autowired
	WReturnCUSIDSTATUSBeanService wReturnCUSIDSTATUSBeanService;

	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();

	@RequestMapping(value = "/mdPiCupAgendaNote", method = RequestMethod.GET)
	public ModelAndView mdPiCupAgendaNote(Model model, HttpSession session) {
		List<PrepareAgendaNotes> prepareAgendaNotesListCommentAll = prepareAgendaNoteService.getAllPrepareAgendaNote();

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

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

		if (prepareAgendaNotesListCommentAll.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesListCommentAll) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Large"))
						&& list.getMdScrutinyDetail() != null) {
					prepareAgendaNotesListComment.add(list);
				}
			}
		}
		
		List<PrepareAgendaNotes> prepareAgendaNotesListACSComments = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesListCommentAll.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesListCommentAll) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))
						&& list.getAcsScrutinyDetail() != null) {
					prepareAgendaNotesListACSComments.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);

		model.addAttribute("prepareAgendaNotesListComment", prepareAgendaNotesListComment);
		model.addAttribute("prepareAgendaNotesAcsComments", prepareAgendaNotesListACSComments);

		return new ModelAndView("mdPiCupDepartAgendaNote");

	}

	@RequestMapping(value = "/viewAgendaDetailsByMdPiCupDepart", method = RequestMethod.GET)
	public ModelAndView viewAgendaDetailsByMdPiCupDepart(Model model,
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
		return new ModelAndView("prepareAgendaNoteByMdPicup");

	}

	@RequestMapping(value = "/dispalyAgendaNoteByMdPiCupDepart", method = RequestMethod.GET)
	public ModelAndView dispalyAgendaNoteByMdPiCupDepart(
			@RequestParam(value = "appliId", required = false) String appliId,
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session, HttpServletRequest request) {

		ProjectDetails projectDetails = projectService.getProjectByapplicantDetailId(appliId);

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
		return new ModelAndView("prepareAgendaNoteByMdPicup");
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

	@RequestMapping(value = "/viewAgendaDetailsByMdPiCupLargeDepart", method = RequestMethod.GET)
	public ModelAndView viewAgendaDetailsByMdPiCupLargeDepart(Model model,
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
		return new ModelAndView("prepareAgendaNoteByMdLargePicup");

	}

	@RequestMapping(value = "/dispalyAgendaNoteLargeByMdPiCupDepart", method = RequestMethod.GET)
	public ModelAndView dispalyAgendaNoteLargeByMdPiCupDepart(
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
		return new ModelAndView("prepareAgendaNoteByMdPicup");
	}

	@RequestMapping(value = "/saveCommenSubByMdPicupDepartment", method = RequestMethod.POST)
	public ModelAndView saveCommenSubByMdPicupDepartment(
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
		prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
		prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
		prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
		prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());

		prepareAgendaNote.setMdScrutinyDetail(prepareAgendaNotes.getMdScrutinyDetail());
		prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
					prepareAgendaNotesMegaMdPiCupLists.add(list);
				} else if (list.getStatus().equalsIgnoreCase("Forward to MDPICUP")
						&& list.getCategoryIndsType().equalsIgnoreCase("Large")) {
					prearepAgendaNotesLargeMdPiCupLists.add(list);
				}
			}
		}

		List<PrepareAgendaNotes> prepareAgendaNotesListComment = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Large"))
						&& list.getMdScrutinyDetail() != null) {
					prepareAgendaNotesListComment.add(list);
				}
			}
		}

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);

		model.addAttribute("prepareAgendaNotesListComment", prepareAgendaNotesListComment);

		return new ModelAndView("mdPiCupDepartAgendaNote");
	}

	@RequestMapping(value = "/saveForwardedToACSIndustry", method = RequestMethod.GET)
	public ModelAndView saveForwardedToACSIndustry(
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
			prepareAgendaNote.setStatus("Agenda Forwarded to ACS for Approval");
			prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
			prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
			prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
			prepareAgendaNote.setSubjectName(prepareAgendaNotes1.getSubjectName());
			prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());

			prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
			prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
			prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
			prepareAgendaNote.setMdScrutinyDetail(prepareAgendaNotes1.getMdScrutinyDetail());
			prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
			prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);
		}

		System.out.println("prepareAgendaNotes.getAppliId() : " + applicantId);
		ApplicantDetails applicantDetails = applicantDetailsRepository.getApplicantDetailsByAppId(applicantId);
		if (applicantDetails != null) {
			applicantDetails.setStatusCode("05");
			applicantDetails.setRemarks("Application Form Submitted for Approval");
			applicantDetails.setPendancyLevel("Pending on ACS");
 
			wReturnCUSIDSTATUSBeanService.WReturn_CUSID_STATUS(applicantDetails);
		}
		
		ApplicationFwdEntity applicationFwdEntity = new ApplicationFwdEntity();
		List<ApplicationFwdEntity> applicationFwdEntityList = new ArrayList<>();
		applicationFwdEntityList = applicationFwdRepository.findByAppId(prepareAgendaNote.getAppliId());
		for (ApplicationFwdEntity applicationFwd : applicationFwdEntityList) {
			applicationFwd.setStatus(false);
			applicationFwdRepository.save(applicationFwd);
		}

		applicationFwdEntity = new ApplicationFwdEntity();

		applicationFwdEntity.setAppId(prepareAgendaNote.getAppliId());
		Timestamp currentDate = new Timestamp(System.currentTimeMillis());
		applicationFwdEntity.setFwddate(currentDate);
		applicationFwdEntity.setDepartment("ACS");
		applicationFwdEntity.setName("ACS");
		applicationFwdEntity.setRolname("ACS");
		applicationFwdEntity.setStatus(true);
		applicationFwdRepository.save(applicationFwdEntity);

		
		// PrepareAgendaNotes prepAgendaNotes =
		// prepAgendaNoteRepository.findByAppliId(applicantId);

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

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
		List<PrepareAgendaNotes> prepareAgendaNotesListcomments = prepareAgendaNoteService.getAllPrepareAgendaNote();
		List<PrepareAgendaNotes> prepareAgendaNotesListComment = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesListcomments.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesListcomments) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Large"))
						&& list.getMdScrutinyDetail() != null) {
					prepareAgendaNotesListComment.add(list);
				}
			}
		}

		model.addAttribute("prepareAgendaNotesListComment", prepareAgendaNotesListComment);

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);

		return new ModelAndView("mdPiCupDepartAgendaNote");
	}

	@RequestMapping(value = "/saveLargeAndApproved", method = RequestMethod.GET)
	public ModelAndView saveLargeAndApproved(@RequestParam(value = "applicantId", required = false) String applicantId,
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes,
			BindingResult result, Model model, HttpSession session)
			throws IOException, GeneralSecurityException, DocumentException {
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();

		// code by pramod solankee
		/*
		 * byte[] signature =
		 * digitalSignatureService.checkDigitalSingleItem(applicantId, model); if
		 * (signature == null) { model.addAttribute("signaturekeyMsg",
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
		 * (list.getStatus().equalsIgnoreCase("Forward to MDPICUP") &&
		 * (list.getCategoryIndsType().equalsIgnoreCase("Mega") ||
		 * list.getCategoryIndsType().equalsIgnoreCase("Mega Plus") ||
		 * list.getCategoryIndsType().equalsIgnoreCase("Super Mega"))) {
		 * prepareAgendaNotesMegaMdPiCupLists.add(list); } else if
		 * (list.getStatus().equalsIgnoreCase("Forward to MDPICUP") &&
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
		 * list.getCategoryIndsType().equalsIgnoreCase("Large")) &&
		 * list.getMdScrutinyDetail() != null) {
		 * prepareAgendaNotesListComment.add(list); } } }
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
		 * return new ModelAndView("mdPiCupDepartAgendaNote"); } else {
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
			prepareAgendaNote.setStatus("Approved By MD PICUP");
			prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
			prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
			prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
			prepareAgendaNote.setSubjectName(prepareAgendaNotes1.getSubjectName());
			prepareAgendaNote.setComments(prepareAgendaNotes1.getComments());

			prepareAgendaNote.setPkupScrutinyDetail(prepareAgendaNotes1.getPkupScrutinyDetail());
			prepareAgendaNote.setPkupFilename(prepareAgendaNotes1.getPkupFilename());
			prepareAgendaNote.setPkupFiledata(prepareAgendaNotes1.getPkupFiledata());
			prepareAgendaNote.setMdScrutinyDetail(prepareAgendaNotes1.getMdScrutinyDetail());
			prepareAgendaNote.setSubmissionDate(prepareAgendaNotes1.getSubmissionDate());
			prepareAgendaNote.setApprovalDate(new Date());
			prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);
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
		applicationFwdEntity.setDepartment("Submit");
		applicationFwdEntity.setName("Submit");
		applicationFwdEntity.setRolname("Submit");
		applicationFwdEntity.setStatus(true);
		applicationFwdRepository.save(applicationFwdEntity);

		System.out.println("prepareAgendaNote.getAppliId()" + prepareAgendaNote.getAppliId());
		ApplicantDetails applicantDetails = applicantDetailsRepository
				.getApplicantDetailsByAppId(prepareAgendaNote.getAppliId());
		if (applicantDetails != null) {
			applicantDetails.setStatusCode("06");
			applicantDetails.setRemarks("Application Approved");
			applicantDetails.setPendancyLevel("pending on Dept");

			wReturnCUSIDSTATUSBeanService.WReturn_CUSID_STATUS(applicantDetails);
		}

		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService.getPrepareAgendaNote(session);
		/* List for Mega,Super Mega and Mega Plus List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesMegaMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Large with Ready Status */
		List<PrepareAgendaNotes> prearepAgendaNotesLargeMdPiCupLists = new ArrayList<PrepareAgendaNotes>();

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

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if ((list.getCategoryIndsType().equalsIgnoreCase("Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Mega Plus")
						|| list.getCategoryIndsType().equalsIgnoreCase("Super Mega")
						|| list.getCategoryIndsType().equalsIgnoreCase("Large"))
						&& list.getMdScrutinyDetail() != null) {
					prepareAgendaNotesListComment.add(list);
				}
			}
		}

		model.addAttribute("prepareAgendaNotesListComment", prepareAgendaNotesListComment);

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesMegaMdPiCupLists", prepareAgendaNotesMegaMdPiCupLists);
		model.addAttribute("prearepAgendaNotesLargeMdPiCupLists", prearepAgendaNotesLargeMdPiCupLists);
		return new ModelAndView("mdPiCupDepartAgendaNote");
	}

	// }
}
