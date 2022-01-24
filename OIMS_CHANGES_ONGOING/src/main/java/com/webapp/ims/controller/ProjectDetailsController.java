package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.EXIST_PROJ_LIST;
import static com.webapp.ims.exception.GlobalConstants.NEW_PROJ_LIST;
import static com.webapp.ims.exception.GlobalConstants.NIVESH_SOAP_RESPONSE;
import static com.webapp.ims.exception.GlobalConstants.PROJECT_DETAILS_FORM;
import static com.webapp.ims.exception.GlobalConstants.Unit_Id;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.DistrictDetails;
import com.webapp.ims.model.ExistProjDetailsDocument;
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.NewProjDetailsDocument;
import com.webapp.ims.model.NewProjectDetails;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.model.Unit;
import com.webapp.ims.repository.ExistProjDocRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.NewProjDocRepository;
import com.webapp.ims.repository.UnitRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.DistrictDetailsService;
import com.webapp.ims.service.ExistingProjectDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.NewProjectDetailsService;
import com.webapp.ims.service.ProjectDocumentService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.impl.NewProjectDetailsServiceImpl;
import com.webapp.ims.webservices.SoapConsumeEx;

@Controller
public class ProjectDetailsController {
	private final Logger logger = LoggerFactory.getLogger(ProjectDetailsController.class);

	SoapConsumeEx soapdetails = new SoapConsumeEx();
	List<ExistingProjectDetails> existProjList = new ArrayList<>();

	List<NewProjectDetails> newProjList = new ArrayList<>();
	private AtomicInteger atomicInteger = new AtomicInteger();
	private int epIndex = 0;

	private int npIndex = 0;
	private boolean isepRowRecord = false;

	private boolean isnpRowRecord = false;

	@Autowired
	UnitRepository unitRepository;
	@Autowired
	ExistProjDocRepository projDocRepository;
	@Autowired
	NewProjDocRepository newProjDocRepository;

	@Autowired
	private ProjectService projectService;
	@Autowired
	private ExistingProjectDetailsService existProjectService;

	@Autowired
	private NewProjectDetailsService newProjectService;

	@Autowired
	private DistrictDetailsService districtDetailsService;
	@Autowired
	private ApplicantDetailsService applicantDetailsService;
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
	@Autowired
	ProjectDocumentService projDocService;

	@GetMapping(value = "/projectDetails")
	public String projectDetails(Model model, HttpSession session) {

		logger.debug("Get Project Details.");
		ProjectDetails projectDetails = new ProjectDetails();
		String projId = "";
		long districtCode = 0;
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				projId = entry.getValue() + "P1";
			}
			if (entry.getKey().equalsIgnoreCase("Industry_District_Id")) {
				districtCode = Long.parseLong(entry.getValue());
			}
		}

		DistrictDetails districtDetails = districtDetailsService.getDistrictByDistrictCode(districtCode);
		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);

		// To retrieve ExistingProjectDetailsList from table on projectId basis
		if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
			existProjList = existProjectService.getExistProjListById(projId);
			newProjList = newProjectService.getNewProjListById(projId);
			System.out.println(ProjectDetail.getNatureOfProject());
			if(ProjectDetail.getNatureOfProject().equalsIgnoreCase("NewProject"))
			{
				model.addAttribute(NEW_PROJ_LIST, newProjList);
				existProjList.clear();
			}
			else{
				model.addAttribute(EXIST_PROJ_LIST, existProjList);
				newProjList.clear();
			}
				//model.addAttribute(NEW_PROJ_LIST, newProjList);
			
		} else {
			//existProjList.clear();
			//newProjList.clear();
		}

		if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
			projectDetails.setDesignation(ProjectDetail.getDesignation());
			projectDetails.setMobileNo(ProjectDetail.getMobileNo());
			projectDetails.setWebSiteName(ProjectDetail.getWebSiteName());
			projectDetails.setExistingProducts(ProjectDetail.getExistingProducts());
			projectDetails.setExistingInstalledCapacity(ProjectDetail.getExistingInstalledCapacity());
			projectDetails.setProposedProducts(ProjectDetail.getProposedProducts());
			projectDetails.setProposedInstalledCapacity(ProjectDetail.getProposedInstalledCapacity());
			projectDetails.setExistingGrossBlock(ProjectDetail.getExistingGrossBlock());
			projectDetails.setProposedGrossBlock(ProjectDetail.getProposedGrossBlock());
			model.addAttribute("natureOfProject", ProjectDetail.getNatureOfProject());
			model.addAttribute("expansion", ProjectDetail.getExpansion());
			model.addAttribute("diversification", ProjectDetail.getDiversification());
			model.addAttribute("id", ProjectDetail.getId());
			projectDetails.setNatureOfProject(ProjectDetail.getNatureOfProject());
			projectDetails.setDistrictName(districtDetails.getDistrictName());
			projectDetails.setMandalName(districtDetails.getMandalName());
			projectDetails.setResionName(districtDetails.getRegionName());

			if (ProjectDetail.getNatureOfProject().equalsIgnoreCase("NewProject")) {
				NewProjDetailsDocument projDoc = newProjDocRepository.findByProjectId(projId);
				if (projDoc != null) {
					byte[] encodeBase64 = Base64.encodeBase64(projDoc.getData());
					String projReportbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
					projectDetails.setProjReportbase64File(projReportbase64Encoded);
					projectDetails.setEnclDetProRepFileName(projDoc.getFileName());
				}
			}

			if (ProjectDetail.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
				List<ExistProjDetailsDocument> projDocList = projDocRepository.getProjDocListByProjectId(projId);
				for (int i = 0; i < projDocList.size(); i++) {
					ExistProjDetailsDocument projDoc = projDocList.get(i);

					if (projDoc != null) {
						try {
							if (i == 0) {
								byte[] encodeBase64 = Base64.encodeBase64(projDoc.getData());
								String projReportbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
								projectDetails.setProjReportbase64File(projReportbase64Encoded);
								projectDetails.setEnclDetProRepFileName(projDoc.getFileName());
							}
							if (i == 1) {
								byte[] caReportBase64 = Base64.encodeBase64(projDoc.getData());
								String caReportbase64Encoded = new String(caReportBase64, StandardCharsets.UTF_8);
								projectDetails.setCaReportbase64File(caReportbase64Encoded);
								projectDetails.setCaCertificateFileName(projDoc.getFileName());
							}

							if (i == 2) {
								byte[] charEngBase64 = Base64.encodeBase64(projDoc.getData());
								String charEngbase64Encoded = new String(charEngBase64, StandardCharsets.UTF_8);
								projectDetails.setCharEngbase64File(charEngbase64Encoded);
								projectDetails.setCharatEngFileName(projDoc.getFileName());
							}
						} catch (Exception e) {
						}
					}
				}

			}

			soapRestData(model, projectDetails, projectResponce);
			model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse);
			model.addAttribute("projectId", ProjectDetail.getId());
			InvestmentDetails investmentDetails = investmentDetailsRepository.getInvestmentDetailsByapplId(ProjectDetail.getApplicantDetailId());
			if (investmentDetails != null) {
				model.addAttribute("Iflag", investmentDetails.getInvId());
			}
			ProposedEmploymentDetails proposedEmploymentDetails = emplDtlService.getProposedEmploymentDetailsByappId(ProjectDetail.getApplicantDetailId());
			if (proposedEmploymentDetails != null) {
				model.addAttribute("PEflag", proposedEmploymentDetails.getId());
			}
		} else {
			projectDetails.setDistrictName(districtDetails.getDistrictName());
			projectDetails.setMandalName(districtDetails.getMandalName());
			projectDetails.setResionName(districtDetails.getRegionName());
			model.addAttribute("natureOfProject", "");
			soapRestData(model, projectDetails, projectResponce);
			model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse);
		}

		return "project_details";

	}

	private void soapRestData(Model model, ProjectDetails projectDetails, Map<String, String> niveshResponse) {
		try {
			for (Map.Entry<String, String> entry : niveshResponse.entrySet()) {

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
					projectDetails.setDistrictName(entry.getValue());
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

			}
		} catch (Exception e) {
			logger.error(String.format("***** ProjectDetail soapRestData exception  **** %s", e.getMessage()));
		}
		model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
	}

	/**
	 * This method is responsible to save documents in MongoDB collection.
	 */
	public void saveDocInMongoDB(ProjectDetails projectDetails, MultipartFile enclDetProRepFileName, MultipartFile caCertificateFileName, MultipartFile charatEngFileName, HttpSession session,
			String projId) {

		if (projectDetails.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			try {
				List<MultipartFile> multipartFileList = new LinkedList<>();
				if ((!enclDetProRepFileName.getOriginalFilename().isEmpty() && enclDetProRepFileName.getOriginalFilename() != null) && enclDetProRepFileName.getBytes() != null) {
					multipartFileList.add(enclDetProRepFileName);
				}
				if ((!caCertificateFileName.getOriginalFilename().isEmpty() && caCertificateFileName.getOriginalFilename() != null) && caCertificateFileName.getBytes() != null) {
					multipartFileList.add(caCertificateFileName);
				}

				if ((!charatEngFileName.getOriginalFilename().isEmpty() && charatEngFileName.getOriginalFilename() != null) && charatEngFileName.getBytes() != null) {
					multipartFileList.add(charatEngFileName);
				}

				if (enclDetProRepFileName.getOriginalFilename().isEmpty() || enclDetProRepFileName.getOriginalFilename() == null) {
					List<ExistProjDetailsDocument> projDocList = projDocRepository.getProjDocListByProjectId(projId);
					ExistProjDetailsDocument projDocObj = null;
					if (!projDocList.isEmpty()) {
						projDocObj = projDocRepository.findByDocId(projDocList.get(0).getDocId());
					}
					ExistProjDetailsDocument dbFile = new ExistProjDetailsDocument();
					if (projDocObj != null) {
						dbFile.set_id(projDocObj.get_id());
						dbFile.setFileName(projDocObj.getFileName());
						dbFile.setFileType(projDocObj.getFileType());
						dbFile.setData(projDocObj.getData());
						dbFile.setDocCreatedBy("User");
						dbFile.setDocUpdateDate(new Date());
						dbFile.setDocCreatedDate(new Date());
						dbFile.setProjectId(projId);
						dbFile.setDocId(projDocObj.getDocId());
						projDocRepository.save(dbFile);
					}
				}

				if (caCertificateFileName.getOriginalFilename().isEmpty() || caCertificateFileName.getOriginalFilename() == null) {
					List<ExistProjDetailsDocument> projDocList = projDocRepository.getProjDocListByProjectId(projId);
					ExistProjDetailsDocument projDocObj = null;
					if (!projDocList.isEmpty()) {
						projDocObj = projDocRepository.findByDocId(projDocList.get(1).getDocId());
					}
					ExistProjDetailsDocument dbFile = new ExistProjDetailsDocument();
					if (projDocObj != null) {
						dbFile.set_id(projDocObj.get_id());
						dbFile.setFileName(projDocObj.getFileName());
						dbFile.setFileType(projDocObj.getFileType());
						dbFile.setData(projDocObj.getData());
						dbFile.setDocCreatedBy("User");
						dbFile.setDocUpdateDate(new Date());
						dbFile.setDocCreatedDate(new Date());
						dbFile.setProjectId(projId);
						dbFile.setDocId(projDocObj.getDocId());
						projDocRepository.save(dbFile);
					}
				}

				if (charatEngFileName.getOriginalFilename().isEmpty() || charatEngFileName.getOriginalFilename() == null) {
					List<ExistProjDetailsDocument> projDocList = projDocRepository.getProjDocListByProjectId(projId);
					ExistProjDetailsDocument projDocObj = null;
					if (!projDocList.isEmpty()) {
						projDocObj = projDocRepository.findByDocId(projDocList.get(2).getDocId());
					}
					ExistProjDetailsDocument dbFile = new ExistProjDetailsDocument();
					if (projDocObj != null) {
						dbFile.set_id(projDocObj.get_id());
						dbFile.setFileName(projDocObj.getFileName());
						dbFile.setFileType(projDocObj.getFileType());
						dbFile.setData(projDocObj.getData());
						dbFile.setDocCreatedBy("User");
						dbFile.setDocUpdateDate(new Date());
						dbFile.setDocCreatedDate(new Date());
						dbFile.setProjectId(projId);
						dbFile.setDocId(projDocObj.getDocId());
						projDocRepository.save(dbFile);
					}
				}

				projDocService.saveAndUpdateMultipleFiles(multipartFileList, session);
			} catch (Exception e) {
				logger.error(String.format("***** ProjectDetail document exception  **** %s", e.getMessage()));
			}
		}
	}

	/**
	 * (This method is used to save projectDetails records in Postgres table and documents in mongoDB collection.)
	 */
	private void commonSaveProjectDetails(ProjectDetails projectDetails, BindingResult result, Model model, MultipartFile enclDetProRepFileName, MultipartFile caCertificateFileName,
			MultipartFile charatEngFileName, HttpSession session) {

		logger.debug("Created Project Details.");
		String projId = "";
		String applicantId = "";

		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

		for (Map.Entry<String, String> entry : investResponce.entrySet()) {

			if (entry.getKey().equalsIgnoreCase("Unit_Category")) {
				model.addAttribute("invIndType", entry.getValue());
			}
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				projId = entry.getValue() + "P1";
			}
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				applicantId = entry.getValue() + "A1";
			}
		}

		ProjectDetails ProjectDetail = new ProjectDetails();
		ProjectDetail.setId(projId);
		ProjectDetail.setApplicantDetailId(applicantId);
		ProjectDetail.setContactPersonName(projectDetails.getContactPersonName());
		ProjectDetail.setDesignation(projectDetails.getDesignation());
		ProjectDetail.setProjectDescription(projectDetails.getProjectDescription());
		ProjectDetail.setMobileNo((projectDetails.getMobileNo()));
		ProjectDetail.setWebSiteName(projectDetails.getWebSiteName());
		ProjectDetail.setFullAddress(projectDetails.getFullAddress());
		ProjectDetail.setDistrictName(projectDetails.getDistrictName());
		ProjectDetail.setMandalName(projectDetails.getMandalName());
		ProjectDetail.setResionName(projectDetails.getResionName());
		ProjectDetail.setPinCode(projectDetails.getPinCode());
		ProjectDetail.setNatureOfProject(projectDetails.getNatureOfProject());

		saveDocInMongoDB(projectDetails, enclDetProRepFileName, caCertificateFileName, charatEngFileName, session, projId);

		if (projectDetails.getNatureOfProject().equalsIgnoreCase("NewProject")) {
			ProjectDetail.setNewProject("NewProject");
			projDocService.saveAndUpdateFile(enclDetProRepFileName, session);

		} else {
			ProjectDetail.setExpansion(projectDetails.getExpansion());
			ProjectDetail.setDiversification(projectDetails.getDiversification());
			ProjectDetail.setExistingProducts(projectDetails.getExistingProducts());
			ProjectDetail.setExistingInstalledCapacity(projectDetails.getExistingInstalledCapacity());
			ProjectDetail.setProposedProducts(projectDetails.getProposedProducts());
			ProjectDetail.setProposedInstalledCapacity(projectDetails.getProposedInstalledCapacity());
			ProjectDetail.setExistingGrossBlock(projectDetails.getExistingGrossBlock());
			ProjectDetail.setProposedGrossBlock(projectDetails.getProposedGrossBlock());
		}
		ProjectDetail.setCreatedBy("AdminUser");
		ProjectDetail.setStatus("active");

		projectService.saveProject(ProjectDetail);

		try {
			if (!existProjectService.getExistProjListById(projId).isEmpty()) {
				List<ExistingProjectDetails> epdList = existProjectService.getExistProjListById(projId);
				for (ExistingProjectDetails epd : epdList) {
					existProjectService.deleteExistProjById(epd.getEpdId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!existProjList.isEmpty()) {
			existProjectService.saveExistProjList(existProjList);
		}

		try {
			if (!newProjectService.getNewProjListById(projId).isEmpty()) {
				List<NewProjectDetails> npdList = newProjectService.getNewProjListById(projId);
				for (NewProjectDetails npd : npdList) {
					newProjectService.deleteNewProjById(npd.getNpdId());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (!newProjList.isEmpty()) {
			newProjectService.saveNewProjList(newProjList);
		}

		model.addAttribute(PROJECT_DETAILS_FORM, new ProjectDetails());

		session.setAttribute("projectId", projectDetails.getId());
		soapRestData(model, projectDetails, investResponce);
		model.addAttribute(NIVESH_SOAP_RESPONSE, investResponce);
		model.addAttribute(EXIST_PROJ_LIST, existProjList);
		model.addAttribute(NEW_PROJ_LIST, newProjList);

	}

	@PostMapping(value = "/projectDetails")
	public ModelAndView save(@ModelAttribute(PROJECT_DETAILS_FORM) @Validated ProjectDetails projectDetails, BindingResult result, Model model,
			@RequestParam("enclDetProRepFileName") MultipartFile enclDetProRepFileName, @RequestParam("caCertificateFileName") MultipartFile caCertificateFileName,
			@RequestParam("charatEngFileName") MultipartFile charatEngFileName, HttpSession session) {

		commonSaveProjectDetails(projectDetails, result, model, enclDetProRepFileName, caCertificateFileName, charatEngFileName, session);

		return new ModelAndView("redirect:/investmentDetails");

	}

	@PostMapping(value = "/autoprojectDetails")
	public ModelAndView autoprojectDetails(@ModelAttribute(PROJECT_DETAILS_FORM) @Validated ProjectDetails projectDetails, BindingResult result, Model model,
			@RequestParam("regiOrLicenseFileName") MultipartFile regiOrLicenseFileName, @RequestParam("enclDetProRepFileName") MultipartFile enclDetProRepFileName,
			@RequestParam("caCertificateFileName") MultipartFile caCertificateFileName, @RequestParam("charatEngFileName") MultipartFile charatEngFileName, HttpSession session) {

		commonSaveProjectDetails(projectDetails, result, model, enclDetProRepFileName, caCertificateFileName, charatEngFileName, session);

		return new ModelAndView("redirect:/projectDetails");

	}

	/*-----------------------------tabs-------------------------------------*/

	@RequestMapping(value = "/getIdByTabs22", method = RequestMethod.GET)
	public ModelAndView getIdByTabs22(Model model, HttpSession session, @RequestParam(value = "authoTab") String authoTab) {

		String appId = "";
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				appId = entry.getValue() + "A1";
			}
		}
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);

		if (applicantDetail != null && authoTab.equalsIgnoreCase("authoTab")) {
			return new ModelAndView("redirect:/editApplicantForm");
		}
		return null;

	}

	@RequestMapping(value = "/getIdByTabs23", method = RequestMethod.GET)
	public ModelAndView getIdByTabs23(Model model, HttpSession session, @RequestParam(value = "busiTab") String busiTab) {

		String appId = "";
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				appId = entry.getValue() + "A1";
			}
		}
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);
		BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(appId);
		if (businessEntityDetails != null && busiTab.equalsIgnoreCase("busiTab")) {
			session.setAttribute("applicantDetails", applicantDetail);
			session.setAttribute("applicantDetailsId", appId);
			return new ModelAndView("redirect:/businessDetails");
		}
		return new ModelAndView("redirect:/businessDetails");

	}

	@RequestMapping(value = "/getIdByTabs24", method = RequestMethod.GET)
	public ModelAndView getIdByTabs24(Model model, HttpSession session, @RequestParam(value = "investTab") String investTab) {

		String investId = "";

		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				investId = entry.getValue() + "I1";
			}

		}

		InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(investId);
		if (invdtlFromDb != null && investTab.equalsIgnoreCase("investTab")) {
			return new ModelAndView("redirect:/investmentDetails");
		} else {

			return new ModelAndView("redirect:/projectDetails");
		}

	}

	@RequestMapping(value = "/getIdByTabs25", method = RequestMethod.GET)
	public ModelAndView getIdByTabs25(Model model, HttpSession session, @RequestParam(value = "propoTab") String propoTab) {

		String propoId = "";
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		for (Map.Entry<String, String> entry : projectResponce.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
				propoId = entry.getValue() + "PE1";
			}

		}

		ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService.getPropEmpById(propoId);
		if (proposedEmploymentDetail != null && propoTab.equalsIgnoreCase("propoTab")) {
			return new ModelAndView("redirect:/skilUnskEmplDet");
		} else {
			return new ModelAndView("redirect:/projectDetails");
		}

	}

	/**
	 * This method is responsible to fetch unit list from Unit_Master table and put into HashMap.
	 */
	@ModelAttribute("unitListMap")
	public Map<String, String> getUnitList() {
		Map<String, String> unitListMap = new LinkedHashMap<>();
		try {
			List<Unit> unitList = unitRepository.findAll();
			List<Unit> sortedList = unitList.stream().sorted(Comparator.comparing(Unit::getUnitName)).collect(Collectors.toList());

			for (Unit unit : sortedList) {
				unitListMap.put(unit.getUnitName(), unit.getUnitName());
			}

		} catch (Exception e) {
			logger.error(String.format("#### getUnitList Investment exception $$$ %s", e.getMessage()));
		}
		return unitListMap;

	}

	/**
	 * This method is responsible to retrieve ProjectDetails' documents from MongoDB collection.
	 */
	public void projDocsFromMongoDB(ProjectDetails projectDetails, String projId) {

		List<ExistProjDetailsDocument> projDocList = projDocRepository.getProjDocListByProjectId(projId);
		for (int i = 0; i < projDocList.size(); i++) {
			ExistProjDetailsDocument projDoc = projDocList.get(i);

			if (projDoc != null) {
				try {
					if (i == 0) {
						byte[] encodeBase64 = Base64.encodeBase64(projDoc.getData());
						String projReportbase64Encoded = new String(encodeBase64, StandardCharsets.UTF_8);
						projectDetails.setProjReportbase64File(projReportbase64Encoded);
						projectDetails.setEnclDetProRepFileName(projDoc.getFileName());
					}
					if (i == 1) {
						byte[] caReportBase64 = Base64.encodeBase64(projDoc.getData());
						String caReportbase64Encoded = new String(caReportBase64, StandardCharsets.UTF_8);
						projectDetails.setCaReportbase64File(caReportbase64Encoded);
						projectDetails.setCaCertificateFileName(projDoc.getFileName());
					}

					if (i == 2) {
						byte[] charEngBase64 = Base64.encodeBase64(projDoc.getData());
						String charEngbase64Encoded = new String(charEngBase64, StandardCharsets.UTF_8);
						projectDetails.setCharEngbase64File(charEngbase64Encoded);
						projectDetails.setCharatEngFileName(projDoc.getFileName());
					}
				} catch (Exception e) {
					logger.error(String.format("### fetchDocumentsFromMongoDB exception ### %s", e.getMessage()));
				}
			}
		}
	}

	public void commonExistProjData(ProjectDetails projectDetails, BindingResult result, HttpSession session, Model model) {

		try {
			String projId = "";
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

			Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (Map.Entry<String, String> entry : investResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
					projId = entry.getValue() + "P1";
				}

			}

			soapRestData(model, projectDetails, projectResponce);
			model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse);
			model.addAttribute("natureOfProject", projectDetails.getNatureOfProject());
			model.addAttribute("expansion", projectDetails.getExpansion());
			model.addAttribute("diversification", projectDetails.getDiversification());
			projDocsFromMongoDB(projectDetails, projId);

		} catch (Exception e) {
			logger.error(String.format("### fetchProjRecordsFromDB exception ### %s", e.getMessage()));
		}

	}

	/**
	 * This method is responsible to fetch ProjectDetails records from table.
	 */
	public void fetchProjRecordsFromDB(ProjectDetails projectDetails, BindingResult result, HttpSession session, Model model) {

		try {
			String projId = "";
			long districtCode = 0;
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

			Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (Map.Entry<String, String> entry : investResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
					projId = entry.getValue() + "P1";
				}
				if (entry.getKey().equalsIgnoreCase("Industry_District_Id")) {
					districtCode = Long.parseLong(entry.getValue());
				}
			}

			DistrictDetails districtDetails = districtDetailsService.getDistrictByDistrictCode(districtCode);
			ProjectDetails ProjectDetail = projectService.getProjDetById(projId);

			if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
				projectDetails.setDesignation(ProjectDetail.getDesignation());
				projectDetails.setMobileNo(ProjectDetail.getMobileNo());
				projectDetails.setWebSiteName(ProjectDetail.getWebSiteName());

				model.addAttribute("natureOfProject", ProjectDetail.getNatureOfProject());
				model.addAttribute("expansion", ProjectDetail.getExpansion());
				model.addAttribute("diversification", ProjectDetail.getDiversification());
				model.addAttribute("id", ProjectDetail.getId());
				projectDetails.setNatureOfProject(ProjectDetail.getNatureOfProject());
				projectDetails.setDistrictName(districtDetails.getDistrictName());
				projectDetails.setMandalName(districtDetails.getMandalName());
				projectDetails.setResionName(districtDetails.getRegionName());

				projDocsFromMongoDB(projectDetails, projId);

				soapRestData(model, projectDetails, projectResponce);
				model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse);

			} else {
				projectDetails.setDistrictName(districtDetails.getDistrictName());
				projectDetails.setMandalName(districtDetails.getMandalName());
				projectDetails.setResionName(districtDetails.getRegionName());
				model.addAttribute("newProject", "");
				model.addAttribute("expansion", "");
				model.addAttribute("diversification", "");

				model.addAttribute("natureOfProject", "");
				soapRestData(model, projectDetails, projectResponce);
				model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse);
			}
		} catch (Exception e) {
			logger.error(String.format("### fetchProjRecordsFromDB exception ### %s", e.getMessage()));
		}

	}

	@PostMapping("/addExistProjectDetails")
	public ModelAndView addExistProjectDetails(@ModelAttribute(PROJECT_DETAILS_FORM) ProjectDetails projectDetails, BindingResult result, HttpSession session, Model model) {

		commonExistProjData(projectDetails, result, session, model);
		commonAddExistProjectDetails(projectDetails, result, session, model);

		return new ModelAndView("project_details");

	}

	@PostMapping("/addNewProjectDetails")
	public ModelAndView addNewProjectDetails(@ModelAttribute(PROJECT_DETAILS_FORM) ProjectDetails projectDetails, BindingResult result, HttpSession session, Model model) {
		
		commonNewProjData(projectDetails, result, session, model);

		commonAddNewProjectDetails(projectDetails, result, session, model);
		return new ModelAndView("project_details");

	}

	public void commonNewProjData(ProjectDetails projectDetails, BindingResult result, HttpSession session, Model model) {

		try {
			String projId = "";
			Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
			Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();

			Map<String, String> investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (Map.Entry<String, String> entry : investResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
					projId = entry.getValue() + "P1";
				}

			}

			soapRestData(model, projectDetails, projectResponce);
			model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse);

			model.addAttribute("natureOfProject", projectDetails.getNatureOfProject());
			//model.addAttribute("expansion", projectDetails.getExpansion());
			//model.addAttribute("diversification", projectDetails.getDiversification());

		} catch (Exception e) {
			logger.error(String.format("### fetchProjRecordsFromDB exception ### %s", e.getMessage()));
		}

	}

	public void commonAddNewProjectDetails(ProjectDetails projectDetails, BindingResult result, HttpSession session, Model model) {

		String projId = "";
		String npdId = "";
		Map<String, String> investResponce = null;
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);

		try {
			NewProjectDetails newProj = new NewProjectDetails();

			newProj.setNewPropProducts(projectDetails.getProposedProductsNew());
			newProj.setNewPropInstallCapacity(projectDetails.getProposedInstalledCapacityNew() + " " + projectDetails.getPicUnit());
			newProj.setStatus("active");

			investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (Map.Entry<String, String> entry : investResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
					projId = entry.getValue() + "P1";
					npdId = entry.getValue() + "NP" + atomicInteger.getAndIncrement();
				}
			}

			newProj.setNpdId(npdId);
			newProj.setNpdProjDtlId(projId);

			projectDetails.setProposedProductsNew(null);

			projectDetails.setPicUnit(null);

			projectDetails.setProposedInstalledCapacityNew(null);

			if (isnpRowRecord) {
				if (!newProjList.isEmpty() && newProjList.get(npIndex) != null) {
					newProjList.remove(npIndex);
				}
				updateNewProjRow(npIndex, newProj);
				isnpRowRecord = false;
				model.addAttribute(NEW_PROJ_LIST, newProjList);
				model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
			} else {
				newProjList.add(newProj);
				model.addAttribute(NEW_PROJ_LIST, newProjList);
				model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
			}

		} catch (Exception e) {
			logger.error(String.format("^^^^ addNewProjectRow Exception ^^^ %s", e.getMessage()));
		}

		model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
		model.addAttribute(NEW_PROJ_LIST, newProjList);
		model.addAttribute("newProjDetails", new NewProjectDetails());

	}

	@GetMapping("/addExistProjectDetails")
	public ModelAndView getaddExistProjectDetails(@ModelAttribute(PROJECT_DETAILS_FORM) ProjectDetails projectDetails, BindingResult result, HttpSession session, Model model) {
		commonExistProjData(projectDetails, result, session, model);
		commonAddExistProjectDetails(projectDetails, result, session, model);

		return new ModelAndView("redirect:/projectDetails");
	}

	@GetMapping("/addNewProjectDetails")
	public ModelAndView getaddNewProjectDetails(@ModelAttribute(PROJECT_DETAILS_FORM) ProjectDetails projectDetails, BindingResult result, HttpSession session, Model model) {
		commonNewProjData(projectDetails, result, session, model);
		commonAddNewProjectDetails(projectDetails, result, session, model);

		return new ModelAndView("redirect:/projectDetails");
	}

	public void commonAddExistProjectDetails(ProjectDetails projectDetails, BindingResult result, HttpSession session, Model model) {

		String projId = "";
		String epdId = "";
		Map<String, String> investResponce = null;
		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);

		try {
			ExistingProjectDetails existProj = new ExistingProjectDetails();
			existProj.setEpdExisProducts(projectDetails.getExistingProducts());
			existProj.setEpdExisInstallCapacity(projectDetails.getExistingInstalledCapacity() + " " + projectDetails.getEicUnit());
			existProj.setEpdPropProducts(projectDetails.getProposedProducts());
			String picunit = projectDetails.getPicUnit();
			existProj.setEpdPropInstallCapacity(projectDetails.getProposedInstalledCapacity() + " " + picunit.replaceAll("[^a-zA-Z0-9]", ""));
			//minutesOfMeetings.setGosAppID(GoAppId.replaceAll("[^a-zA-Z0-9]", ""));
			existProj.setEpdExisGrossBlock(projectDetails.getExistingGrossBlock());
			existProj.setEpdPropoGrossBlock(projectDetails.getProposedGrossBlock());
			existProj.setEpdStatus("active");
			existProj.setEpdCreatedBy("User");
			existProj.setEpdModifiyDate(new Date());

			investResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
			for (Map.Entry<String, String> entry : investResponce.entrySet()) {
				if (entry.getKey().equalsIgnoreCase(Unit_Id)) {
					projId = entry.getValue() + "P1";
					epdId = entry.getValue() + "EP" + atomicInteger.getAndIncrement();
				}
			}

			existProj.setEpdId(epdId);
			existProj.setEpdProjDtlId(projId);

			/*
			 * if (result.hasErrors()) { model.addAttribute(EXIST_PROJ_LIST, existProjList); model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
			 * soapRestData(model, projectDetails, projectResponce); model.addAttribute(NIVESH_SOAP_RESPONSE, niveshResponse); return new
			 * ModelAndView("project_details"); }
			 */

			projectDetails.setProposedProducts(null);
			projectDetails.setEicUnit(null);
			projectDetails.setPicUnit(null);
			projectDetails.setExistingGrossBlock(null);
			projectDetails.setExistingProducts(null);
			projectDetails.setExistingInstalledCapacity(null);
			projectDetails.setProposedGrossBlock(null);
			projectDetails.setProposedInstalledCapacity(null);

			if (isepRowRecord) {
				if (!existProjList.isEmpty() && existProjList.get(epIndex) != null) {
					existProjList.remove(epIndex);
				}
				updateExistProjRow(epIndex, existProj);
				isepRowRecord = false;
				model.addAttribute(EXIST_PROJ_LIST, existProjList);
				model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
			} else {
				existProjList.add(existProj);
				model.addAttribute(EXIST_PROJ_LIST, existProjList);
				model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
			}

		} catch (Exception e) {
			logger.error(String.format("^^^^ addExistProjectRow Exception ^^^ %s", e.getMessage()));
		}

		model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
		model.addAttribute(EXIST_PROJ_LIST, existProjList);
		model.addAttribute("existProjDetails", new ExistingProjectDetails());

	}

	@GetMapping("/deleteExistProjectRow")
	public ModelAndView deleteExistProjectRow(@ModelAttribute("existProjDetails") ExistingProjectDetails existProj, BindingResult result,
			@ModelAttribute(PROJECT_DETAILS_FORM) ProjectDetails projectDetails, Model model, @RequestParam(value = "selectedRow", required = false) int index, HttpSession session) {

		Object niveshResponse = session.getAttribute(NIVESH_SOAP_RESPONSE);
		Map<String, String> projectResponce = ((SoapDataModel) niveshResponse).getNiveshSoapResponse();
		try {
			if (!existProjList.isEmpty() && existProjList.get(index) != null) {
				ExistingProjectDetails existProjl2 = existProjList.get(index);
				try {
					existProjectService.deleteExistProjById(existProjl2.getEpdId());
				} catch (Exception e) {
				}
				existProjList.remove(index);
			}
			soapRestData(model, projectDetails, projectResponce);
		} catch (Exception e) {
			logger.error(String.format("^^^^ deleteExistProjectRow Exception ^^^ %s", e.getMessage()));
		}

		if (result.hasErrors()) {
			System.out.println("error:::::");
		}
		model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
		model.addAttribute(EXIST_PROJ_LIST, existProjList);

		model.addAttribute("existProjDetails", new ExistingProjectDetails());
		commonExistProjData(projectDetails, result, session, model);
		model.addAttribute("natureOfProject", projectDetails.getNatureOfProject());

		return new ModelAndView("project_details");

	}

	@PostMapping("/deleteExistProjectRow")
	public ModelAndView postDeleteExistProjectRow(@ModelAttribute(PROJECT_DETAILS_FORM) ProjectDetails projectDetails, @ModelAttribute("existProjDetails") ExistingProjectDetails existProj,
			BindingResult result, Model model, @RequestParam(value = "selectedRow", required = false) int index, HttpSession session) {
		try {
			if (!existProjList.isEmpty() && existProjList.get(index) != null) {
				ExistingProjectDetails existProjl2 = existProjList.get(index);
				try {
					existProjectService.deleteExistProjById(existProjl2.getEpdId());
				} catch (Exception e) {
				}
				existProjList.remove(index);
			}

		} catch (Exception e) {
			logger.error(String.format("^^^^ deleteExistProjectRow Exception ^^^ %s", e.getMessage()));
		}
		model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
		model.addAttribute(EXIST_PROJ_LIST, existProjList);
		model.addAttribute("existProjDetails", new ExistingProjectDetails());
		commonExistProjData(projectDetails, result, session, model);

		return new ModelAndView("project_details");

	}

	@PostMapping("/editExistProjectRow")
	public ModelAndView editExistProjectRow(@ModelAttribute(PROJECT_DETAILS_FORM) ProjectDetails projectDetails, BindingResult result, @RequestParam(value = "selectedRow", required = false) int index,
			Model model, HttpSession session) {

		ExistingProjectDetails existProject = null;
		try {
			if (!existProjList.isEmpty() && existProjList.get(index) != null) {
				existProject = existProjList.get(index);

				String[] eicArr = existProject.getEpdExisInstallCapacity().split(" ", 2);
				String[] picArr = existProject.getEpdPropInstallCapacity().split(" ", 2);

				projectDetails.setExistingProducts(existProject.getEpdExisProducts());
				projectDetails.setProposedProducts(existProject.getEpdPropProducts());
				projectDetails.setExistingGrossBlock(existProject.getEpdExisGrossBlock());
				projectDetails.setProposedGrossBlock(existProject.getEpdPropoGrossBlock());
				projectDetails.setExistingInstalledCapacity(Long.parseLong(eicArr[0]));
				projectDetails.setEicUnit(eicArr[1]);
				projectDetails.setProposedInstalledCapacity(Long.parseLong(picArr[0]));
				projectDetails.setPicUnit(picArr[1]);

				epIndex = index;
				isepRowRecord = true;
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ editExistProjectRow Exception ^^^ %s", e.getMessage()));
		}

		model.addAttribute("existProjDetails", existProject);
		model.addAttribute("editepd", "editepd");

		model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
		model.addAttribute(EXIST_PROJ_LIST, existProjList);
		model.addAttribute("existProjDetails", new ExistingProjectDetails());
		commonExistProjData(projectDetails, result, session, model);
		return new ModelAndView("project_details");
	}
	
	
	//For New Sachin
	
	@PostMapping("/deleteNewProjectRow")
	public ModelAndView postDeleteNewProjectRow(@ModelAttribute(PROJECT_DETAILS_FORM) ProjectDetails projectDetails, @ModelAttribute("newProjDetails") NewProjectDetails newProj,
			BindingResult result, Model model, @RequestParam(value = "selectedRow", required = false) int index, HttpSession session) {
		try {
			if (!newProjList.isEmpty() && newProjList.get(index) != null) {
				NewProjectDetails existProjl2 = newProjList.get(index);
				try {
					newProjectService.deleteNewProjById(existProjl2.getNpdId());
				} catch (Exception e) {
				}
				newProjList.remove(index);
			}

		} catch (Exception e) {
			logger.error(String.format("^^^^ deleteNewProjectRow Exception ^^^ %s", e.getMessage()));
		}
		model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
		model.addAttribute(NEW_PROJ_LIST, newProjList);
		model.addAttribute("newProjDetails", new NewProjectDetails());
		commonExistProjData(projectDetails, result, session, model);

		return new ModelAndView("project_details");

	}

	@PostMapping("/editNewProjectRow")
	public ModelAndView editNewProjectRow(@ModelAttribute(PROJECT_DETAILS_FORM) ProjectDetails projectDetails, BindingResult result, @RequestParam(value = "selectedRow", required = false) int index,
			Model model, HttpSession session) {

		NewProjectDetails newProject = null;
		try {
			if (!existProjList.isEmpty() && existProjList.get(index) != null) {
				newProject = newProjList.get(index);

				
				String[] picArr = newProject.getNewPropInstallCapacity().split(" ", 2);
			

				
				projectDetails.setProposedProductsNew(newProject.getNewPropProducts());
				
			
			
				projectDetails.setProposedInstalledCapacity(Long.parseLong(picArr[0]));
				projectDetails.setPicUnit(picArr[1]);

				npIndex = index;
				isnpRowRecord = true;
			}
		} catch (Exception e) {
			logger.error(String.format("^^^^ editNewProjectRow Exception ^^^ %s", e.getMessage()));
		}

		model.addAttribute("newProjDetails", newProject);
		model.addAttribute("editnpd", "editnpd");

		model.addAttribute(PROJECT_DETAILS_FORM, projectDetails);
		model.addAttribute(NEW_PROJ_LIST, newProjList);
		model.addAttribute("newProjDetails", new NewProjectDetails());
		commonNewProjData(projectDetails, result, session, model);
		return new ModelAndView("project_details");
	}
	

	public void updateExistProjRow(int index, ExistingProjectDetails epd) {
		existProjList.add(index, epd);
	}

	public void updateNewProjRow(int index, NewProjectDetails npd) {
		newProjList.add(index, npd);
	}
}
