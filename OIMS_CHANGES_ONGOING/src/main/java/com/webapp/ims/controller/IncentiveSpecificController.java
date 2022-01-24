package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.EXIST_PROJ_LIST;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicantDocument;
import com.webapp.ims.model.AvailCustomisedDetails;
import com.webapp.ims.model.BusinessEntity;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.BusinessEntityDocument;
import com.webapp.ims.model.ExistProjDetailsDocument;
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.InvestmentDocument;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.repository.BusinessEntityDocumentRepository;
import com.webapp.ims.repository.ExistProjDocRepository;
import com.webapp.ims.repository.InvestmentDocumentRepository;
import com.webapp.ims.repository.StateDetailsRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.BusinessEntityDocumentService;
import com.webapp.ims.service.ExistingProjectDetailsService;
import com.webapp.ims.service.INVInstalledCapacitiesService;
import com.webapp.ims.service.INVOthersService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.MeansOfFinanceService;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;
import com.webapp.ims.service.PolicyService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;
import com.webapp.ims.service.WReturnCUSIDSTATUSBeanService;
import com.webapp.ims.service.impl.AdditionalInterest;
import com.webapp.ims.webservices.SoapConsumeEx;

@Controller
@Component
public class IncentiveSpecificController {

	@Autowired
	PolicyService PolicyService;
	@Autowired
	private ApplicantDetailsService applicantDetailsService;

	@Autowired
	ExistProjDocRepository projDocRepository;

	@Autowired
	IncentiveDetailsService incentiveDetailsService;

	@Autowired
	InvestmentDocumentRepository invDocRepository;
	@Autowired
	WReturnCUSIDSTATUSBeanService wReturnCUSIDSTATUSBeanService;

	@Autowired
	private INVOthersService invOthersService;

	@Autowired
	private MeansOfFinanceService meansOfFinanceService;

	// ---------------------Rathour:STARTED----------------------------------------------

	@Autowired
	private ProjectService projectService;
	@Autowired
	BusinessEntityDetailsService businessService;

	@Autowired
	BusinessEntityDocumentService businessEntyDocService;

	@Autowired
	BusinessEntityDocumentRepository beDocRepository;

	@Autowired
	ProprietorDetailsService proprietorService;
	@Autowired
	private ProposedEmploymentDetailsService proposedEmploymentDetailsService;
	@Autowired
	private InvestmentDetailsService investDs;
	@Autowired
	private PhaseWiseInvestmentDetailsService pwInvestDs;
	@Autowired
	private ApplicantDocumentService fileStorageService;
	// --------------------------------------------------ENDED-----------------------
	SoapConsumeEx soapdetails = new SoapConsumeEx();

	@Autowired
	AvailCustmisedDetailsService availCustmisedDetailsService;

	@Autowired
	AdditionalInterest additionalInterest;

	@Autowired
	private StateDetailsRepository stateRepository;

	@Autowired
	ApplicantDetailsRepository applicantDetailsRepository;

	@Autowired
	private ExistingProjectDetailsService existProjectService;

	private final Logger logger = LoggerFactory.getLogger(IncentiveSpecificController.class);

	public List<ExistingProjectDetails> existProjList = new ArrayList<>();

	boolean isRowRecord = false;

	@Autowired
	private INVInstalledCapacitiesService iNVInstalledCapacitiesService;

	@RequestMapping(value = "/incentiveTypes", method = RequestMethod.GET)
	public String incentiveType(Model model) {
		model.addAttribute("incentiveTypeForm", new IncentiveDetails());
		return "incentiveTypes";
	}

	@RequestMapping(value = "/incentiveDetails", method = RequestMethod.GET)
	public String incentivDetails(ModelMap model, HttpSession session) {

		session.getAttribute("applicantDetailsId");
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String appId = responce.get("appID");
		String a = additionalInterest.getSgstEligibility(appId);

		String isfId = "";
		for (Map.Entry<String, String> entry : responce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				isfId = entry.getValue() + "INC1";
			}
		}

		String invId = "";
		Object niveshResponse1 = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce1 = ((SoapDataModel) niveshResponse1).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : responce1.entrySet()) {

			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				invId = entry.getValue() + "I1";
			}

		}

		IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
		if (IncentiveDetail != null) {
			incentiveSpecificDetails.setISF_Claim_Reim(IncentiveDetail.getISF_Claim_Reim());
			incentiveSpecificDetails.setISF_Reim_SCST(IncentiveDetail.getISF_Reim_SCST());
			incentiveSpecificDetails.setISF_Reim_FW(IncentiveDetail.getISF_Reim_FW());
			incentiveSpecificDetails.setISF_Reim_BPLW(IncentiveDetail.getISF_Reim_BPLW());
			incentiveSpecificDetails.setISF_Ttl_SGST_Reim(IncentiveDetail.getISF_Ttl_SGST_Reim());

			incentiveSpecificDetails.setISF_Stamp_Duty_EX(IncentiveDetail.getISF_Stamp_Duty_EX());
			incentiveSpecificDetails.setISF_Additonal_Stamp_Duty_EX(IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
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

			if (IncentiveDetail.getIsfCustIncDocName() != null && !IncentiveDetail.getIsfCustIncDocName().isEmpty()) {
				incentiveSpecificDetails.setIsfCustIncDoc(IncentiveDetail.getIsfCustIncDocName().getBytes());
			}

			incentiveSpecificDetails.setOthAddRequest1(IncentiveDetail.getOthAddRequest1());

			incentiveSpecificDetails.setId(IncentiveDetail.getId());
			model.addAttribute("availCustomisedDetailsList",
					availCustmisedDetailsService.findAllByAvaCustId(IncentiveDetail.getId()));
		} else {
		}

		model.values();
		model.put("Marker", a);

		String stamp = additionalInterest.getStampEligibility(appId);

		String aepfw = additionalInterest.getepfa(appId);

		String aepfe = additionalInterest.getepfaa(appId);

		String cis = additionalInterest.getaddCIS(appId);

		String projectType = additionalInterest.getprojecttype(appId);

		String region = additionalInterest.getregion(appId);

		long unskilled = additionalInterest.getunskilled(appId);
		InvestmentDetails investmentDetails = investDs.getInvestmentDetailsById(invId);
		model.put("category", investmentDetails.getInvIndType());
		model.put("eligibleaepfe", aepfe);
		model.put("eligibleaepfw", aepfw);
		model.put("eligiblestamp", stamp);
		model.put("eligiblecis", cis);
		model.put("eligiblereg", region);
		model.put("eligibleuns", unskilled);
		model.put("projectType", projectType);
		model.put("isctmincentive", incentiveSpecificDetails.getISF_Cstm_Inc_Status());

		session.setAttribute("incentiveId", isfId);
		session.setAttribute("investmentId", invId);
		model.addAttribute("incentiveDeatilsForm", incentiveSpecificDetails);
		model.addAttribute("availCustomisedDetails", new AvailCustomisedDetails());
		model.addAttribute("niveshSoapResponse", niveshResponse);
		return "incentiveDetails";
	}

	@RequestMapping(value = "/saveIncentiveDetails", method = RequestMethod.POST)
	public ModelAndView saveIncentivDetails(@Validated IncentiveDetails incentiveDetails, BindingResult result,
			Model model, HttpSession session, @RequestParam("isfCustIncDocName") MultipartFile multipartFile)
			throws IOException {
		String isfId = session.getAttribute("incentiveId").toString();
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		String applicantId = responce.get("appID");
		String invId = "";
		Object niveshResponse1 = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce1 = ((SoapDataModel) niveshResponse1).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : responce1.entrySet()) {

			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				invId = entry.getValue() + "I1";
			}

		}
		incentiveDetails.setModify_Date(new Date());
		incentiveDetails.setId(isfId);
		incentiveDetails.setIsfapcId(applicantId);
		incentiveDetails.setIsfCustIncDocName(multipartFile.getOriginalFilename());
		incentiveDetails.setIsfCustIncDoc(multipartFile.getBytes());
		IncentiveDetails IncentiveDetailexisit = incentiveDetailsService.getIncentiveisfById(isfId);

		if (incentiveDetails != null && incentiveDetails.getIsfCustIncDocName() != null
				&& !incentiveDetails.getIsfCustIncDocName().isEmpty()) {

		} else {
			if (IncentiveDetailexisit != null) {
				incentiveDetails.setIsfCustIncDocName(IncentiveDetailexisit.getIsfCustIncDocName());
				incentiveDetails.setIsfCustIncDoc(IncentiveDetailexisit.getIsfCustIncDoc());

			}
		}
		try {
			incentiveDetailsService.updateIncentiveDetails(incentiveDetails);

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			if (incentiveDetails.getISF_Cstm_Inc_Status() == "No"
					|| incentiveDetails.getISF_Cstm_Inc_Status().equalsIgnoreCase("No")) {
				availCustmisedDetailsService.deleteAllById(isfId);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
		IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
		if (IncentiveDetail != null) {
			incentiveSpecificDetails.setISF_Claim_Reim(IncentiveDetail.getISF_Claim_Reim());
			incentiveSpecificDetails.setISF_Reim_SCST(IncentiveDetail.getISF_Reim_SCST());
			incentiveSpecificDetails.setISF_Reim_FW(IncentiveDetail.getISF_Reim_FW());
			incentiveSpecificDetails.setISF_Reim_BPLW(IncentiveDetail.getISF_Reim_BPLW());
			incentiveSpecificDetails.setISF_Ttl_SGST_Reim(IncentiveDetail.getISF_Ttl_SGST_Reim());

			incentiveSpecificDetails.setISF_Stamp_Duty_EX(IncentiveDetail.getISF_Stamp_Duty_EX());
			incentiveSpecificDetails.setISF_Additonal_Stamp_Duty_EX(IncentiveDetail.getISF_Additonal_Stamp_Duty_EX());
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

			if (IncentiveDetail.getIsfCustIncDocName() != null && !IncentiveDetail.getIsfCustIncDocName().isEmpty()) {
				incentiveSpecificDetails.setIsfCustIncDoc(IncentiveDetail.getIsfCustIncDocName().getBytes());
			}

			incentiveSpecificDetails.setOthAddRequest1(IncentiveDetail.getOthAddRequest1());

			incentiveSpecificDetails.setId(IncentiveDetail.getId());
			model.addAttribute("availCustomisedDetailsList",
					availCustmisedDetailsService.findAllByAvaCustId(IncentiveDetail.getId()));

			InvestmentDetails investmentDetails = investDs.getInvestmentDetailsById(invId);
			model.addAttribute("category", investmentDetails.getInvIndType());
		}

		// session.setAttribute("availCustomisedDetailsList",
		// availCustomisedDetailsList);
		model.addAttribute("incentiveDeatilsForm", incentiveSpecificDetails);
		model.addAttribute("niveshSoapResponse", niveshResponse);
		return new ModelAndView("redirect:/incentiveDetails");

	}

	@RequestMapping(value = "/availCustomisedDetails", method = RequestMethod.POST)
	public ModelAndView saveAvailCustomisedDetails(Model model,
			@ModelAttribute("incentiveDeatilsForm") IncentiveSpecificDetails incentiveSpecificDetails,
			@ModelAttribute("availCustomisedDetails") AvailCustomisedDetails availCustomisedDetails,
			BindingResult result, @RequestParam("isfCustIncDocName") MultipartFile multipartFile, HttpSession session) {

		String isfId = session.getAttribute("incentiveId").toString();
		List<AvailCustomisedDetails> availCustomisedDetailsList1 = null;
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		responce.get("appID");

		try {
			IncentiveDetails IncentiveDetailexisit = incentiveDetailsService.getIncentiveisfById(isfId);

			if (IncentiveDetailexisit == null) {
				IncentiveDetails incentiveDetails = new IncentiveDetails();
				incentiveDetails.setModify_Date(new Date());
				incentiveDetails.setId(isfId);
				incentiveDetails.setIsfCustIncDocName(incentiveSpecificDetails.getIsfCustIncDocName());
				incentiveDetails.setIsfCustIncDoc(incentiveSpecificDetails.getIsfCustIncDoc());
				incentiveDetails.setISF_Cstm_Inc_Status(incentiveSpecificDetails.getISF_Cstm_Inc_Status());
				incentiveDetailsService.updateIncentiveDetails(incentiveDetails);
			} else {
				IncentiveDetailexisit.setISF_Cstm_Inc_Status(incentiveSpecificDetails.getISF_Cstm_Inc_Status());
				incentiveDetailsService.updateIncentiveDetails(IncentiveDetailexisit);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		incentiveSpecificDetails.setAddAmt(null);
		incentiveSpecificDetails.setAddIncentiveType("");
		incentiveSpecificDetails.setOthAddRequest("");
		incentiveSpecificDetails.setTypeDtlCusIncentives("");
		incentiveSpecificDetails.setISF_Cstm_Inc_Status(incentiveSpecificDetails.getISF_Cstm_Inc_Status());

		try {
			availCustomisedDetails.setIncentiveDetails(isfId);
			if (availCustomisedDetails.getAcdid() == null || availCustomisedDetails.getAcdid().isEmpty()) {
				availCustomisedDetails.setAcdid(UUID.randomUUID().toString().toUpperCase());
			}

			availCustmisedDetailsService.updateAvailCustomisedDetails(availCustomisedDetails);
			availCustomisedDetailsList1 = availCustmisedDetailsService.findAllByAvaCustId(isfId);
		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList1);
		model.addAttribute("availCustomisedDetails", new AvailCustomisedDetails());
		model.addAttribute("availCustomisedDetails", new AvailCustomisedDetails());
		model.addAttribute("incentiveDeatilsForm", incentiveSpecificDetails);
		session.setAttribute("incentiveDeatilsForm", incentiveSpecificDetails);
		model.addAttribute("niveshSoapResponse", niveshResponse);

		InvestmentDetails investmentDetails = investDs
				.getInvestmentDetailsById(session.getAttribute("investmentId").toString());
		model.addAttribute("category", investmentDetails.getInvIndType());
		model.addAttribute("customlist", "customlist");
		return new ModelAndView("redirect:/incentiveDetails");
	}

	@RequestMapping(value = "/editAvailCustomisedDetails", method = RequestMethod.GET)
	public ModelAndView editAvailCustomisedDetails(
			@RequestParam(value = "editAvailCustomisedRecord", required = false) int index, Model model,
			AvailCustomisedDetails availCustomisedDetails, HttpSession session) {

		LinkedList<AvailCustomisedDetails> availCustomisedDetailsList1 = null;

		String isfId = session.getAttribute("incentiveId").toString();

		availCustomisedDetailsList1 = availCustmisedDetailsService.findAllByAvaCustId(isfId);

		availCustomisedDetails = availCustomisedDetailsList1.get(index);
		IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
		incentiveSpecificDetails.setAddAmt(availCustomisedDetails.getAddAmt());
		incentiveSpecificDetails.setAddIncentiveType(availCustomisedDetails.getAddIncentiveType());
		incentiveSpecificDetails.setOthAddRequest(availCustomisedDetails.getOthAddRequest());
		incentiveSpecificDetails.setTypeDtlCusIncentives(availCustomisedDetails.getTypeDtlCusIncentives());
		incentiveSpecificDetails.setAcdid(availCustomisedDetails.getAcdid());

		model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList1);
		model.addAttribute("incentiveDeatilsForm", incentiveSpecificDetails);

		InvestmentDetails investmentDetails = investDs
				.getInvestmentDetailsById(session.getAttribute("investmentId").toString());

		IncentiveDetails IncentiveDetailexisit = incentiveDetailsService.getIncentiveisfById(isfId);
		if (IncentiveDetailexisit != null) {
			incentiveSpecificDetails.setISF_Claim_Reim(IncentiveDetailexisit.getISF_Claim_Reim());
			incentiveSpecificDetails.setISF_Reim_SCST(IncentiveDetailexisit.getISF_Reim_SCST());
			incentiveSpecificDetails.setISF_Reim_FW(IncentiveDetailexisit.getISF_Reim_FW());
			incentiveSpecificDetails.setISF_Reim_BPLW(IncentiveDetailexisit.getISF_Reim_BPLW());
			incentiveSpecificDetails.setISF_Ttl_SGST_Reim(IncentiveDetailexisit.getISF_Ttl_SGST_Reim());

			incentiveSpecificDetails.setISF_Stamp_Duty_EX(IncentiveDetailexisit.getISF_Stamp_Duty_EX());
			incentiveSpecificDetails
					.setISF_Additonal_Stamp_Duty_EX(IncentiveDetailexisit.getISF_Additonal_Stamp_Duty_EX());
			incentiveSpecificDetails.setISF_Amt_Stamp_Duty_Reim(IncentiveDetailexisit.getISF_Amt_Stamp_Duty_Reim());
			incentiveSpecificDetails.setISF_Ttl_Stamp_Duty_EX(IncentiveDetailexisit.getISF_Ttl_Stamp_Duty_EX());

			incentiveSpecificDetails.setISF_Epf_Reim_UW(IncentiveDetailexisit.getISF_Epf_Reim_UW());
			incentiveSpecificDetails.setISF_Add_Epf_Reim_SkUkW(IncentiveDetailexisit.getISF_Add_Epf_Reim_SkUkW());
			incentiveSpecificDetails.setISF_Add_Epf_Reim_DIVSCSTF(IncentiveDetailexisit.getISF_Add_Epf_Reim_DIVSCSTF());
			incentiveSpecificDetails.setISF_Ttl_EPF_Reim(IncentiveDetailexisit.getISF_Ttl_EPF_Reim());
			incentiveSpecificDetails.setISF_Cis(IncentiveDetailexisit.getISF_Cis());
			incentiveSpecificDetails.setISF_ACI_Subsidy_Indus(IncentiveDetailexisit.getISF_ACI_Subsidy_Indus());
			incentiveSpecificDetails.setISF_Infra_Int_Subsidy(IncentiveDetailexisit.getISF_Infra_Int_Subsidy());
			incentiveSpecificDetails.setISF_AII_Subsidy_DIVSCSTF(IncentiveDetailexisit.getISF_AII_Subsidy_DIVSCSTF());
			incentiveSpecificDetails.setISF_Loan_Subsidy(IncentiveDetailexisit.getISF_Loan_Subsidy());
			incentiveSpecificDetails.setISF_Total_Int_Subsidy(IncentiveDetailexisit.getISF_Total_Int_Subsidy());
			incentiveSpecificDetails.setISF_Tax_Credit_Reim(IncentiveDetailexisit.getISF_Tax_Credit_Reim());
			incentiveSpecificDetails.setISF_EX_E_Duty(IncentiveDetailexisit.getISF_EX_E_Duty());
			incentiveSpecificDetails.setISF_EX_E_Duty_PC(IncentiveDetailexisit.getISF_EX_E_Duty_PC());
			incentiveSpecificDetails.setISF_EX_Mandee_Fee(IncentiveDetailexisit.getISF_EX_Mandee_Fee());
			incentiveSpecificDetails.setISF_Indus_Payroll_Asst(IncentiveDetailexisit.getISF_Indus_Payroll_Asst());
			incentiveSpecificDetails.setISF_EX_E_Duty(IncentiveDetailexisit.getISF_EX_E_Duty());
			incentiveSpecificDetails.setTotal_Other_Incentive(IncentiveDetailexisit.getTotal_Other_Incentive());
		}

		model.addAttribute("incentiveDeatilsForm", incentiveSpecificDetails);
		model.addAttribute("category", investmentDetails.getInvIndType());
		model.addAttribute("customlist", "customlist");
		return new ModelAndView("incentiveDetails");
	}

	@RequestMapping(value = "/removeAvailList", method = RequestMethod.GET)
	public ModelAndView removeAvailCustomisedDetailsList(
			@RequestParam(value = "deleteAvailRecord", required = false) int removeid, Model model,
			AvailCustomisedDetails availCustomisedDetails, HttpSession session) {

		LinkedList<AvailCustomisedDetails> availCustomisedDetailsList1 = null;
		String isfId = session.getAttribute("incentiveId").toString();

		IncentiveSpecificDetails incentiveSpecificDetails = null;
		incentiveSpecificDetails = (IncentiveSpecificDetails) session.getAttribute("incentiveDeatilsForm");
		if (incentiveSpecificDetails == null) {
			incentiveSpecificDetails = new IncentiveSpecificDetails();
		}

		availCustomisedDetailsList1 = availCustmisedDetailsService.findAllByAvaCustId(isfId);
		availCustomisedDetails = availCustomisedDetailsList1.get(removeid);
		try {
			availCustmisedDetailsService.deleteById(availCustomisedDetails.getAcdid());
		} catch (Exception e) {

		}

		IncentiveDetails IncentiveDetailexisit = incentiveDetailsService.getIncentiveisfById(isfId);

		if (incentiveSpecificDetails != null && incentiveSpecificDetails.getIsfCustIncDocName() != null
				&& !incentiveSpecificDetails.getIsfCustIncDocName().isEmpty()) {

		} else {
			if (IncentiveDetailexisit != null) {
				incentiveSpecificDetails.setIsfCustIncDocName(IncentiveDetailexisit.getIsfCustIncDocName());
				incentiveSpecificDetails.setIsfCustIncDoc(IncentiveDetailexisit.getIsfCustIncDoc());
			}
		}
		if (IncentiveDetailexisit != null) {
			incentiveSpecificDetails.setISF_Claim_Reim(IncentiveDetailexisit.getISF_Claim_Reim());
			incentiveSpecificDetails.setISF_Reim_SCST(IncentiveDetailexisit.getISF_Reim_SCST());
			incentiveSpecificDetails.setISF_Reim_FW(IncentiveDetailexisit.getISF_Reim_FW());
			incentiveSpecificDetails.setISF_Reim_BPLW(IncentiveDetailexisit.getISF_Reim_BPLW());
			incentiveSpecificDetails.setISF_Ttl_SGST_Reim(IncentiveDetailexisit.getISF_Ttl_SGST_Reim());

			incentiveSpecificDetails.setISF_Stamp_Duty_EX(IncentiveDetailexisit.getISF_Stamp_Duty_EX());
			incentiveSpecificDetails
					.setISF_Additonal_Stamp_Duty_EX(IncentiveDetailexisit.getISF_Additonal_Stamp_Duty_EX());
			incentiveSpecificDetails.setISF_Amt_Stamp_Duty_Reim(IncentiveDetailexisit.getISF_Amt_Stamp_Duty_Reim());
			incentiveSpecificDetails.setISF_Ttl_Stamp_Duty_EX(IncentiveDetailexisit.getISF_Ttl_Stamp_Duty_EX());

			incentiveSpecificDetails.setISF_Epf_Reim_UW(IncentiveDetailexisit.getISF_Epf_Reim_UW());
			incentiveSpecificDetails.setISF_Add_Epf_Reim_SkUkW(IncentiveDetailexisit.getISF_Add_Epf_Reim_SkUkW());
			incentiveSpecificDetails.setISF_Add_Epf_Reim_DIVSCSTF(IncentiveDetailexisit.getISF_Add_Epf_Reim_DIVSCSTF());
			incentiveSpecificDetails.setISF_Ttl_EPF_Reim(IncentiveDetailexisit.getISF_Ttl_EPF_Reim());
			incentiveSpecificDetails.setISF_Cis(IncentiveDetailexisit.getISF_Cis());
			incentiveSpecificDetails.setISF_ACI_Subsidy_Indus(IncentiveDetailexisit.getISF_ACI_Subsidy_Indus());
			incentiveSpecificDetails.setISF_Infra_Int_Subsidy(IncentiveDetailexisit.getISF_Infra_Int_Subsidy());
			incentiveSpecificDetails.setISF_AII_Subsidy_DIVSCSTF(IncentiveDetailexisit.getISF_AII_Subsidy_DIVSCSTF());
			incentiveSpecificDetails.setISF_Loan_Subsidy(IncentiveDetailexisit.getISF_Loan_Subsidy());
			incentiveSpecificDetails.setISF_Total_Int_Subsidy(IncentiveDetailexisit.getISF_Total_Int_Subsidy());
			incentiveSpecificDetails.setISF_Tax_Credit_Reim(IncentiveDetailexisit.getISF_Tax_Credit_Reim());
			incentiveSpecificDetails.setISF_EX_E_Duty(IncentiveDetailexisit.getISF_EX_E_Duty());
			incentiveSpecificDetails.setISF_EX_E_Duty_PC(IncentiveDetailexisit.getISF_EX_E_Duty_PC());
			incentiveSpecificDetails.setISF_EX_Mandee_Fee(IncentiveDetailexisit.getISF_EX_Mandee_Fee());
			incentiveSpecificDetails.setISF_Indus_Payroll_Asst(IncentiveDetailexisit.getISF_Indus_Payroll_Asst());
			incentiveSpecificDetails.setISF_EX_E_Duty(IncentiveDetailexisit.getISF_EX_E_Duty());
			incentiveSpecificDetails.setTotal_Other_Incentive(IncentiveDetailexisit.getTotal_Other_Incentive());

			InvestmentDetails investmentDetails = investDs
					.getInvestmentDetailsById(session.getAttribute("investmentId").toString());

			model.addAttribute("category", investmentDetails.getInvIndType());

		}
		model.addAttribute("availCustomisedDetails", new AvailCustomisedDetails());
		model.addAttribute("availCustomisedDetailsList", availCustmisedDetailsService.findAllByAvaCustId(isfId));
		model.addAttribute("incentiveDeatilsForm", incentiveSpecificDetails);
		session.setAttribute("incentiveDeatilsForm", incentiveSpecificDetails);
		// model.addAttribute("incentiveDeatilsForm", incentiveSpecificDetails);
		model.addAttribute("customlist", "customlist");
		return new ModelAndView("incentiveDetails");

	}

	@RequestMapping(value = "/submitIncentiveDetails", method = RequestMethod.POST)
	public RedirectView submitIncentivDetails(@Validated IncentiveDetails incentiveDetails, BindingResult result,
			@RequestParam("isfCustIncDocName") MultipartFile multipartFile, Model model, HttpSession session)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {

		try {
			incentiveDetails.setStatus("Submit");
			saveIncentivDetails(incentiveDetails, result, model, session, multipartFile);

			ApplicantDetails applicantDetails = applicantDetailsRepository
					.getApplicantDetailsByAppId(incentiveDetails.getIsfapcId());
			if (applicantDetails != null)
				if (applicantDetails.isSubmitStatus()) {
					applicantDetails.setStatusCode("14");
					applicantDetails.setRemarks("Application Re-Submmited");
					// soapConsumeEx.WReturn_CUSID_STATUS(applicantDetails);
					applicantDetails.setPendancyLevel("pending investment Detiils");

					wReturnCUSIDSTATUSBeanService.WReturn_CUSID_STATUS(applicantDetails);
				} else if (applicantDetails != null) {
					applicantDetails.setStatusCode("13");
					applicantDetails.setRemarks("application submmited");
					applicantDetails.setPendancyLevel("pending investment Detiils");
					applicantDetails.setSubmitStatus(true);
					wReturnCUSIDSTATUSBeanService.WReturn_CUSID_STATUS(applicantDetails);

				}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RedirectView redirectView = new RedirectView();
		redirectView.setUrl("http://72.167.225.87/Testing_NMSWP/nmmasters/Entrepreneur_Dashboard.aspx");
		// redirectView.setUrl("http://72.167.225.87/testing_nmswp/nmmasters/Entrepreneur_Bck_page.aspx");

		return redirectView;

	}

	// RATHOUR--------------------------------------STARTED-------------
	String appliId = "";
	String businId = "";
	String projId = "";
	String propId = "";
	// String investId = "";
	String isfId = "";

	@RequestMapping(value = "/previewAfIfForm", method = RequestMethod.GET)
	public String previewAfIfForm(Model model, HttpSession session) {

		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : responce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appliId = entry.getValue() + "A1";
			}
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				businId = entry.getValue() + "B1";
			}
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				projId = entry.getValue() + "P1";
			}
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				propId = entry.getValue() + "PE1";
			}

			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				isfId = entry.getValue() + "INC1";
			}
		}

		/* Applicant Details */

		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appliId);
		ApplicantDocument applicantDocument = fileStorageService.getApplicantDocumentByDocAppId(appliId);
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
		BusinessEntity businessEntity = new BusinessEntity();
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
//			String statename = stateRepository
//					.findByStateCode(Long.valueOf(businessEntityDetails.getBusinessStateName()));

			model.addAttribute("businessStateName", businessEntityDetails.getBusinessStateName());
			model.addAttribute("businessDistrictName", businessEntityDetails.getBusinessDistrictName());
			model.addAttribute("PinCode", businessEntityDetails.getPinCode());
			model.addAttribute("yearEstablishment", businessEntityDetails.getYearEstablishment());
			model.addAttribute("gstin", businessEntityDetails.getGstin());
			model.addAttribute("cin", businessEntityDetails.getCin());
			model.addAttribute("companyPanNo", businessEntityDetails.getCompanyPanNo());

			businessDocFromMongoDB(businessEntity, businId, model);

			List<ProprietorDetails> proprietorDetailsList = proprietorService
					.findAllByBusinessId(businessEntityDetails.getId());
			model.addAttribute("ProprietorDetailsList", proprietorDetailsList);

		}

		/* Project Details */

		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);
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

			projDocsFromMongoDB(projId, model);

			// To retrieve ExistingProjectDetailsList from table on projectId basis
			if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
				existProjList = existProjectService.getExistProjListById(projId);
				model.addAttribute(EXIST_PROJ_LIST, existProjList);
			} else {
				existProjList.clear();
			}

		}

		/* Investment Details */

		InvestmentDetails invdtlFromDb = investDs
				.getInvestmentDetailsById(session.getAttribute("investmentId").toString());
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
			model.addAttribute("invdtlFromDb", invdtlFromDb);
			model.addAttribute("phases", pwInvestDs.getPhasesById(session.getAttribute("investmentId").toString()));
			model.addAttribute("invotherlist",
					invOthersService.getAllByINV_ID(session.getAttribute("investmentId").toString()));
			model.addAttribute("invic",
					iNVInstalledCapacitiesService.getAllByINV_ID(session.getAttribute("investmentId").toString()));
			model.addAttribute("momlist", meansOfFinanceService.getMeansOfFinanceList());
			investMongoDBDoc(invdtlFromDb, session.getAttribute("investmentId").toString(), model);

			List<PhaseWiseInvestmentDetails> pwInvListDromDb = pwInvestDs
					.getPwInvDetailListById(invdtlFromDb.getInvId());
			if (pwInvListDromDb.size() > 0) {
				model.addAttribute("pwApply", "Yes");
			} else {
				model.addAttribute("pwApply", "No");
			}

			model.addAttribute("pwInvList", pwInvListDromDb);
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
			model.addAttribute("incentiveId", IncentiveDetail.getId());
			model.addAttribute("category", invdtlFromDb.getInvIndType());

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
			model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList);

			model.addAttribute("incentiveDeatilsData", IncentiveDetail);
		}

		return ("preview_af_if_forms");

	}

	private void businessDocFromMongoDB(BusinessEntity businessEntity, String businessEntityId, Model model) {

		try {
			List<BusinessEntityDocument> beDocList = beDocRepository
					.getBusinessDocListByBusinessEntityId(businessEntityId);

			for (int i = 0; i < beDocList.size(); i++) {
				BusinessEntityDocument beDoc = beDocList.get(i);
				if (beDoc != null) {
					if (i == 0) {
						model.addAttribute("moaDocFname", beDoc.getFileName());
					}
					if (i == 1) {
						model.addAttribute("regisAttacDocFname", beDoc.getFileName());
					}

					if (i == 2) {
						model.addAttribute("bodDocFname", beDoc.getFileName());

					}

					if (i == 3) {
						model.addAttribute("annexureiaformat", beDoc.getFileName());
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ BusinessEntityDocument $$$$ %s", e.getMessage()));
		}

	}

	/**
	 * This method is responsible to retrieve ProjectDetails' documents from MongoDB
	 * collection.
	 */
	public void projDocsFromMongoDB(String projId, Model model) {

		List<ExistProjDetailsDocument> projDocList = projDocRepository.getProjDocListByProjectId(projId);
		for (int i = 0; i < projDocList.size(); i++) {
			ExistProjDetailsDocument projDoc = projDocList.get(i);

			if (projDoc != null) {
				try {
					if (i == 0) {
						model.addAttribute("enclDetProRepFileName", projDoc.getFileName());
					}
					if (i == 1) {
						model.addAttribute("caCertificateFileName", projDoc.getFileName());
					}

					if (i == 2) {
						model.addAttribute("charatEngFileName", projDoc.getFileName());
					}
				} catch (Exception e) {
					logger.error(String.format("3333 projDocsFromMongoDB exception 33333 %s", e.getMessage()));
				}
			}
		}
	}

	/**
	 * This method is responsible to fetch InvestmentDetails documents from MongoDB
	 * collection.
	 */
	public void investMongoDBDoc(InvestmentDetails invdtlFromDb, String investId, Model model) {
		try {
			List<InvestmentDocument> invDocList = invDocRepository.getInvDocListByInvestId(investId);
			for (int i = 0; i < invDocList.size(); i++) {
				InvestmentDocument investDoc = invDocList.get(i);

				if (investDoc != null) {

					if (i == 0) {
						model.addAttribute("invFileName", investDoc.getFileName());
					}
					if (i == 1) {
						model.addAttribute("regiOrLicenseFileName", investDoc.getFileName());
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ investMongoDBDoc exception ^^^ %s", e.getMessage()));
		}
	}

	// RATHOUR--------------------------------------ENDED-------------
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

	@RequestMapping(value = "/downloadDocIncentive", method = RequestMethod.GET)
	public void downloadDocIncentive(@RequestParam(value = "fileName", required = false) String fileName, Model model,
			HttpServletResponse response, HttpSession session) {

		String isfId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : responce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				isfId = entry.getValue() + "INC1";
			}

		}
		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
		String isfCustIncDocName = IncentiveDetail.getIsfCustIncDocName();
		byte[] isfCustIncDocData = IncentiveDetail.getIsfCustIncDoc();
		response.setHeader("Content-Disposition", "attachment; filename=" + isfCustIncDocName + "");
		response.setHeader("Content-Type", "application/pdf");
		InputStream is = new ByteArrayInputStream(isfCustIncDocData);
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

	/**
	 * This method is responsible to download BusinessEntityDetails files from
	 * MongoDB collection 'Ind_Business_Entity_Doc'.
	 */
	@GetMapping(value = "/downloadBusinessEntityDoc1")
	public void downloadBusinessEntityDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response) {

		List<BusinessEntityDocument> beDocList = beDocRepository.getBusinessDocListByBusinessEntityId(businId);
		if (!beDocList.isEmpty()) {
			for (BusinessEntityDocument beDoc : beDocList) {
				if (fileName.equals(beDoc.getFileName())) {
					String beDocName = beDoc.getFileName();
					byte[] beDocData = beDoc.getData();

					downloadMongoDBDoc(beDocName, beDocData, response);
					break;
				}
			}
		}
	}

	/**
	 * This method is responsible to download ProjectDetails files from MongoDB
	 * collection 'Ind_Exist_Proj_Details_Doc'.
	 */
	@GetMapping(value = "/downloadProjectDoc1")
	public void downloadProjectDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response) {

		List<ExistProjDetailsDocument> projDocList = projDocRepository.getProjDocListByProjectId(projId);
		for (ExistProjDetailsDocument epdDoc : projDocList) {
			if (fileName.equals(epdDoc.getFileName())) {
				String epdDocFname = epdDoc.getFileName();
				byte[] epdDocData = epdDoc.getData();

				downloadMongoDBDoc(epdDocFname, epdDocData, response);
				break;
			}
		}
	}

	/**
	 * This method is responsible to download Investment files from MongoDB
	 * collection 'Ind_Investment_Details_Doc'.
	 */
	@GetMapping(value = "/downloadInvestmentDoc1")
	public void downloadInvestmentDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, HttpSession session) {

		List<InvestmentDocument> invDocList = invDocRepository
				.getInvDocListByInvestId(session.getAttribute("investmentId").toString());
		if (!invDocList.isEmpty()) {
			for (InvestmentDocument invDoc : invDocList) {
				if (fileName.equals(invDoc.getFileName())) {
					String invDocFname = invDoc.getFileName();
					byte[] invDocData = invDoc.getData();

					downloadMongoDBDoc(invDocFname, invDocData, response);
					break;
				}
			}
		}
	}

	public void downloadMongoDBDoc(String fileName, byte[] fileData, HttpServletResponse response) {

		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		response.setHeader("Content-Type", "application/pdf");

		// try-with-resources statement
		try (InputStream is = new ByteArrayInputStream(fileData)) {
			try {
				IOUtils.copy(is, response.getOutputStream());
			} catch (IOException e) {
				logger.error(String.format("#### downloadMongoDBDoc exception $$$ %s", e.getMessage()));
			}
		} catch (IOException e1) {
			logger.error(String.format("@@@@@ downloadMongoDBDoc exception @@@@@ %s", e1.getMessage()));
		}
	}
}
