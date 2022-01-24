/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.dis.controller;

import static com.webapp.ims.exception.GlobalConstants.EXIST_PROJ_LIST;
import static com.webapp.ims.exception.GlobalConstants.PROPOSED_EMPLOYMENT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.PW_INVESTMENT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.PW_INVESTMENT_LIST;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import com.webapp.ims.controller.ApplicationDetailsViewController;
import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.dis.model.DisReimbrsDepositeGstTable;
import com.webapp.ims.dis.model.DisStampDeauty;
import com.webapp.ims.dis.model.Discis;
import com.webapp.ims.dis.model.Disepfriem;
import com.webapp.ims.dis.model.Disiis;
import com.webapp.ims.dis.model.DissbursmentReimbrsDepositeGST;
import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.model.NewProjDisbursement;
import com.webapp.ims.dis.model.OtherIncentive;
import com.webapp.ims.dis.repository.CISDocumentRepository;
import com.webapp.ims.dis.repository.CapitalInvestDocumentRepository;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.dis.repository.DisEPFRepository;
import com.webapp.ims.dis.repository.DisElectricityDutyExemptionRepository;
import com.webapp.ims.dis.repository.DisMandiFeeExemptionRepository;
import com.webapp.ims.dis.repository.EPFDocumentRepository;
import com.webapp.ims.dis.repository.EmploymentDetailsDocumentRepository;
import com.webapp.ims.dis.repository.IISDocumentRepository;
import com.webapp.ims.dis.repository.NewProjDisburseRepository;
import com.webapp.ims.dis.repository.ProjDisburseRepository;
import com.webapp.ims.dis.repository.ReimbrsDepositGSTDocumentRepository;
import com.webapp.ims.dis.service.CISDocumentService;
import com.webapp.ims.dis.service.CapitalInvestService;
import com.webapp.ims.dis.service.CapitalInvestmentDocumentService;
import com.webapp.ims.dis.service.DisEvaluationDocumentService;
import com.webapp.ims.dis.service.DisReimbrsGStTableService;
import com.webapp.ims.dis.service.DisViewEvaluateService;
import com.webapp.ims.dis.service.DisbursmentApplicantDetailsService;
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
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;
import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.ApplicantDocument;
import com.webapp.ims.model.AvailCustomisedDetails;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.BusinessEntityDocument;
import com.webapp.ims.model.ConcernDepartment;
import com.webapp.ims.model.Department;
import com.webapp.ims.model.EvaluateProjectDetails;
import com.webapp.ims.model.ExistProjDetailsDocument;
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.InvestmentDocument;
import com.webapp.ims.model.NewProjDetailsDocument;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.ProposedEmploymentDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.model.SkilledUnSkilledEmployemnt;
import com.webapp.ims.repository.BusinessEntityDocumentRepository;
import com.webapp.ims.repository.DeptBusinessEntityDetailsRepository;
import com.webapp.ims.repository.ExistProjDocRepository;
import com.webapp.ims.repository.IncentiveDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.InvestmentDocumentRepository;
import com.webapp.ims.repository.LoginRepository;
import com.webapp.ims.repository.NewProjDocRepository;
import com.webapp.ims.repository.ProjectRepository;
import com.webapp.ims.repository.StateDetailsRepository;
import com.webapp.ims.service.ApplicantDetailsService;
import com.webapp.ims.service.ApplicantDocumentService;
import com.webapp.ims.service.AvailCustmisedDetailsService;
import com.webapp.ims.service.BreakupCostService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.DepartmentService;
import com.webapp.ims.service.EvaluateProjectDetailsService;
import com.webapp.ims.service.ExistingProjectDetailsService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.MeansOfFinanceService;
import com.webapp.ims.service.PhaseWiseInvestmentDetailsService;
import com.webapp.ims.service.PrepareAgendaNoteService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.ProprietorDetailsService;
import com.webapp.ims.service.SendingEmail;
import com.webapp.ims.service.StateDetailsService;
import com.webapp.ims.service.impl.AdditionalInterest;


/**
 * @author dell
 *
 */
@Controller
public class DisConcernDepartmentController {
	private final Logger logger = LoggerFactory.getLogger(ApplicationDetailsViewController.class);
	private ApplicantDetailsService appDtlService;
	private ApplicantDocumentService appDocService;
	private InvestmentDetailsService invDtlService;
	private PhaseWiseInvestmentDetailsService pwInvDtlService;
	private BreakupCostService brkupService;
	private MeansOfFinanceService mofService;
	private ProposedEmploymentDetailsService emplDtlService;
	private BusinessEntityDetailsService businessEntityDetailsService;
	@Autowired
	private ProprietorDetailsService proprietorService;
	@Autowired
	private AvailCustmisedDetailsService availCustmisedDetailsService;
	@Autowired
	private ProjectService projectService;
	private List<SkilledUnSkilledEmployemnt> skillUnskillEmplList = new ArrayList<>();
	private String applicantId = null;
	public List<ExistingProjectDetails> existProjList = new ArrayList<>();

	private List<DisReimbrsDepositeGstTable> disReimbrsGSTTableList = new LinkedList<>();
	@Autowired
	StateDetailsRepository stateRepository;
	@Autowired
	DepartmentService departmentService;
	@Autowired
	private ApplicantDetailsService applicantDetailsService;
	@Autowired
	CapitalInvestService capitalInvestService;
	@Autowired
	DisbursmentRiembrsDepositGSTService disbursmentRiembrsDepositGSTService;
	@Autowired
	private ExistingProjectDetailsService existProjectService;
	@Autowired	
	ProjDisburseRepository projDisburseRepository;
	@Autowired
	StampDutyService stampDutyService;
	@Autowired
	NewProjDisburseRepository newProjDisburseRepository;
	@Autowired
	DisReimbrsGStTableService disReimbrsGStTableService;
	@Autowired
	OtherDetailService otherDetailService;
	@Autowired
	BusinessEntityDocumentRepository beDocRepository;
	@Autowired
	DisEPFRepository disEPFRepository;
	
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
	private LoginRepository loginRepository;
	private List<Department> deptList = new LinkedList<>();
	private String[] deptId = new String[50];
	
	@Autowired
	TblUsersService loginservice;
	@Autowired
	SendingEmail sendingEmail;
	@Autowired
	PrepareAgendaNoteService prepareAgendaNoteService;
	@Autowired
	private PrepareAgendaNotesService prepareAgendaNotesService;
	@Autowired
	private DepartmentService deptService;
	
	@Autowired
	DisbursmentApplicantDetailsService disApplicantService;

	@Autowired
	BusinessEntityDetailsService businessService;

	@Autowired
	ApplicationDetailsViewController applicantViewController;

	@Autowired
	DisbursmentCisService disbursmentCisService;
	@Autowired
	DisbursmentIisService disbursmentIisService;

	@Autowired
	DisViewEvaluateService disViewEvaluateService;

	@Autowired
	DisMandiFeeExemptionRepository disMandiFeeExemptionRepository;

	@Autowired
	DisElectricityDutyExemptionRepository disElectricityDutyExemptionRepository;

	@Autowired
	DisEvaluationDocumentService disEvaluationDocumentService;

	@Autowired
	DeptBusinessEntityDetailsRepository deptBusEntRepository;

	@Autowired
	DISPrepareAgendaNotesRepository prepareAgendaNotesRepository;
	@Autowired
	InvestmentDetailsService investmentDetailsService;

	@Autowired
	IncentiveDetailsService incentiveDetailsService;

	@Autowired
	EvaluateProjectDetailsService evaluateProjectDetailsService;

	@Autowired
	InvestmentDetailsRepository investRepository;

	@Autowired
	AdditionalInterest additionalInterest;
	
	@PostMapping("/dissaveConcernDepartment")
	public ModelAndView saveConcernDepartment(@Validated @ModelAttribute("department") Department department,
			@Validated @ModelAttribute("concernDepartment") ConcernDepartment concernDept, BindingResult result,
			HttpSession session, Model model) {

		String applId = (String) session.getAttribute("appId");
		List<String> newlist = new ArrayList<>();
		//String applId = (String) session.getAttribute("appId");
		//String mail = null;
		String str=department.getNewDeptName().toString();
		String[] strarr = str.split(",");
		for(String mail1:strarr) {
			newlist.add(mail1);
		}
		List<String> emailList = new ArrayList<>(Arrays.asList(department.getNewDeptName()));
		List<TblUsers> loginList = new ArrayList<>();
		
		try {
				if (result.hasErrors()) {
				System.out.println("::::: concren dept error::::");
				model.addAttribute("deptList", deptList);

			}
			
			String userid = (String) session.getAttribute("userId");
			Login login = loginRepository.findById(userid);
			String key = login.getUserName() + "##" + "addDept";
			List<Department> list = (List<Department>) session.getAttribute(key);
			if (list != null) {
				list = deptService.saveDepartmentList(list);
				if (list.size() > 0) {
					loginList = new ArrayList<TblUsers>();
					for (Department temp : list) {
						TblUsers obj = new TblUsers();
						obj.setUserName(temp.getDeptEmail());
						//obj.setDepartment(temp.getNewDeptName());
						obj.setPassword(loginservice.getRandomPassword().toString());
						loginservice.insertWithQuery(obj);
						loginList.add(obj);
					}
				}
			}

			String msg = "Department Name: dept name \r\n Application Id" +applId +": Wed Dec 02 15:42:01 IST 2020 \r\nReady for your comments \r\nMeeting Location: DIS";
			newlist.forEach(x -> sendingEmail.sentEmail("Application" +applId + " Ready for your comments ", msg, x));

			loginList.forEach(x -> sendingEmail.sentEmail("Created Login credential for Department",
					"your new login credential is \r\nEmail =" + x.getUserName() + "\r\npassword =" + x.getPassword(),
					x.getUserName()));

			// save into login

			// Start Change Status Send to concern department
			if (!emailList.isEmpty()) {
				BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(applId);

				InvestmentDetails invdtlFromDb = investmentDetailsService.getInvestmentDetailsByapplId(applId);

				EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
						.getEvalProjDetByapplicantDetailId(applId);

				String userId = (String) session.getAttribute("userId");
				DISPrepareAgendaNotes disPrepareAgendaNotes = new DISPrepareAgendaNotes();
				disPrepareAgendaNotes.setId(applId + "DISAGN");
				disPrepareAgendaNotes.setAppliId(applId);
				disPrepareAgendaNotes.setCompanyName(businessEntityDetails.getBusinessEntityName());
				disPrepareAgendaNotes.setInvestment(String.valueOf(invdtlFromDb.getInvFci()));
				disPrepareAgendaNotes.setCategoryIndsType(invdtlFromDb.getInvIndType());
				disPrepareAgendaNotes.setDistrict(evaluateProjectDetails.getDistrictName());
				disPrepareAgendaNotes.setRegion(evaluateProjectDetails.getResionName());
				disPrepareAgendaNotes.setStatus("Sent To Concerned Department");
				disPrepareAgendaNotes.setCreateDate(new Timestamp(System.currentTimeMillis()));
				try {

					if (userId != null && userId != "") {
						Optional<TblUsers> loginUser = loginservice.getLoginIdById(userId);
						disPrepareAgendaNotes.setUserId(String.valueOf(loginUser.get().getid()));
						disPrepareAgendaNotes.setCreatedBY((loginUser.get().getFirstName() + " " + loginUser.get().getLastName()));
					}
					prepareAgendaNotesRepository.save(disPrepareAgendaNotes);
				} catch (Exception e) {
					e.getMessage();
				}
			}

			// End
			model.addAttribute("viewSignatoryDetail", new ApplicantDetails());
			model.addAttribute("viewInvestDetails", new InvestmentDetails());
			model.addAttribute("phaseWiseInvestmentDetails", new PhaseWiseInvestmentDetails());
			model.addAttribute("proposedEmploymentDetails", new ProposedEmploymentDetails());
			model.addAttribute("skilledUnSkilledEmployemnt", new SkilledUnSkilledEmployemnt());

		} catch (Exception e) {
			logger.error("saveConcernDepartment exception ^^^^^ " + e.getMessage());
			e.printStackTrace();
		}
		return new ModelAndView("redirect:/viewApplicationDetailsDis?applicantId=" + applId);

	}
	
	@GetMapping(value = "/viewApplicationDetailsDisConcern")
	public ModelAndView viewApplicationDetailsDisConcern(@RequestParam(value = "applicantId", required = false) String applId,
			HttpSession session, Model model) {
		commonViewApplicationDetailsDISConcern(applId, session, model);
		// send to concernDepartment
		 Iterable<Department> list = departmentService.getDepartmentList();
		 model.addAttribute("concernDepartment", list);
		return new ModelAndView("/Disbursement/disapplicantpreviewForConcern");

	}
	String businId = "";
	String projId = "";
	String investId = "";
	String propId = "";
	String isfId = "";
	public void commonViewApplicationDetailsDISConcern(String appId, HttpSession session, Model model) {

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
	public void newProjDocMongoDB(String projId, Model model) {
		NewProjDetailsDocument newProjDoc = newProjDocRepository.findByProjectId(projId);
		model.addAttribute("enclDetProRepFileName", newProjDoc.getFileName());
	}
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
	
	@GetMapping(value = "/DisfacilitiesReliefSought")
	public ModelAndView facilitiesReliefSought(ModelMap model, HttpSession session) {
		
		String s = (String) session.getAttribute("role");
		model.addAttribute("role", s);
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
			
			
			  incentiveSpecificDetails.setIsfSgstCommentdis(IncentiveDetail.getIsfSgstCommentdis());
			  incentiveSpecificDetails.setIsfScstCommentdis(IncentiveDetail.getIsfScstCommentdis());
			  incentiveSpecificDetails.setIsffwCommentdis(IncentiveDetail.getIsffwCommentdis());
			  incentiveSpecificDetails.setIsfBplCommentdis(IncentiveDetail.getIsfBplCommentdis());
			  incentiveSpecificDetails.setIsfElecdutyCommentdis(IncentiveDetail.getIsfElecdutyCommentdis());
			  incentiveSpecificDetails.setIsfMandiCommentdis(IncentiveDetail.getIsfMandiCommentdis());
			  incentiveSpecificDetails.setIsfStampCommentdis(IncentiveDetail.getIsfStampCommentdis());
			  incentiveSpecificDetails.setIsfStampremCommentdis(IncentiveDetail.getIsfStampremCommentdis());
			  incentiveSpecificDetails.setIsfStampscstCommentdis(IncentiveDetail.getIsfStampscstCommentdis());
			  incentiveSpecificDetails.setIsfepfCommentdis(IncentiveDetail.getIsfepfCommentdis());
			  incentiveSpecificDetails.setIsfepfaddCommentdis(IncentiveDetail.getIsfepfaddCommentdis());
			  incentiveSpecificDetails.setIsfepfscCommentdis(IncentiveDetail.getIsfepfscCommentdis());
			  incentiveSpecificDetails.setIsfcapisCommentdis(IncentiveDetail.getIsfcapisCommentdis());
			  incentiveSpecificDetails.setIsfcapisaCommentdis(IncentiveDetail.getIsfcapisaCommentdis());
			  incentiveSpecificDetails.setIsfinfCommentdis(IncentiveDetail.getIsfinfCommentdis());
			  incentiveSpecificDetails.setIsfinfaCommentdis(IncentiveDetail.getIsfinfaCommentdis());
			  incentiveSpecificDetails.setIsfloanCommentdis(IncentiveDetail.getIsfloanCommentdis());
			  incentiveSpecificDetails.setIsfdisCommentdis(IncentiveDetail.getIsfdisCommentdis());
			  incentiveSpecificDetails.setIsfelepodownCommentdis(IncentiveDetail.getIsfelepodownCommentdis());
			  incentiveSpecificDetails.setIsfdifferabilCommentdis(IncentiveDetail.getIsfdifferabilCommentdis());
			 
			
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
		return new ModelAndView("/Disbursement/DisFacilitiesReliefSought");

	}
	
	@PostMapping(value = "/addIsfCommentsdis")
	public ModelAndView addIsfCommentsdis(
			@ModelAttribute("incentiveDeatilsData") @Validated IncentiveSpecificDetails isd, HttpSession session) {
		
		String role=(String) session.getAttribute("role");
		
		
		if(role.equalsIgnoreCase("ConDept0")) {

		incentiveDetailsService.updateCommentsConDeptDis0(applicantId, isd.getIsfMandiCommentdis());
		}
		
		if(role.equalsIgnoreCase("ConDept25")) {

			incentiveDetailsService.updateCommentsConDeptDis25(applicantId, isd.getIsfElecdutyCommentdis(), isd.getIsfelepodownCommentdis());
			}
		if(role.equalsIgnoreCase("ConDept5")) {

			incentiveDetailsService.updateCommentsConDeptDis5(applicantId, isd.getIsfStampCommentdis(), isd.getIsfStampremCommentdis(), isd.getIsfStampscstCommentdis());
			
			}	
		if(role.equalsIgnoreCase("ConDept4")) {
			  
			  incentiveDetailsService.updateCommentsConDeptDis4(applicantId, isd.getIsfepfCommentdis(),
			  isd.getIsfepfaddCommentdis(), isd.getIsfepfscCommentdis(), isd.getIsfdifferabilCommentdis());			  
			  }
		if(role.equalsIgnoreCase("ConDept2")) {

			incentiveDetailsService.updateCommentsConDeptDis2(applicantId, isd.getIsfSgstCommentdis(), isd.getIsfScstCommentdis(),
					isd.getIsffwCommentdis(), isd.getIsfBplCommentdis(),   isd.getIsfcapisaCommentdis(),
					isd.getIsfcapisCommentdis(), isd.getIsfinfaCommentdis(), isd.getIsfinfCommentdis(), isd.getIsfloanCommentdis(),
					isd.getIsfdisCommentdis());
			
			}
		return new ModelAndView("redirect:/DisfacilitiesReliefSought");

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
