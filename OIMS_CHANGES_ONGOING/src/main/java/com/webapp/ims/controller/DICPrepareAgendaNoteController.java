package com.webapp.ims.controller;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
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

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicantDocument;
import com.webapp.ims.model.AvailCustomisedDetails;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.model.StateDetails;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BusinessEntityDetailsService;
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

@Controller
public class DICPrepareAgendaNoteController {

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
	private StateDetailsService stateDetailsService;

	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();

	@RequestMapping(value = "/prepareAgendaNoteDicList", method = RequestMethod.GET)
	public ModelAndView prepareAgendaNoteDicList(Model model, HttpSession session) {

		String userid = (String) session.getAttribute("userId");
		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
				.findPrepAgendaNotesListByUserId(userid);
		/* List for Small and Medium List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesSmallMediumLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Small and Medium List with Prepared Status */
		List<PrepareAgendaNotes> prepareAgenNotesSmallMediumPreparedLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Small and Medium List with Aprroval Status */
		List<PrepareAgendaNotes> prepareAgenNotesSmallMediumAprrovalLists = new ArrayList<PrepareAgendaNotes>();

		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Application Included In Agenda Note")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					prepareAgendaNotesSmallMediumLists.add(list);
				}

				if (list.getStatus().equalsIgnoreCase("Agenda Note Prepared")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					prepareAgenNotesSmallMediumPreparedLists.add(list);
				}

				if (list.getStatus().equalsIgnoreCase("Approved By JCI")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					prepareAgenNotesSmallMediumAprrovalLists.add(list);
				}

			}
		}

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesSmallMediumLists", prepareAgendaNotesSmallMediumLists);
		model.addAttribute("prepareAgenNotesSmallMediumPreparedLists", prepareAgenNotesSmallMediumPreparedLists);
		model.addAttribute("prepareAgenNotesSmallMediumAprrovalLists", prepareAgenNotesSmallMediumAprrovalLists);

		return new ModelAndView("prepare-agenda-note-dic");
	}

	@RequestMapping(value = "/viewAgendaDetailsDic", method = RequestMethod.GET)
	public ModelAndView viewAgendaDetailsDic(Model model,
			@RequestParam(value = "applicantId", required = false) String applId) {

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
		return new ModelAndView("view_agen_note_applicationdetails_ByDicDepartment");
	}

	@RequestMapping(value = "/saveDicDepartmentNotes", method = RequestMethod.POST)
	public ModelAndView saveDicDepartmentNotes(
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
		prepareAgendaNote.setNotes(prepareAgendaNotes.getNotes());
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setStatus(prepareAgendaNotes1.getStatus());
		prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);

		/* List for Small && Medium List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesSmallMediumLists = new ArrayList<PrepareAgendaNotes>();

		String userid = (String) session.getAttribute("userId");
		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
				.findPrepAgendaNotesListByUserId(userid);
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Application Included In Agenda Note")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					prepareAgendaNotesSmallMediumLists.add(list);
				}

			}
		}

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesSmallMediumLists", prepareAgendaNotesSmallMediumLists);
		return new ModelAndView("prepare-agenda-note-dic");

	}

	@RequestMapping(value = "/prepareAgendaNoteDic", method = RequestMethod.POST)
	public ModelAndView prepareAgendaNoteDic(
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {

		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String userid = (String) session.getAttribute("userId");
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {

			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)) {
					investmentDetailsmixedList.add(list);
				}
			}

		}
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("PrepareAgendaNotes", prepareAgendaNotes);
		model.addAttribute("flag", "false");
		return new ModelAndView("agenda_note_evaluation_view_byDicDepartment");
	}

	@RequestMapping(value = "/dispalyAgendaNoteByDicDepart", method = RequestMethod.GET)
	public String dispalyAgendaNoteByDicDepart(@RequestParam(value = "appliId", required = false) String appliId,
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
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {
			String userid = (String) session.getAttribute("userId");
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Application Included In Agenda Note")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					investmentDetailsmixedList.add(list);
				}
			}

		}
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("PrepareAgendaNotes", prepareAgendaNotes);
		return ("agenda_note_evaluation_view_byDicDepartment");
	}

	@RequestMapping(value = "/prepAgenNotePreparedByDicDepart", method = RequestMethod.POST)
	public ModelAndView prepAgenNotePreparedByDicDepart(
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
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
		prepareAgendaNote.setStatus("Agenda Note Prepared");
		prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);

		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {
			String userid = (String) session.getAttribute("userId");
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Application Included In Agenda Note")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					investmentDetailsmixedList.add(list);
				}
			}

		}
		model.addAttribute("flag", "false");
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		return new ModelAndView("agenda_note_evaluation_view_byDicDepartment");
	}

	@RequestMapping(value = "/approvingAgendaNoteByDicDepart", method = RequestMethod.POST)
	public ModelAndView approvingAgendaNoteByDicDepart(
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {

		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {
			String userid = (String) session.getAttribute("userId");
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Agenda Note Prepared")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					investmentDetailsmixedList.add(list);
				}
			}

		}
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("PrepareAgendaNotes", prepareAgendaNotes);
		model.addAttribute("flag", "false");
		return new ModelAndView("agen_note_submit_for_approval_dicdepartment");
	}

	@RequestMapping(value = "/viewPreparedAgenDetByDicDepart", method = RequestMethod.GET)
	public ModelAndView viewPreparedAgenDetByDicDepart(Model model,
			@RequestParam(value = "appliId", required = false) String id,
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes,
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
			final String SMALL = "Small";
			final String MEDIUM = "Medium";
			final String LARGE = "Large";
			final String MEGA = "Mega";
			final String MEGAPLUS = "Mega Plus";
			final String SUPERMEGA = "Super Mega";
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
		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {
			String userid = (String) session.getAttribute("userId");
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId) && list.getStatus().equalsIgnoreCase("Prepared")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					investmentDetailsmixedList.add(list);
				}
			}

		}
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		prepareAgendaNote.setAppliId(id);
		prepareAgendaNote.setPrepareAgendaNote(applicantList);
		model.addAttribute("PrepareAgendaNotes", prepareAgendaNote);
		// model.addAttribute("flag", "flag");
		model.addAttribute("flag", "true");
		if (projectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			model.addAttribute("flag1", "false1");
		}
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService
				.getPrepareByAppliId(prepareAgendaNotes.getAppliId());
		model.addAttribute("prepAgenNotes", prepareAgendaNotes1.getNotes());
		return new ModelAndView("agen_note_submit_for_approval_dicdepartment");
	}

	@RequestMapping(value = "/approvedAgendaNoteByDicDepart", method = RequestMethod.POST)
	public ModelAndView approvedAgendaNoteByDicDepart(
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
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
		for (String applId : applicantList) {
			String userid = (String) session.getAttribute("userId");
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Agenda Note Prepared")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					investmentDetailsmixedList.add(list);
				}
			}

		}
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("flag", "false");
		return new ModelAndView("agen_note_submit_for_approval_dicdepartment");
	}

	@RequestMapping(value = "/viewCommitteeAgendaNoteByDicDepart", method = RequestMethod.POST)
	public ModelAndView viewCommitteeAgendaNoteByDicDepart(
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {

		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {
			String userid = (String) session.getAttribute("userId");
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId) && list.getStatus().equalsIgnoreCase("Approved By JCI")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					investmentDetailsmixedList.add(list);
				}
			}

		}
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		model.addAttribute("PrepareAgendaNotes", prepareAgendaNotes);
		model.addAttribute("flag", "false");
		return new ModelAndView("viewAgendaNoteByDicCommittee");
	}

	@RequestMapping(value = "/viewApprovedAgendaDetailsByDicDepart", method = RequestMethod.GET)
	public ModelAndView viewApprovedAgendaDetailsByDicDepart(Model model,
			@RequestParam(value = "appliId", required = false) String id,
			@ModelAttribute("PrepareAgendaNotes") @Validated PrepareAgendaNotes prepareAgendaNotes,
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
			final String SMALL = "Small";
			final String MEDIUM = "Medium";
			final String LARGE = "Large";
			final String MEGA = "Mega";
			final String MEGAPLUS = "Mega Plus";
			final String SUPERMEGA = "Super Mega";
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
		List<PrepareAgendaNotes> investmentDetailsmixedList = new ArrayList<PrepareAgendaNotes>();
		String[] applicantList = prepareAgendaNotes.getPrepareAgendaNote();
		for (String applId : applicantList) {
			String userid = (String) session.getAttribute("userId");
			List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
					.findPrepAgendaNotesListByUserId(userid);
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getAppliId().equalsIgnoreCase(applId)
						&& list.getStatus().equalsIgnoreCase("Approved By JCI")) {
					investmentDetailsmixedList.add(list);
				}
			}

		}
		model.addAttribute("investmentDetailsmixedList", investmentDetailsmixedList);
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		prepareAgendaNote.setAppliId(id);
		prepareAgendaNote.setPrepareAgendaNote(applicantList);
		model.addAttribute("PrepareAgendaNotes", prepareAgendaNote);
		model.addAttribute("flag", "true");
		if (projectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			model.addAttribute("flag1", "false1");
		}
		return new ModelAndView("viewAgendaNoteByDicCommittee");

	}

	@RequestMapping(value = "/submitToComiteeByDicDepartment", method = RequestMethod.GET)
	public ModelAndView submitToComiteeByDicDepartment(Model model,
			@RequestParam(value = "applicantId", required = false) String id, HttpSession session) {
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		PrepareAgendaNotes prepareAgendaNotes1 = prepareAgendaNoteService.getPrepareByAppliId(id);
		prepareAgendaNote.setId(prepareAgendaNotes1.getId());
		prepareAgendaNote.setAppliId(prepareAgendaNotes1.getAppliId());
		prepareAgendaNote.setCompanyName(prepareAgendaNotes1.getCompanyName());
		prepareAgendaNote.setInvestment(prepareAgendaNotes1.getInvestment());
		prepareAgendaNote.setUserId(prepareAgendaNotes1.getUserId());
		prepareAgendaNote.setNotes(prepareAgendaNotes1.getNotes());
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setStatus("Submitted To Committee");
		prepareAgendaNote.setCategoryIndsType(prepareAgendaNotes1.getCategoryIndsType());
		prepareAgendaNote.setDistrict(prepareAgendaNotes1.getDistrict());
		prepareAgendaNote.setRegion(prepareAgendaNotes1.getRegion());
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);

		/* List for Small and Medium List with Ready Status */
		List<PrepareAgendaNotes> prepareAgendaNotesSmallMediumLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Small and Medium List with Prepared Status */
		List<PrepareAgendaNotes> prepareAgenNotesSmallMediumPreparedLists = new ArrayList<PrepareAgendaNotes>();

		/* List for Small and Medium List with Aprroval Status */
		List<PrepareAgendaNotes> prepareAgenNotesSmallMediumAprrovalLists = new ArrayList<PrepareAgendaNotes>();

		String userid = (String) session.getAttribute("userId");
		List<PrepareAgendaNotes> prepareAgendaNotesList = prepareAgendaNoteService
				.findPrepAgendaNotesListByUserId(userid);
		if (prepareAgendaNotesList.size() > 0) {
			for (PrepareAgendaNotes list : prepareAgendaNotesList) {
				if (list.getStatus().equalsIgnoreCase("Application Included In Agenda Note")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					prepareAgendaNotesSmallMediumLists.add(list);
				}

				if (list.getStatus().equalsIgnoreCase("Agenda Note Prepared")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					prepareAgenNotesSmallMediumPreparedLists.add(list);
				}

				if (list.getStatus().equalsIgnoreCase("Approved By JCI")
						&& (list.getCategoryIndsType().equalsIgnoreCase("Small")
								|| list.getCategoryIndsType().equalsIgnoreCase("Medium"))) {
					prepareAgenNotesSmallMediumAprrovalLists.add(list);
				}

			}
		}

		model.addAttribute("PrepareAgendaNotes", new PrepareAgendaNotes());
		model.addAttribute("prepareAgendaNotesSmallMediumLists", prepareAgendaNotesSmallMediumLists);
		model.addAttribute("prepareAgenNotesSmallMediumPreparedLists", prepareAgenNotesSmallMediumPreparedLists);
		model.addAttribute("prepareAgenNotesSmallMediumAprrovalLists", prepareAgenNotesSmallMediumAprrovalLists);
		return new ModelAndView("prepare-agenda-note-dic");
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
}
