package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.EXIST_PROJ_LIST;
import static com.webapp.ims.exception.GlobalConstants.PROPOSED_EMPLOYMENT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.PW_INVESTMENT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.PW_INVESTMENT_LIST;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.persistence.Tuple;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.dis.model.CISDocument;
import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.dis.model.CapitalInvestmentDocument;
import com.webapp.ims.dis.model.DisReimbrsDepositeGstTable;
import com.webapp.ims.dis.model.DisStampDeauty;
import com.webapp.ims.dis.model.Discis;
import com.webapp.ims.dis.model.Disepfriem;
import com.webapp.ims.dis.model.Disiis;
import com.webapp.ims.dis.model.DissbursmentReimbrsDepositeGST;
import com.webapp.ims.dis.model.EPFDocument;
import com.webapp.ims.dis.model.EmploymentdetailsDocument;
import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.model.IISDocument;
import com.webapp.ims.dis.model.NewProjDisbursement;
import com.webapp.ims.dis.model.OtherIncentive;
import com.webapp.ims.dis.model.ReimbrsmentGSTDocument;
import com.webapp.ims.dis.repository.CISDocumentRepository;
import com.webapp.ims.dis.repository.CapitalInvestDocumentRepository;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.dis.repository.DisEPFRepository;
import com.webapp.ims.dis.repository.DisStampDeautyRepository;
import com.webapp.ims.dis.repository.EPFDocumentRepository;
import com.webapp.ims.dis.repository.EmploymentDetailsDocumentRepository;
import com.webapp.ims.dis.repository.IISDocumentRepository;
import com.webapp.ims.dis.repository.NewProjDisburseRepository;
import com.webapp.ims.dis.repository.ProjDisburseRepository;
import com.webapp.ims.dis.repository.ReimbrsDepositGSTDocumentRepository;
import com.webapp.ims.dis.service.CISDocumentService;
import com.webapp.ims.dis.service.CapitalInvestService;
import com.webapp.ims.dis.service.CapitalInvestmentDocumentService;
import com.webapp.ims.dis.service.DisReimbrsGStTableService;
import com.webapp.ims.dis.service.DisbursmentCisService;
import com.webapp.ims.dis.service.DisbursmentIisService;
import com.webapp.ims.dis.service.DisbursmentRiembrsDepositGSTService;
import com.webapp.ims.dis.service.EPFDocumentService;
import com.webapp.ims.dis.service.EmployementDetailsDocumentService;
import com.webapp.ims.dis.service.IISDocumentService;
import com.webapp.ims.dis.service.OtherDetailService;
import com.webapp.ims.dis.service.ReimbrsGSTDocumentService;
import com.webapp.ims.dis.service.StampDutyService;
import com.webapp.ims.login.model.Login;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicantDocument;
import com.webapp.ims.model.AvailCustomisedDetails;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.BusinessEntityDocument;
import com.webapp.ims.model.CirculateToDepartment;
import com.webapp.ims.model.ConcernDepartment;
import com.webapp.ims.model.Department;
import com.webapp.ims.model.ExistProjDetailsDocument;
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.InvBreakupCost;
import com.webapp.ims.model.InvMeansOfFinance;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.InvestmentDocument;
import com.webapp.ims.model.MinutesOfMeeting;
import com.webapp.ims.model.NewProjDetailsDocument;
import com.webapp.ims.model.NewProjectDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.model.StateDetails;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.repository.BusinessEntityDocumentRepository;
import com.webapp.ims.repository.CirculateToDepartmentRepository;
import com.webapp.ims.repository.DPTAgendaRepository;
import com.webapp.ims.repository.ExistProjDocRepository;
import com.webapp.ims.repository.IncentiveDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.InvestmentDocumentRepository;
import com.webapp.ims.repository.MinutesOfMeetingRepository;
import com.webapp.ims.repository.NewProjDocRepository;
import com.webapp.ims.repository.PrepareAgendaNoteRepository;
import com.webapp.ims.repository.ProjectRepository;
import com.webapp.ims.repository.StateDetailsRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BreakupCostService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.CirculateToDepartmentService;
import com.webapp.ims.service.DepartmentService;
import com.webapp.ims.service.ExistingProjectDetailsService;
import com.webapp.ims.service.INVInstalledCapacitiesService;
import com.webapp.ims.service.INVOthersService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.MeansOfFinanceService;
import com.webapp.ims.service.NewProjectDetailsService;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;
import com.webapp.ims.service.PrepareAgendaNoteService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;
import com.webapp.ims.service.SendingEmail;
import com.webapp.ims.service.StateDetailsService;
import com.webapp.ims.service.WReturnCUSIDSTATUSBeanService;
import com.webapp.ims.service.impl.AdditionalInterest;
import com.webapp.ims.service.impl.GlobalServiceUtil;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;

@Controller
public class ApplicationDetailsViewController {
	private final Logger logger = LoggerFactory.getLogger(ApplicationDetailsViewController.class);
	private ApplicantDetailsService appDtlService;
	private ApplicantDocumentService appDocService;
	private InvestmentDetailsService invDtlService;
	private PhaseWiseInvestmentDetailsService pwInvDtlService;
	private BreakupCostService brkupService;
	private MeansOfFinanceService mofService;
	private ProposedEmploymentDetailsService emplDtlService;
	private BusinessEntityDetailsService businessEntityDetailsService;
	private BusinessEntityDetailsService businessService;
	private ProprietorDetailsService proprietorService;
	private IncentiveDetailsService incentiveDetailsService;
	private AvailCustmisedDetailsService availCustmisedDetailsService;
	private ProjectService projectService;
	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();
	private String applicantId = null;
	public List<ExistingProjectDetails> existProjList = new ArrayList<>();

	private List<DisReimbrsDepositeGstTable> disReimbrsGSTTableList = new LinkedList<>();

	@Autowired
	DisReimbrsGStTableService disReimbrsGStTableService;
	@Autowired
	private NewProjectDetailsService newProjectService;

	@Autowired
	CirculateToDepartmentService circulateToDepartmentService;

	@Autowired
	ApplicantDetailsRepository applicantDetailsRepository;
	@Autowired
	CirculateToDepartmentRepository circulateToDepartmentRepository;

	@Autowired
	private ExistingProjectDetailsService existProjectService;
	@Autowired
	private StateDetailsRepository stateRepository;

	@Autowired
	AdditionalInterest additionalInterest;
	@Autowired
	public GlobalServiceUtil globalServiceUtil;

	@Autowired
	InvestmentDetailsRepository investRepository;

	@Autowired
	BusinessEntityDocumentRepository beDocRepository;

	@Autowired
	PrepareAgendaNoteService prepareAgendaNoteService;
	@Autowired
	WReturnCUSIDSTATUSBeanService wReturnCUSIDSTATUSBeanService;

	@Autowired
	private PrepareAgendaNotesService prepareAgendaNotesService;
	@Autowired
	DisbursmentRiembrsDepositGSTService disbursmentRiembrsDepositGSTService;

	@Autowired
	private ApplicantDetailsService applicantDetailsService;

	@Autowired
	private TblUsersService loginService;
	@Autowired
	private StateDetailsService stateDetailsService;

	@Autowired
	private ApplicantDocumentService fileStorageService;

	@Autowired
	CapitalInvestDocumentRepository capInvRepos;

	@Autowired
	CapitalInvestmentDocumentService capInvDocService;

	@Autowired
	EmployementDetailsDocumentService emplDetalService;

	@Autowired
	EmploymentDetailsDocumentRepository emplDetilRepos;

	@Autowired
	ReimbrsDepositGSTDocumentRepository reimbrsDocRepos;

	@Autowired
	ReimbrsGSTDocumentService reimGSTDocService;

	@Autowired
	CISDocumentService cisDocService;

	@Autowired
	CISDocumentRepository cisDocRepos;

	@Autowired
	IISDocumentService iisDocService;

	@Autowired
	IISDocumentRepository iisDocRepos;

	@Autowired
	EPFDocumentService epfDocService;

	@Autowired
	EPFDocumentRepository epfRepos;

	@Autowired
	public ExistProjDocRepository existProjDocRepository;
	@Autowired
	public NewProjDocRepository newProjDocRepository;

	@Autowired
	private InvestmentDocumentRepository invDocRepository;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	InvestmentDetailsRepository investmentDetailsRepository;

	@Autowired
	IncentiveDetailsRepository incentiveDetailsRepository;

	@Autowired
	private InvestmentDetailsService investDs;

	@Autowired
	private PhaseWiseInvestmentDetailsService pwInvestDs;

	@Autowired
	private ProposedEmploymentDetailsService proposedEmploymentDetailsService;

	@Autowired
	InvestmentDetailsService investmentDetailsService;

	@Autowired
	DepartmentService departmentService;

	@Autowired
	DPTAgendaRepository dptAgendaRepository;

	@Autowired
	DISPrepareAgendaNotesRepository disPrepareAgendaNoteRepos;

	@Autowired
	MinutesOfMeetingRepository meetingRepos;

	@Autowired
	PrepareAgendaNoteRepository prepareAgendaNoteRepository;

	@Autowired
	DisbursmentCisService disbursmentCisService;
	@Autowired
	DisbursmentIisService disbursmentIisService;

	@Autowired
	DisStampDeautyRepository disStampDeautyRepository;

	@Autowired
	StampDutyService stampDutyService;

	@Autowired
	OtherDetailService otherDetailService;

	@Autowired
	NewProjDisburseRepository newProjDisburseRepository;
	/*
	 * @Autowired CapitalInvestmentDetails capitalInvestmentDetails;
	 */

	@Autowired
	CapitalInvestService capitalInvestService;

	@Autowired
	ProjDisburseRepository projDisburseRepository;

	@Autowired
	DisEPFRepository disEPFRepository;

	@Autowired
	SendingEmail sendingEmail;

	private AtomicInteger atomicInteger = new AtomicInteger();

	@Autowired
	private INVOthersService invOthersService;

	@Autowired
	private INVInstalledCapacitiesService iNVInstalledCapacitiesService;

	@Autowired
	private MeansOfFinanceService meansOfFinanceService;

	private List<AvailCustomisedDetails> availCustomisedDetailsList = new ArrayList<AvailCustomisedDetails>();
	List<NewProjectDetails> newProjList = new ArrayList<>();

	@Autowired
	public ApplicationDetailsViewController(ApplicantDetailsService appDtlService,
			ApplicantDocumentService appDocService, InvestmentDetailsService invDtlService,
			PhaseWiseInvestmentDetailsService pwInvDtlService, BreakupCostService brkupService,
			MeansOfFinanceService mofService, ProposedEmploymentDetailsService emplDtlService,
			BusinessEntityDetailsService businessEntityDetailsService, BusinessEntityDetailsService businessService,
			ProprietorDetailsService proprietorService, IncentiveDetailsService incentiveDetailsService,
			AvailCustmisedDetailsService availCustmisedDetailsService, ProjectService projectService) {
		super();
		this.appDtlService = appDtlService;
		this.appDocService = appDocService;
		this.invDtlService = invDtlService;
		this.pwInvDtlService = pwInvDtlService;
		this.brkupService = brkupService;
		this.mofService = mofService;
		this.emplDtlService = emplDtlService;
		this.businessEntityDetailsService = businessEntityDetailsService;
		this.businessService = businessService;
		this.proprietorService = proprietorService;
		this.incentiveDetailsService = incentiveDetailsService;
		this.availCustmisedDetailsService = availCustmisedDetailsService;
		this.projectService = projectService;
	}

	/**
	 * 
	 */
	public ApplicationDetailsViewController() {
	}

	/**
	 * This method is responsible to fetch InvestmentDetails documents from MongoDB
	 * collection.
	 */
	public void investMongoDBDoc(String investId, Model model) {
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

	public void newProjDocMongoDB(String projId, Model model) {
		NewProjDetailsDocument newProjDoc = newProjDocRepository.findByProjectId(projId);
		model.addAttribute("enclDetProRepFileName", newProjDoc.getFileName());
	}

	/**
	 * This method is responsible to retrieve ProjectDetails' existing project
	 * documents from MongoDB collection.
	 */
	public void existProjDocsFromMongoDB(String projId, Model model) {

		List<ExistProjDetailsDocument> projDocList = existProjDocRepository.getProjDocListByProjectId(projId);
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

	public void businessDocFromMongoDB(String businessEntityId, Model model) {
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
	 * This method is responsible to fetch data from database tables on the basis of
	 * applicant id and renders view page to show the complete form data filled up
	 * by the applicant.
	 */

	String businId = "";
	String projId = "";
	String investId = "";
	String propId = "";
	String isfId = "";

	public void commonViewApplicationDetails(String appId, HttpSession session, Model model) {

		applicantId = appId;
		session.setAttribute("appId", applicantId);

		String appStr = appId.substring(0, appId.length() - 2);
		businId = appStr + "B1";
		projId = appStr + "P1";
		investId = appStr + "I1";
		propId = appStr + "PE1";
		isfId = appStr + "INC1";

		model.addAttribute("concernDepartment", new Department());
		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);
		ApplicantDocument applicantDocument = fileStorageService.getApplicantDocumentByDocAppId(appId);
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
				applicantDetail.setSubmitStatus(false);
			}
			model.addAttribute("applicantDetail", applicantDetail);
		}

		/* Business Entity Details */

		BusinessEntityDetails businessEntityDetails = businessService.getBusinDetById(businId);
		businessDocFromMongoDB(businId, model);
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

			List<ProprietorDetails> proprietorDetailsList = proprietorService.findAllByBusinessId(businId);
			model.addAttribute("ProprietorDetailsList", proprietorDetailsList);

		}

		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);
		// fetch ProjectDetails MonggoDB documents
		if (ProjectDetail.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			existProjDocsFromMongoDB(projId, model);
		}

		else if (ProjectDetail.getNatureOfProject().equalsIgnoreCase("NewProject")) {
			newProjDocMongoDB(projId, model);
		}

		// To retrieve ExistingProjectDetailsList from table on projectId basis
		if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
			existProjList = existProjectService.getExistProjListById(projId);
			model.addAttribute(EXIST_PROJ_LIST, existProjList);
		} else {
			existProjList.clear();
		}

		if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
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

			model.addAttribute("natureOfProject", ProjectDetail.getNatureOfProject());
			model.addAttribute("expansion", ProjectDetail.getExpansion());
			model.addAttribute("diversification", ProjectDetail.getDiversification());

		}

		/* Investment Details */

		InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(investId);
		investMongoDBDoc(investId, model);

		if (invdtlFromDb != null) {

			model.addAttribute("categorytype", invdtlFromDb.getInvIndType());
			model.addAttribute("invFci", invdtlFromDb.getInvFci());
			model.addAttribute("invTotalProjCost", invdtlFromDb.getInvTotalProjCost());
			model.addAttribute("invLandCost", invdtlFromDb.getInvLandCost());
			model.addAttribute("invBuildingCost", invdtlFromDb.getInvBuildingCost());
			model.addAttribute("invPlantAndMachCost", invdtlFromDb.getInvPlantAndMachCost());
			model.addAttribute("invOtherCost", invdtlFromDb.getInvOtherCost());
			model.addAttribute("invCommenceDate", invdtlFromDb.getInvCommenceDate());
			model.addAttribute("propCommProdDate", invdtlFromDb.getPropCommProdDate());
			model.addAttribute("viewInvestDetails", invdtlFromDb);
			model.addAttribute("invotherlist", invOthersService.getAllByINV_ID(investId));
			model.addAttribute("invic", iNVInstalledCapacitiesService.getAllByINV_ID(investId));
			model.addAttribute("momlist", meansOfFinanceService.getMeansOfFinanceList());
			List<PhaseWiseInvestmentDetails> pwInvListDromDb = pwInvestDs
					.getPwInvDetailListById(invdtlFromDb.getInvId());
			if (!pwInvListDromDb.isEmpty()) {
				model.addAttribute("pwApply", "Yes");
			} else {
				model.addAttribute("pwApply", "No");
			}

			model.addAttribute(PW_INVESTMENT_LIST, pwInvListDromDb);
			model.addAttribute(PW_INVESTMENT_DETAILS, new PhaseWiseInvestmentDetails());

		}

		newProjList = newProjectService.getNewProjListById(projId);
		model.addAttribute("newProjList", newProjList);

		/* Proposed Employment Details */

		ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService.getPropEmpById(propId);
		if (proposedEmploymentDetail != null) {

			if (proposedEmploymentDetail != null) {
				List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = proposedEmploymentDetail
						.getSkilledUnSkilledEmployemnt();
				totalSkilledAndUnSkilledEmploment(model, skilledUnSkilledEmployemntslist);
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, skilledUnSkilledEmployemntslist);
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

			try {
				Long isf_Claim_Reim = IncentiveDetail.getISF_Ttl_SGST_Reim() == null ? 0
						: IncentiveDetail.getISF_Ttl_SGST_Reim();
				Long isf_Reim_SCST = IncentiveDetail.getISF_Ttl_Stamp_Duty_EX() == null ? 0
						: IncentiveDetail.getISF_Ttl_Stamp_Duty_EX();
				Long isf_EX_E_Duty_PC = IncentiveDetail.getISF_EX_E_Duty_PC() == null ? 0
						: IncentiveDetail.getISF_EX_E_Duty_PC();
				Long isf_EX_Mandee_Fee = IncentiveDetail.getISF_Ttl_EPF_Reim() == null ? 0
						: IncentiveDetail.getISF_Ttl_EPF_Reim();
				Long isf_Reim_BPLW = IncentiveDetail.getISF_Total_Int_Subsidy() == null ? 0
						: IncentiveDetail.getISF_Total_Int_Subsidy();
				Long isf_Reim_FW = IncentiveDetail.getTotal_Other_Incentive() == null ? 0
						: IncentiveDetail.getTotal_Other_Incentive();
				Long total = isf_Claim_Reim + isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW
						+ isf_Reim_FW;
				model.addAttribute("total", total);
			} catch (Exception e) {
				logger.error("total  exception **** " + e.getMessage());
				e.printStackTrace();
			}

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
				model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList);
			} else {
				model.addAttribute("ISF_Cstm_Inc_Status", "No");
			}

		}

		if (ProjectDetail.getNatureOfProject().equalsIgnoreCase("NewProject")) {
			System.out.println("ppppppp" + ProjectDetail.getNatureOfProject());
			model.addAttribute(EXIST_PROJ_LIST, null);
		}
		if (ProjectDetail.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			model.addAttribute("newProjList", null);
		}

		model.addAttribute("department", new Department());
		model.addAttribute("incentiveDeatilsData", IncentiveDetail);

	}

	@GetMapping(value = "/viewApplicationDetails")
	public ModelAndView viewApplicationDetails(@RequestParam(value = "applicantId", required = false) String applId,
			HttpSession session, Model model) {
		commonViewApplicationDetails(applId, session, model);
		// send to concernDepartment
		Iterable<Department> list = departmentService.getDepartmentList();
		model.addAttribute("concernDepartment", list);
		return new ModelAndView("view_all_application_details");

	}

	@GetMapping(value = "/viewApplicationDetailsDis")
	public ModelAndView viewApplicationDetailsDis(@RequestParam(value = "applicantId", required = false) String applId,
			HttpSession session, Model model) {
		commonViewApplicationDetailsDIS(applId, session, model);
		// send to concernDepartment
		Iterable<Department> list = departmentService.getDepartmentList();
		model.addAttribute("concernDepartment", list);
		return new ModelAndView("/Disbursement/view_all_application_details_dis");

	}

	// Sachin-DIS//

	public void commonViewApplicationDetailsDIS(String appId, HttpSession session, Model model) {

		applicantId = appId;
		session.setAttribute("appId", applicantId);

		String appStr = appId.substring(0, appId.length() - 2);
		businId = appStr + "B1";
		projId = appStr + "P1";
		investId = appStr + "I1";
		propId = appStr + "PE1";
		isfId = appStr + "INC1";
		String capId = appStr + "CI";
		String capCIS = appId + "CIS";
		String capIIS = appId + "IIS";
		String capINVId = appId + "CAPINV";
		String docReimbrsId = appId + "RGST";
		String epfDocId = appId + "EPF";
		String emplDtlId = appId + "EMP";

		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);
		CapitalInvestmentDetails capinv = capitalInvestService.getDetailsByprojDisId(capId);

		NewProjDisbursement newProjDisbursement = newProjDisburseRepository.getDetailsBynewprojApcId(appId);

		ExistProjDisbursement exitstingProj = projDisburseRepository.getDetailsByprojApcId(appId);

		model.addAttribute("newprjLandCost", newProjDisbursement.getNewprojLandCost());
		model.addAttribute("newprjBuildingCost", newProjDisbursement.getNewprojBldgCost());
		model.addAttribute("newprjPlantAndMachCost", newProjDisbursement.getNewprojPlantMachCost());
		model.addAttribute("newprjInfCost", newProjDisbursement.getNewprojInfra());
		// model.addAttribute("newprjInfCost", newProjDisbursement.getNewprojInfra());

		model.addAttribute("exLand", exitstingProj.getProjDisExpandOrDiverse());
		model.addAttribute("exbuild", exitstingProj.getProjDisBldgCost());
		model.addAttribute("projDisConstruct", exitstingProj.getProjDisConstruct());
		model.addAttribute("explant", exitstingProj.getProjDisPlantMachCost());
		model.addAttribute("exinfra", exitstingProj.getProjDisInfra());
		model.addAttribute("ttlAmtExitProj", exitstingProj.getTtlAmtExitProj());
		model.addAttribute("projLandIncrement", exitstingProj.getProjLandIncrement());
		model.addAttribute("projBuildIncrement", exitstingProj.getProjBuildIncrement());
		model.addAttribute("projConstructIncrement", exitstingProj.getProjConstructIncrement());
		model.addAttribute("projPlantIncrement", exitstingProj.getProjPlantIncrement());
		model.addAttribute("projInfraIncrement", exitstingProj.getProjInfraIncrement());
		model.addAttribute("totalAmountOfInvest", exitstingProj.getTotalAmountOfInvest());
		model.addAttribute("totalIncrement", exitstingProj.getTotalIncrement());

		// Get Mongo Doc and Download CapitalInvestmentDetails
		capInvDocService.capInvDocFromMongoDB(capINVId, model);

		DissbursmentReimbrsDepositeGST reimGST = disbursmentRiembrsDepositGSTService.getDetailsBydisAppId(appId);
		Discis cisdetail = disbursmentCisService.getDiscisBydiscisId(capCIS);
		Disiis iisdetail = disbursmentIisService.getDisiisBydisiisId(capIIS);
		// DisStampDeauty = disStampDeautyRepository.getDetailsByStampApcId(appId);
		DisStampDeauty stam = stampDutyService.getDetailsByStampApcId(appId);
		OtherIncentive oth = otherDetailService.getDetailsByOthApcid(appId);
		// Discis cisdetail = disbursmentCisService.getDetailsBydisAppId(appId);

		// for Cost of Project As per DPR 2 column
		if (capinv != null) {

			model.addAttribute("CapInvDPRMFA", capinv.getCapInvDPRMFA());
			model.addAttribute("CapInvDPRTKF", capinv.getCapInvDPRTKF());
			model.addAttribute("CapInvDPRICP", capinv.getCapInvDPRICP());
			model.addAttribute("CapInvDPRPPE", capinv.getCapInvDPRPPE());
			model.addAttribute("CapInvDPRMMC", capinv.getCapInvDPRMMC());

			// for Cost of Project As per Appraisal 3 column
			model.addAttribute("CapInvAppraisalLC", capinv.getCapInvAppraisalLC());
			model.addAttribute("CapInvAppraisalBC", capinv.getCapInvAppraisalBC());
			model.addAttribute("CapInvAppraisalPMC", capinv.getCapInvAppraisalPMC());
			model.addAttribute("CapInvAppraisalMFA", capinv.getCapInvAppraisalMFA());
			model.addAttribute("CapInvAppraisalTKF", capinv.getCapInvAppraisalTKF());
			model.addAttribute("CapInvAppraisalICP", capinv.getCapInvAppraisalICP());
			model.addAttribute("CapInvAppraisalPPE", capinv.getCapInvAppraisalPPE());
			model.addAttribute("CapInvAppraisalMMC", capinv.getCapInvAppraisalMMC());

			// for Before Cut Off Date 4 column
			model.addAttribute("CapInvCutoffDateLC", capinv.getCapInvCutoffDateLC());
			model.addAttribute("CapInvCutoffDateBC", capinv.getCapInvCutoffDateBC());
			model.addAttribute("CapInvCutoffDatePMC", capinv.getCapInvCutoffDatePMC());
			model.addAttribute("CapInvCutoffDateMFA", capinv.getCapInvCutoffDateMFA());
			model.addAttribute("CapInvCutoffDateTKF", capinv.getCapInvCutoffDateTKF());
			model.addAttribute("CapInvCutoffDateICP", capinv.getCapInvCutoffDateICP());
			model.addAttribute("CapInvCutoffDatePPE", capinv.getCapInvCutoffDatePPE());
			model.addAttribute("CapInvCutoffDateMMC", capinv.getCapInvCutoffDateMMC());

			// for Actual Investment (100%) 5 column
			model.addAttribute("CapInvActualInvLC", capinv.getCapInvActualInvLC());
			model.addAttribute("CapInvActualInvBC", capinv.getCapInvActualInvBC());
			model.addAttribute("CapInvActualInvPMC", capinv.getCapInvActualInvPMC());
			model.addAttribute("CapInvActualInvMFA", capinv.getCapInvActualInvMFA());
			model.addAttribute("CapInvActualInvTKF", capinv.getCapInvActualInvTKF());
			model.addAttribute("CapInvActualInvICP", capinv.getCapInvActualInvICP());
			model.addAttribute("CapInvActualInvPPE", capinv.getCapInvActualInvPPE());
			model.addAttribute("CapInvActualInvMMC", capinv.getCapInvActualInvMMC());

			// for Cut Off date to the date of commencement of 6 column
			model.addAttribute("CapInvCommProdLC", capinv.getCapInvCommProdLC());
			model.addAttribute("CapInvCommProdBC", capinv.getCapInvCommProdBC());
			model.addAttribute("CapInvCommProdPMC", capinv.getCapInvCommProdPMC());
			model.addAttribute("CapInvCommProdMFA", capinv.getCapInvCommProdMFA());
			model.addAttribute("CapInvCommProdTKF", capinv.getCapInvCommProdTKF());
			model.addAttribute("CapInvCommProdICP", capinv.getCapInvCommProdICP());
			model.addAttribute("CapInvCommProdPPPE", capinv.getCapInvCommProdPPE());
			model.addAttribute("CapInvCommProdMMC", capinv.getCapInvCommProdMMC());

			// for Additional 10% of actual investment beyond the date of actual of 7 column
			model.addAttribute("CapInvAddlInvLC", capinv.getCapInvAddlInvLC());
			model.addAttribute("CapInvAddlInvBC", capinv.getCapInvAddlInvBC());
			model.addAttribute("CapInvAddlInvPMC", capinv.getCapInvAddlInvPMC());
			model.addAttribute("CapInvAddlInvMFA", capinv.getCapInvAddlInvMFA());
			model.addAttribute("CapInvAddlInvTKF", capinv.getCapInvAddlInvTKF());
			model.addAttribute("CapInvAddlInvICP", capinv.getCapInvAddlInvICP());
			model.addAttribute("CapInvAddlInvPPE", capinv.getCapInvAddlInvPPE());
			model.addAttribute("CapInvAddlInvMMC", capinv.getCapInvAddlInvMMC());

			// for total column
			model.addAttribute("capInvTotalLC", capinv.getCapInvTotalLC());
			model.addAttribute("capInvTotalBC", capinv.getCapInvTotalBC());
			model.addAttribute("capInvTotalPMC", capinv.getCapInvTotalPMC());
			model.addAttribute("capInvTotalMFA", capinv.getCapInvTotalMFA());
			model.addAttribute("capInvTotalTKF", capinv.getCapInvTotalTKF());
			model.addAttribute("capInvTotalICP", capinv.getCapInvTotalICP());
			model.addAttribute("capInvTotalPPE", capinv.getCapInvTotalPPE());
			model.addAttribute("capInvTotalMMC", capinv.getCapInvTotalMMC());

			model.addAttribute("capinv", capinv);
		}

		// for GST incentive
		if (reimGST != null) {
			model.addAttribute("gstComTaxDept", reimGST.getGstComrTaxDept());

			disReimbrsGSTTableList = disReimbrsGStTableService.getListBydisAppId(appId);
			model.addAttribute("disReimbrsGSTTableList", disReimbrsGSTTableList);

			/*
			 * model.addAttribute("FYear", reimGST.getFinancialYear());
			 * model.addAttribute("amtNetSgstQY", reimGST.getAmtNetSgstQYr());
			 * model.addAttribute("amtNetSgst", reimGST.getAmtNetSgst());
			 * model.addAttribute("amtSgstTotal", reimGST.getTtlSgstReim());
			 * model.addAttribute("amtAdmtTaxDeptGst", reimGST.getAmtAdmtTaxDeptGst());
			 * model.addAttribute("eligbAmtDepo", reimGST.getEligbAmtDepo());
			 */
		}
		// for CIS
		if (cisdetail != null) {
			model.addAttribute("bankname", cisdetail.getBankname()); // Name of Banks/Financial Institutions
			model.addAttribute("bankadd", cisdetail.getBankadd()); // Address of Banks/Financial Institutions
			model.addAttribute("totalLoTkn", cisdetail.getTotal());
			model.addAttribute("bankcert", cisdetail.getBankcert());
			model.addAttribute("pnmloan", cisdetail.getPnmloan()); // Amount of loan sanctioned on Investment in Plant &
																	// Machinery
			model.addAttribute("totalIntrs", cisdetail.getTotalint());
			model.addAttribute("roi", cisdetail.getRoi()); // Rate of Interest
			model.addAttribute("sanctiondate", cisdetail.getSanctiondate()); // Date of Sanction
			model.addAttribute("disloan", cisdetail.getDisbursedloan()); // Amount of Loan Disbursed towards Investment
																			// in Plant & Machinery with dates of
																			// Disbursement.
			// Year for which subsidy Applied
			model.addAttribute("Year1", cisdetail.getYearI());
			model.addAttribute("Year2", cisdetail.getYearII());
			model.addAttribute("Year3", cisdetail.getYearIII());
			model.addAttribute("Year4", cisdetail.getYearIV());
			model.addAttribute("Year5", cisdetail.getYearV());

			// Duration/Period
			// From
			model.addAttribute("durationFr1", cisdetail.getDurationPeriodDtFr1());
			model.addAttribute("durationFr2", cisdetail.getDurationPeriodDtFr2());
			model.addAttribute("durationFr3", cisdetail.getDurationPeriodDtFr3());
			model.addAttribute("durationFr4", cisdetail.getDurationPeriodDtFr4());
			model.addAttribute("durationFr5", cisdetail.getDurationPeriodDtFr5());

			// Duration/Period
			// To
			model.addAttribute("durationTo1", cisdetail.getDurationPeriodDtTo1());
			model.addAttribute("durationTo2", cisdetail.getDurationPeriodDtTo2());
			model.addAttribute("durationTo3", cisdetail.getDurationPeriodDtTo3());
			model.addAttribute("durationTo4", cisdetail.getDurationPeriodDtTo4());
			model.addAttribute("durationTo5", cisdetail.getDurationPeriodDtTo5());

			// Amount of Interest Subsidy Applied
			model.addAttribute("FirstYTI", cisdetail.getFirstYTI());
			model.addAttribute("SecondYTI", cisdetail.getSecondYTI());
			model.addAttribute("ThirdYTI", cisdetail.getThirdYTI());
			model.addAttribute("FourthYTI", cisdetail.getFourthYTI());
			model.addAttribute("FifthYTI", cisdetail.getFifthYTI());
			// Principal
			model.addAttribute("FirstYP", cisdetail.getFirstYP());
			model.addAttribute("SecondYP", cisdetail.getSecondYP());
			model.addAttribute("ThirdYP", cisdetail.getThirdYP());
			model.addAttribute("FourthYP", cisdetail.getFourthYP());
			model.addAttribute("FifthYP", cisdetail.getFifthYP());
			// Interest
			model.addAttribute("FirstYI", cisdetail.getFirstYI());
			model.addAttribute("SecondYI", cisdetail.getSecondYI());
			model.addAttribute("ThirdYI", cisdetail.getThirdYI());
			model.addAttribute("FourthYI", cisdetail.getFourthYI());
			model.addAttribute("FifthYI", cisdetail.getFifthYI());

			// Amt Interest Subsidy
			model.addAttribute("FirstYAIS", cisdetail.getFirstYAmtIntSubsidy());
			model.addAttribute("SecondYAIS", cisdetail.getSecondYAmtIntSubsidy());
			model.addAttribute("ThirdYAIS", cisdetail.getThirdYAmtIntSubsidy());
			model.addAttribute("FourthYAIS", cisdetail.getFourthYAmtIntSubsidy());
			model.addAttribute("FifthYAIS", cisdetail.getFifthYAmtIntSubsidy());
		}

		// for IIS
		if (iisdetail != null) {
			model.addAttribute("iibankname", iisdetail.getBankname()); // Name of Banks/Financial Institutions
			model.addAttribute("iisbankadd", iisdetail.getBankadd()); // Address of Banks/Financial Institutions
			model.addAttribute("iistotalLoTkn", iisdetail.getTotal()); // Total Loan Taken (In Rs.)
			model.addAttribute("iisbankcert", iisdetail.getBankcert()); // FI/ Bank certified amount of loan on Infra
																		// available?
			model.addAttribute("iisloan", iisdetail.getIisloan()); // Amount of loan sanctioned forInvestment in
																	// Infrastructure Facilities, as defined

			model.addAttribute("iistotalIntrs", iisdetail.getTotalint()); // Total Interest

			model.addAttribute("iisroi", iisdetail.getRoi()); // Rate of Interest
			model.addAttribute("iissanctiondate", iisdetail.getSanctiondate()); // Date of Sanction
			model.addAttribute("iisdisloan", iisdetail.getDisbursedloan()); // Amount of Loan Disbursed towards
																			// Investment
																			// in Plant & Machinery with dates of
																			// Disbursement.
			// Year for which subsidy Applied
			model.addAttribute("Year1i", iisdetail.getYearI());
			model.addAttribute("Year2i", iisdetail.getYearII());
			model.addAttribute("Year3i", iisdetail.getYearIII());
			model.addAttribute("Year4i", iisdetail.getYearIV());
			model.addAttribute("Year5i", iisdetail.getYearV());

			// Duration/Period
			// From
			model.addAttribute("durationFr1i", iisdetail.getDurationPeriodDtFr1());
			model.addAttribute("durationFr2i", iisdetail.getDurationPeriodDtFr2());
			model.addAttribute("durationFr3i", iisdetail.getDurationPeriodDtFr3());
			model.addAttribute("durationFr4i", iisdetail.getDurationPeriodDtFr4());
			model.addAttribute("durationFr5i", iisdetail.getDurationPeriodDtFr5());

			// Duration/Period
			// From
			model.addAttribute("durationTo1i", iisdetail.getDurationPeriodDtTo1());
			model.addAttribute("durationTo2i", iisdetail.getDurationPeriodDtTo2());
			model.addAttribute("durationTo3i", iisdetail.getDurationPeriodDtTo3());
			model.addAttribute("durationTo4i", iisdetail.getDurationPeriodDtTo4());
			model.addAttribute("durationTo5i", iisdetail.getDurationPeriodDtTo5());

			// Amount of Interest Subsidy Applied
			model.addAttribute("FirstYTIi", iisdetail.getFirstYTI());
			model.addAttribute("SecondYTIi", iisdetail.getSecondYTI());
			model.addAttribute("ThirdYTIi", iisdetail.getThirdYTI());
			model.addAttribute("FourthYTIi", iisdetail.getFourthYTI());
			model.addAttribute("FifthYTIi", iisdetail.getFifthYTI());
			// Principal
			model.addAttribute("FirstYPi", iisdetail.getFirstYP());
			model.addAttribute("SecondYPi", iisdetail.getSecondYP());
			model.addAttribute("ThirdYPi", iisdetail.getThirdYP());
			model.addAttribute("FourthYPi", iisdetail.getFourthYP());
			model.addAttribute("FifthYPi", iisdetail.getFifthYP());
			// Interest
			model.addAttribute("FirstYIi", iisdetail.getFirstYI());
			model.addAttribute("SecondYIi", iisdetail.getSecondYI());
			model.addAttribute("ThirdYIi", iisdetail.getThirdYI());
			model.addAttribute("FourthYIi", iisdetail.getFourthYI());
			model.addAttribute("FifthYIi", iisdetail.getFifthYI());

			// Amt Interest Subsidy
			model.addAttribute("IISFirstYAIS", iisdetail.getFirstYAmtIntSubsidy());
			model.addAttribute("IISSecondYAIS", iisdetail.getSecondYAmtIntSubsidy());
			model.addAttribute("IISThirdYAIS", iisdetail.getThirdYAmtIntSubsidy());
			model.addAttribute("IISFourthYAIS", iisdetail.getFourthYAmtIntSubsidy());
			model.addAttribute("IISFifthYAIS", iisdetail.getFifthYAmtIntSubsidy());
		}
		// for STAMP
		if (stam != null) {
			model.addAttribute("StampAvailAmount", stam.getAdditionalStampAvailAmount());
			model.addAttribute("StampClaimAmount", stam.getAdditionalStampClaimAmount());
			model.addAttribute("ExemptionAvailAmount", stam.getExemptionAvailAmount());
			model.addAttribute("ExemptionClaimAmount", stam.getExemptionClaimAmount());
			model.addAttribute("ReimbursementAvailAmount", stam.getReimbursementAvailAmount());
			model.addAttribute("ReimbursementClaimAmount", stam.getReimbursementClaimAmount());
			model.addAttribute("TotalAvailAmount", stam.getTotalAvailAmount());
			model.addAttribute("TotalClaimAmount", stam.getTotalClaimAmount());

		}

		// for Others
		if (oth != null) {
			model.addAttribute("ReimDissAllowedAvailAmt", oth.getReimDissAllowedAvailAmt());
			model.addAttribute("ExCapitivePowerAmt", oth.getExCapitivePowerAmt());
			model.addAttribute("ExePowerDrawnAmt", oth.getExePowerDrawnAmt());
			model.addAttribute("ExeMandiFreeAmt", oth.getExeMandiFreeAmt());
			model.addAttribute("IndustrialUnitAmt", oth.getIndustrialUnitAmt());

		}

		// Get Mongo Doc and Download Employment Details
		emplDetalService.employmentDetailsDocFromMongoDB(emplDtlId, model);

		// Get Mongo Doc and Download
		reimGSTDocService.reimbrsDocFromMongoDB(docReimbrsId, model);

		// Get Mongo Doc and Download CIS
		cisDocService.cisDocFromMongoDB(capCIS, model);

		// Get Mongo Doc and Download IIS
		iisDocService.iisDocFromMongoDB(capIIS, model);

		model.addAttribute("concernDepartment", new Department());
		// ApplicantDetails applicantDetail =
		// applicantDetailsService.getApplicantDetailsByAppId(appId);
		ApplicantDocument applicantDocument = fileStorageService.getApplicantDocumentByDocAppId(appId);
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

		}

		/* Business Entity Details */

		BusinessEntityDetails businessEntityDetails = businessService.getBusinDetById(businId);
		businessDocFromMongoDB(businId, model);
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
			String statename = stateRepository
					.findByStateCode(Long.valueOf(businessEntityDetails.getBusinessStateName()));
			model.addAttribute("businessStateName", statename);
			model.addAttribute("businessDistrictName", businessEntityDetails.getBusinessDistrictName());
			model.addAttribute("PinCode", businessEntityDetails.getPinCode());
			model.addAttribute("yearEstablishment", businessEntityDetails.getYearEstablishment());
			model.addAttribute("gstin", businessEntityDetails.getGstin());
			model.addAttribute("cin", businessEntityDetails.getCin());
			model.addAttribute("companyPanNo", businessEntityDetails.getCompanyPanNo());

			List<ProprietorDetails> proprietorDetailsList = proprietorService.findAllByBusinessId(businId);
			model.addAttribute("ProprietorDetailsList", proprietorDetailsList);

		}

		ProjectDetails ProjectDetail = projectService.getProjDetById(projId);
		// fetch ProjectDetails MonggoDB documents
		if (ProjectDetail.getNatureOfProject().equalsIgnoreCase("ExistingProject")) {
			existProjDocsFromMongoDB(projId, model);
		}

		else if (ProjectDetail.getNatureOfProject().equalsIgnoreCase("NewProject")) {
			newProjDocMongoDB(projId, model);
		}

		// To retrieve ExistingProjectDetailsList from table on projectId basis
		if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
			existProjList = existProjectService.getExistProjListById(projId);
			model.addAttribute(EXIST_PROJ_LIST, existProjList);
		} else {
			existProjList.clear();
		}

		if (ProjectDetail != null && ProjectDetail.getId() != null && !ProjectDetail.getId().isEmpty()) {
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

			model.addAttribute("natureOfProject", ProjectDetail.getNatureOfProject());
			model.addAttribute("expansion", ProjectDetail.getExpansion());
			model.addAttribute("diversification", ProjectDetail.getDiversification());

		}

		/* Investment Details */

		InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(investId);
		investMongoDBDoc(investId, model);

		if (invdtlFromDb != null) {

			model.addAttribute("categorytype", invdtlFromDb.getInvIndType());
			model.addAttribute("invFci", invdtlFromDb.getInvFci());
			model.addAttribute("invTotalProjCost", invdtlFromDb.getInvTotalProjCost());
			model.addAttribute("invLandCost", invdtlFromDb.getInvLandCost());
			model.addAttribute("invBuildingCost", invdtlFromDb.getInvBuildingCost());
			model.addAttribute("invPlantAndMachCost", invdtlFromDb.getInvPlantAndMachCost());
			model.addAttribute("invOtherCost", invdtlFromDb.getInvOtherCost());
			model.addAttribute("invCommenceDate", invdtlFromDb.getInvCommenceDate());
			model.addAttribute("propCommProdDate", invdtlFromDb.getPropCommProdDate());
			model.addAttribute("viewInvestDetails", invdtlFromDb);

			List<PhaseWiseInvestmentDetails> pwInvListDromDb = pwInvestDs
					.getPwInvDetailListById(invdtlFromDb.getInvId());
			if (!pwInvListDromDb.isEmpty()) {
				model.addAttribute("pwApply", "Yes");
			} else {
				model.addAttribute("pwApply", "No");
			}

			model.addAttribute(PW_INVESTMENT_LIST, pwInvListDromDb);
			model.addAttribute(PW_INVESTMENT_DETAILS, new PhaseWiseInvestmentDetails());

		}

		/* Proposed Employment Details */

		ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService.getPropEmpById(propId);
		if (proposedEmploymentDetail != null) {

			if (proposedEmploymentDetail != null) {
				List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = proposedEmploymentDetail
						.getSkilledUnSkilledEmployemnt();
				Long bpl = (long) 0;
				Long sc = (long) 0;
				Long st = (long) 0;
				Long femaleEmp = (long) 0;

				for (SkilledUnSkilledEmployemnt count : skilledUnSkilledEmployemntslist) {
					if (count.getNumberOfBpl() != null)
						bpl += count.getNumberOfBpl();
					sc += count.getNumberOfSc();
					st += count.getNumberOfSt();
					femaleEmp += count.getNumberOfFemaleEmployees();

				}
				model.addAttribute("totalBPL", bpl);
				model.addAttribute("totalSC", sc);
				model.addAttribute("totalSt", st);
				model.addAttribute("totalFemaleEmp", femaleEmp);
				totalSkilledAndUnSkilledEmploment(model, skilledUnSkilledEmployemntslist);
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, skilledUnSkilledEmployemntslist);
			}
		}

		// Get Mongo Doc and Download EPF Reimbrs
		epfDocService.epfDocFromMongoDB(epfDocId, model);

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

			try {
				Long isf_Claim_Reim = IncentiveDetail.getISF_Ttl_SGST_Reim() == null ? 0
						: IncentiveDetail.getISF_Ttl_SGST_Reim();
				Long isf_Reim_SCST = IncentiveDetail.getISF_Ttl_Stamp_Duty_EX() == null ? 0
						: IncentiveDetail.getISF_Ttl_Stamp_Duty_EX();
				Long isf_EX_E_Duty_PC = IncentiveDetail.getISF_EX_E_Duty_PC() == null ? 0
						: IncentiveDetail.getISF_EX_E_Duty_PC();
				Long isf_EX_Mandee_Fee = IncentiveDetail.getISF_Ttl_EPF_Reim() == null ? 0
						: IncentiveDetail.getISF_Ttl_EPF_Reim();
				Long isf_Reim_BPLW = IncentiveDetail.getISF_Total_Int_Subsidy() == null ? 0
						: IncentiveDetail.getISF_Total_Int_Subsidy();
				Long isf_Reim_FW = IncentiveDetail.getTotal_Other_Incentive() == null ? 0
						: IncentiveDetail.getTotal_Other_Incentive();
				Long total = isf_Claim_Reim + isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW
						+ isf_Reim_FW;
				model.addAttribute("total", total);
			} catch (Exception e) {
				logger.error("total  exception **** " + e.getMessage());
				e.printStackTrace();
			}

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
				model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList);
			} else {
				model.addAttribute("ISF_Cstm_Inc_Status", "No");
			}

		}

		model.addAttribute("department", new Department());
		model.addAttribute("incentiveDeatilsData", IncentiveDetail);

		/* EMPLOYEES PROVIDENT FUND REIMBURSEMENT */

		Disepfriem emp = disEPFRepository.getDetailsByappId(appId);
		if (emp != null) {
			model.addAttribute("unskill", emp.getUnskillemp());
			model.addAttribute("epfreim", emp.getEpfReim());
		}
	}

	@GetMapping(value = "/viewAndEvaluateNodalDept")
	public ModelAndView nodalDeptApplicationsForLoc(Model model) {
		List<Object> objectList = investRepository.getAllDetailsByAppId();
		model.addAttribute("objectList", objectList);
		return new ModelAndView("view-incentive-applications-head-department");
	}

	@GetMapping(value = "/viewNodalDeptHeadApplications")
	public ModelAndView viewNodalDeptHeadDashboard() {

		return new ModelAndView("select-policy-head-department");
	}

	@GetMapping(value = "/viewNodalDeptHeadApplicationDetails")
	public String viewNodalDeptHeadApplicationDetails(
			@RequestParam(value = "applicantId", required = false) String applId, HttpSession session, Model model) {
		commonViewApplicationDetails(applId, session, model);
		return "view-all-application-details-head-department";
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

	@GetMapping(value = "/viewApplicationDetailsSME")
	public ModelAndView viewApplicationDetailsSME(@RequestParam(value = "applicantId", required = false) String applId,
			HttpSession session, Model model) {

		String appStr = applId.substring(0, 14);
		String businId = appStr + "B1";
		String projId = appStr + "P1";
		String investId = appStr + "I1";
		String propId = appStr + "PE1";
		String isfId = appStr + "INC1";

		session.setAttribute("appId", applicantId);

		model.addAttribute("concernDepartment", new Department());
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

		/* Business Entity Details */

		BusinessEntityDetails businessEntityDetails = businessService.getBusinDetById(businId);
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
			List<ProprietorDetails> proprietorDetailsList = proprietorService.findAllByBusinessId(businId);
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
			model.addAttribute("enclDetProRepFileName", ProjectDetail.getEnclDetProRepFileName());
			model.addAttribute("caCertificateFileName", ProjectDetail.getCaCertificateFileName());
			model.addAttribute("charatEngFileName", ProjectDetail.getCharatEngFileName());
		}

		/* Investment Details */

		InvestmentDetails invdtlFromDb = investDs.getInvestmentDetailsById(investId);
		if (invdtlFromDb != null) {
			model.addAttribute("categorytype", invdtlFromDb.getInvIndType());
			model.addAttribute("invFci", invdtlFromDb.getInvFci());
			model.addAttribute("invTotalProjCost", invdtlFromDb.getInvTotalProjCost());
			model.addAttribute("invLandCost", invdtlFromDb.getInvLandCost());
			model.addAttribute("invBuildingCost", invdtlFromDb.getInvBuildingCost());
			model.addAttribute("invPlantAndMachCost", invdtlFromDb.getInvPlantAndMachCost());
			model.addAttribute("invOtherCost", invdtlFromDb.getInvOtherCost());
			model.addAttribute("invCommenceDate", invdtlFromDb.getInvCommenceDate());
			model.addAttribute("propCommProdDate", invdtlFromDb.getPropCommProdDate());
			model.addAttribute("invFileName", invdtlFromDb.getInvFilename());
			List<PhaseWiseInvestmentDetails> pwInvListDromDb = pwInvestDs
					.getPwInvDetailListById(invdtlFromDb.getInvId());
			if (pwInvListDromDb.size() > 0) {
				model.addAttribute("pwApply", "Yes");
			} else {
				model.addAttribute("pwApply", "No");
			}

			model.addAttribute(PW_INVESTMENT_LIST, pwInvListDromDb);
		}

		/* Proposed Employment Details */

		ProposedEmploymentDetails proposedEmploymentDetail = proposedEmploymentDetailsService.getPropEmpById(propId);
		if (proposedEmploymentDetail != null) {

			if (proposedEmploymentDetail != null) {
				List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = proposedEmploymentDetail
						.getSkilledUnSkilledEmployemnt();
				totalSkilledAndUnSkilledEmploment(model, skilledUnSkilledEmployemntslist);
				model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, skilledUnSkilledEmployemntslist);
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
			model.addAttribute("department", new Department());
			// model.addAttribute("aggregatequantumbenifit", aggregatequantumbenifit);
			model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList);

		}
		session.setAttribute("appId", applId);
		return new ModelAndView("view_all_application_detailsSME");

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

	@GetMapping(value = "/applicationForLoc")
	public String applicationForLoc(ModelMap model, HttpSession session) {
		logger.debug("Applications For LOC");

		List<String[]> finalList = new ArrayList<>();

		List<Tuple> list = investRepository.getAllDetailsByAppIdTuple();
		for (Tuple tuple : list) {
			String[] str = new String[10];
			str[0] = tuple.get(0).toString();
			str[1] = tuple.get(1).toString();
			str[2] = tuple.get(2).toString();
			str[3] = tuple.get(3).toString();
			str[4] = tuple.get(4).toString();
			str[5] = tuple.get(5).toString();
			String temp = dptAgendaRepository.getStatusByDptActId(tuple.get(0).toString());
			str[6] = temp == null ? "Pending for Evaluation" : temp;
			finalList.add(str);
		}
		model.addAttribute("investmentDetailsList", finalList);

		List<String[]> finalListdis = new ArrayList<>();

		List<Tuple> listdis = investRepository.getAllDetailsByAppIdTupleDis();
		for (Tuple tuple : listdis) {
			String[] str = new String[10];
			str[0] = tuple.get(0).toString();
			str[1] = tuple.get(1).toString();
			str[2] = tuple.get(2).toString();
			str[3] = tuple.get(3).toString();
			str[4] = tuple.get(4).toString();
			str[5] = tuple.get(5).toString();
			String temp = disPrepareAgendaNoteRepos.findStatusByappliId(tuple.get(0).toString());
			str[6] = temp == null ? "Pending for Evaluation" : temp;
			finalListdis.add(str);
		}
		model.addAttribute("investmentDetailsListdis", finalListdis);

		return "applicationsForLoc";
	}

	@GetMapping(value = "/applicationForLocSME")
	public String applicationForLocSME(ModelMap model, HttpSession session) {
		logger.debug("Applications SME For LOC");

		List<Object> investmentDetailsList = investRepository.getAllDetailsSMEByAppId();

		model.addAttribute("investmentDetailsList", investmentDetailsList);

		return "applicationsForLocSME";
	}

	@GetMapping(value = "/viewAndEvaluate")
	public String viewAndEvaluate() {
		logger.debug("View And Evaluate Policy");
		return "viewAndEvaluate";
	}

	@GetMapping(value = "/viewAndEvaluateSME")
	public String viewAndEvaluateSME() {
		logger.debug("View And Evaluate SME Policy");
		return "viewAndEvaluateSME";
	}

	@GetMapping(value = "/getFacilitiesRelief")
	public ModelAndView getFacilitiesRelief(ModelMap model) {

		String isfId = incentiveDetailsService.getIsfIdByApplId(applicantId);

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
			incentiveSpecificDetails.setIsfCustIncDoc(IncentiveDetail.getIsfCustIncDocName().getBytes());

			incentiveSpecificDetails.setOthAddRequest1(IncentiveDetail.getOthAddRequest1());

			incentiveSpecificDetails.setId(IncentiveDetail.getId());

			try {
				Long isf_Claim_Reim = incentiveSpecificDetails.getISF_Claim_Reim() == null ? 0
						: incentiveSpecificDetails.getISF_Claim_Reim();
				Long isf_Reim_SCST = incentiveSpecificDetails.getISF_Reim_SCST() == null ? 0
						: incentiveSpecificDetails.getISF_Reim_SCST();
				Long isf_EX_E_Duty_PC = incentiveSpecificDetails.getISF_EX_E_Duty_PC() == null ? 0
						: incentiveSpecificDetails.getISF_EX_E_Duty_PC();
				Long isf_EX_Mandee_Fee = incentiveSpecificDetails.getISF_EX_Mandee_Fee() == null ? 0
						: incentiveSpecificDetails.getISF_EX_Mandee_Fee();
				Long isf_Reim_BPLW = incentiveSpecificDetails.getISF_Reim_BPLW() == null ? 0
						: incentiveSpecificDetails.getISF_Reim_BPLW();
				Long isf_Reim_FW = incentiveSpecificDetails.getISF_Reim_FW() == null ? 0
						: incentiveSpecificDetails.getISF_Reim_FW();
				Long total = isf_Claim_Reim + isf_Reim_SCST + isf_EX_E_Duty_PC + isf_EX_Mandee_Fee + isf_Reim_BPLW
						+ isf_Reim_FW;
				model.addAttribute("total", total);
			} catch (Exception e) {
				logger.error("total  exception **** " + e.getMessage());
				e.printStackTrace();
			}
		}
		model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);
		return new ModelAndView("facilities-reliefs-sought");

	}

	@GetMapping(value = "/generateLoc")
	public String generateLoc(ModelMap model, HttpSession session) {
		logger.debug("generateLoc For LOC");

		String userid = (String) session.getAttribute("userId");

		List<PrepareAgendaNotes> prepareAgendaNotesMagaLists = prepareAgendaNoteRepository
				.AllPrepareAgendaNotebyStatus();

		List<PrepareAgendaNotes> prepareAgendaNotesLargeLists = prepareAgendaNoteRepository
				.getPrepareAgendaNotebyStatus();

		model.addAttribute("prepareAgendaNotesMagaLists", prepareAgendaNotesMagaLists);
		model.addAttribute("prepareAgendaNotesLargeLists", prepareAgendaNotesLargeLists);
		// ConcernDepartment concernDepartment = new ConcernDepartment();
		model.addAttribute("CirculateToDepartment", new CirculateToDepartment());
		model.addAttribute("department", new Department());
		Iterable<Department> list = departmentService.getDepartmentList();
		model.addAttribute("concernDepartment", list);
		return "generateLoc";
	}

	@RequestMapping(value = "/savegenerateLoc", method = RequestMethod.GET)
	public ModelAndView savegenerateLoc(Model model, HttpSession session,
			@RequestParam(value = "applicantId", required = false) String applicantId, HttpServletResponse response) {

		// prepareAgendaNotesService.generateLocReport(applicantId);
		byte[] output = prepareAgendaNotesService.createMultipleReport(applicantId);

		PrepareAgendaNotes prepareAgendaNotes = new PrepareAgendaNotes();
		String unitid = applicantId.substring(10, applicantId.length() - 2);
		String locno = "UPLOC" + GlobalServiceUtil.getRandomNumberString() + unitid;

		if (applicantId != null && !applicantId.isEmpty()) {
			PrepareAgendaNotes prepareAgendaNote = prepareAgendaNoteService.getPrepareByAppliId(applicantId);
			prepareAgendaNotes.setAppliId(prepareAgendaNote.getAppliId());
			prepareAgendaNotes.setCompanyName(prepareAgendaNote.getCompanyName());
			prepareAgendaNotes.setId(prepareAgendaNote.getId());
			prepareAgendaNotes.setInvestment(prepareAgendaNote.getInvestment());
			prepareAgendaNotes.setCategoryIndsType(prepareAgendaNote.getCategoryIndsType());
			prepareAgendaNotes.setNotes(prepareAgendaNote.getNotes());
			prepareAgendaNotes.setCreatedBY(prepareAgendaNote.getCreatedBY());
			prepareAgendaNotes.setCreateDate(prepareAgendaNote.getCreateDate());
			prepareAgendaNotes.setAcsapprovalDate(prepareAgendaNote.getAcsapprovalDate());
			prepareAgendaNotes.setApprovalDate(prepareAgendaNote.getApprovalDate());
			prepareAgendaNotes.setSubmissionDate(prepareAgendaNote.getSubmissionDate());
			prepareAgendaNotes.setDistrict(prepareAgendaNote.getDistrict());
			prepareAgendaNotes.setRegion(prepareAgendaNote.getRegion());
			prepareAgendaNotes.setStatus("LOC Generated");
			prepareAgendaNotes.setLocNumber(locno);
			prepareAgendaNotes.setModifyDate(new Timestamp(System.currentTimeMillis()));
			prepareAgendaNotes.setUserId(prepareAgendaNote.getUserId());

			prepareAgendaNotes.setComments(prepareAgendaNote.getComments());
			prepareAgendaNotes.setPkupScrutinyDetail(prepareAgendaNote.getPkupScrutinyDetail());
			prepareAgendaNotes.setMdScrutinyDetail(prepareAgendaNote.getMdScrutinyDetail());
			prepareAgendaNotes.setAcsScrutinyDetail(prepareAgendaNote.getAcsScrutinyDetail());
			prepareAgendaNotes.setPkupFilename(prepareAgendaNote.getPkupFilename());
			prepareAgendaNotes.setPkupFiledata(prepareAgendaNote.getPkupFiledata());
			prepareAgendaNotes.setAppliacntEmailId(prepareAgendaNote.getAppliacntEmailId());

			prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNotes);
		}
		downloadReport("LocReport.pdf", output, response);

		model.addAttribute("msg", "Succefully Complete Agenda Notes");

		return new ModelAndView("redirect:/generateLocEvaluation?applicantId=" + applicantId);
	}

	@GetMapping(value = "/savegenerateLocLarge")
	public ModelAndView savegenerateLocLarge(Model model, HttpSession session,
			@RequestParam(value = "applicantId", required = false) String applicantId) {

		String unitid = applicantId.substring(10, applicantId.length() - 2);
		String locno = "UPLOC" + "54" + GlobalServiceUtil.getRandomNumberString() + unitid;

		PrepareAgendaNotes prepareAgendaNotes = new PrepareAgendaNotes();
		PrepareAgendaNotes prepareAgendaNote = prepareAgendaNoteService.getPrepareByAppliId(applicantId);
		prepareAgendaNotes.setAppliId(prepareAgendaNote.getAppliId());
		prepareAgendaNotes.setCompanyName(prepareAgendaNote.getCompanyName());
		prepareAgendaNotes.setId(prepareAgendaNote.getId());
		prepareAgendaNotes.setInvestment(prepareAgendaNote.getInvestment());
		prepareAgendaNotes.setCategoryIndsType(prepareAgendaNote.getCategoryIndsType());
		prepareAgendaNotes.setNotes(prepareAgendaNote.getNotes());
		prepareAgendaNotes.setCreatedBY(prepareAgendaNote.getCreatedBY());
		prepareAgendaNotes.setCreateDate(prepareAgendaNote.getCreateDate());
		prepareAgendaNotes.setAcsapprovalDate(prepareAgendaNote.getAcsapprovalDate());
		prepareAgendaNotes.setApprovalDate(prepareAgendaNote.getApprovalDate());
		prepareAgendaNotes.setSubmissionDate(prepareAgendaNote.getSubmissionDate());
		prepareAgendaNotes.setDistrict(prepareAgendaNote.getDistrict());
		prepareAgendaNotes.setRegion(prepareAgendaNote.getRegion());
		prepareAgendaNotes.setStatus("LOC Generated");
		prepareAgendaNotes.setUserId(prepareAgendaNote.getUserId());
		prepareAgendaNotes.setLocNumber(locno);
		prepareAgendaNotes.setModifyDate(new Timestamp(System.currentTimeMillis()));

		prepareAgendaNotes.setComments(prepareAgendaNote.getComments());
		prepareAgendaNotes.setPkupScrutinyDetail(prepareAgendaNote.getPkupScrutinyDetail());
		prepareAgendaNotes.setMdScrutinyDetail(prepareAgendaNote.getMdScrutinyDetail());
		prepareAgendaNotes.setAcsScrutinyDetail(prepareAgendaNote.getAcsScrutinyDetail());
		prepareAgendaNotes.setPkupFilename(prepareAgendaNote.getPkupFilename());
		prepareAgendaNotes.setPkupFiledata(prepareAgendaNote.getPkupFiledata());

		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNotes);

		model.addAttribute("msg", "Succefully Complete Agenda Notes");

		return new ModelAndView("redirect:/generateLocEvaluation?applicantId=" + applicantId);
	}

	@GetMapping(value = "/rejectLoc")
	public ModelAndView saverejectLoc(Model model, HttpSession session,
			@RequestParam(value = "applicantId", required = false) String applicantId) {

		PrepareAgendaNotes prepareAgendaNotes = new PrepareAgendaNotes();
		String appId = (String) session.getAttribute("appId");
		PrepareAgendaNotes prepareAgendaNote = prepareAgendaNoteService.getPrepareByAppliId(applicantId);
		prepareAgendaNotes.setAppliId(prepareAgendaNote.getAppliId());
		prepareAgendaNotes.setCompanyName(prepareAgendaNote.getCompanyName());
		prepareAgendaNotes.setId(prepareAgendaNote.getId());
		prepareAgendaNotes.setInvestment(prepareAgendaNote.getInvestment());
		prepareAgendaNotes.setCategoryIndsType(prepareAgendaNote.getCategoryIndsType());
		prepareAgendaNotes.setNotes(prepareAgendaNote.getNotes());
		prepareAgendaNotes.setCreatedBY(prepareAgendaNote.getCreatedBY());
		prepareAgendaNotes.setDistrict(prepareAgendaNote.getDistrict());
		prepareAgendaNotes.setRegion(prepareAgendaNote.getRegion());
		prepareAgendaNotes.setStatus("LOC Rejected");
		prepareAgendaNotes.setUserId(prepareAgendaNote.getUserId());

		prepareAgendaNotes.setComments(prepareAgendaNote.getComments());
		prepareAgendaNotes.setPkupScrutinyDetail(prepareAgendaNote.getPkupScrutinyDetail());
		prepareAgendaNotes.setMdScrutinyDetail(prepareAgendaNote.getMdScrutinyDetail());
		prepareAgendaNotes.setAcsScrutinyDetail(prepareAgendaNote.getAcsScrutinyDetail());
		prepareAgendaNotes.setPkupFilename(prepareAgendaNote.getPkupFilename());
		prepareAgendaNotes.setPkupFiledata(prepareAgendaNote.getPkupFiledata());

		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNotes);

		model.addAttribute("msg", "Succefully Reject Notes");
		ApplicantDetails applicantDetails = applicantDetailsRepository.getApplicantDetailsByAppId(appId);
		if (applicantDetails != null) {
			applicantDetails.setStatusCode("07");
			applicantDetails.setRemarks("Application Rejected during LOC Generation");

			wReturnCUSIDSTATUSBeanService.WReturn_CUSID_STATUS(applicantDetails);
		}

		return new ModelAndView("redirect:/generateLoc");
	}

	@GetMapping(value = "/deferApplcation")
	public ModelAndView deferApplcation(Model model, HttpSession session,
			@RequestParam(value = "applicantId", required = false) String applicantId) {

		PrepareAgendaNotes prepareAgendaNotes = new PrepareAgendaNotes();
		PrepareAgendaNotes prepareAgendaNote = prepareAgendaNoteService.getPrepareByAppliId(applicantId);
		prepareAgendaNotes.setAppliId(prepareAgendaNote.getAppliId());
		prepareAgendaNotes.setCompanyName(prepareAgendaNote.getCompanyName());
		prepareAgendaNotes.setId(prepareAgendaNote.getId());
		prepareAgendaNotes.setInvestment(prepareAgendaNote.getInvestment());
		prepareAgendaNotes.setCategoryIndsType(prepareAgendaNote.getCategoryIndsType());
		prepareAgendaNotes.setNotes(prepareAgendaNote.getNotes());
		prepareAgendaNotes.setCreatedBY(prepareAgendaNote.getCreatedBY());
		prepareAgendaNotes.setDistrict(prepareAgendaNote.getDistrict());
		prepareAgendaNotes.setRegion(prepareAgendaNote.getRegion());
		prepareAgendaNotes.setStatus("Application Deferred");
		prepareAgendaNotes.setUserId(prepareAgendaNote.getUserId());

		prepareAgendaNotes.setComments(prepareAgendaNote.getComments());
		prepareAgendaNotes.setPkupScrutinyDetail(prepareAgendaNote.getPkupScrutinyDetail());
		prepareAgendaNotes.setMdScrutinyDetail(prepareAgendaNote.getMdScrutinyDetail());
		prepareAgendaNotes.setAcsScrutinyDetail(prepareAgendaNote.getAcsScrutinyDetail());
		prepareAgendaNotes.setPkupFilename(prepareAgendaNote.getPkupFilename());
		prepareAgendaNotes.setPkupFiledata(prepareAgendaNote.getPkupFiledata());

		prepareAgendaNotes.setSubmissionDate(prepareAgendaNote.getSubmissionDate());
		prepareAgendaNotes.setApprovalDate(prepareAgendaNote.getApprovalDate());
		prepareAgendaNotes.setAcsapprovalDate(prepareAgendaNote.getAcsapprovalDate());
		prepareAgendaNotes.setAppliacntEmailId(prepareAgendaNote.getAppliacntEmailId());

		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNotes);

		model.addAttribute("msg", "Succefully Reject Notes");

		return new ModelAndView("redirect:/generateLoc");
	}

	@GetMapping(value = "/generateLocEvaluation")
	public ModelAndView generateLocEvaluation(@RequestParam(value = "applicantId", required = false) String applId,
			HttpSession session, Model model) {
		String investId = null;
		String propEmplId = null;
		investId = invDtlService.getInvDetailIdByapplId(applId);
		propEmplId = emplDtlService.getPropEmplIdByapplId(applId);
		applicantId = applId;
		session.setAttribute("appId", applicantId);
		try {
			// Authorised Signatory Details view
			try {
				ApplicantDetails appDtlFromDb = appDtlService.getApplicantDetailsByAppId(applId);
				ApplicantDocument applDoc = null;
				if (appDocService.getApplicantDocumentByDocAppId(applId) != null) {
					applDoc = appDocService.getApplicantDocumentByDocAppId(applId);
				}

				if (appDtlFromDb != null) {
					if (applDoc != null) {
						try {
							byte[] encodeBase64 = Base64.encodeBase64(applDoc.getData());
							String base64Encoded = null;
							if (applDoc.getFileName() != null && applDoc.getFileName() != ""
									&& !applDoc.getFileName().isEmpty()) {
								base64Encoded = new String(encodeBase64, "UTF-8");
							}
							appDtlFromDb.setBase64imageFile(base64Encoded);
							appDtlFromDb.setFileName(applDoc.getFileName());
						} catch (UnsupportedEncodingException e) {
							e.printStackTrace();
						}
					}
				}
				model.addAttribute("viewSignatoryDetail", appDtlFromDb);
			} catch (Exception e) {
				logger.error("viewSignatoryDetail exception &&&&& " + e.getMessage());
				e.printStackTrace();
			}

			/* Business Entity Details */

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

			/* Project Details */

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
				model.addAttribute("newProject", ProjectDetail.getNewProject());
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

			// Investment Details view
			try {

				InvestmentDetails invDetailFromDb = invDtlService.getInvestmentDetailsById(investId);
				if (invDetailFromDb != null) {
					if ((!invDetailFromDb.getApplId().isEmpty() && invDetailFromDb.getApplId() != null)) {
						List<InvBreakupCost> brkupViewList = brkupService.getInvBreakupCostListById(investId);
						List<InvMeansOfFinance> mofViewList = mofService.getInvMeansOfFinanceListById(investId);
						List<PhaseWiseInvestmentDetails> pwInvViewList = pwInvDtlService
								.getPwInvDetailListById(investId);

						int sum = 0;

						// Loop through the array to calculate sum of elements
						for (int i = 0; i < 1; i++) {
							for (PhaseWiseInvestmentDetails phaseWiseInvestmentDetails : pwInvViewList) {
								Long pwFci = phaseWiseInvestmentDetails.getPwFci();
								sum = (int) (sum + pwFci);
								model.addAttribute("PWFCI", sum);
								model.addAttribute("PWpropsdtcoprd", phaseWiseInvestmentDetails.getPwPropProductDate());
							}
						}

						String categorytype = invDetailFromDb.getInvIndType();
						Long pnm = invDetailFromDb.getInvPlantAndMachCost();
						Long fci = invDetailFromDb.getInvFci();

						model.addAttribute("pnm", pnm);
						model.addAttribute("fci", fci);
						Float fci1 = (float) (fci / 10000000);

						model.addAttribute("fci1", fci1);
						model.addAttribute("categorytype", categorytype);

						model.addAttribute("brkupList", brkupViewList);
						model.addAttribute("mofList", mofViewList);
						model.addAttribute(PW_INVESTMENT_LIST, pwInvViewList);
						model.addAttribute("viewInvestDetails", invDetailFromDb);
						model.addAttribute(PW_INVESTMENT_DETAILS, new PhaseWiseInvestmentDetails());

					}

				} else {
					model.addAttribute("viewInvestDetails", new InvestmentDetails());
					model.addAttribute(PW_INVESTMENT_DETAILS, new PhaseWiseInvestmentDetails());
				}
			} catch (Exception e) {
				logger.error("InvestmentDetails exception #### " + e.getMessage());
				e.printStackTrace();
			}

			// Proposed Employment Details view
			try {
				if (!propEmplId.isEmpty()) {
					ProposedEmploymentDetails emplDetail = emplDtlService.getPropEmpById(propEmplId);
					if (emplDetail != null) {
						List<SkilledUnSkilledEmployemnt> skilledUnSkilledEmployemntslist = emplDetail
								.getSkilledUnSkilledEmployemnt();
						model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, emplDetail);
						skillUnskillEmplList.clear();
						skillUnskillEmplList.addAll(skilledUnSkilledEmployemntslist);
						model.addAttribute("skilledUnSkilledEmployemntList", skillUnskillEmplList);
						totalSkilledAndUnSkilledEmploment(model);
						model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, new ProposedEmploymentDetails());
						model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

					} else {
						skillUnskillEmplList.clear();
						model.addAttribute(PROPOSED_EMPLOYMENT_DETAILS, new ProposedEmploymentDetails());
						model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());
					}
				}

			} catch (Exception e) {
				logger.error("PropEmplDetails exception @@@@@ " + e.getMessage());
				e.printStackTrace();

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

				model.addAttribute("createdate", IncentiveDetail.getCreateDate());
				List<AvailCustomisedDetails> availCustomisedDetailsList = availCustmisedDetailsService
						.findAllByAvaCustId(IncentiveDetail.getId());
				if (availCustomisedDetailsList.size() > 0) {
					model.addAttribute("ISF_Cstm_Inc_Status", "Yes");
				} else {
					model.addAttribute("ISF_Cstm_Inc_Status", "No");
				}
				model.addAttribute("availCustomisedDetailsList", availCustomisedDetailsList);

				Long tsgst = IncentiveDetail.getISF_Ttl_SGST_Reim() == null ? 0
						: IncentiveDetail.getISF_Ttl_SGST_Reim();
				Long tstam = IncentiveDetail.getISF_Stamp_Duty_EX() == null ? 0
						: IncentiveDetail.getISF_Stamp_Duty_EX();
				Long taddist = IncentiveDetail.getISF_Additonal_Stamp_Duty_EX() == null ? 0
						: IncentiveDetail.getISF_Additonal_Stamp_Duty_EX();
				Long tepf = IncentiveDetail.getISF_Ttl_EPF_Reim() == null ? 0 : IncentiveDetail.getISF_Ttl_EPF_Reim();
				Long tis = IncentiveDetail.getISF_Total_Int_Subsidy() == null ? 0
						: IncentiveDetail.getISF_Total_Int_Subsidy();
				Long toth = IncentiveDetail.getTotal_Other_Incentive() == null ? 0
						: IncentiveDetail.getTotal_Other_Incentive();

				long aggregatequantumbenifit = tsgst + tstam + taddist + tepf + tis + toth;

				model.addAttribute("aggregatequantumbenifit", aggregatequantumbenifit);
			}

		} catch (Exception e) {
			logger.error(" exception **** " + e.getMessage());
			e.printStackTrace();
		}

		// Go's No.
		List<MinutesOfMeeting> meeting = meetingRepos.getBygosAppID(applId);

		for (MinutesOfMeeting mom1 : meeting) {
			if (mom1.getGosNo() != null && !mom1.getGosNo().isEmpty())
				model.addAttribute("meeting", mom1.getGosNo());
		}
		return new ModelAndView("generateLocEvaluation");

	}

	@RequestMapping(value = "/SendtoConcernDepartment", method = RequestMethod.POST)
	public ModelAndView SendConcernDepartment(
			@ModelAttribute("SendtoConcernDepartment") @Validated PrepareAgendaNotes prepareAgendaNotes, Model model,
			HttpSession session) {
		String appId = (String) session.getAttribute("appId");
		String userId = (String) session.getAttribute("userId");

		return new ModelAndView("redirect:/viewApplicationDetails");
	}

	@RequestMapping(value = "/downloadSupport", method = RequestMethod.POST)
	public void downloadSupport(
			@ModelAttribute("downloadSupport") @RequestParam(value = "applicantId", required = false) String applId,
			HttpSession session, HttpServletResponse response, Model model) {
		String appId = (String) session.getAttribute("appId");

		ApplicantDetails applicantDetail = applicantDetailsService.getApplicantDetailsByAppId(appId);
		ApplicantDocument applicantDocument = fileStorageService.getApplicantDocumentByDocAppId(appId);

		BusinessEntityDetails bussinessDetails = businessEntityDetailsService
				.getBusinessEntityByapplicantDetailId(appId);

		List<BusinessEntityDocument> beDocList = beDocRepository
				.getBusinessDocListByBusinessEntityId(bussinessDetails.getId());

		ProjectDetails projectDetails = projectService.getProjectByapplicantDetailId(appId);
		List<ExistProjDetailsDocument> projDocList = existProjDocRepository
				.getProjDocListByProjectId(projectDetails.getId());

		InvestmentDetails investmentDetails = invDtlService.getInvestmentDetailsByapplId(appId);
		List<InvestmentDocument> invDocList = invDocRepository.getInvDocListByInvestId(investmentDetails.getInvId());

		/*
		 * IncentiveDetails incentiveDetails =
		 * incentiveDetailsService.getIncentiveByisfapcId(appId); List<IncentiveDetails>
		 * incentiveDocList = incentiveDetailsRepository
		 * .getIncentiveByisfapcId(incentiveDetails.getId());
		 */
		response.setHeader("Content-Disposition", "attachment; filename=" + appId + "file.zip");
		response.setHeader("Content-Type", "application/zip");
		ZipOutputStream zipOutputStream;
		try {
			zipOutputStream = new ZipOutputStream(response.getOutputStream());

			if (applicantDocument != null) {
				byte[] encodeBase64 = Base64.encodeBase64(applicantDocument.getData());
				String appdoc = applicantDocument.getFileName();
				zipOutputStream.putNextEntry(new ZipEntry(appdoc));
				InputStream is = new ByteArrayInputStream(encodeBase64);
				IOUtils.copy(is, zipOutputStream);
			}

			if (!projDocList.isEmpty()) {
				for (int i = 0; i < projDocList.size(); i++) {
					ExistProjDetailsDocument projDoc = projDocList.get(i);
					if (projDoc.getData() != null && projDoc.getFileName() != null) {
						zipOutputStream.putNextEntry(new ZipEntry(projDoc.getFileName()));
						InputStream is = new ByteArrayInputStream(projDoc.getData());
						IOUtils.copy(is, zipOutputStream);
					}
				}
			}

			if (!invDocList.isEmpty()) {
				for (int i = 0; i < invDocList.size(); i++) {
					InvestmentDocument investDoc = invDocList.get(i);
					if (investDoc.getData() != null && investDoc.getFileName() != null) {
						zipOutputStream.putNextEntry(new ZipEntry(investDoc.getFileName()));
						InputStream is = new ByteArrayInputStream(investDoc.getData());
						IOUtils.copy(is, zipOutputStream);
					}
				}
			}

			if (!beDocList.isEmpty()) {
				for (int i = 0; i < beDocList.size(); i++) {
					BusinessEntityDocument beDoc = beDocList.get(i);
					if (beDoc.getData() != null && beDoc.getFileName() != null) {
						zipOutputStream.putNextEntry(new ZipEntry(beDoc.getFileName()));
						InputStream is = new ByteArrayInputStream(beDoc.getData());
						IOUtils.copy(is, zipOutputStream);
					}
				}
			}

			zipOutputStream.closeEntry();
			zipOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@GetMapping(value = "/forwardToDICSMDepart")
	public String forwardToDICSMDepart(@RequestParam(value = "appliId", required = false) String appliId,
			HttpSession session, Model model) {
		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();
		prepareAgendaNote.setAppliId(appliId);
		BusinessEntityDetails businessEntityDetails = businessEntityDetailsService
				.getBusinessEntityByapplicantDetailId(appliId);
		prepareAgendaNote.setCompanyName(businessEntityDetails.getBusinessEntityName());
		prepareAgendaNote.setId(appliId + "AGN");
		InvestmentDetails invdtlFromDb = investmentDetailsService.getInvestmentDetailsByapplId(appliId);
		prepareAgendaNote.setInvestment(String.valueOf(invdtlFromDb.getInvFci()));
		prepareAgendaNote.setCategoryIndsType(invdtlFromDb.getInvIndType());
		ProjectDetails projectDetails = projectService.getProjectByapplicantDetailId(appliId);
		prepareAgendaNote.setDistrict(projectDetails.getDistrictName());
		prepareAgendaNote.setRegion(projectDetails.getResionName());
		prepareAgendaNote.setNotes("");
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setStatus("ForwardToDIC");

		String userId = (String) session.getAttribute("userId");
		try {

			if (userId != null && !userId.isEmpty()) {
				Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
				prepareAgendaNote.setUserId(String.valueOf(loginUser.get().getid()));
			}
			prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);
		} catch (Exception e) {
			e.getMessage();
		}

		model.addAttribute("msg", "Succefully Add Agenda Notes");
		List<Object> investmentDetailsList = investRepository.getAllDetailsSMEByAppId();

		model.addAttribute("investmentDetailsList", investmentDetailsList);
		return ("applicationsForLocSME");

	}

	/**
	 * This method is responsible to download BusinessEntityDetails files from
	 * MongoDB collection 'Ind_Business_Entity_Doc'.
	 */
	@GetMapping(value = "/downloadBusinessEntityDoc")
	public void downloadBusinessEntityDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, HttpSession session) {
		String appId = (String) session.getAttribute("appId");
		String appStr = appId.substring(0, appId.length() - 2);
		String businId = appStr + "B1";
		List<BusinessEntityDocument> beDocList = beDocRepository.getBusinessDocListByBusinessEntityId(businId);
		if (!beDocList.isEmpty()) {
			for (BusinessEntityDocument beDoc : beDocList) {
				if (fileName.equals(beDoc.getFileName())) {
					String beDocName = beDoc.getFileName();
					byte[] beDocData = beDoc.getData();
					globalServiceUtil.downloadMongoDBDoc(beDocName, beDocData, response);
					break;
				}
			}
		}
	}

	/**
	 * This method is responsible to download ProjectDetails files from MongoDB
	 * collection 'Ind_Exist_Proj_Details_Doc'.
	 */
	@GetMapping(value = "/downloadExistProjDoc")
	public void downloadExistProjDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response) {

		List<ExistProjDetailsDocument> projDocList = existProjDocRepository.getProjDocListByProjectId(projId);
		for (ExistProjDetailsDocument epdDoc : projDocList) {
			if (fileName.equals(epdDoc.getFileName())) {
				String epdDocFname = epdDoc.getFileName();
				byte[] epdDocData = epdDoc.getData();

				globalServiceUtil.downloadMongoDBDoc(epdDocFname, epdDocData, response);
				break;
			}
		}
	}

	/**
	 * This method is responsible to download New Project file from MongoDB
	 * collection 'Ind_New_Proj_Details_Doc'.
	 */
	@GetMapping(value = "/downloadNewProjDoc")
	public void downloadNewProjDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, HttpSession session) {
		String appId = (String) session.getAttribute("appId");
		String appStr = appId.substring(0, appId.length() - 2);
		projId = appStr + "P1";
		NewProjDetailsDocument newProjDoc = newProjDocRepository.findByProjectId(projId);
		if (fileName.equals(newProjDoc.getFileName())) {
			String epdDocFname = newProjDoc.getFileName();
			byte[] epdDocData = newProjDoc.getData();
			globalServiceUtil.downloadMongoDBDoc(epdDocFname, epdDocData, response);
		}
	}

	/**
	 * This method is responsible to download Investment files from MongoDB
	 * collection 'Ind_Investment_Details_Doc'.
	 */
	@GetMapping(value = "/downloadInvestmentDoc")
	public void downloadInvestmentDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response) {

		List<InvestmentDocument> invDocList = invDocRepository.getInvDocListByInvestId(investId);
		if (!invDocList.isEmpty()) {
			for (InvestmentDocument invDoc : invDocList) {
				if (fileName.equals(invDoc.getFileName())) {
					String invDocFname = invDoc.getFileName();
					byte[] invDocData = invDoc.getData();

					globalServiceUtil.downloadMongoDBDoc(invDocFname, invDocData, response);
					break;
				}
			}
		}
	}

	/**
	 * This method is responsible to download Capital Investment files from MongoDB
	 * collection 'Ind_CapitalInvestment_Doc'. By Pankaj
	 */
	@GetMapping(value = "/downloadDISCapInvDoc")
	public void downloadDISCapInvDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, HttpSession session) {
		String appId = (String) session.getAttribute("appId");
		String capINVId = appId + "CAPINV";

		List<CapitalInvestmentDocument> capInvDocList = capInvRepos.getListBycapInvId(capINVId);
		if (!capInvDocList.isEmpty()) {
			for (CapitalInvestmentDocument capInvDoc : capInvDocList) {
				if (fileName.equals(capInvDoc.getFileName())) {
					String capInvDocName = capInvDoc.getFileName();
					byte[] capInvDocData = capInvDoc.getData();
					globalServiceUtil.downloadMongoDBDoc(capInvDocName, capInvDocData, response);
					break;
				}
			}
		}
	}

	/**
	 * This method is responsible to download Employment details files from MongoDB
	 * collection 'Employment DOC'. By Pankaj
	 */
	@GetMapping(value = "/downloadDISempDetlDoc")
	public void downloadDISempDetlDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, HttpSession session) {
		String appId = (String) session.getAttribute("appId");
		String empId = appId + "EMP";

		List<EmploymentdetailsDocument> empDetlDocList = emplDetilRepos.getListByemplDtlId(empId);
		if (!empDetlDocList.isEmpty()) {
			for (EmploymentdetailsDocument empDetlDoc : empDetlDocList) {
				if (fileName.equals(empDetlDoc.getFileName())) {
					String empDetlDocName = empDetlDoc.getFileName();
					byte[] empDetlDocData = empDetlDoc.getData();
					globalServiceUtil.downloadMongoDBDoc(empDetlDocName, empDetlDocData, response);
					break;
				}
			}
		}
	}

	/**
	 * This method is responsible to download ReimbrsDepsitGST files from MongoDB
	 * collection 'Ind_ReimbrsDepsitGST_Doc'. By Pankaj
	 */
	@GetMapping(value = "/downloadDISReimbrsGSTDoc")
	public void downloadDISReimbrsGSTDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, HttpSession session) {
		String appId = (String) session.getAttribute("appId");
		String docReimbrsId = appId + "RGST";

		List<ReimbrsmentGSTDocument> reimbrsDocList = reimbrsDocRepos.getListByreimbrsGSTId(docReimbrsId);
		if (!reimbrsDocList.isEmpty()) {
			for (ReimbrsmentGSTDocument reimbrsDoc : reimbrsDocList) {
				if (fileName.equals(reimbrsDoc.getFileName())) {
					String reimbrsDocName = reimbrsDoc.getFileName();
					byte[] reimbrsDocData = reimbrsDoc.getData();
					globalServiceUtil.downloadMongoDBDoc(reimbrsDocName, reimbrsDocData, response);
					break;
				}
			}
		}
	}

	/**
	 * This method is responsible to download Ind_DIS_CIS_Doc files from MongoDB
	 * collection 'Ind_DIS_CIS_Doc'. By Pankaj
	 */
	@GetMapping(value = "/downloadDISCISDoc")
	public void downloadDISCISDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, HttpSession session) {
		String appId = (String) session.getAttribute("appId");
		String capCIS = appId + "CIS";

		List<CISDocument> cisDocList = cisDocRepos.getListBydisCisId(capCIS);
		if (!cisDocList.isEmpty()) {
			for (CISDocument cisDoc : cisDocList) {
				if (fileName.equals(cisDoc.getFileName())) {
					String cisDocName = cisDoc.getFileName();
					byte[] cisDocData = cisDoc.getData();
					globalServiceUtil.downloadMongoDBDoc(cisDocName, cisDocData, response);
					break;
				}
			}
		}
	}

	/**
	 * This method is responsible to download Ind_DIS_IIS_Doc files from MongoDB
	 * collection 'Ind_DIS_IIS_Doc'. By Pankaj
	 */
	@GetMapping(value = "/downloadDISIISDoc")
	public void downloadDISIISDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, HttpSession session) {
		String appId = (String) session.getAttribute("appId");
		String capIIS = appId + "IIS";

		List<IISDocument> iisDocList = iisDocRepos.getListBydisIISId(capIIS);
		if (!iisDocList.isEmpty()) {
			for (IISDocument iisDoc : iisDocList) {
				if (fileName.equals(iisDoc.getFileName())) {
					String iisDocName = iisDoc.getFileName();
					byte[] iisDocData = iisDoc.getData();
					globalServiceUtil.downloadMongoDBDoc(iisDocName, iisDocData, response);
					break;
				}
			}
		}
	}

	/**
	 * This method is responsible to download Ind_DIS_IIS_Doc files from MongoDB
	 * collection 'Ind_DIS_IIS_Doc'. By Pankaj
	 */
	@GetMapping(value = "/downloadDISEPFDoc")
	public void downloadDISEPFDoc(@RequestParam(value = "fileName", required = false) String fileName,
			HttpServletResponse response, HttpSession session) {
		String appId = (String) session.getAttribute("appId");
		String epfDocID = appId + "EPF";

		List<EPFDocument> epfDocList = epfRepos.getListBydisEpfId(epfDocID);
		if (!epfDocList.isEmpty()) {
			for (EPFDocument epfDoc : epfDocList) {
				if (fileName.equals(epfDoc.getFileName())) {
					String epfDocName = epfDoc.getFileName();
					byte[] epfDocData = epfDoc.getData();
					globalServiceUtil.downloadMongoDBDoc(epfDocName, epfDocData, response);
					break;
				}
			}
		}
	}

	@RequestMapping(value = "/downloadSupportDIS", method = RequestMethod.POST)
	public void downloadSupportDIS(
			@ModelAttribute("downloadSupportDIS") @RequestParam(value = "applicantId", required = false) String applId,
			HttpSession session, HttpServletResponse response, Model model) {

		String appId = (String) session.getAttribute("appId");

		String capINVId = appId + "CAPINV";
		List<CapitalInvestmentDocument> capInvDocList = capInvRepos.getListBycapInvId(capINVId);

		String docReimbrsId = appId + "RGST";
		List<ReimbrsmentGSTDocument> reimbrsDocList = reimbrsDocRepos.getListByreimbrsGSTId(docReimbrsId);

		String capCIS = appId + "CIS";
		List<CISDocument> cisDocList = cisDocRepos.getListBydisCisId(capCIS);

		String capIIS = appId + "IIS";
		List<IISDocument> iisDocList = iisDocRepos.getListBydisIISId(capIIS);

		String epfDocID = appId + "EPF";
		List<EPFDocument> epfDocList = epfRepos.getListBydisEpfId(epfDocID);

		response.setHeader("Content-Disposition", "attachment; filename=" + appId + "file.zip");
		response.setHeader("Content-Type", "application/zip");
		ZipOutputStream zipOutputStream;
		try {
			zipOutputStream = new ZipOutputStream(response.getOutputStream());

			if (!capInvDocList.isEmpty()) {
				for (int i = 0; i < capInvDocList.size(); i++) {
					CapitalInvestmentDocument capInvDoc = capInvDocList.get(i);
					if (capInvDoc.getData() != null && capInvDoc.getFileName() != null) {
						zipOutputStream.putNextEntry(new ZipEntry(capInvDoc.getFileName()));
						InputStream is = new ByteArrayInputStream(capInvDoc.getData());
						IOUtils.copy(is, zipOutputStream);
					}
				}
			}

			if (!reimbrsDocList.isEmpty()) {
				for (int i = 0; i < reimbrsDocList.size(); i++) {
					ReimbrsmentGSTDocument rgstDoc = reimbrsDocList.get(i);
					if (rgstDoc.getData() != null && rgstDoc.getFileName() != null) {
						zipOutputStream.putNextEntry(new ZipEntry(rgstDoc.getFileName()));
						InputStream is = new ByteArrayInputStream(rgstDoc.getData());
						IOUtils.copy(is, zipOutputStream);
					}
				}
			}

			if (!cisDocList.isEmpty()) {
				for (int i = 0; i < cisDocList.size(); i++) {
					CISDocument cisDoc = cisDocList.get(i);
					if (cisDoc.getData() != null && cisDoc.getFileName() != null) {
						zipOutputStream.putNextEntry(new ZipEntry(cisDoc.getFileName()));
						InputStream is = new ByteArrayInputStream(cisDoc.getData());
						IOUtils.copy(is, zipOutputStream);
					}
				}
			}

			if (!iisDocList.isEmpty()) {
				for (int i = 0; i < iisDocList.size(); i++) {
					IISDocument iisDoc = iisDocList.get(i);
					if (iisDoc.getData() != null && iisDoc.getFileName() != null) {
						zipOutputStream.putNextEntry(new ZipEntry(iisDoc.getFileName()));
						InputStream is = new ByteArrayInputStream(iisDoc.getData());
						IOUtils.copy(is, zipOutputStream);
					}
				}
			}

			if (!epfDocList.isEmpty()) {
				for (int i = 0; i < epfDocList.size(); i++) {
					EPFDocument epfDoc = epfDocList.get(i);
					if (epfDoc.getData() != null && epfDoc.getFileName() != null) {
						zipOutputStream.putNextEntry(new ZipEntry(epfDoc.getFileName()));
						InputStream is = new ByteArrayInputStream(epfDoc.getData());
						IOUtils.copy(is, zipOutputStream);
					}
				}
			}

			zipOutputStream.closeEntry();
			zipOutputStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@PostMapping("/locconcerncirculatest")
	public ModelAndView locconcerncirculatesan(@Validated @ModelAttribute("department") Department department,
			@Validated @ModelAttribute("concernDepartment") ConcernDepartment concernDept, BindingResult result,
			HttpSession session, Model model, @RequestParam(value = "applicantId", required = false) String applId) {

		List<String> newlist = new ArrayList<>();
		// String applId = (String) session.getAttribute("appId");
		// String mail = null;
		try {
			// String str=department.getNewDeptName().toString();
			// String[] strarr = str.split(",");
			/*
			 * for(String mail1:strarr) { newlist.add(mail1); }
			 */
			String str = department.getNewDeptName().toString();
			String deptName = "";

			List<String> aalist = new ArrayList<>();

			/* System.out.println(str); */

			String[] strarr = str.split(",");
			for (String a : strarr) {
				if (a.contains("@")) {
					deptName += a;

					newlist.add(a);
				} else {
					deptName += a;
					aalist.add(deptName);
					deptName = "";
				}

				if (deptName != "") {
					deptName += ",";
				}

			}
			for (String aa : aalist) {
				String data[] = aa.split(",");
				CirculateToDepartment circulateToDepartment = new CirculateToDepartment();
				String mom = "Draft LoC";

				List<CirculateToDepartment> cirList = circulateToDepartmentRepository
						.searchExsiting(concernDept.getConDeptApplId(), data[1].toString(), mom);

				if (data[0] != null) {
					circulateToDepartment.setDeptEmail(data[0]);
				}
				if (data[1] != null) {
					circulateToDepartment.setDeptName(data[1]);
					if (cirList.size() == 0) {
						circulateToDepartment.setCirculateId("CD" + atomicInteger.getAndIncrement());
						circulateToDepartment.setAppId(concernDept.getConDeptApplId());
						circulateToDepartment.setNoteReportStatus("Draft LoC");
						circulateToDepartmentService.save(circulateToDepartment);
					}

				}

			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		List<Login> loginList = new ArrayList<>();

		try {
			String msg = "Department Name: dept name \r\n Application Id" + concernDept.getConDeptApplId()
					+ ": Wed Dec 02 15:42:01 IST 2020 \r\nReady for your comments \r\nMeeting Location: DIS";
			newlist.forEach(x -> sendingEmail.sentEmail(
					"Application" + concernDept.getConDeptApplId() + " Agenda Note prepared and Circulated ", msg, x));

			loginList.forEach(x -> sendingEmail.sentEmail("Created Login credential for Department",
					"your new login credential is \r\nEmail =" + x.getUserName() + "\r\npassword =" + x.getPassword(),
					x.getUserName()));

			// save into login

			// End
			model.addAttribute("viewSignatoryDetail", new ApplicantDetails());
			model.addAttribute("viewInvestDetails", new InvestmentDetails());
			model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
			model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
			model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

		}

		catch (Exception e) {
			logger.error("saveConcernDepartment exception ^^^^^ " + e.getMessage());
			e.printStackTrace();
		}

		return new ModelAndView("redirect:/generateLoc");

	}

	public void downloadReport(String fileName, byte[] fileData, HttpServletResponse response) {

		response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
		response.setHeader("Content-Type", "application/pdf");

		// try-with-resources statement
		try (InputStream is = new ByteArrayInputStream(fileData)) {
			try {
				IOUtils.copy(is, response.getOutputStream());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

}