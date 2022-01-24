package com.webapp.ims.controller;

import static com.webapp.ims.exception.GlobalConstants.APPLICATION_ID;
import static com.webapp.ims.exception.GlobalConstants.USER_ID;

import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.dis.model.DisRaiseQuery;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.dis.service.DisRaiseQueryDocService;
import com.webapp.ims.dis.service.DisRaiseQueryService;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.repository.TblUsersServiceRepository;
import com.webapp.ims.uam.service.TblUsersService;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.DPTAgendaNotes;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.model.RaiseQueryDocument;
import com.webapp.ims.model.ResponseQuery;
import com.webapp.ims.model.ScrutinyDocument;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.repository.DPTAgendaRepository;
import com.webapp.ims.repository.LoginRepository;
import com.webapp.ims.repository.RaiseQueryDocRepository;
import com.webapp.ims.repository.RaiseQueryRepository;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.RaiseQueryDocService;
import com.webapp.ims.service.RaiseQueryService;
import com.webapp.ims.service.ResponseQueryService;
import com.webapp.ims.service.WReturnCUSIDSTATUSBeanService;
import com.webapp.ims.service.impl.GlobalServiceUtil;

@Controller
public class RaiseQueryController {
	private final Logger logger = LoggerFactory.getLogger(RaiseQueryController.class);
	private RaiseQueryService rqService;
	private BusinessEntityDetailsService businessEntityDetailsService;
	private ProjectService projectService;
	private PrepareAgendaNotesService prepareAgendaNotesService;
	private InvestmentDetailsService investmentDetailsService;
	private TblUsersServiceRepository loginRepository;
	@Autowired
	private ResponseQueryService respService;
	@Autowired
	private RaiseQueryDocService rqDocService;
	
	@Autowired
	RaiseQueryRepository rqRepository;

	@Autowired
	DisRaiseQueryDocService disRqDocService;

	@Autowired
	private RaiseQueryDocRepository rqDocRepository;
	@Autowired
	private DPTAgendaRepository dptAgendaRepository;
	@Autowired
	private GlobalServiceUtil globalServiceUtil;

	@Autowired
	private TblUsersService loginService;

	@Autowired
	private DISPrepareAgendaNotesRepository prepareAgendaNotesRepository;

	@Autowired
	private ApplicantDetailsRepository applicantDetailsRepository;
	@Autowired
	private WReturnCUSIDSTATUSBeanService wReturnCUSIDSTATUSBeanService;
	
	@Autowired
	private DisRaiseQueryService disRqService;

	List<ResponseQuery> respQueryList;

	String appid = null;
 
	@Autowired
	public RaiseQueryController(RaiseQueryService rqService, BusinessEntityDetailsService businessEntityDetailsService,
			ProjectService projectService, PrepareAgendaNotesService prepareAgendaNotesService,
			InvestmentDetailsService investmentDetailsService, TblUsersServiceRepository loginRepository) {
		super();
		this.rqService = rqService;
		this.businessEntityDetailsService = businessEntityDetailsService;
		this.projectService = projectService;
		this.prepareAgendaNotesService = prepareAgendaNotesService;
		this.investmentDetailsService = investmentDetailsService;
		this.loginRepository = loginRepository;
	}

	/**
	 * This method is responsible to save Raise Query records in the table
	 * 'Raise_Query' and document in MongoDB collection 'Ind_Raise_Query_Doc'.
	 */
	public void commonSaveRaiseQuery(RaiseQuery raiseQuery, Model model, HttpSession session,
			MultipartFile multipartFiles) {

		String userid = "";
		//new Id
		try {
			String applId = (String) session.getAttribute(APPLICATION_ID);
			userid = (String) session.getAttribute(USER_ID);
			Optional<TblUsers> login = loginRepository.findById(userid);
			String rqid = "";
			String id = "";
			Random random = new Random();
			
			int x = random.nextInt(500);
			id = applId.substring(0, applId.length() - 2) + "RQ" + x;
	rqid = applId.substring(0, applId.length() - 2) + "RQ";
			
			if (raiseQuery != null) {
				raiseQuery.setId(id);
				raiseQuery.setRqId(rqid);
				raiseQuery.setRqApplId(applId);
				raiseQuery.setRqStatus("active");
				raiseQuery.setRqCreatedBy("User");
				raiseQuery.setRqModifiyDate(new Date());
				raiseQuery.setRqUserId(userid);
				raiseQuery.setRqDept(login.get().getDepartment());
				raiseQuery.setRqFilename(multipartFiles.getOriginalFilename());
			}

			model.addAttribute("raiseQuery", raiseQuery);
			model.addAttribute("incentiveDeatilsData", new IncentiveDetails());
			RaiseQuery raiseQ=	rqService.saveRaiseQuery(raiseQuery);
			model.addAttribute("raisedQ", raiseQ);
			
			//System.out.println("raiseQ ID" + raiseQ.getId());
			
			
			rqDocService.saveAndUpdateRaiseQueryDoc(multipartFiles, session);// save document in MongoDB
			raiseQuery.setRqClarifySought(null);
			raiseQuery.setRqMissdocdtl(null);
			raiseQuery.setRqFilename(null);
			
			ApplicantDetails applicantDetails =applicantDetailsRepository.getApplicantDetailsByAppId(applId);
			if (applicantDetails != null) {
				applicantDetails.setStatusCode("08");
				applicantDetails.setRemarks("Query Raised By PICUP Processing Team");
				wReturnCUSIDSTATUSBeanService.WReturn_CUSID_STATUS(applicantDetails);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		PrepareAgendaNotes prepareAgendaNote = new PrepareAgendaNotes();

		String appId = (String) session.getAttribute(APPLICATION_ID);
		prepareAgendaNote.setAppliId(appId);
		BusinessEntityDetails businessEntityDetails = businessEntityDetailsService
				.getBusinessEntityByapplicantDetailId(appId);
		prepareAgendaNote.setCompanyName(businessEntityDetails.getBusinessEntityName());
		prepareAgendaNote.setId(appId + "AGN");
		InvestmentDetails invdtlFromDb = investmentDetailsService.getInvestmentDetailsByapplId(appId);
		prepareAgendaNote.setInvestment(String.valueOf(invdtlFromDb.getInvFci()));
		prepareAgendaNote.setCategoryIndsType(invdtlFromDb.getInvIndType());
		ProjectDetails projectDetails = projectService.getProjectByapplicantDetailId(appId);
		prepareAgendaNote.setDistrict(projectDetails.getDistrictName());
		prepareAgendaNote.setRegion(projectDetails.getResionName());
		prepareAgendaNote.setNotes("");
		prepareAgendaNote.setCreatedBY("AdminUser");
		prepareAgendaNote.setStatus("Query");
		prepareAgendaNote.setUserId(userid);
		prepareAgendaNotesService.savePrepareAgendaNotes(prepareAgendaNote);

	}

	
	
	//for create query by pramod 
		@PostMapping(value = "/saveRaiseQuery")
	public ModelAndView saveRaiseQuery(@Validated @ModelAttribute("raiseQuery") RaiseQuery raiseQuery,
			BindingResult result, Model model, HttpSession session,
			@RequestParam("rqFilename") MultipartFile multipartFile) {
		commonSaveRaiseQuery(raiseQuery, model, session, multipartFile);
		return new ModelAndView("redirect:/evaluateApplication");
	}

	@PostMapping(value = "/saveRaiseQueryDIS")
	public ModelAndView saveRaiseQueryDIS(@Validated @ModelAttribute("raiseQuery") DisRaiseQuery raiseQuery,
			BindingResult result, Model model, HttpSession session,
			@RequestParam("rqFilename") MultipartFile multipartFile) {
		commonSaveRaiseQueryDIS(raiseQuery, model, session, multipartFile);
		return new ModelAndView("redirect:/evaluateApplicationDis");
	}

	/**
	 * This method is responsible to save Raise Query records in the table
	 * 'DIS_Raise_Query' and document in MongoDB collection
	 * 'Dis_Ind_Raise_Query_Doc'.
	 */
	public void commonSaveRaiseQueryDIS(DisRaiseQuery raiseQuery, Model model, HttpSession session,
			MultipartFile multipartFile) {

		String userid = null;
		try {
			String applId = (String) session.getAttribute(APPLICATION_ID);
			userid = (String) session.getAttribute(USER_ID);
			Optional<TblUsers> login = loginRepository.findById(userid);
			String rqid = "";
			rqid = applId.substring(0, applId.length() - 2) + "RQ";
			raiseQuery.setRqId(rqid);
			raiseQuery.setRqApplId(applId);
			raiseQuery.setRqStatus("active");
			raiseQuery.setRqCreatedBy(login.get().getUserName());
			raiseQuery.setRqModifiedBy(login.get().getUserName());
			raiseQuery.setRqModifiyDate(new Date());
			raiseQuery.setRqUserId(userid);
			raiseQuery.setRqDept(login.get().getDepartment());
			raiseQuery.setRqFilename(multipartFile.getOriginalFilename());
			raiseQuery.setRqFiledata(multipartFile.getBytes());

			model.addAttribute("raiseQuery", raiseQuery);
			model.addAttribute("incentiveDeatilsData", new IncentiveDetails());
			disRqService.saveRaiseQuery(raiseQuery);
			
			disRqDocService.saveAndUpdateRaiseQueryDoc(multipartFile, session);// save document in MongoDB
			raiseQuery.setRqClarifySought(null);
			raiseQuery.setRqMissdocdtl(null);
			raiseQuery.setRqFilename(null);
		} catch (Exception e) {
			logger.error(String.format("@@@@@ commonSaveRaiseQuery exception @@@@@ %s", e.getMessage()));
		}

		DISPrepareAgendaNotes disPrepareAgendaNotes = new DISPrepareAgendaNotes();

		String appId = (String) session.getAttribute("appId");

		BusinessEntityDetails businessEntityDetails = businessEntityDetailsService
				.getBusinessEntityByapplicantDetailId(appId);

		InvestmentDetails invdtlFromDb = investmentDetailsService.getInvestmentDetailsByapplId(appId);

		ProjectDetails evaluateProjectDetails = projectService.getProjectByapplicantDetailId(appId);

		String userId = (String) session.getAttribute("userId");

		disPrepareAgendaNotes.setId(appId + "DISAGN");
		disPrepareAgendaNotes.setAppliId(appId);
		disPrepareAgendaNotes.setCompanyName(businessEntityDetails.getBusinessEntityName());
		disPrepareAgendaNotes.setInvestment(String.valueOf(invdtlFromDb.getInvFci()));
		disPrepareAgendaNotes.setCategoryIndsType(invdtlFromDb.getInvIndType());
		disPrepareAgendaNotes.setDistrict(evaluateProjectDetails.getDistrictName());
		disPrepareAgendaNotes.setRegion(evaluateProjectDetails.getResionName());
		disPrepareAgendaNotes.setStatus("Query Raised By Processing Officer");
		disPrepareAgendaNotes.setCreateDate(new Timestamp(System.currentTimeMillis()));
		try {

			if (userId != null && userId != "") {
				Optional<TblUsers> loginUser = loginService.getLoginIdById(userId);
				disPrepareAgendaNotes.setUserId(String.valueOf(loginUser.get().getid()));
				disPrepareAgendaNotes.setCreatedBY((loginUser.get().getFirstName() + " " + loginUser.get().getLastName()));
			}
			prepareAgendaNotesRepository.save(disPrepareAgendaNotes);
		} catch (Exception e) {
			e.getMessage();
		}

	}

	@PostMapping(value = "/saveRaiseQueryDIC")
	public ModelAndView saveRaiseQueryDIC(@Validated @ModelAttribute("raiseQuery") RaiseQuery raiseQuery,
			BindingResult result, Model model, HttpSession session,
			@RequestParam("rqFilename") MultipartFile multipartFile) {
		commonSaveRaiseQuery(raiseQuery, model, session, multipartFile);
		return new ModelAndView("redirect:/evaluateApplicationDIC");
	}

	/**
	 * This method is responsible to fetch ApplicationId list on userid basis and
	 * view Query Raised on ApplicationId basis.
	 */
	public void commonViewQueryRaised(Model model, HttpSession session) {
		try {

			List<RaiseQuery> rqList = rqService.getRaiseQueryList();
			model.addAttribute("raiseQueryList", rqList);
			model.addAttribute("incentiveDeatilsData", new IncentiveDetails());
			
		} catch (Exception e) {
			logger.error(String.format("@@@@@ commonQueryRaised exception @@@@@ %s", e.getMessage()));
		}
	}

	/**
	 * This method is responsible for retrieving ResponseQuery list based on user
	 * id.
	 */
	public void commonViewQueryResponse(Model model, HttpSession session) {
		try {
			respQueryList = respService.getResponseQueryList();
			model.addAttribute("responseQueryList", respQueryList);
		} catch (Exception e) {
			logger.error(String.format("***** commonViewQueryResponse exception ***** %s", e.getMessage()));
		}
	}

	@GetMapping(value = "/queryRaised")
	public ModelAndView queryRaised(Model model, HttpSession session) {
		commonViewQueryRaised(model, session);
		commonViewQueryResponse(model, session);
		return new ModelAndView("query-raised");
	}

	
	
	@GetMapping(value = "/queryRaisedDIC")
	public ModelAndView queryRaisedDIC(Model model, HttpSession session) {
		commonViewQueryRaised(model, session);
		return new ModelAndView("query-raised-dic");
	}

	@RequestMapping("/saveRejectApplication")
	public String saveRejectApplication(@RequestParam(name = "rejectValue") String rejectValue, HttpSession session) {

		String userid = (String) session.getAttribute(USER_ID);
		DPTAgendaNotes dptAgendaNotes = new DPTAgendaNotes();

		String appId = (String) session.getAttribute(APPLICATION_ID);
		dptAgendaNotes.setDptApcId(appId);
		BusinessEntityDetails businessEntityDetails = businessEntityDetailsService
				.getBusinessEntityByapplicantDetailId(appId);
		dptAgendaNotes.setDptCompanyName(businessEntityDetails.getBusinessEntityName());
		dptAgendaNotes.setDptId(appId + "AGN");
		InvestmentDetails invdtlFromDb = investmentDetailsService.getInvestmentDetailsByapplId(appId);
		dptAgendaNotes.setDptInvestment(String.valueOf(invdtlFromDb.getInvFci()));
		dptAgendaNotes.setdPTCategoryType(invdtlFromDb.getInvIndType());
		ProjectDetails projectDetails = projectService.getProjectByapplicantDetailId(appId);
		dptAgendaNotes.setdPtDistrict(projectDetails.getDistrictName());
		dptAgendaNotes.setdPTRegion(projectDetails.getResionName());
		dptAgendaNotes.setDptNotes("");
		dptAgendaNotes.setCreatedBy("AdminUser");
		dptAgendaNotes.setStatus("LOC Rejected");
		dptAgendaNotes.setDptUserIds(userid);
		dptAgendaNotes.setCreatedDate(new Timestamp(System.currentTimeMillis()));
		dptAgendaNotes.setdPTReason(rejectValue);
		dptAgendaRepository.save(dptAgendaNotes);
		
		ApplicantDetails applicantDetails =applicantDetailsRepository.getApplicantDetailsByAppId(appId);
		if (applicantDetails != null) {
			applicantDetails.setStatusCode("07");
			applicantDetails.setRemarks("Application Rejected during Evalution");
 
			wReturnCUSIDSTATUSBeanService.WReturn_CUSID_STATUS(applicantDetails);
		}

		return "redirect:/evaluateApplication";
	}

	/**
	 * This method is responsible to download RaiseQuery PDF file from MongoDB
	 * collection.
	 */
	@GetMapping(value = "/downloadRaiseQueryDoc")
	public void downloadRaiseQueryDoc(@RequestParam(value = "fileName", required = false) String fileName, Model model,
			HttpServletResponse response, HttpSession session) {

		List<RaiseQueryDocument> rqDocList = rqDocService.getRaiseQueryDocList();
		if (!rqDocList.isEmpty()) {
			for (RaiseQueryDocument rqDoc : rqDocList) {
				if (fileName.equals(rqDoc.getFileName())) {
					String beDocName = rqDoc.getFileName();
					byte[] beDocData = rqDoc.getData();
					globalServiceUtil.downloadMongoDBDoc(beDocName, beDocData, response);
					break;
				}
			}
		}
	}

}
