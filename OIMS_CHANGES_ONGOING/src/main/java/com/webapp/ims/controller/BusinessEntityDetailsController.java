package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.NIVESH_SOAP_RESPONSE;
import static com.webapp.ims.exception.GlobalConstants.Unit_Id;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.view.RedirectView;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.BusinessEntity;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.BusinessEntityDocument;
import com.webapp.ims.model.DistrictDetails1;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.model.StateDetails;
import com.webapp.ims.repository.BusinessEntityDocumentRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.ProprietorDetailsRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.BusinessEntityDocumentService;
import com.webapp.ims.service.DistrictDetailsMasterService;
import com.webapp.ims.service.DistrictDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;
import com.webapp.ims.service.StateDetailsService;
import com.webapp.ims.webservices.SoapConsumeEx;

@Controller
@Component
public class BusinessEntityDetailsController {

//	private AtomicInteger atomicInteger = new AtomicInteger();
	private final Logger logger = LoggerFactory.getLogger(BusinessEntityDetailsController.class);

//	private List<ProprietorDetails> proprietorDetailsList = new ArrayList<ProprietorDetails>();

//	private Map<String, ProprietorDetails> proprietorDetailsMap = new HashMap<String, ProprietorDetails>();

	private static final String INTERNALFILE = "documents/Annexure-I-A-Format.doc";
	int rowIndex = 0;
	boolean isRowRecord = false;

	@Autowired
	BusinessEntityDetailsService businessService;

	@Autowired
	BusinessEntityDocumentService businessEntyDocService;
	@Autowired
	BusinessEntityDocumentRepository beDocRepository;

	@Autowired
	private DistrictDetailsService districtDetailsService;

	@Autowired
	private DistrictDetailsMasterService daDetailsMasterService;

	@Autowired
	private StateDetailsService stateDetailsService;

	@Autowired
	ProprietorDetailsRepository proprietorDetailsRepository;

	@Autowired
	ProprietorDetailsService proprietorService;
	@Autowired
	private ProjectService projectService;
	@Autowired
	private InvestmentDetailsService investDs;
	@Autowired
	private ProposedEmploymentDetailsService proposedEmploymentDetailsService;
	@Autowired
	private ApplicantDetailsService applicantDetailsService;

	@Autowired
	InvestmentDetailsRepository investmentDetailsRepository;
	@Autowired
	private ProposedEmploymentDetailsService emplDtlService;
	SoapConsumeEx soapdetails = new SoapConsumeEx();

	private void businessDocFromMongoDB(BusinessEntity businessEntity, String businessEntityId) {

		try {
			List<BusinessEntityDocument> beDocList = beDocRepository
					.getBusinessDocListByBusinessEntityId(businessEntityId);

			for (int i = 0; i < beDocList.size(); i++) {
				BusinessEntityDocument beDoc = beDocList.get(i);
				if (beDoc != null) {
					if (i == 0) {
						byte[] encodeBase64 = Base64.encodeBase64(beDoc.getData());
						String moaDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
						businessEntity.setMoaDocbase64File(moaDocbase64Encoded);
						businessEntity.setMoaDocFname(beDoc.getFileName());
					}
					if (i == 1) {
						byte[] encodeBase64 = Base64.encodeBase64(beDoc.getData());
						String regisAttacbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
						businessEntity.setRegisAttacbase64File(regisAttacbase64Encoded);
						businessEntity.setRegisAttacDocFname(beDoc.getFileName());
					}

					if (i == 2) {
						byte[] encodeBase64 = Base64.encodeBase64(beDoc.getData());
						String bodDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
						businessEntity.setBodDocbase64File(bodDocbase64Encoded);
						businessEntity.setBodDocFname(beDoc.getFileName());
					}

					if (i == 3) {
						byte[] encodeBase64 = Base64.encodeBase64(beDoc.getData());
						String indAffiDocbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
						businessEntity.setIndusAffidaDocbase64File(indAffiDocbase64Encoded);
						businessEntity.setAnnexureiaformat(beDoc.getFileName());
					}
				}
			}
		} catch (Exception e) {
			logger.error(String.format("$$$$ BusinessEntityDocument $$$$ %s", e.getMessage()));
		}

	}

	@RequestMapping(value = "/businessDetails", method = RequestMethod.GET)
	public ModelAndView show(Model model, HttpSession session) {
		logger.debug("View Business Entity Details");
		String businessEntityId = null;
		try {
			Object niveshResponse = session.getAttribute("niveshSoapResponse");
			Map<String, String> businessResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

			BusinessEntity businessEntity = new BusinessEntity();
			String appId = "";
			for (Map.Entry<String, String> entry : businessResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Company_Name")) {
					businessEntity.setBusinessEntityName(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					appId = entry.getValue() + "A1";
					businessEntityId = entry.getValue() + "B1";
				}
			}

			session.setAttribute("besiId", businessEntityId);

			businessEntity.setEmailId(businessResponce.get("Occupier_Email_ID"));
			businessEntity.setMobileNumber(Long.parseLong(businessResponce.get("Occupier_Mobile_No")));
			businessEntity.setBusinessAddress(businessResponce.get("Occupier_Address"));
			businessEntity.setBusinessCountryName("India");
			businessEntity.setBusinessDistrictName(businessResponce.get("Occupier_District_Name"));
			businessEntity.setBusinessEntityType(businessResponce.get("Organization_Type"));

			StateDetails stateDetails = stateDetailsService
					.getStateBystateCode(Long.parseLong(businessResponce.get("Occupier_State_ID")));
			model.addAttribute("appState", stateDetails.getStateName());
			businessEntity.setBusinessStateName(stateDetails.getStateName());
			businessEntity.setPinCode(Long.parseLong(businessResponce.get("Occupier_Pin_Code")));

			List<StateDetails> stateDetailslst = stateDetailsService.findAllByStateName();
			if (stateDetailslst.size() > 0) {
				Collections.sort(stateDetailslst, new Comparator<StateDetails>() {

					@Override
					public int compare(StateDetails o1, StateDetails o2) {
						return o1.getStateName().compareTo(o2.getStateName());
					}
				});
			}
			if (stateDetails != null) {
				model.addAttribute("stateDetails", stateDetailslst);
			}

			List<DistrictDetails1> districtDetails = daDetailsMasterService.findAllByDistrictName();

			if (districtDetails.size() > 0) {
				Collections.sort(districtDetails, new Comparator<DistrictDetails1>() {

					@Override
					public int compare(DistrictDetails1 o1, DistrictDetails1 o2) {
						return o1.getDistrictName().compareTo(o2.getDistrictName());
					}
				});
			}

			if (districtDetails != null) {
				model.addAttribute("districtDetails", districtDetails);
			}

			if (appId != null && !appId.isEmpty()) {

				BusinessEntityDetails businessEntityDetails = businessService
						.getBusinessEntityByapplicantDetailId(appId);
				if (businessEntityDetails != null) {
					businessEntity.setApplicantDetailId(businessEntityDetails.getApplicantDetailId());
					businessEntity.setAuthorisedSignatoryName(businessEntityDetails.getAuthorisedSignatoryName());
					businessEntity.setBusinessEntityType(businessEntityDetails.getBusinessEntityType());
//					businessEntity.setBusinessAddress(businessEntityDetails.getBusinessAddress());
//					businessEntity.setBusinessCountryName(businessEntityDetails.getBusinessCountryName());
//					businessEntity.setBusinessDistrictName(businessEntityDetails.getBusinessDistrictName());
//					businessEntity.setBusinessStateName(businessEntityDetails.getBusinessStateName());
					businessEntity.setCompanyPanNo(businessEntityDetails.getCompanyPanNo());
//					businessEntity.setPinCode(businessEntityDetails.getPinCode());
//					businessEntity.setMobileNumber(businessEntityDetails.getMobileNumber());
					businessEntity.setPhoneNo(businessEntityDetails.getPhoneNo());
					businessEntity.setFax(businessEntityDetails.getFax());
					businessEntity.setYearEstablishment(businessEntityDetails.getYearEstablishment());
//					businessEntity.setEmailId(businessEntityDetails.getEmailId());
					businessEntity.setGstin(businessEntityDetails.getGstin());
					businessEntity.setCin(businessEntityDetails.getCin());

					session.setAttribute("bidId", businessEntityDetails.getId());

					// fetch BusinessDocument from MongoDB collection
					businessDocFromMongoDB(businessEntity, businessEntityId);

					// businessEntity.setId(businessEntityDetails.getId());
					// proprietorDetailsList.clear();
					List<ProprietorDetails> proprietorDetailsListexist = proprietorService
							.findAllByBusinessId(businessEntityId);
//					proprietorDetailsList.addAll(proprietorDetailsListexist);
					List<ProprietorDetails> proprietorDetailsLists = proprietorDetailsRepository.findAll();
					String besiId = (String) session.getAttribute("besiId");
					List<ProprietorDetails> proprietorDetailsList1 = proprietorService.findAllByBusinessId(besiId);
					model.addAttribute("ProprietorDetailsList", proprietorDetailsListexist);
					model.addAttribute("bussinessId", businessEntityDetails.getId());
					ProjectDetails ProjectDetail = projectService.getProjectByapplicantDetailId(appId);
					if (ProjectDetail != null) {
						model.addAttribute("Pflag", ProjectDetail.getId());
					}

					InvestmentDetails investmentDetails = investmentDetailsRepository
							.getInvestmentDetailsByapplId(appId);
					if (investmentDetails != null) {
						model.addAttribute("Iflag", investmentDetails.getInvId());
					}
					ProposedEmploymentDetails proposedEmploymentDetails = emplDtlService
							.getProposedEmploymentDetailsByappId(appId);
					if (proposedEmploymentDetails != null) {
						model.addAttribute("PEflag", proposedEmploymentDetails.getId());
					}
				} else {
//					proprietorDetailsList.clear();
					// proprietorDetailsRepository
					// .deleteAll(proprietorDetailsRepository.findAllByBusinessEntityDetails(businessEntityId));
				}
			}
			ApplicantDetails applicantDetails = applicantDetailsService.getApplicantDetailsByAppId(appId);
			businessEntity.setAuthorisedSignatoryName(applicantDetails.getAppFirstName() + " "
					+ applicantDetails.getAppMiddleName() + " " + applicantDetails.getAppLastName());
			businessEntity.setBusinessDesignation(applicantDetails.getAppDesignation());
			model.addAttribute("businessEntityDetails", businessEntity);
			String formattedDate = null;
			if (businessEntity.getYearEstablishment() != null) {

				DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
				formattedDate = formatter.format(businessEntity.getYearEstablishment());

			}
			model.addAttribute("bedDate", formattedDate);
			model.addAttribute("proprietorDetails", new ProprietorDetails());
			model.addAttribute("niveshSoapResponse", niveshResponse);
			model.addAttribute("applicantDetails", applicantDetails);

		} catch (Exception e) {
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("http://72.167.225.87/Testing_NMSWP/");
			e.printStackTrace();
		}
		return new ModelAndView("business_entity_details");
	}

	public void commonSaveBusinessEntityRecords(BusinessEntity businessEntity, BindingResult result,
			MultipartFile multipartFile, MultipartFile multipartFile1, MultipartFile multipartFile2,
			MultipartFile multipartFile4, Model model, HttpSession session) {
		// List<ProprietorDetails> proprietorDetailsList = (List<ProprietorDetails>)
		// session.getAttribute("ProprietorDetailsList");
		/*
		 * if (!proprietorDetailsList.isEmpty()) { List<ProprietorDetails>
		 * proprietorDetailsList11 = (List<ProprietorDetails>)
		 * session.getAttribute("ProprietorDetailsList");
		 * model.addAttribute("ProprietorDetailsList", proprietorDetailsList11);
		 */
		// model.addAttribute("ProprietorDetailsList", proprietorDetailsList);
		List<ProprietorDetails> propDetailsList1 = new ArrayList<ProprietorDetails>();
		ProjectDetails projectDetails = new ProjectDetails();
		String besiId = "";
		String applictId = "";
		try {
			Object niveshResponse = session.getAttribute("niveshSoapResponse");
			Map<String, String> responce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

			for (Map.Entry<String, String> entry : responce.entrySet()) {

				if (entry.getKey().equalsIgnoreCase("Industry_Type_Name")) {
					projectDetails.setProjectDescription(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Occupier_Name")) {
					projectDetails.setContactPersonName(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Industry_Address")) {
					projectDetails.setFullAddress(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Industry_District")) {
					model.addAttribute("districtName", entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Industry_Mandal")) {
					projectDetails.setMandalName(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Industry_Region")) {
					projectDetails.setResionName(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Pin_Code")) {
					projectDetails.setPinCode(Integer.parseInt(entry.getValue()));
				}
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					besiId = entry.getValue() + "B1";
				}
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					applictId = entry.getValue() + "A1";
				}
			}

			ApplicantDetails applicantDetails = applicantDetailsService.getApplicantDetailsByAppId(applictId);

			businessEntity.setAuthorisedSignatoryName(applicantDetails.getAppFirstName() + " "
					+ applicantDetails.getAppMiddleName() + " " + applicantDetails.getAppLastName());
			businessEntity.setBusinessDesignation(applicantDetails.getAppDesignation());
			BusinessEntityDetails businessEntityDetails = new BusinessEntityDetails();
			businessEntityDetails.setApplicantDetailId(businessEntity.getApplicantDetailId());
			businessEntityDetails.setAuthorisedSignatoryName(businessEntity.getAuthorisedSignatoryName());
			businessEntityDetails.setBusinessAddress(businessEntity.getBusinessAddress());
			businessEntityDetails.setBusinessCountryName(businessEntity.getBusinessCountryName());
			businessEntityDetails.setBusinessDesignation(businessEntity.getBusinessDesignation());
			businessEntityDetails.setBusinessDistrictName(businessEntity.getBusinessDistrictName());
			businessEntityDetails.setBusinessEntityName(businessEntity.getBusinessEntityName());
			businessEntityDetails.setBusinessEntityType(businessEntity.getBusinessEntityType());
			businessEntityDetails.setBusinessStateName(businessEntity.getBusinessStateName());
			businessEntityDetails.setCompanyPanNo(businessEntity.getCompanyPanNo());
			businessEntityDetails.setPinCode(businessEntity.getPinCode());
			businessEntityDetails.setMobileNumber(businessEntity.getMobileNumber());
			businessEntityDetails.setPhoneNo(businessEntity.getPhoneNo());
			businessEntityDetails.setFax(businessEntity.getFax());
			businessEntityDetails.setYearEstablishment(businessEntity.getYearEstablishment());
			businessEntityDetails.setGstin(businessEntity.getGstin());
			businessEntityDetails.setCin(businessEntity.getCin());

			BusinessEntityDetails busineEntDetaiExisit = businessService
					.getBusinessEntityByapplicantDetailId(applictId);

			try {
				List<MultipartFile> multipartFileList = new LinkedList<>();
				if ((!multipartFile.getOriginalFilename().isEmpty() && multipartFile.getOriginalFilename() != null)
						&& multipartFile.getBytes() != null) {
					multipartFileList.add(multipartFile);
				}
				if ((!multipartFile1.getOriginalFilename().isEmpty() && multipartFile1.getOriginalFilename() != null)
						&& multipartFile1.getBytes() != null) {
					multipartFileList.add(multipartFile1);
				}

				if ((!multipartFile2.getOriginalFilename().isEmpty() && multipartFile2.getOriginalFilename() != null)
						&& multipartFile2.getBytes() != null) {
					multipartFileList.add(multipartFile2);
				}

				if ((!multipartFile4.getOriginalFilename().isEmpty() && multipartFile4.getOriginalFilename() != null)
						&& multipartFile4.getBytes() != null) {
					multipartFileList.add(multipartFile4);
				}

				if (multipartFile.getOriginalFilename().isEmpty() || multipartFile.getOriginalFilename() == null) {
					List<BusinessEntityDocument> beDocList = beDocRepository
							.getBusinessDocListByBusinessEntityId(besiId);
					BusinessEntityDocument beDocObj = beDocRepository.findByDocId(beDocList.get(0).getDocId());
					BusinessEntityDocument dbFile = new BusinessEntityDocument();
					dbFile.set_id(beDocObj.get_id());
					dbFile.setFileName(beDocObj.getFileName());
					dbFile.setFileType(beDocObj.getFileType());
					dbFile.setData(beDocObj.getData());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					dbFile.setBusinessEntityId(besiId);
					dbFile.setDocId(beDocObj.getDocId());
					beDocRepository.save(dbFile);
				}

				if (multipartFile1.getOriginalFilename().isEmpty() || multipartFile1.getOriginalFilename() == null) {
					List<BusinessEntityDocument> beDocList = beDocRepository
							.getBusinessDocListByBusinessEntityId(besiId);
					BusinessEntityDocument beDocObj = beDocRepository.findByDocId(beDocList.get(1).getDocId());
					BusinessEntityDocument dbFile = new BusinessEntityDocument();
					dbFile.set_id(beDocObj.get_id());
					dbFile.setFileName(beDocObj.getFileName());
					dbFile.setFileType(beDocObj.getFileType());
					dbFile.setData(beDocObj.getData());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					dbFile.setBusinessEntityId(besiId);
					dbFile.setDocId(beDocObj.getDocId());
					beDocRepository.save(dbFile);
				}

				if (multipartFile2.getOriginalFilename().isEmpty() || multipartFile2.getOriginalFilename() == null) {
					List<BusinessEntityDocument> beDocList = beDocRepository
							.getBusinessDocListByBusinessEntityId(besiId);
					BusinessEntityDocument beDocObj = beDocRepository.findByDocId(beDocList.get(2).getDocId());
					BusinessEntityDocument dbFile = new BusinessEntityDocument();
					dbFile.set_id(beDocObj.get_id());
					dbFile.setFileName(beDocObj.getFileName());
					dbFile.setFileType(beDocObj.getFileType());
					dbFile.setData(beDocObj.getData());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					dbFile.setBusinessEntityId(besiId);
					dbFile.setDocId(beDocObj.getDocId());
					beDocRepository.save(dbFile);
				}

				if (multipartFile4.getOriginalFilename().isEmpty() || multipartFile4.getOriginalFilename() == null) {
					List<BusinessEntityDocument> beDocList = beDocRepository
							.getBusinessDocListByBusinessEntityId(besiId);
					BusinessEntityDocument beDocObj = beDocRepository.findByDocId(beDocList.get(3).getDocId());
					BusinessEntityDocument dbFile = new BusinessEntityDocument();
					dbFile.set_id(beDocObj.get_id());
					dbFile.setFileName(beDocObj.getFileName());
					dbFile.setFileType(beDocObj.getFileType());
					dbFile.setData(beDocObj.getData());
					dbFile.setDocCreatedBy("User");
					dbFile.setDocUpdateDate(new Date());
					dbFile.setDocCreatedDate(new Date());
					dbFile.setBusinessEntityId(besiId);
					dbFile.setDocId(beDocObj.getDocId());
					beDocRepository.save(dbFile);
				}

				businessEntyDocService.saveAndUpdateMultipleFiles(multipartFileList, session);
			} catch (Exception e) {
				logger.error(String.format("***** BusinessEntity document exception  **** %s", e.getMessage()));
			}

			businessEntityDetails.setCreated_By("Admin");
			businessEntityDetails.setModified_By("Admin");
			businessEntityDetails.setModify_Date(businessEntity.getModify_Date());
			businessEntityDetails.setStatus("Activate");
			businessEntityDetails.setEmailId(businessEntity.getEmailId());
			businessEntityDetails.setId(besiId);
			businessEntityDetails.setApplicantDetailId(applictId);

			/*
			 * List<ProprietorDetails> proprietorDetailsList1 =
			 * proprietorService.findAllByBusinessId(besiId); for (ProprietorDetails list :
			 * proprietorDetailsList1) { proprietorService.deleteBypropId(list.getPropId());
			 * }
			 */

			try {

				businessService.updateBusinessEntity(businessEntityDetails);
			} catch (Exception e) {
				logger.error(String.format("1111 BusinessEntity 11111 %s", e.getMessage()));
			}

			try {
				// vinay
				businessService.saveBusinessEntity(businessEntityDetails);
				// businessService.updateBusinessEntity(businessEntityDetails);
			} catch (Exception e) {
				logger.error(String.format("1111 BusinessEntity 11111 %s", e.getMessage()));
			}

			try {

				// proprietorService.saveProprietorDetails(proprietorDetailsList);
				// model.addAttribute("ProprietorDetailsList", proprietorDetailsList);
			} catch (Exception e) {
				logger.error(String.format("4444 proprietorService exception 4444 %s", e.getMessage()));
			}

			propDetailsList1 = proprietorService.findAllByBusinessId(besiId);
			model.addAttribute("ProprietorDetailsList", propDetailsList1);

			model.addAttribute("businessEntityDetails", businessEntity);
			model.addAttribute("proprietorDetails", new ProprietorDetails());
			model.addAttribute("businessEntityDetails", new BusinessEntity());
			session.setAttribute("businessEntityDetailsId", businessEntity.getId());
			session.setAttribute("businessEntityDetails", businessEntity);

			model.addAttribute("projectDetailsForm", projectDetails);
			model.addAttribute("applicantDetails", applicantDetails);
			model.addAttribute("niveshSoapResponse", niveshResponse);
		} catch (

		Exception e) {
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("http://72.167.225.87/Testing_NMSWP/");
			logger.error(String.format("##### BusinessEntity exception #### %s", e.getMessage()));
		}

	}

	@RequestMapping(value = "/businessDetails", method = RequestMethod.POST)
	public ModelAndView saveEntityDetails(@Validated BusinessEntity businessEntity, BindingResult result,
			@RequestParam("moaDocFname") MultipartFile multipartFile,
			@RequestParam("yearEstablishment") String yearEstablishment,
			@RequestParam("regisAttacDocFname") MultipartFile multipartFile1,
			@RequestParam("bodDocFname") MultipartFile multipartFile2,
			@RequestParam("annexureiaformat") MultipartFile multipartFile4, Model model, HttpSession session,
			RedirectAttributes redirectAttribute) {

		String besiId = "";
		String appliId = "";

		try {
			Object niveshResponse = session.getAttribute("niveshSoapResponse");

			Map<String, String> businessResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (Map.Entry<String, String> entry : businessResponce.entrySet()) {
				
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					appliId = entry.getValue() + "A1";

				}

				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					besiId = entry.getValue() + "B1";
				}
				//session.setAttribute("besiId", businessEntityId);
			
				String appid = (String) session.getAttribute("besiId");
				//String propId = appid + "B1";

				List<ProprietorDetails> proprietorDetailsList1 = proprietorService.findAllByBusinessId(appid);
				if (proprietorDetailsList1.size() <= 0) {
					redirectAttribute.addFlashAttribute("propritorsDtlMsg",
							"Please Fill the Director / Partner / Owner / Proprietor Details.");
					return new ModelAndView("redirect:/businessDetails");
				}
			}
			SimpleDateFormat formatter1 = new SimpleDateFormat("dd-MM-yyyy");
			Date date1 = formatter1.parse(yearEstablishment);
			businessEntity.setYearEstablishment(date1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		commonSaveBusinessEntityRecords(businessEntity, result, multipartFile, multipartFile1, multipartFile2,
				multipartFile4, model, session);
		return new ModelAndView("redirect:/projectDetails");

	}

	// Sachin Autosave
	@PostMapping(value = "/autobusinessDetails")
	public ModelAndView autosaveEntityDetails(@Validated BusinessEntity businessEntity, BindingResult result,
			@RequestParam("moaDocFname") MultipartFile multipartFile,
			@RequestParam("regisAttacDocFname") MultipartFile multipartFile1,
			@RequestParam("bodDocFname") MultipartFile multipartFile2,
			@RequestParam("annexureiaformat") MultipartFile multipartFile4, Model model, HttpSession session) {

		commonSaveBusinessEntityRecords(businessEntity, result, multipartFile, multipartFile1, multipartFile2,
				multipartFile4, model, session);

		return new ModelAndView("redirect:/businessDetails");

	}

	@RequestMapping(value = "/kkproprietorDetails", method = RequestMethod.POST)
	public ModelAndView saveProprietorDetails(@ModelAttribute("newpropId") String addpropId,
			@ModelAttribute("businessEntityDetails") BusinessEntity businessEntityDetails,
			ProprietorDetails proprietorDetails1, BindingResult result, Model model, HttpSession session) {

		String besiId = "";
		String appliId = "";
		System.out.println(session.getAttribute("uuid"));
		UUID uuid = null;

		// if(!session.getAttribute("uuid").equals(uuid.toString())) {
		try {
			Object niveshResponse = session.getAttribute("niveshSoapResponse");

			Map<String, String> businessResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (Map.Entry<String, String> entry : businessResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Company_Name")) {
					businessEntityDetails.setBusinessEntityName(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					besiId = entry.getValue() + "B1";
				}
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					appliId = entry.getValue() + "A1";
				}
			}

			session.setAttribute("besiId", besiId);
			ApplicantDetails applicantDetails = applicantDetailsService.getApplicantDetailsByAppId(appliId);
			/*
			 * ApplicantDetails applicantDetails = (ApplicantDetails)
			 * session.getAttribute("applicantDetails"); String fullName =
			 * applicantDetails.getAppFirstName() + " " +
			 * applicantDetails.getAppMiddleName() + " " +
			 * applicantDetails.getAppLastName();
			 */
			businessEntityDetails.setAuthorisedSignatoryName(applicantDetails.getAppFirstName() + " "
					+ applicantDetails.getAppMiddleName() + " " + applicantDetails.getAppLastName());
			// String designation = applicantDetails.getAppDesignation();
			businessEntityDetails.setBusinessDesignation(applicantDetails.getAppDesignation());

			ProprietorDetails proprietorDetails = new ProprietorDetails();
			proprietorDetails.setBusinessEntityDetails(besiId);
			proprietorDetails.setCategory(businessEntityDetails.getCategory());
			proprietorDetails.setCreated_By(businessEntityDetails.getCreated_By());
			proprietorDetails.setDesignation(businessEntityDetails.getDesignation());
			proprietorDetails.setDirectorName(businessEntityDetails.getDirectorName());
			proprietorDetails.setDiv_Status(businessEntityDetails.getDiv_Status());
			proprietorDetails.setEmail(businessEntityDetails.getEmail());

			String editPropId = (String) session.getAttribute("editPropId");

			if (editPropId == null || editPropId == "" || editPropId.isEmpty()) {

				businessEntityDetails.setId(besiId);
				Random random = new Random();
				// Generates random integers 0 to 49
				int x = random.nextInt(50);
				proprietorDetails.setPropId(besiId + "PR" + x);

			} else {
				proprietorDetails.setPropId(editPropId);
				session.setAttribute("editPropId", null);
			}

			proprietorDetails.setProprietorDetailsaddress(businessEntityDetails.getProprietorDetailsaddress());
			proprietorDetails.setEquity(businessEntityDetails.getEquity());
			proprietorDetails.setMobileNo(businessEntityDetails.getMobileNo());
			proprietorDetails.setGender(businessEntityDetails.getGender());
			proprietorDetails.setPanCardNo(businessEntityDetails.getPanCardNo());
			proprietorDetails.setDin(businessEntityDetails.getDin());
			proprietorDetails.setCreated_By(businessEntityDetails.getCreated_By());
			proprietorDetails.setModified_By(businessEntityDetails.getModified_By());
			proprietorDetails.setModify_Date(businessEntityDetails.getModify_Date());
			proprietorDetails.setStatus(businessEntityDetails.getStatus());

			BusinessEntityDetails businessEntityDetails1 = new BusinessEntityDetails();
			businessEntityDetails1.setApplicantDetailId(businessEntityDetails.getApplicantDetailId());
			businessEntityDetails1.setAuthorisedSignatoryName(businessEntityDetails.getAuthorisedSignatoryName());
			businessEntityDetails1.setBusinessAddress(businessEntityDetails.getBusinessAddress());
			businessEntityDetails1.setBusinessCountryName(businessEntityDetails.getBusinessCountryName());
			businessEntityDetails1.setBusinessDesignation(businessEntityDetails.getBusinessDesignation());
			businessEntityDetails1.setBusinessDistrictName(businessEntityDetails.getBusinessDistrictName());
			businessEntityDetails1.setBusinessEntityName(businessEntityDetails.getBusinessEntityName());
			businessEntityDetails1.setBusinessEntityType(businessEntityDetails.getBusinessEntityType());
			businessEntityDetails1.setBusinessStateName(businessEntityDetails.getBusinessStateName());
			businessEntityDetails1.setCompanyPanNo(businessEntityDetails.getCompanyPanNo());
			businessEntityDetails1.setPinCode(businessEntityDetails.getPinCode());
			businessEntityDetails1.setMobileNumber(businessEntityDetails.getMobileNumber());
			businessEntityDetails1.setPhoneNo(businessEntityDetails.getPhoneNo());
			businessEntityDetails1.setFax(businessEntityDetails.getFax());
			businessEntityDetails1.setYearEstablishment(businessEntityDetails.getYearEstablishment());
			businessEntityDetails1.setGstin(businessEntityDetails.getGstin());
			businessEntityDetails1.setCin(businessEntityDetails.getCin());
			businessEntityDetails1.setEmailId(businessEntityDetails.getEmail());
			businessEntityDetails1.setId(besiId);
			businessService.updateBusinessEntity(businessEntityDetails1);

			proprietorDetailsRepository.save(proprietorDetails);

			businessEntityDetails.setCategory("");
			businessEntityDetails.setCreated_By("");
			businessEntityDetails.setDesignation("");
			businessEntityDetails.setDirectorName("");
			businessEntityDetails.setDiv_Status("");
			businessEntityDetails.setEmail("");
			businessEntityDetails.setProprietorDetailsaddress("");
			businessEntityDetails.setEquity(null);
			businessEntityDetails.setMobileNo(null);
			businessEntityDetails.setGender("");
			businessEntityDetails.setPanCardNo("");
			businessEntityDetails.setDin("");
			/*
			 * System.out.println(isRowRecord); if (isRowRecord) { try {
			 * proprietorDetailsList.remove(rowIndex); } catch (Exception e) {
			 * 
			 * } updateRow(rowIndex, proprietorDetails); isRowRecord = false;
			 * model.addAttribute("ProprietorDetailsList", proprietorDetailsList); } else {
			 * getProprietorDetailsList(proprietorDetails); }
			 */

			List<StateDetails> stateDetails = stateDetailsService.findAllByStateName();
			if (stateDetails.size() > 0) {
				Collections.sort(stateDetails, new Comparator<StateDetails>() {

					@Override
					public int compare(StateDetails o1, StateDetails o2) {
						return o1.getStateName().compareTo(o2.getStateName());
					}
				});
			}
			if (stateDetails != null) {
				model.addAttribute("stateDetails", stateDetails);
			}

			List<DistrictDetails1> districtDetails = daDetailsMasterService.findAllByDistrictName();

			if (districtDetails.size() > 0) {
				Collections.sort(districtDetails, new Comparator<DistrictDetails1>() {

					@Override
					public int compare(DistrictDetails1 o1, DistrictDetails1 o2) {
						return o1.getDistrictName().compareTo(o2.getDistrictName());
					}
				});
			}

			if (districtDetails != null) {
				model.addAttribute("districtDetails", districtDetails);
			}

			/*
			 * BusinessEntityDetails busineEntDetaiExisit =
			 * businessService.getBusinessEntityByapplicantDetailId(appliId);
			 * businessDocFromMongoDB(businessEntityDetails, besiId);
			 */
			businessDocFromMongoDB(businessEntityDetails, besiId);

			List<ProprietorDetails> proprietorDetailsList1 = proprietorService.findAllByBusinessId(besiId);
			// SKRATHOUR--------------------------
			model.addAttribute("ProprietorDetailsList", proprietorDetailsList1);
			model.addAttribute("businessEntityDetails", businessEntityDetails);
			model.addAttribute("proprietorDetails", new ProprietorDetails());
			model.addAttribute("niveshSoapResponse", niveshResponse);
			model.addAttribute("applicantDetails", applicantDetails);
			session.setAttribute("businessEntityDetails", businessEntityDetails);
			model.addAttribute("saveproriter", "saveproriter");
		} catch (Exception e) {
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("http://72.167.225.87/Testing_NMSWP/");
			e.printStackTrace();
		}

		return new ModelAndView("business_entity_details");
	}

	/*
	 * @RequestMapping(value = "/proprietorDetails", method = RequestMethod.POST)
	 * public ModelAndView saveProprietorDetails(
	 * 
	 * @ModelAttribute("businessEntityDetails") BusinessEntity
	 * businessEntityDetails, BindingResult result,
	 * 
	 * @RequestParam("moaDocFname") MultipartFile multipartFile,
	 * 
	 * @RequestParam("regisAttacDocFname") MultipartFile multipartFile1,
	 * 
	 * @RequestParam("bodDocFname") MultipartFile multipartFile2,
	 * 
	 * @RequestParam("annexureiaformat") MultipartFile multipartFile4, Model model,
	 * HttpSession session) {
	 * 
	 * String besiId = ""; String appliId = "";
	 * System.out.println(session.getAttribute("uuid")); UUID uuid = null;
	 * 
	 * //if(!session.getAttribute("uuid").equals(uuid.toString())) { try { Object
	 * niveshResponse = session.getAttribute("niveshSoapResponse");
	 * 
	 * Map<String, String> businessResponce = ((SoapDataModel)
	 * niveshResponse).getNiveshSoapResponse(); for (Map.Entry<String, String> entry
	 * : businessResponce.entrySet()) { if
	 * (entry.getKey().equalsIgnoreCase("Company_Name")) {
	 * businessEntityDetails.setBusinessEntityName(entry.getValue()); } if
	 * (entry.getKey().equalsIgnoreCase("Unit_Id")) { besiId = entry.getValue() +
	 * "B1"; } if (entry.getKey().equalsIgnoreCase("Unit_Id")) { appliId =
	 * entry.getValue() + "A1"; } }
	 * 
	 * ApplicantDetails applicantDetails =
	 * applicantDetailsService.getApplicantDetailsByAppId(appliId);
	 * 
	 * ApplicantDetails applicantDetails = (ApplicantDetails)
	 * session.getAttribute("applicantDetails"); String fullName =
	 * applicantDetails.getAppFirstName() + " " +
	 * applicantDetails.getAppMiddleName() + " " +
	 * applicantDetails.getAppLastName();
	 * 
	 * businessEntityDetails.setAuthorisedSignatoryName(applicantDetails.
	 * getAppFirstName() + " " + applicantDetails.getAppMiddleName() + " " +
	 * applicantDetails.getAppLastName()); // String designation =
	 * applicantDetails.getAppDesignation();
	 * businessEntityDetails.setBusinessDesignation(applicantDetails.
	 * getAppDesignation());
	 * 
	 * ProprietorDetails proprietorDetails = new ProprietorDetails();
	 * proprietorDetails.setBusinessEntityDetails(besiId);
	 * proprietorDetails.setCategory(businessEntityDetails.getCategory());
	 * proprietorDetails.setCreated_By(businessEntityDetails.getCreated_By());
	 * proprietorDetails.setDesignation(businessEntityDetails.getDesignation());
	 * proprietorDetails.setDirectorName(businessEntityDetails.getDirectorName());
	 * proprietorDetails.setDiv_Status(businessEntityDetails.getDiv_Status());
	 * proprietorDetails.setEmail(businessEntityDetails.getEmail());
	 * 
	 * if ((proprietorDetails.getPropId() != null &&
	 * !proprietorDetails.getPropId().isEmpty()) ||
	 * (businessEntityDetails.getPropId() != null &&
	 * !businessEntityDetails.getPropId().isEmpty())) {
	 * proprietorDetails.setPropId(businessEntityDetails.getPropId()); } else {
	 * businessEntityDetails.setId(besiId); proprietorDetails.setPropId(besiId +
	 * "PR" + atomicInteger.getAndIncrement()); }
	 * 
	 * proprietorDetails.setProprietorDetailsaddress(businessEntityDetails.
	 * getProprietorDetailsaddress());
	 * proprietorDetails.setEquity(businessEntityDetails.getEquity());
	 * proprietorDetails.setMobileNo(businessEntityDetails.getMobileNo());
	 * proprietorDetails.setGender(businessEntityDetails.getGender());
	 * proprietorDetails.setPanCardNo(businessEntityDetails.getPanCardNo());
	 * proprietorDetails.setDin(businessEntityDetails.getDin());
	 * proprietorDetails.setCreated_By(businessEntityDetails.getCreated_By());
	 * proprietorDetails.setModified_By(businessEntityDetails.getModified_By());
	 * proprietorDetails.setModify_Date(businessEntityDetails.getModify_Date());
	 * proprietorDetails.setStatus(businessEntityDetails.getStatus());
	 * 
	 * businessEntityDetails.setCategory("");
	 * businessEntityDetails.setCreated_By("");
	 * businessEntityDetails.setDesignation("");
	 * businessEntityDetails.setDirectorName("");
	 * businessEntityDetails.setDiv_Status(""); businessEntityDetails.setEmail("");
	 * businessEntityDetails.setProprietorDetailsaddress("");
	 * businessEntityDetails.setEquity(null);
	 * businessEntityDetails.setMobileNo(null); businessEntityDetails.setGender("");
	 * businessEntityDetails.setPanCardNo(""); businessEntityDetails.setDin("");
	 * System.out.println(isRowRecord); if (isRowRecord) { try {
	 * proprietorDetailsList.remove(rowIndex); } catch (Exception e) {
	 * 
	 * } updateRow(rowIndex, proprietorDetails); isRowRecord = false;
	 * model.addAttribute("ProprietorDetailsList", proprietorDetailsList); } else {
	 * getProprietorDetailsList(proprietorDetails); }
	 * 
	 * List<StateDetails> stateDetails = stateDetailsService.findAllByStateName();
	 * if (stateDetails.size() > 0) { Collections.sort(stateDetails, new
	 * Comparator<StateDetails>() {
	 * 
	 * @Override public int compare(StateDetails o1, StateDetails o2) { return
	 * o1.getStateName().compareTo(o2.getStateName()); } }); } if (stateDetails !=
	 * null) { model.addAttribute("stateDetails", stateDetails); }
	 * 
	 * List<DistrictDetails1> districtDetails =
	 * daDetailsMasterService.findAllByDistrictName();
	 * 
	 * if (districtDetails.size() > 0) { Collections.sort(districtDetails, new
	 * Comparator<DistrictDetails1>() {
	 * 
	 * @Override public int compare(DistrictDetails1 o1, DistrictDetails1 o2) {
	 * return o1.getDistrictName().compareTo(o2.getDistrictName()); } }); }
	 * 
	 * if (districtDetails != null) { model.addAttribute("districtDetails",
	 * districtDetails); }
	 * 
	 * BusinessEntityDetails busineEntDetaiExisit =
	 * businessService.getBusinessEntityByapplicantDetailId(appliId);
	 * businessDocFromMongoDB(businessEntityDetails, besiId);
	 * List<ProprietorDetails> proprietorDetailsList1 =
	 * proprietorService.findAllByBusinessId(besiId); //
	 * SKRATHOUR--------------------------
	 * model.addAttribute("ProprietorDetailsList", proprietorDetailsList);
	 * model.addAttribute("businessEntityDetails", businessEntityDetails);
	 * model.addAttribute("proprietorDetails", new ProprietorDetails());
	 * model.addAttribute("niveshSoapResponse", niveshResponse);
	 * model.addAttribute("applicantDetails", applicantDetails);
	 * session.setAttribute("businessEntityDetails", businessEntityDetails);
	 * model.addAttribute("saveproriter", "saveproriter"); } catch (Exception e) {
	 * RedirectView redirectView = new RedirectView();
	 * redirectView.setUrl("http://72.167.225.87/Testing_NMSWP/");
	 * e.printStackTrace(); }
	 * 
	 * 
	 * return new ModelAndView("business_entity_details"); }
	 */

	@RequestMapping(value = "/removeList", method = RequestMethod.GET)
	public ModelAndView removeList(@ModelAttribute("removeItem") String removeid, Model model,
			ProprietorDetails proprietorDetails, BusinessEntityDetails businessEntityDetail, HttpSession session) {
		// getProprietorDetailsList(proprietorDetails);
		// proprietorDetails = proprietorDetailsList.get(removeid);

		// ProprietorDetails proprietorDetail=proprietorService.getBypropId(removeid);
		try {
			proprietorService.deleteBypropId(removeid);
		} catch (Exception e) {

		}
		// proprietorDetailsList.remove(removeid);

		// List<ProprietorDetails>
		// proprietorDetailsList=proprietorDetailsRepository.findAll();
		String besiId = (String) session.getAttribute("besiId");
		List<ProprietorDetails> proprietorDetailsList1 = proprietorService.findAllByBusinessId(besiId);

		List<ProprietorDetails> proprietorDetailsListexist = proprietorService
				.findAllByBusinessId(businessEntityDetail.getId());
		if (proprietorDetailsList1.size() > 0) {
			model.addAttribute("ProprietorDetailsList", proprietorDetailsList1);
			// session.setAttribute("ProprietorDetailsList", proprietorDetailsList1);
		}

		model.addAttribute("proprietorDetails", new ProprietorDetails());

		BusinessEntity businessEntityDetails = null;
		String bentityId = null;
		try {
			businessEntityDetails = (BusinessEntity) session.getAttribute("businessEntityDetails");
			if (businessEntityDetails == null) {
				businessEntityDetails = new BusinessEntity();
			}

			Object niveshResponse = session.getAttribute("niveshSoapResponse");
			Map<String, String> businessResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			// Map<String, String> businessResponce = new HashMap<String,
			// String>(soapdetails.soapwebservice());
			String appliId = "";
			for (Map.Entry<String, String> entry : businessResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Company_Name")) {
					businessEntityDetails.setBusinessEntityName(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					appliId = entry.getValue() + "A1";

				}
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					bentityId = entry.getValue() + "B1";
				}
			}
			ApplicantDetails applicantDetails = applicantDetailsService.getApplicantDetailsByAppId(appliId);

			businessEntityDetails.setAuthorisedSignatoryName(applicantDetails.getAppFirstName() + " "
					+ applicantDetails.getAppMiddleName() + " " + applicantDetails.getAppLastName());

			businessEntityDetails.setBusinessDesignation(applicantDetails.getAppDesignation());
			List<StateDetails> stateDetails = stateDetailsService.findAllByStateName();
			if (stateDetails.size() > 0) {
				Collections.sort(stateDetails, new Comparator<StateDetails>() {

					@Override
					public int compare(StateDetails o1, StateDetails o2) {
						return o1.getStateName().compareTo(o2.getStateName());
					}
				});
			}
			if (stateDetails != null) {
				model.addAttribute("stateDetails", stateDetails);
			}

			List<DistrictDetails1> districtDetails = daDetailsMasterService.findAllByDistrictName();

			if (districtDetails.size() > 0) {
				Collections.sort(districtDetails, new Comparator<DistrictDetails1>() {

					@Override
					public int compare(DistrictDetails1 o1, DistrictDetails1 o2) {
						return o1.getDistrictName().compareTo(o2.getDistrictName());
					}
				});
			}

			if (districtDetails != null) {
				model.addAttribute("districtDetails", districtDetails);
			}

			BusinessEntityDetails busineEntDetaiExisit = businessService.getBusinessEntityByapplicantDetailId(appliId);

			if (busineEntDetaiExisit != null) {
				businessEntityDetails.setBusinessEntityType(busineEntDetaiExisit.getBusinessEntityType());
				businessEntityDetails.setEmailId(busineEntDetaiExisit.getEmailId());
				businessEntityDetails.setMobileNumber(busineEntDetaiExisit.getMobileNumber());
				businessEntityDetails.setFax(busineEntDetaiExisit.getFax());
				businessEntityDetails.setBusinessAddress(busineEntDetaiExisit.getBusinessAddress());
				businessEntityDetails.setBusinessCountryName(busineEntDetaiExisit.getBusinessCountryName());
				businessEntityDetails.setBusinessStateName(busineEntDetaiExisit.getBusinessStateName());
				businessEntityDetails.setBusinessDistrictName(busineEntDetaiExisit.getBusinessDistrictName());
				businessEntityDetails.setPinCode(busineEntDetaiExisit.getPinCode());
				businessEntityDetails.setYearEstablishment(busineEntDetaiExisit.getYearEstablishment());
				businessEntityDetails.setCompanyPanNo(busineEntDetaiExisit.getCompanyPanNo());
				businessEntityDetails.setGstin(busineEntDetaiExisit.getGstin());
				businessEntityDetails.setCin(busineEntDetaiExisit.getCin());
			}
			businessDocFromMongoDB(businessEntityDetails, bentityId);
			// SKRATHOUR--------------------------
			model.addAttribute("businessEntityDetails", businessEntityDetails);
			model.addAttribute("proprietorDetails", new ProprietorDetails());
			model.addAttribute("niveshSoapResponse", niveshResponse);
			model.addAttribute("applicantDetails", applicantDetails);
		} catch (Exception e) {
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("http://72.167.225.87/Testing_NMSWP/");
			e.printStackTrace();
		}
		model.addAttribute("saveproriter", "saveproriter");
		return new ModelAndView("business_entity_details");

	}

	@RequestMapping(value = "/editProprietorDetails", method = RequestMethod.GET)
	public ModelAndView editProposedEmploymentDetails(
			@RequestParam(value = "editProprietorDetailsRecord", required = false) String index, Model model,
			ProprietorDetails proprietorDetails, HttpSession session) {

		String besiId = (String) session.getAttribute("bidId");
		List<ProprietorDetails> proprietorDetailsList1 = proprietorService.findAllByBusinessId(besiId);
		if (proprietorDetailsList1.size() > 0) {
			model.addAttribute("ProprietorDetailsList", proprietorDetailsList1);
			// session.setAttribute("ProprietorDetailsList", proprietorDetailsList1);
		}

		session.setAttribute("editPropId", proprietorDetails.getPropId());

		/*
		 * if (proprietorDetailsList.size() > 0) {
		 * model.addAttribute("ProprietorDetailsList", proprietorDetailsList); }
		 */
		BusinessEntity businessEntityDetails = null;
		try {
			businessEntityDetails = (BusinessEntity) session.getAttribute("businessEntityDetails");
			if (businessEntityDetails == null) {
				businessEntityDetails = new BusinessEntity();
			}

			Object niveshResponse = session.getAttribute("niveshSoapResponse");
			Map<String, String> businessResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

			String appliId = "";
			String bentityId = null;
			for (Map.Entry<String, String> entry : businessResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Company_Name")) {
					businessEntityDetails.setBusinessEntityName(entry.getValue());
				}
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					appliId = entry.getValue() + "A1";
					bentityId = entry.getValue() + "B1";
				}
			}
			ApplicantDetails applicantDetails = applicantDetailsService.getApplicantDetailsByAppId(appliId);
			businessEntityDetails.setAuthorisedSignatoryName(applicantDetails.getAppFirstName() + " "
					+ applicantDetails.getAppMiddleName() + " " + applicantDetails.getAppLastName());

			businessEntityDetails.setBusinessDesignation(applicantDetails.getAppDesignation());
			proprietorDetails = proprietorService.getBypropId(index);
			// proprietorDetails = proprietorDetailsList.get(index);

			session.setAttribute("editPropId", proprietorDetails.getPropId());

			businessEntityDetails.setCategory(proprietorDetails.getCategory());
			businessEntityDetails.setCreated_By(proprietorDetails.getCreated_By());
			businessEntityDetails.setDesignation(proprietorDetails.getDesignation());
			businessEntityDetails.setDirectorName(proprietorDetails.getDirectorName());
			businessEntityDetails.setDiv_Status(proprietorDetails.getDiv_Status());
			businessEntityDetails.setEmail(proprietorDetails.getEmail());
			businessEntityDetails.setProprietorDetailsaddress(proprietorDetails.getProprietorDetailsaddress());
			businessEntityDetails.setEquity(proprietorDetails.getEquity());
			businessEntityDetails.setMobileNo(proprietorDetails.getMobileNo());
			businessEntityDetails.setGender(proprietorDetails.getGender());
			businessEntityDetails.setPanCardNo(proprietorDetails.getPanCardNo());
			businessEntityDetails.setDin(proprietorDetails.getDin());
			businessEntityDetails.setId(proprietorDetails.getPropId());
			if (!(proprietorDetails.getGender() == null)) {
				model.addAttribute("gender", proprietorDetails.getGender());
			}
			if (!(proprietorDetails.getDiv_Status() == null)) {
				model.addAttribute("divyang", proprietorDetails.getDiv_Status());
			}

			// rowIndex = index;
			isRowRecord = true;

			List<StateDetails> stateDetails = stateDetailsService.findAllByStateName();
			if (stateDetails.size() > 0) {
				Collections.sort(stateDetails, new Comparator<StateDetails>() {

					@Override
					public int compare(StateDetails o1, StateDetails o2) {
						return o1.getStateName().compareTo(o2.getStateName());
					}
				});
			}
			if (stateDetails != null) {
				model.addAttribute("stateDetails", stateDetails);
			}

			List<DistrictDetails1> districtDetails = daDetailsMasterService.findAllByDistrictName();

			if (districtDetails.size() > 0) {
				Collections.sort(districtDetails, new Comparator<DistrictDetails1>() {

					@Override
					public int compare(DistrictDetails1 o1, DistrictDetails1 o2) {
						return o1.getDistrictName().compareTo(o2.getDistrictName());
					}
				});
			}

			if (districtDetails != null) {
				model.addAttribute("districtDetails", districtDetails);
			}

			BusinessEntityDetails busineEntDetaiExisit = businessService.getBusinessEntityByapplicantDetailId(appliId);

			Float equatity = proprietorDetails.getEquity();
			model.addAttribute("equatity", equatity);

			if (busineEntDetaiExisit != null) {
				businessEntityDetails.setBusinessEntityType(busineEntDetaiExisit.getBusinessEntityType());
				businessEntityDetails.setEmailId(busineEntDetaiExisit.getEmailId());
				businessEntityDetails.setMobileNumber(busineEntDetaiExisit.getMobileNumber());
				businessEntityDetails.setFax(busineEntDetaiExisit.getFax());
				businessEntityDetails.setBusinessAddress(busineEntDetaiExisit.getBusinessAddress());
				businessEntityDetails.setBusinessCountryName(busineEntDetaiExisit.getBusinessCountryName());
				businessEntityDetails.setBusinessStateName(busineEntDetaiExisit.getBusinessStateName());
				businessEntityDetails.setBusinessDistrictName(busineEntDetaiExisit.getBusinessDistrictName());
				businessEntityDetails.setPinCode(busineEntDetaiExisit.getPinCode());
				businessEntityDetails.setYearEstablishment(busineEntDetaiExisit.getYearEstablishment());
				businessEntityDetails.setCompanyPanNo(busineEntDetaiExisit.getCompanyPanNo());
				businessEntityDetails.setGstin(busineEntDetaiExisit.getGstin());
				businessEntityDetails.setCin(busineEntDetaiExisit.getCin());

			}

			businessDocFromMongoDB(businessEntityDetails, bentityId);
			// SKRATHOUR--------------------------
			model.addAttribute("proprietorDetails", proprietorDetails);
			// model.addAttribute("ProprietorDetailsList", proprietorDetailsList);
			model.addAttribute("businessEntityDetails", businessEntityDetails);
			model.addAttribute("applicantDetails", applicantDetails);
			model.addAttribute("niveshSoapResponse", niveshResponse);
		} catch (Exception e) {
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("http://72.167.225.87/Testing_NMSWP/");
			e.printStackTrace();
		}
		model.addAttribute("edit", "edit");
		model.addAttribute("saveproriter", "saveproriter");
		return new ModelAndView("business_entity_details");
	}

//	public void updateRow(int index, ProprietorDetails proprietorDetails) {
//		proprietorDetailsList.add(index, proprietorDetails);
//	}

//	private List<ProprietorDetails> getProprietorDetailsList(ProprietorDetails proprietorDetails) {
//		proprietorDetailsList = new ArrayList<ProprietorDetails>();
//		if (!proprietorDetailsMap.isEmpty()) {
//			List<ProprietorDetails> proprietorDetailsListTmp = new ArrayList<ProprietorDetails>();
//			proprietorDetailsListTmp.addAll(proprietorDetailsList);
//
//			if (!proprietorDetailsMap.containsKey(proprietorDetails.getPanCardNo())) {
//				proprietorDetailsMap.put(proprietorDetails.getPanCardNo(), proprietorDetails);
//			}
//			/*
//			 * for(ProprietorDetails propList:proprietorDetailsListTmp) {
//			 * if(!propList.getPanCardNo().equals(proprietorDetails.getPanCardNo())){
//			 * proprietorDetailsList.add(proprietorDetails);
//			 * proprietorDetailsMap.put(propList.getPanCardNo(), propList); } }
//			 */
//		} else {
//			proprietorDetailsMap.put(proprietorDetails.getPanCardNo(), proprietorDetails);
//		}
//
//		proprietorDetailsList.addAll(proprietorDetailsMap.values());
//		return proprietorDetailsList;
//	}

	@SuppressWarnings("unchecked")
	@GetMapping(value = "/busPreviousRecord")
	public ModelAndView invPreviousRecord(Model model, HttpSession session, HttpServletRequest request) {
		try {
			BusinessEntity businessEntityDetails = (BusinessEntity) session.getAttribute("businessEntityDetails");

			ApplicantDetails applicantDetails = (ApplicantDetails) session.getAttribute("applicantDetails");
			String fullName = applicantDetails.getAppFirstName() + " " + applicantDetails.getAppMiddleName() + " "
					+ applicantDetails.getAppLastName();
			businessEntityDetails.setAuthorisedSignatoryName(fullName);
			String designation = applicantDetails.getAppDesignation();
			businessEntityDetails.setBusinessDesignation(designation);

			List<StateDetails> stateDetails = stateDetailsService.findAllByStateName();
			if (stateDetails.size() > 0) {
				Collections.sort(stateDetails, new Comparator<StateDetails>() {

					@Override
					public int compare(StateDetails o1, StateDetails o2) {
						return o1.getStateName().compareTo(o2.getStateName());
					}
				});
			}
			if (stateDetails != null) {
				model.addAttribute("stateDetails", stateDetails);
			}

			List<DistrictDetails1> districtDetails = daDetailsMasterService.findAllByDistrictName();

			if (districtDetails.size() > 0) {
				Collections.sort(districtDetails, new Comparator<DistrictDetails1>() {

					@Override
					public int compare(DistrictDetails1 o1, DistrictDetails1 o2) {
						return o1.getDistrictName().compareTo(o2.getDistrictName());
					}
				});
			}

			if (districtDetails != null) {
				model.addAttribute("districtDetails", districtDetails);
			}

			byte[] encodeBase64MaoDoc = Base64.encodeBase64(businessEntityDetails.getMoaDoc());
			byte[] encodeBase64RCDoc = Base64.encodeBase64(businessEntityDetails.getRegistrationAttachedDoc());
			String base64EncodedMaoDoc = null;
			String base64EncodedRCDoc = null;
			try {
				base64EncodedMaoDoc = new String(encodeBase64MaoDoc, "UTF-8");
				base64EncodedRCDoc = new String(encodeBase64RCDoc, "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			Object niveshResponse = session.getAttribute("niveshSoapResponse");
			Map<String, String> businessResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			// Map<String, String> businessResponce = new HashMap<String,
			// String>(soapdetails.soapwebservice());
			for (Map.Entry<String, String> entry : businessResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase("Company_Name")) {
					businessEntityDetails.setBusinessEntityName(entry.getValue());
				}
			}

			businessEntityDetails.setBase64EncodedMaoDoc(base64EncodedMaoDoc);
			businessEntityDetails.setBase64EncodedRCDoc(base64EncodedRCDoc);

			model.addAttribute("ProprietorDetails", new ProprietorDetails());
			List<ProprietorDetails> pwInvList1 = (List<ProprietorDetails>) session
					.getAttribute("ProprietorDetailsList");
			model.addAttribute("ProprietorDetailsList", pwInvList1);
			model.addAttribute("businessEntityDetails", businessEntityDetails);
			model.addAttribute("niveshSoapResponse", niveshResponse);
			model.addAttribute("applicantDetails", applicantDetails);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return new ModelAndView("business_entity_details");

	}

	@RequestMapping(value = "/downloadFile/{type}", method = RequestMethod.GET)
	public void downloadFile(HttpServletResponse response, @PathVariable("type") String type) throws IOException {
		File file = null;
		if (type.equalsIgnoreCase("doc")) {
			ClassLoader classloader = Thread.currentThread().getContextClassLoader();
			file = new File(classloader.getResource(INTERNALFILE).getFile());
		}
		if (!file.exists()) {
			String errorMessage = "The file you are looking for does not exist";
			OutputStream outputStream = response.getOutputStream();
			outputStream.write(errorMessage.getBytes(Charset.forName("UTF-8")));
			outputStream.close();
			return;
		}

		String mimeType = URLConnection.guessContentTypeFromName(file.getName());
		if (mimeType == null) {
			mimeType = "application/msword";
		}
		response.setContentType(mimeType);
		response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() + "\""));
		response.setContentLength((int) file.length());
		InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
	}

	/*-----------------------------tabs-------------------------------------*/

	@RequestMapping(value = "/getIdByTabs11", method = RequestMethod.GET)
	public ModelAndView getIdByTabs11(Model model, HttpSession session,
			@RequestParam(value = "authoTab") String authoTab) {

		String appId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appId = entry.getValue() + "A1";
			}
		}
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);

		if (applicantDetail != null && authoTab.equalsIgnoreCase("authoTab")) {
			return new ModelAndView("redirect:/editApplicantForm");
		}
		return null;

	}

	@RequestMapping(value = "/getIdByTabs12", method = RequestMethod.GET)
	public ModelAndView getIdByTabs12(Model model, HttpSession session,
			@RequestParam(value = "projTab") String projTab) {

		String projId = "";
		String appId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				projId = entry.getValue() + "P1";
			}
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appId = entry.getValue() + "A1";
			}
		}
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);
		BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(appId);
		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);
		if (ProjectDetail != null && projTab.equalsIgnoreCase("projTab")) {
			return new ModelAndView("redirect:/projectDetails");
		} else {
			session.setAttribute("applicantDetails", applicantDetail);
			session.setAttribute("applicantDetailsId", appId);
			return new ModelAndView("redirect:/businessDetails");
		}

	}

	@RequestMapping(value = "/getIdByTabs13", method = RequestMethod.GET)
	public ModelAndView getIdByTabs3(Model model, HttpSession session,
			@RequestParam(value = "investTab") String investTab) {

		String investId = "";
		String appId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				investId = entry.getValue() + "I1";
			}
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appId = entry.getValue() + "A1";
			}
		}
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);
		BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(appId);
		InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(investId);
		if (invdtlFromDb != null && investTab.equalsIgnoreCase("investTab")) {
			return new ModelAndView("redirect:/investmentDetails");
		} else {
			session.setAttribute("applicantDetails", applicantDetail);
			session.setAttribute("applicantDetailsId", appId);
			return new ModelAndView("redirect:/businessDetails");
		}

	}

	@RequestMapping(value = "/getIdByTabs14", method = RequestMethod.GET)
	public ModelAndView getIdByTabs4(Model model, HttpSession session,
			@RequestParam(value = "propoTab") String propoTab) {

		String propoId = "";
		String appId = "";
		Object niveshResponse = session.getAttribute("niveshSoapResponse");
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				propoId = entry.getValue() + "PE1";
			}
			if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
				appId = entry.getValue() + "A1";
			}
		}
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);
		BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(appId);
		ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService.getPropEmpById(propoId);
		if (proposedEmploymentDetail != null && propoTab.equalsIgnoreCase("propoTab")) {
			return new ModelAndView("redirect:/skilUnskEmplDet");
		} else {
			session.setAttribute("applicantDetails", applicantDetail);
			session.setAttribute("applicantDetailsId", appId);
			return new ModelAndView("redirect:/businessDetails");
		}

	}

	@RequestMapping(value = "/proprietorDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ProprietorDetails> addProprietorDetails(@RequestBody ProprietorDetails proprietorDetails1,
			BindingResult result, Model model, HttpSession session) {

		String besiId = "";
		String appliId = "";
		System.out.println(session.getAttribute("uuid"));
		UUID uuid = null;
		List<ProprietorDetails> proprietorDetailsList1 = new ArrayList<ProprietorDetails>();

		try {
			Object niveshResponse = session.getAttribute("niveshSoapResponse");

			Map<String, String> businessResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (Map.Entry<String, String> entry : businessResponce.entrySet()) {
				/*
				 * if (entry.getKey().equalsIgnoreCase("Company_Name")) {
				 * businessEntityDetails.setBusinessEntityName(entry.getValue()); }
				 */
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					besiId = entry.getValue() + "B1";
				}
				if (entry.getKey().equalsIgnoreCase("Unit_Id")) {
					appliId = entry.getValue() + "A1";
				}
			}
			if (businessService.getBusinDetById(besiId) == null) {
				BusinessEntityDetails businessEntityDetails = null;
				businessEntityDetails = new BusinessEntityDetails();
				businessEntityDetails.setId(besiId);
				businessService.saveBusinessEntity(businessEntityDetails);
			}
			session.setAttribute("besiId", besiId);
			ApplicantDetails applicantDetails = applicantDetailsService.getApplicantDetailsByAppId(appliId);

			ProprietorDetails proprietorDetails = new ProprietorDetails();
			proprietorDetails.setBusinessEntityDetails(besiId);
			proprietorDetails.setCategory(proprietorDetails1.getCategory());
			proprietorDetails.setCreated_By(proprietorDetails1.getCreated_By());
			proprietorDetails.setDesignation(proprietorDetails1.getDesignation());
			proprietorDetails.setDirectorName(proprietorDetails1.getDirectorName());
			proprietorDetails.setDiv_Status(proprietorDetails1.getDiv_Status());
			proprietorDetails.setEmail(proprietorDetails1.getEmail());

			if (proprietorDetails1.getPropId() == null || proprietorDetails1.getPropId().isEmpty()
					|| proprietorDetails1.getPropId() == "") {

				Random random = new Random();
				// Generates random integers 0 to 49
				int x = random.nextInt(50);
				proprietorDetails.setPropId(besiId + "PR" + x);

			} else {
				proprietorDetails.setPropId(proprietorDetails1.getPropId());

			}

			proprietorDetails.setProprietorDetailsaddress(proprietorDetails1.getProprietorDetailsaddress());
			proprietorDetails.setEquity(proprietorDetails1.getEquity());
			proprietorDetails.setMobileNo(proprietorDetails1.getMobileNo());
			proprietorDetails.setGender(proprietorDetails1.getGender());
			proprietorDetails.setPanCardNo(proprietorDetails1.getPanCardNo());
			proprietorDetails.setDin(proprietorDetails1.getDin());
			proprietorDetails.setCreated_By(proprietorDetails1.getCreated_By());
			proprietorDetails.setModified_By(proprietorDetails1.getModified_By());
			proprietorDetails.setModify_Date(proprietorDetails1.getModify_Date());
			proprietorDetails.setStatus(proprietorDetails1.getStatus());

			proprietorDetailsRepository.save(proprietorDetails);

			List<StateDetails> stateDetails = stateDetailsService.findAllByStateName();
			if (stateDetails.size() > 0) {
				Collections.sort(stateDetails, new Comparator<StateDetails>() {

					@Override
					public int compare(StateDetails o1, StateDetails o2) {
						return o1.getStateName().compareTo(o2.getStateName());
					}
				});
			}
			if (stateDetails != null) {
				model.addAttribute("stateDetails", stateDetails);
			}

			List<DistrictDetails1> districtDetails = daDetailsMasterService.findAllByDistrictName();

			if (districtDetails.size() > 0) {
				Collections.sort(districtDetails, new Comparator<DistrictDetails1>() {

					@Override
					public int compare(DistrictDetails1 o1, DistrictDetails1 o2) {
						return o1.getDistrictName().compareTo(o2.getDistrictName());
					}
				});
			}

			if (districtDetails != null) {
				model.addAttribute("districtDetails", districtDetails);
			}

			proprietorDetailsList1 = proprietorService.findAllByBusinessId(besiId);
			proprietorDetailsList1.sort(Comparator.comparing(ProprietorDetails::getPropId));
			session.setAttribute("proprietorDetailsList1", proprietorDetailsList1);
			// SKRATHOUR--------------------------
			model.addAttribute("ProprietorDetailsList", proprietorDetailsList1);
			// model.addAttribute("businessEntityDetails", businessEntityDetails);
			model.addAttribute("proprietorDetails", new ProprietorDetails());
			model.addAttribute("niveshSoapResponse", niveshResponse);
			model.addAttribute("applicantDetails", applicantDetails);
			// session.setAttribute("businessEntityDetails", businessEntityDetails);
			model.addAttribute("saveproriter", "saveproriter");
		} catch (Exception e) {
			RedirectView redirectView = new RedirectView();
			redirectView.setUrl("http://72.167.225.87/Testing_NMSWP/");
			e.printStackTrace();
		}

		return proprietorDetailsList1;
	}

	@RequestMapping(value = "/getAllPropritorDtls", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ProprietorDetails> getAllPropritorsDtls(HttpSession session) {
		List<ProprietorDetails> proprietorDtlList = new ArrayList<ProprietorDetails>();

		String besid = (String) session.getAttribute("besiId");
		proprietorDtlList = proprietorService.findAllByBusinessId(besid);
		// proprietorDtlList.sort(Comparator.comparing(ProprietorDetails::getPropId));
		Comparator<ProprietorDetails> propId = (list1, list2) -> list1.getPropId()
				.substring(20, list1.getPropId().length() - 1)
				.compareTo(list2.getPropId().substring(20, list2.getPropId().length() - 1));
		proprietorDtlList.sort(propId);
		return proprietorDtlList;

	}

	@RequestMapping(value = "/deletePropritorDtls", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ProprietorDetails> deletePropritorsDtls(@RequestBody ProprietorDetails proprietorDetails,
			HttpSession session) {
		List<ProprietorDetails> proprietorDtlList = new ArrayList<ProprietorDetails>();

		String besid = (String) session.getAttribute("besiId");
		proprietorService.deleteBypropId(proprietorDetails.getPropId());
		proprietorDtlList = proprietorService.findAllByBusinessId(besid);
		proprietorDtlList.sort(Comparator.comparing(ProprietorDetails::getPropId));
		return proprietorDtlList;

	}
	
	
	@RequestMapping(value="/getTotalEquity", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<ProprietorDetails> getTotalEquity(HttpSession session)
	{

		List<ProprietorDetails> proprietorDtlList = new ArrayList<ProprietorDetails>();

		String besid = (String) session.getAttribute("besiId");
		proprietorDtlList = proprietorService.findAllByBusinessId(besid);
		
		return proprietorDtlList;
		
	}
	
	

	/*-----------------------------tabs-------------------------------------*/
}