package com.webapp.ims.controller;

import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicantDocument;
import com.webapp.ims.model.BusinessEntity;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.model.StateDetails;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.StateDetailsService;
import com.webapp.ims.webservices.SoapConsumeEx;

@Controller
@SessionAttributes("appId")
public class ApplicantDetailsController {

	private final Logger logger = LoggerFactory.getLogger(ApplicantDetailsController.class);
	private ApplicantDetailsService applicantDetailsService;
	private ApplicantDocumentService fileStorageService;

	@Autowired
	public ApplicantDetailsController(ApplicantDetailsService applicantDetailsService,
			ApplicantDocumentService fileStorageService) {
		super();
		this.applicantDetailsService = applicantDetailsService;
		this.fileStorageService = fileStorageService;
	}

	@Autowired
	private StateDetailsService stateDetailsService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	BusinessEntityDetailsService businessService;
	@Autowired
	private InvestmentDetailsService investDs;
	@Autowired
	private ProposedEmploymentDetailsService proposedEmploymentDetailsService;
	@Autowired
	InvestmentDetailsRepository investmentDetailsRepository;
	@Autowired
	private ProposedEmploymentDetailsService emplDtlService;
	SoapConsumeEx soapdetails = null;

	/*
	 * @GetMapping(value = { "/", "/applicantDetails" }) public String
	 * applicantCommonApplicationForm(Model model, @RequestParam Map<String, String>
	 * params, HttpSession session) {
	 * logger.debug("applicant details form initilization.."); try { SoapDataModel
	 * niveshSoapData = new SoapDataModel(); soapdetails = new
	 * SoapConsumeEx(params); Map<String, String> niveshResponse = new
	 * HashMap<String, String>(soapdetails.soapwebservice());
	 * niveshResponse.put("appID", niveshResponse.get("Unit_Id") + "A1");
	 * niveshSoapData.setNiveshSoapResponse(niveshResponse);
	 * model.addAttribute("niveshSoapResponse", niveshSoapData);
	 * 
	 * model.addAttribute("applicantDetails", new ApplicantDetails());
	 * model.addAttribute("applicantDoc", new ApplicantDocument()); ApplicantDetails
	 * applicantDetails = new ApplicantDetails();
	 * 
	 * ApplicantDetails applicantDetail = applicantDetailsService
	 * .getApplicantDetailsByAppId(niveshResponse.get("appID"));
	 * 
	 * // RATHOUR---------------- ApplicantDocument ApplicantDocument
	 * ApplicantDocument applicantDocument = fileStorageService
	 * .getApplicantDocumentByDocAppId(niveshResponse.get("appID")); if
	 * (applicantDetail != null && applicantDetail.getAppId() != null &&
	 * !applicantDetail.getAppId().isEmpty()) {
	 * applicantDetails.setAppPhoneNo(applicantDetail.getAppPhoneNo());
	 * applicantDetails.setAppDesignation(applicantDetail.getAppDesignation());
	 * applicantDetails.setAppAadharNo(applicantDetail.getAppAadharNo());
	 * applicantDetails.setAppAddressLine2(applicantDetail.getAppAddressLine2());
	 * 
	 * if (applicantDocument != null) {
	 * 
	 * byte[] encodeBase64 = Base64.encodeBase64(applicantDocument.getData());
	 * String base64Encoded = null; try { if (applicantDocument.getFileName() !=
	 * null && applicantDocument.getFileName() != "" &&
	 * !applicantDocument.getFileName().isEmpty()) { base64Encoded = new
	 * String(encodeBase64, "UTF-8"); } } catch (UnsupportedEncodingException e) {
	 * e.printStackTrace(); } applicantDetails.setBase64imageFile(base64Encoded);
	 * applicantDetails.setFileName(applicantDocument.getFileName()); }
	 * soapRestData(model, applicantDetails, niveshResponse);
	 * session.setAttribute("niveshSoapResponse", niveshSoapData);
	 * model.addAttribute("applicantId", applicantDetail.getAppId());
	 * model.addAttribute("Aflag", applicantDetail.getAppId());
	 * BusinessEntityDetails businessEntityDetails = businessService
	 * .getBusinessEntityByapplicantDetailId(applicantDetail.getAppId()); if
	 * (businessEntityDetails != null) { model.addAttribute("Bflag",
	 * businessEntityDetails.getId()); } ProjectDetails ProjectDetail =
	 * projectService.getProjectByapplicantDetailId(applicantDetail.getAppId()); if
	 * (ProjectDetail != null) { model.addAttribute("Pflag", ProjectDetail.getId());
	 * }
	 * 
	 * InvestmentDetails investmentDetails = investmentDetailsRepository
	 * .getInvestmentDetailsByapplId(applicantDetail.getAppId()); if
	 * (investmentDetails != null) { model.addAttribute("Iflag",
	 * investmentDetails.getInvId()); } ProposedEmploymentDetails
	 * proposedEmploymentDetails = emplDtlService
	 * .getProposedEmploymentDetailsByappId(applicantDetail.getAppId()); if
	 * (proposedEmploymentDetails != null) { model.addAttribute("PEflag",
	 * proposedEmploymentDetails.getId()); } } else { soapRestData(model,
	 * applicantDetails, niveshResponse); session.setAttribute("niveshSoapResponse",
	 * niveshSoapData); } } catch (Exception ex) { ex.printStackTrace(); } return
	 * "applicant_details"; }
	 */

	/*
	 * @GetMapping(value = { "/", "/applicantDetails" }) public String
	 * applicantCommonApplicationForm(Model model, @RequestParam Map<String, String>
	 * params, HttpSession session) {
	 * logger.debug("applicant details form initilization.."); try { SoapDataModel
	 * niveshSoapData = new SoapDataModel(); soapdetails = new
	 * SoapConsumeEx(params); Map<String, String> niveshResponse = new
	 * HashMap<String, String>(soapdetails.soapwebservice());
	 * niveshResponse.put("appID", niveshResponse.get("Unit_Id") + "A1");
	 * niveshSoapData.setNiveshSoapResponse(niveshResponse);
	 * model.addAttribute("niveshSoapResponse", niveshSoapData);
	 */

	@GetMapping(value = { "/", "/applicantDetails" })
	public String applicantCommonApplicationForm(Model model, @RequestParam Map<String, String> params,
			HttpSession session) {
		logger.debug("applicant details form initilization..");
		try {
			session.setAttribute("params", params);
			SoapDataModel niveshSoapData = new SoapDataModel();
			soapdetails = new SoapConsumeEx(params);
			Map<String, String> niveshResponse = new HashMap<String, String>(soapdetails.soapwebservice());
			niveshResponse.put("appID", niveshResponse.get("Unit_Id") + "A1");
			session.setAttribute("propid", niveshResponse.get("Unit_Id"));
			niveshSoapData.setNiveshSoapResponse(niveshResponse);
			model.addAttribute("niveshSoapResponse", niveshSoapData);

			model.addAttribute("applicantDetails", new ApplicantDetails());
			model.addAttribute("applicantDoc", new ApplicantDocument());
			ApplicantDetails applicantDetails = new ApplicantDetails();

			ApplicantDetails applicantDetail = applicantDetailsService
					.getApplicantDetailsByAppId(niveshResponse.get("appID"));

			// RATHOUR---------------- ApplicantDocument ApplicantDocument
			ApplicantDocument applicantDocument = fileStorageService
					.getApplicantDocumentByDocAppId(niveshResponse.get("appID"));
			if (applicantDetail != null && applicantDetail.getAppId() != null
					&& !applicantDetail.getAppId().isEmpty()) {
				applicantDetails.setAppPhoneNo(applicantDetail.getAppPhoneNo());
				applicantDetails.setAppDesignation(applicantDetail.getAppDesignation());
				applicantDetails.setAppAadharNo(applicantDetail.getAppAadharNo());
				applicantDetails.setAppAddressLine2(applicantDetail.getAppAddressLine2());

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
					applicantDetails.setBase64imageFile(base64Encoded);
					applicantDetails.setFileName(applicantDocument.getFileName());
				}
				soapRestData(model, applicantDetails, niveshResponse);
				session.setAttribute("niveshSoapResponse", niveshSoapData);
				model.addAttribute("applicantId", applicantDetail.getAppId());
				model.addAttribute("Aflag", applicantDetail.getAppId());
				BusinessEntityDetails businessEntityDetails = businessService
						.getBusinessEntityByapplicantDetailId(applicantDetail.getAppId());
				if (businessEntityDetails != null) {
					model.addAttribute("Bflag", businessEntityDetails.getId());
				}
				ProjectDetails ProjectDetail = projectService.getProjectByapplicantDetailId(applicantDetail.getAppId());
				if (ProjectDetail != null) {
					model.addAttribute("Pflag", ProjectDetail.getId());
				}

				InvestmentDetails investmentDetails = investmentDetailsRepository
						.getInvestmentDetailsByapplId(applicantDetail.getAppId());
				if (investmentDetails != null) {
					model.addAttribute("Iflag", investmentDetails.getInvId());
				}
				ProposedEmploymentDetails proposedEmploymentDetails = emplDtlService
						.getProposedEmploymentDetailsByappId(applicantDetail.getAppId());
				if (proposedEmploymentDetails != null) {
					model.addAttribute("PEflag", proposedEmploymentDetails.getId());
				}
			} else {
//				soapRestData(model, applicantDetails, niveshResponse);
				model.addAttribute("applicantDetails", new ApplicantDetails());
				session.setAttribute("niveshSoapResponse", niveshSoapData);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "applicant_details";
	}

	@PostMapping(value = { "/", "/applicantDetails" }, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public String applicantNiveshCommonApplicationForm(Model model, @RequestParam Map<String, String> params,
			HttpSession session) {
		logger.debug("applicant details form initilization..");

		try {

		//	System.out.println(params);

			session.setAttribute("params", params);

			SoapDataModel niveshSoapData = new SoapDataModel();
			soapdetails = new SoapConsumeEx(params);
			Map<String, String> niveshResponse = new HashMap<String, String>(soapdetails.soapwebservice());

			niveshResponse.put("appID", niveshResponse.get("Unit_Id") + "A1");
			niveshSoapData.setNiveshSoapResponse(niveshResponse);
			model.addAttribute("niveshSoapResponse", niveshSoapData);

			model.addAttribute("applicantDetails", new ApplicantDetails());
			model.addAttribute("applicantDoc", new ApplicantDocument());
			ApplicantDetails applicantDetails = new ApplicantDetails();

			ApplicantDetails applicantDetail = applicantDetailsService
					.getApplicantDetailsByAppId(niveshResponse.get("appID"));

			ApplicantDocument applicantDocument = fileStorageService
					.getApplicantDocumentByDocAppId(niveshResponse.get("appID"));

			if (applicantDetail != null && applicantDetail.getAppId() != null
					&& !applicantDetail.getAppId().isEmpty()) {
				applicantDetails.setAppPhoneNo(applicantDetail.getAppPhoneNo());
				applicantDetails.setAppDesignation(applicantDetail.getAppDesignation());
				applicantDetails.setAppAadharNo(applicantDetail.getAppAadharNo());
				applicantDetails.setAppAddressLine2(applicantDetail.getAppAddressLine2());

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
					applicantDetails.setBase64imageFile(base64Encoded);
					applicantDetails.setFileName(applicantDocument.getFileName());
				}

				soapRestData(model, applicantDetails, niveshResponse);
				session.setAttribute("soapDeatils", soapdetails);
				session.setAttribute("niveshSoapResponse", niveshSoapData);
				model.addAttribute("applicantId", applicantDetail.getAppId());
				model.addAttribute("Aflag", applicantDetail.getAppId());

				System.out.println("niveshResponse:::::::::" + niveshResponse);
				BusinessEntityDetails businessEntityDetails = businessService
						.getBusinessEntityByapplicantDetailId(applicantDetail.getAppId());
				if (businessEntityDetails != null) {
					model.addAttribute("Bflag", businessEntityDetails.getId());
				}
				ProjectDetails ProjectDetail = projectService.getProjectByapplicantDetailId(applicantDetail.getAppId());
				if (ProjectDetail != null) {
					model.addAttribute("Pflag", ProjectDetail.getId());
				}

				InvestmentDetails investmentDetails = investmentDetailsRepository
						.getInvestmentDetailsByapplId(applicantDetail.getAppId());
				if (investmentDetails != null) {
					model.addAttribute("Iflag", investmentDetails.getInvId());
				}
				ProposedEmploymentDetails proposedEmploymentDetails = emplDtlService
						.getProposedEmploymentDetailsByappId(applicantDetail.getAppId());
				if (proposedEmploymentDetails != null) {
					model.addAttribute("PEflag", proposedEmploymentDetails.getId());
				}

			} else {
				soapRestData(model, applicantDetails, niveshResponse);
				//model.addAttribute("applicantDetails", new ApplicantDetails());
				session.setAttribute("soapDeatils", soapdetails);
				session.setAttribute("niveshSoapResponse", niveshSoapData);
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "applicant_details";

	}

	private void soapRestData(Model model, ApplicantDetails applicantDetails, Map<String, String> niveshResponse) {
		try {
			for (Map.Entry<String, String> entry : niveshResponse.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Occupier_First_Name")) {
					applicantDetails.setAppFirstName(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_Middle_Name")) {
					applicantDetails.setAppMiddleName(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_Last_Name")) {
					applicantDetails.setAppLastName(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_Email_ID")) {
					applicantDetails.setAppEmailId(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_Mobile_No")) {
					applicantDetails.setAppMobileNo(Long.valueOf((entry.getValue())));
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_Gender")) {
					applicantDetails.setGender(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_PAN")) {
					applicantDetails.setAppPancardNo(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_Address")) {
					applicantDetails.setAppAddressLine1(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_Country")) {
					applicantDetails.setAppCountry(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_State_ID")) {
					StateDetails stateDetails = stateDetailsService.getStateBystateCode(Long.valueOf(entry.getValue()));
					model.addAttribute("appState", stateDetails.getStateName());
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_District_Name")) {

					model.addAttribute("appDistrict", entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_Pin_Code")) {
					applicantDetails.setAppPinCode(Long.valueOf(entry.getValue()));
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		model.addAttribute("applicantDetails", applicantDetails);
	}

	public void appIdToRequestId() {
		Map<String, String> appIdToRequestId = new HashMap<String, String>();
		try {

			appIdToRequestId.put("", null);

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {

		}

	}

	/*
	 * The responsibility of this method is to save AuthorizedSignatoryDetails
	 * records in the table and document in mongodb collection.
	 */
	@SuppressWarnings("unchecked")
	public void commonSaveApplicantDetails(ApplicantDetails applicantDetails, BindingResult result, Model model,
			MultipartFile multipartFile, HttpSession session) {
		Object niveshResponse = null;
		Map<String, String> response = null;
		try {
			Map<String, String> params = null;
			if (session != null) {
				niveshResponse = session.getAttribute("niveshSoapResponse");

//				System.out.println("niveshresponse :::" + niveshResponse);

				params = (Map<String, String>) session.getAttribute("params");

			}
			if (niveshResponse != null) {
				response = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
				applicantDetails.setAppId(response.get("appID"));
				applicantDetails.setControlId("TxtRequestID");
			}
			applicantDetails.setPasssalt("v8926bb8a5c915ba4b896325bc3c2k3w");
			applicantDetails.setProcessIndustryId(params.get("TxtProcessIndustryID"));
			applicantDetails.setServiceId(params.get("TxtServiceID"));
			applicantDetails.setUnitId(params.get("TxtUnitID"));
			applicantDetails.setControlId(params.get("TxtControlID"));
			applicantDetails.setRequestId(params.get("TxtRequestID"));
			applicantDetails.setSubmitStatus(false);
			applicantDetails.setFileName(multipartFile.getOriginalFilename());
			applicantDetails.setStatus("Active");
			applicantDetails.setAppCreatedBy("User");
			applicantDetails.setApcModifiyDate(new Date());
			applicantDetails.setAppPhoto(multipartFile.getBytes());

			applicantDetailsService.saveApplicantDetails(applicantDetails);

			// save document in mongodb collection
			if (multipartFile.getOriginalFilename() != null && !multipartFile.getOriginalFilename().isEmpty()) {
				fileStorageService.saveAndUpdateFile(multipartFile, session);
			}

			model.addAttribute("businessEntityDetailsForm", new BusinessEntity());
			model.addAttribute("proprietorDetails", new ProprietorDetails());
			model.addAttribute("applicantDetails", applicantDetails.getAppId());
			model.addAttribute("applicantDetails", applicantDetails);
			if (session != null) {
				session.removeAttribute("businessEntityDetailsId");
				session.setAttribute("applicantDetailsId", applicantDetails.getAppId());
				session.setAttribute("applicantDetails", applicantDetails);
			}

			model.addAttribute("proprietorDetails", new ProprietorDetails());
			model.addAttribute("niveshSoapResponse", niveshResponse);
		} catch (Exception ex) {
			logger.error(String.format("!!!! commonSaveApplicantDetails!!!! %s", ex.getMessage()));
		}

	}

	/**/
	@PostMapping("/saveApplicantDetails")
	public ModelAndView saveApplicantDetails(
			@Validated @ModelAttribute("applicantDetails") ApplicantDetails applicantDetails, BindingResult result,
			Model model, @RequestParam("file") MultipartFile multipartFile, HttpSession session) {

		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		
		if (result.hasErrors()) {
//			ApplicantDetails applicantDetails2 = applicantDetails;
//			soapRestData(model, applicantDetails, responce);
			model.addAttribute("applicantDetails", applicantDetails);
			return new ModelAndView("applicant_details");
		}
		commonSaveApplicantDetails(applicantDetails, result, model, multipartFile, session);
		soapResponseData(model, applicantDetails, responce);

		return new ModelAndView("redirect:/businessDetails");

	}

	// Method for update status on Nivesh Mitra

	void soapResponseData(Model model, ApplicantDetails applicantDetails, Map<String, String> responce) {
		SoapDataModel niveshSoapData = new SoapDataModel();
		Map<String, String> params = new HashMap<String, String>();

		params.put("ControlID", "UPSWP200001485");
		params.put("UnitID", "UPSWP20000148501");
		params.put("ServiceID", "SC54001");
		params.put("ProcessIndustryID", "54");
		params.put("passsalt", "v8926bb8a5c915ba4b896325bc3c2k3w");
		soapdetails = new SoapConsumeEx(params);
		// String niveshResponse = soapdetails.WReturn_CUSID_STATUS();
//		WReturn_CUSID_STATUS wReturn_CUSID_STATUS=new WReturn_CUSID_STATUS();
//		Map<String, String> niveshResponse = new HashMap<String, String>(soapdetails.WReturn_CUSID_STATUS( wReturn_CUSID_STATUS));
//
//		String str = soapdetails.WGetUBPaymentDetails(niveshResponse);
//		str = soapdetails.WReturn_CUSID_Entrepreneur_NOC_IN_BINARYFORMAT(niveshResponse);
//		str = soapdetails.WReturn_CUSID_ISLandPurchased( niveshResponse);
//
//		System.out.println("Nivesh Response :::::*****:::::" + niveshResponse);
		System.out.println("Model" + model);
		System.out.println("ApplicantDetails" + applicantDetails);

	}

	@PostMapping("/autosaveApplicantDetails")
	public ModelAndView autosaveApplicantDetails(
			@Validated @ModelAttribute("databaseFile") ApplicantDocument applicantDocument,
			@Validated @ModelAttribute("applicantDetails") ApplicantDetails applicantDetails, BindingResult result,
			Model model, @RequestParam("file") MultipartFile multipartFile, HttpSession session) {

		try {
			commonSaveApplicantDetails(applicantDetails, result, model, multipartFile, session);
			Object niveshResponse = session.getAttribute("niveshSoapResponse");
			Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			if (result.hasErrors()) {
				soapRestData(model, applicantDetails, responce);
				return new ModelAndView("applicant_details");
			}

		} catch (Exception ex) {
			logger.error(String.format("!!!! autosaveApplicantDetails!!!! %s", ex.getMessage()));
		}
		return new ModelAndView("redirect:/editApplicantForm");
	}

	@RequestMapping(value = "/editApplicantForm", method = RequestMethod.GET)
	public ModelAndView editApplicantForm(Model model, HttpSession session) {

		logger.debug("Initilization edit Applicant Details Form Details.");

		String appliId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appliId = entry.getValue() + "A1";
			}
		}
		try {
			ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appliId);
			ApplicantDocument applicantDocument = fileStorageService.getApplicantDocumentByDocAppId(appliId);
			if (applicantDetail != null && applicantDetail.getAppId() != null
					&& !applicantDetail.getAppId().isEmpty()) {

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
				model.addAttribute("applicantDetails", applicantDetail);
				model.addAttribute("applicantId", applicantDetail.getAppId());
				model.addAttribute("Aflag", applicantDetail.getAppId());
				BusinessEntityDetails businessEntityDetails = businessService
						.getBusinessEntityByapplicantDetailId(applicantDetail.getAppId());
				if (businessEntityDetails != null) {
					model.addAttribute("Bflag", businessEntityDetails.getId());
				}
				ProjectDetails ProjectDetail = projectService.getProjectByapplicantDetailId(applicantDetail.getAppId());
				if (ProjectDetail != null) {
					model.addAttribute("Pflag", ProjectDetail.getId());
				}

				InvestmentDetails investmentDetails = investmentDetailsRepository
						.getInvestmentDetailsByapplId(applicantDetail.getAppId());
				if (investmentDetails != null) {
					model.addAttribute("Iflag", investmentDetails.getInvId());
				}
				ProposedEmploymentDetails proposedEmploymentDetails = emplDtlService
						.getProposedEmploymentDetailsByappId(applicantDetail.getAppId());
				if (proposedEmploymentDetails != null) {
					model.addAttribute("PEflag", proposedEmploymentDetails.getId());
				}

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("applicant_details");
	}

	@RequestMapping(value = "/applicantFormTabs", method = RequestMethod.GET)
	public ModelAndView applicantFormTabs(Model model, HttpSession session) {

		logger.debug("Initilization edit Applicant Details Form Details.");

		SoapDataModel niveshSoapData = new SoapDataModel();
		String appliId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> responceNiveshMitra = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		responceNiveshMitra.put("appID", responceNiveshMitra.get("Unit_Id") + "A1");
		niveshSoapData.setNiveshSoapResponse(responceNiveshMitra);
		model.addAttribute("niveshSoapResponse", niveshSoapData);
		for (Map.Entry<String, String> entry : responceNiveshMitra.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appliId = entry.getValue() + "A1";
			}
		}
		try {
			ApplicantDetails applicantDetails = new ApplicantDetails();
			ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appliId);
			ApplicantDocument applicantDocument = fileStorageService.getApplicantDocumentByDocAppId(appliId);
			if (applicantDetail != null && applicantDetail.getAppId() != null
					&& !applicantDetail.getAppId().isEmpty()) {

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
				model.addAttribute("applicantDetails", applicantDetail);
				model.addAttribute("applicantId", applicantDetail.getAppId());
			} else {
				soapRestData(model, applicantDetails, responceNiveshMitra);
				session.setAttribute("soapDeatils", soapdetails);
				session.setAttribute("niveshSoapResponse", niveshSoapData);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return new ModelAndView("applicant_details");
	}

	/*-----------------------------tabs-------------------------------------*/

	@RequestMapping(value = "/getIdByTabs1", method = RequestMethod.GET)
	public ModelAndView getIdByTabs1(Model model, HttpSession session,
			@RequestParam(value = "busiTab") String busiTab) {

		String appId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appId = entry.getValue() + "A1";
			}
		}
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);
		BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(appId);
		if (businessEntityDetails != null && busiTab.equalsIgnoreCase("busiTab")) {
			session.setAttribute("applicantDetails", applicantDetail);
			session.setAttribute("applicantDetailsId", appId);
			return new ModelAndView("redirect:/businessDetails");
		} else {
			return new ModelAndView("redirect:/applicantFormTabs");
		}

	}

	@RequestMapping(value = "/getIdByTabs2", method = RequestMethod.GET)
	public ModelAndView getIdByTabs2(Model model, HttpSession session,
			@RequestParam(value = "projTab") String projTab) {

		String projId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				projId = entry.getValue() + "P1";
			}
		}
		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);
		if (ProjectDetail != null && projTab.equalsIgnoreCase("projTab")) {
			return new ModelAndView("redirect:/projectDetails");
		} else {
			return new ModelAndView("redirect:/applicantFormTabs");
		}

	}

	@RequestMapping(value = "/getIdByTabs3", method = RequestMethod.GET)
	public ModelAndView getIdByTabs3(Model model, HttpSession session,
			@RequestParam(value = "investTab") String investTab) {

		String investId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				investId = entry.getValue() + "I1";
			}
		}
		InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(investId);
		if (invdtlFromDb != null && investTab.equalsIgnoreCase("investTab")) {
			return new ModelAndView("redirect:/investmentDetails");
		} else {
			return new ModelAndView("redirect:/applicantFormTabs");
		}

	}

	@RequestMapping(value = "/getIdByTabs4", method = RequestMethod.GET)
	public ModelAndView getIdByTabs4(Model model, HttpSession session,
			@RequestParam(value = "propoTab") String propoTab) {

		String propoId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				propoId = entry.getValue() + "PE1";
			}
		}
		ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService.getPropEmpById(propoId);
		if (proposedEmploymentDetail != null && propoTab.equalsIgnoreCase("propoTab")) {
			return new ModelAndView("redirect:/skilUnskEmplDet");
		} else {
			return new ModelAndView("redirect:/applicantFormTabs");
		}

	}

}
