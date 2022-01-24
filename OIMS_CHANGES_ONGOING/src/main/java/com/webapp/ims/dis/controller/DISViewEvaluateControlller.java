/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.dis.controller;

import static com.webapp.ims.exception.GlobalConstants.EVALUATE_PROJECT_DETAILS;
import static com.webapp.ims.exception.GlobalConstants.EXISTING_PROJECT;
import static com.webapp.ims.exception.GlobalConstants.NATURE_OF_PROJECT;
import static com.webapp.ims.exception.GlobalConstants.NEW_PROJECT;
import static com.webapp.ims.exception.GlobalConstants.PRODUCTS;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.controller.ApplicationDetailsViewController;
import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.dis.model.DISPrepareAgendaNotes;
import com.webapp.ims.dis.model.DisEligibleAmtCIS;
import com.webapp.ims.dis.model.DisEligibleAmtIIS;
import com.webapp.ims.dis.model.DisEvaluationBankDetails;
import com.webapp.ims.dis.model.DisEvaluationPandMBankDetails;
import com.webapp.ims.dis.model.DisEvaluationReimbrsDepositeGstTable;
import com.webapp.ims.dis.model.DisReimDisallowedInput;
import com.webapp.ims.dis.model.DisReimbrsDepositeGstTable;
import com.webapp.ims.dis.model.DisViewEvaluate;
import com.webapp.ims.dis.model.Discis;
import com.webapp.ims.dis.model.Disiis;
import com.webapp.ims.dis.model.DissbursmentApplicantDetails;
import com.webapp.ims.dis.model.EPFComputationAndEligibility;
import com.webapp.ims.dis.model.ElectricityDutyExemption;
import com.webapp.ims.dis.model.ExistProjDisbursement;
import com.webapp.ims.dis.model.MandiFeeExemption;
import com.webapp.ims.dis.model.NewProjDisbursement;
import com.webapp.ims.dis.model.SGSTReimExpanDivers;
import com.webapp.ims.dis.model.SGSTReimTurnOver;

import com.webapp.ims.dis.model.StampDutyExemption;
import com.webapp.ims.dis.repository.CapitalInvestRepository;
import com.webapp.ims.dis.repository.DISPrepareAgendaNotesRepository;
import com.webapp.ims.dis.repository.DisEPFCompAndElgblRepository;
import com.webapp.ims.dis.repository.DisEPFRepository;
import com.webapp.ims.dis.repository.DisElectricityDutyExemptionRepository;
import com.webapp.ims.dis.repository.DisMandiFeeExemptionRepository;
import com.webapp.ims.dis.repository.DisStampDeautyRepository;
import com.webapp.ims.dis.repository.DisViewEvaluateRepository;
import com.webapp.ims.dis.repository.DisbursmentCisIncentiveRepository;
import com.webapp.ims.dis.repository.DisbursmentIisIncentiveRepository;
import com.webapp.ims.dis.repository.DisbursmentReimbrsofDepositGSTRepository;
import com.webapp.ims.dis.repository.NewProjDisburseRepository;
import com.webapp.ims.dis.repository.OtherIncRepository;
import com.webapp.ims.dis.repository.ProjDisburseRepository;
import com.webapp.ims.dis.repository.ReimDisAllowedRepository;
import com.webapp.ims.dis.repository.ReimbrsGSTTableRepository;
import com.webapp.ims.dis.repository.SGSTReimClaimTurnOverRepo;
import com.webapp.ims.dis.repository.SGSTReimExpDiversRepo;
import com.webapp.ims.dis.repository.StampDutyApplicationFormRepo;
import com.webapp.ims.dis.repository.StampDutyExemptionRepository;
import com.webapp.ims.dis.service.DisEligibleAmtCISService;
import com.webapp.ims.dis.service.DisEligibleAmtIISService;
import com.webapp.ims.dis.service.DisEvaluationDocumentService;
import com.webapp.ims.dis.service.DisEvaluationReimbrsGStTableService;
import com.webapp.ims.dis.service.DisReimbrsGStTableService;
import com.webapp.ims.dis.service.DisViewEvaluateService;
import com.webapp.ims.dis.service.DisbursmentApplicantDetailsService;
import com.webapp.ims.dis.service.DisbursmentCisService;
import com.webapp.ims.dis.service.DisbursmentIisService;
import com.webapp.ims.food.Model.DetailsofEligibleTechnicalCivilWork;
import com.webapp.ims.food.Model.DetailsofProposedPlantAndMachinerytobeInstalled;
import com.webapp.ims.food.Model.ISEligibleTechnicalCivilWork;
import com.webapp.ims.login.model.Login;
//import com.webapp.ims.login.service.LoginService;
import com.webapp.ims.model.BusinessEntityDetails;
import com.webapp.ims.model.DeptBusinessEntityDetails;
import com.webapp.ims.model.DeptIncentiveDetails;
import com.webapp.ims.model.Dept_PhaseWiseInvestmentDetails;
import com.webapp.ims.model.EvaluateExistNewProjDetails;
import com.webapp.ims.model.EvaluateInvestmentDetails;
import com.webapp.ims.model.EvaluateProjectDetails;
import com.webapp.ims.model.ExistingProjectDetails;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.IncentiveSpecificDetails;
import com.webapp.ims.model.InvestmentDetails;
import com.webapp.ims.model.NewProjectDetails;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.ProjectDetails;
import com.webapp.ims.model.RaiseQuery;
import com.webapp.ims.repository.DeptBusinessEntityDetailsRepository;
import com.webapp.ims.repository.DeptIncentiveDetailsRepository;
import com.webapp.ims.repository.DeptPhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.repository.DeptProposedEmploymentDetailsRepository;
import com.webapp.ims.repository.EvaluateAuditTrialRepository;
import com.webapp.ims.repository.EvaluateExistNewProjDetailsRepository;
import com.webapp.ims.repository.ExistingProjectDetailsRepository;
import com.webapp.ims.repository.InvestmentDetailsRepository;
import com.webapp.ims.repository.LoanBankDetailsRepository;
import com.webapp.ims.repository.NewProjectRepository;
import com.webapp.ims.repository.PandMBankDetailsRepository;
import com.webapp.ims.repository.PhaseWiseInvestmentDetailsRepository;
import com.webapp.ims.repository.ProjectRepository;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.EvaluateCapInvestService;
import com.webapp.ims.service.EvaluateInvestMentDetailsService;
import com.webapp.ims.service.EvaluateMeanofFinanceService;
import com.webapp.ims.service.EvaluateProjectDetailsService;
import com.webapp.ims.service.EvaluationAuditTrailService;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.InvestmentDetailsService;
import com.webapp.ims.service.NewProjectDetailsService;
import com.webapp.ims.service.PrepareAgendaNotesService;
import com.webapp.ims.service.ProjectService;
import com.webapp.ims.service.ProposedEmploymentDetailsService;
import com.webapp.ims.service.impl.AdditionalInterest;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblUsersService;

/**
 * @author dell
 */
@Controller
public class DISViewEvaluateControlller {
	private final Logger logger = LoggerFactory.getLogger(DISViewEvaluateControlller.class);

	@Autowired
	DisbursmentApplicantDetailsService disApplicantService;

	@Autowired
	private ProjectService projectService;
	
	@Autowired
	SGSTReimClaimTurnOverRepo sgstReimClaimTurnOverRepo;

	@Autowired
	BusinessEntityDetailsService businessService;
	
	@Autowired
	SGSTReimExpDiversRepo sgstReimExpDiversRepo;

	@Autowired
	EvaluateInvestMentDetailsService evaluateInvestMentDetailsService;

	@Autowired
	ApplicationDetailsViewController applicantViewController;

	@Autowired
	PhaseWiseInvestmentDetailsRepository pwRepository;

	@Autowired
	CapitalInvestRepository capitalInvestRepository;
	
	

	@Autowired
	DisbursmentCisService disbursmentCisService;
	@Autowired
	DisbursmentIisService disbursmentIisService;

	@Autowired
	DisViewEvaluateService disViewEvaluateService;

	@Autowired
	DisEligibleAmtCISService disEligibleAmtCISService;
	
	@Autowired
	StampDutyExemptionRepository stampDutyExemptionRepository;

	@Autowired
	DisEligibleAmtIISService disEligibleAmtIISService;
	
	@Autowired
	ReimbrsGSTTableRepository reimbrsGSTTableRepository;

	@Autowired
	DisEPFCompAndElgblRepository disEPFCompAndElgblRepository;

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
	PrepareAgendaNotesService prepareAgendaNotesService;
	@Autowired
	EvaluateProjectDetailsService evaluateProjectDetailsService;

	@Autowired
	InvestmentDetailsRepository investRepository;

	@Autowired
	AdditionalInterest additionalInterest;

	@Autowired
	private DeptProposedEmploymentDetailsRepository deptPrpsEmplRepository;

	@Autowired
	private ProposedEmploymentDetailsService emplDtlService;

	@Autowired
	NewProjDisburseRepository newProjDisburseRepository;

	@Autowired
	ProjDisburseRepository projDisburseRepository;

	@Autowired
	DeptPhaseWiseInvestmentDetailsRepository deptPWInvRepository;

	@Autowired
	LoanBankDetailsRepository loanBankDetailsRepository;

	@Autowired
	PandMBankDetailsRepository pAndmBankDetailsRepository;

	@Autowired
	InvestmentDetailsService investmentDetailsService;

	@Autowired
	IncentiveDetailsService incentiveDetailsService;

	@Autowired
	private TblUsersService loginService;

	@Autowired
	private EvaluateCapInvestService evalCapInvService;

	@Autowired
	private EvaluateMeanofFinanceService evalMOf;

	@Autowired
	ProjectRepository projectRepository;

	@Autowired
	DisViewEvaluateRepository disViewEvaluateRepository;

	@Autowired
	public EvaluationAuditTrailService evaluationAuditTrailService;

	@Autowired
	public EvaluateAuditTrialRepository evaluateAuditTrialRepository;
	@Autowired
	private DeptIncentiveDetailsRepository deptIncRepository;

	@Autowired
	public EvaluateExistNewProjDetailsRepository evaluateExistNewProjDetailsRepository;

	@Autowired
	private NewProjectDetailsService newProjectService;

	@Autowired
	private ExistingProjectDetailsRepository existProjRepository;

	@Autowired
	private NewProjectRepository newexistProjRepository;

	@Autowired

	DisbursmentCisIncentiveRepository disbursmentCisIncentiveRepository;

	@Autowired
	DisbursmentIisIncentiveRepository disbursmentIisIncentiveRepository;

	@Autowired
	DisbursmentReimbrsofDepositGSTRepository disbursmentReimbrsofDepositGSTRepository;

	@Autowired
	DisEPFRepository disEPFRepository;

	@Autowired
	OtherIncRepository otherIncRepository;
	
	@Autowired
	ReimDisAllowedRepository reimDisAllowedRepository;

	

	@Autowired
	StampDutyApplicationFormRepo stampDutyApplicationFormRepo;

	@Autowired
	DisStampDeautyRepository disStampDeautyRepository;

	private List<DisReimbrsDepositeGstTable> disReimbrsGSTTableList = new LinkedList<>();

	private List<DisEvaluationReimbrsDepositeGstTable> disEvalReimbrsGSTTableList = new LinkedList<>();

	@Autowired
	DisReimbrsGStTableService disReimbrsGStTableService;

	@Autowired
	DisEvaluationReimbrsGStTableService disEvaluationReimbrsGStTableService;

	private AtomicInteger atomicInteger = new AtomicInteger();

	@GetMapping(value = "/evaluateApplicationDis")
	public ModelAndView evaluateApplicationDis(@ModelAttribute("disViewEvaluate") DisViewEvaluate disViewEvaluate,
			@RequestParam(value = "applicantId", required = false) String applId, Model model,
			HttpServletRequest request, HttpSession session) {

		logger.debug("Evaluate Application Start");
		System.out.println("kkkkkkkk" + applId);

		String appId = (String) session.getAttribute("appId");
		String appStr = appId.substring(0, appId.length() - 2);
		String cisInvId = appStr + "CI";
		String projId = appStr + "P1"; 
		
		
		SGSTReimExpanDivers sgstTReimExpanDivers=sgstReimExpDiversRepo.findBySgstExpApcId(appId);
		if(sgstTReimExpanDivers !=null)
		{
			model.addAttribute("sgstduration", sgstTReimExpanDivers.getFinancialYrPeriod());
			model.addAttribute("sgstIncrement", sgstTReimExpanDivers.getIncreTurnover());
			
			model.addAttribute("sgstPartFull", sgstTReimExpanDivers.getTurnOverPartFullYrDivers());
			model.addAttribute("ttlNetSGSTPaidFinYer", sgstTReimExpanDivers.getTtlNetSGSTPaidFinYr());
			model.addAttribute("ttlNetSGSTPaidIncreTurnOvers", sgstTReimExpanDivers.getTtlNetSGSTPaidIncreTurnOver());
			
			model.addAttribute("amtNetSGSTReimCliams", sgstTReimExpanDivers.getAmtNetSGSTReimCliam());//amtOfNetSGSTReimEligibility
			model.addAttribute("amtOfNetSGSTReimEligibilitys", sgstTReimExpanDivers.getAmtOfNetSGSTReimEligibility());//amtOfNetSGSTReimEligibility
		}
		SGSTReimTurnOver turnOver = sgstReimClaimTurnOverRepo.findMaxTurnOverBaseProduction();
		if(turnOver !=null)
		{
		 model.addAttribute("turnOverFinYr", turnOver.getSgstFinYr());
		 model.addAttribute("turnOverBaseProduction", turnOver.getSgstTurnOverBaseProduction());
		}
		List<StampDutyExemption> stampAppList=stampDutyExemptionRepository.findAll();    
		if(stampAppList.size()>0)
		{
			model.addAttribute("stampAppList", stampAppList);
		}
		
		List<EPFComputationAndEligibility> allEpfClaim=disEPFCompAndElgblRepository.findAll();
		if(allEpfClaim.size()>0)
		{
		model.addAttribute("allEpfClaim", allEpfClaim);
		}
		
		
		/*
		 * List<ElectricityDutyExemption>
		 * electricityDutyList=disElectricityDutyExemptionRepository.findAll();
		 * if(electricityDutyList.size()>0) { model.addAttribute("electricityDutyList1",
		 * electricityDutyList); }
		 */
		 
		
		 String capitivePower = "CaptivePower";
		  String powerDrawn = "PowerDrawn";
		  
		  List<ElectricityDutyExemption> electricityDutyList11=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(appId,capitivePower);
		  List<ElectricityDutyExemption> electricityDutyList22=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(appId,powerDrawn);
		  
		  if(electricityDutyList11.size()>0)
		  {
			  
			  model.addAttribute("electricityDutyList1", electricityDutyList11);
		  }
		  
		  if(electricityDutyList22.size()>0)
		  {
			  model.addAttribute("electricityDutyList2", electricityDutyList22);
		  }
		  
		  List<MandiFeeExemption> mandiFeeList=disMandiFeeExemptionRepository.findAll();
		  if(mandiFeeList.size()>0)
		  {
			model.addAttribute("mandiFeeList", mandiFeeList) ; 
		  }
		  
		  
		  List<DisReimDisallowedInput> disReimDisallowedInput=reimDisAllowedRepository.findAll();
		  if(disReimDisallowedInput.size()>0)
		  {
			  model.addAttribute("disReimDisallowedInput", disReimDisallowedInput);
		  }
		
		  
		  List<DisReimbrsDepositeGstTable> disReimbrsDepositeGstTblList=reimbrsGSTTableRepository.findAll();
		  if(disReimbrsDepositeGstTblList.size()>0){
			  model.addAttribute("disReimbrsDepositeGstTblList", disReimbrsDepositeGstTblList);
			  
		  }
		  
		  List<SGSTReimTurnOver> sGSTReimTurnOverData=sgstReimClaimTurnOverRepo.findAll();
		
		  if(sGSTReimTurnOverData.size()>0)
		  {
			  
			  model.addAttribute("sGSTReimTurnOverData", sGSTReimTurnOverData);
			  
			  
		  }
		
		  
		/*
		 * DisViewEvaluate disViewEvaluateFinYr=null;
		 * 
		 * disViewEvaluateFinYr=disViewEvaluateRepository.findDisViewEvaluateToAppId(
		 * appId); System.out.println(disViewEvaluateFinYr);
		 * System.out.println("turnOver"+disViewEvaluateFinYr.getTurnoverOfProduction1()
		 * );
		 * 
		 * Long p1=disViewEvaluateFinYr.getTurnoverOfProduction1(); Long
		 * p2=disViewEvaluateFinYr.getTurnoverOfProduction2(); Long
		 * p3=disViewEvaluateFinYr.getTurnoverOfProduction3(); Long
		 * p4=disViewEvaluateFinYr.getTurnoverOfProduction4(); Long
		 * p5=disViewEvaluateFinYr.getTurnoverOfProduction5(); { Long arr[] = {p1,p2,
		 * p3, p4, p5};
		 * 
		 * // Method to find maximum in arr[]
		 * 
		 * int i;
		 * 
		 * // Initialize maximum element Long max = arr[0];
		 * 
		 * // Traverse array elements from second and // compare every element with
		 * current max for (i = 1; i < arr.length; i++) if (arr[i] > max) max = arr[i];
		 * System.out.println("max no is"+max);
		 * 
		 * }
		 */
		
		List<StampDutyExemption> stampDutyExemptionList=stampDutyExemptionRepository.findAll();
		if(stampDutyExemptionList.size()>0)
		{
			
			model.addAttribute("stampAppList", stampDutyExemptionList);
			
		}
		else
		{
			model.addAttribute("stampAppList", stampDutyExemptionList);
			
		}
		  
		PrepareAgendaNotes prepagendanote = prepareAgendaNotesService.findPrepAgendaNotesByAppliId(appId);
		model.addAttribute("locDate", prepagendanote.getModifyDate());

		DISPrepareAgendaNotes disPrepareAgendaNotesList = prepareAgendaNotesRepository
				.findDisPrepAgendaNotesByAppliId(appId);
		if (disPrepareAgendaNotesList != null) {
			model.addAttribute("enableIncludeAgenda", disPrepareAgendaNotesList);
		}

		DissbursmentApplicantDetails disApplicantDetails = new DissbursmentApplicantDetails();
		disApplicantDetails = disApplicantService.getDetailsBydisAppId(appId);
		model.addAttribute("disApplicantDetails", disApplicantDetails);

		businessService.commonDetails(appId, session, model);

		DeptBusinessEntityDetails deptBusEntFromDb = deptBusEntRepository.findByApplicantDetailId(appId);
		// fetch BusinessEntity documents from MongoDB
		applicantViewController.businessDocFromMongoDB(deptBusEntFromDb.getId(), model);

		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);

		EvaluateInvestmentDetails evaluateInvestMentDetails = null;
		if (evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId) != null) {
			evaluateInvestMentDetails = evaluateInvestMentDetailsService.getEvalInvestDetByapplId(appId);
		}
		System.out.println(evaluateInvestMentDetails.getInvCommenceDate());
		String category = additionalInterest.getCategory(appId);

		if (category.equalsIgnoreCase("Mega") || category.equalsIgnoreCase("Mega Plus")) {
			Date today = evaluateInvestMentDetails.getInvCommenceDate();
			// LocalDateTime dateTime = LocalDateTime.ofInstant(today.toInstant(),
			// ZoneId.systemDefault());

			// LocalDate date = dateTime.toLocalDate().plusYears(5);
			// System.out.println(date);
			model.addAttribute("toDate", today);
		}

		if (category.equalsIgnoreCase("Large")) {
			Date today = evaluateInvestMentDetails.getInvCommenceDate();
			// LocalDateTime dateTime = LocalDateTime.ofInstant(today.toInstant(),
			// ZoneId.systemDefault());

			// LocalDate date = dateTime.toLocalDate().plusYears(4);
			// System.out.println(date);
			model.addAttribute("toDate", today);
		}

		if (category.equalsIgnoreCase("Super Mega")) {
			Date today = evaluateInvestMentDetails.getInvCommenceDate();
			// LocalDateTime dateTime = LocalDateTime.ofInstant(today.toInstant(),
			// ZoneId.systemDefault());

			// LocalDate date = dateTime.toLocalDate().plusYears(7);
			// System.out.println(date);
			model.addAttribute("toDate", today);
		}

		model.addAttribute("evaluateInvestMentDetails", evaluateInvestMentDetails);

		model.addAttribute("categoryregion", category);
		if (category.equalsIgnoreCase("Small")) {
			model.addAttribute("category", "90%");
			// model.addAttribute("categoryofindustry", "0.90");
			List<DisReimbrsDepositeGstTable> disReimbrsGSTList = new LinkedList<>();
			disReimbrsGSTTableList = disReimbrsGStTableService.getListBydisAppId(appId);
			for (DisReimbrsDepositeGstTable disReimbrsDepositeGstTable : disReimbrsGSTTableList) {

				DisReimbrsDepositeGstTable disReimbrsDepositeGstTabl1 = new DisReimbrsDepositeGstTable();
				Float categoryofindustry = (float) 0.90;
				String amtnetgst = disReimbrsDepositeGstTable.getAmtNetSgst();
				long amtnetgstconvrt = Long.parseLong(amtnetgst);

				long reimnewsgst11 = (long) (amtnetgstconvrt * categoryofindustry);
				disReimbrsDepositeGstTabl1 = disReimbrsDepositeGstTable;
				disReimbrsDepositeGstTabl1.setAmtnetconvert(reimnewsgst11);
				disReimbrsGSTList.add(disReimbrsDepositeGstTabl1);
			}
			model.addAttribute("disReimbrsGSTTableList", disReimbrsGSTList);
		} else if (category.equalsIgnoreCase("Medium") || category.equalsIgnoreCase("Large")) {
			model.addAttribute("category", "60%");
			// model.addAttribute("categoryofindustry", "0.60");
			List<DisReimbrsDepositeGstTable> disReimbrsGSTList = new LinkedList<>();
			disReimbrsGSTTableList = disReimbrsGStTableService.getListBydisAppId(appId);
			for (DisReimbrsDepositeGstTable disReimbrsDepositeGstTable : disReimbrsGSTTableList) {

				DisReimbrsDepositeGstTable disReimbrsDepositeGstTabl1 = new DisReimbrsDepositeGstTable();
				Float categoryofindustry = (float) 0.60;
				String amtnetgst = disReimbrsDepositeGstTable.getAmtNetSgst();
				long amtnetgstconvrt = Long.parseLong(amtnetgst);

				long reimnewsgst11 = (long) (amtnetgstconvrt * categoryofindustry);
				disReimbrsDepositeGstTabl1 = disReimbrsDepositeGstTable;
				disReimbrsDepositeGstTabl1.setAmtnetconvert(reimnewsgst11);
				disReimbrsGSTList.add(disReimbrsDepositeGstTabl1);
			}
			model.addAttribute("disReimbrsGSTTableList", disReimbrsGSTList);
		} else {
			model.addAttribute("category", "70%");
			/*
			 * // model.addAttribute("categoryofindustry", "0.70");
			 * List<DisReimbrsDepositeGstTable> disReimbrsGSTList = new LinkedList<>();
			 * disReimbrsGSTTableList = disReimbrsGStTableService.getListBydisAppId(appId);
			 * for (DisReimbrsDepositeGstTable disReimbrsDepositeGstTable :
			 * disReimbrsGSTTableList) {
			 * 
			 * DisReimbrsDepositeGstTable disReimbrsDepositeGstTabl1 = new
			 * DisReimbrsDepositeGstTable(); Float categoryofindustry = (float) 0.70; String
			 * amtnetgst = disReimbrsDepositeGstTable.getAmtNetSgst(); long amtnetgstconvrt
			 * = Long.parseLong(amtnetgst);
			 * 
			 * long reimnewsgst11 = (long) (amtnetgstconvrt * categoryofindustry);
			 * disReimbrsDepositeGstTabl1 = disReimbrsDepositeGstTable;
			 * disReimbrsDepositeGstTabl1.setAmtnetconvert(reimnewsgst11);
			 * disReimbrsGSTList.add(disReimbrsDepositeGstTabl1); }
			 * model.addAttribute("disReimbrsGSTTableList", disReimbrsGSTList);
			 */
		}

		String expension = evaluateProjectDetails.getExpansion() != null ? evaluateProjectDetails.getExpansion() : "";
		String diversification = evaluateProjectDetails.getDiversification() != null
				? evaluateProjectDetails.getDiversification()
				: "";

		if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(EXISTING_PROJECT)) {
			// fetch documents from MongoDB
			applicantViewController.existProjDocsFromMongoDB(evaluateProjectDetails.getId(), model);
			model.addAttribute(NATURE_OF_PROJECT, expension);
			if (!expension.isEmpty() && !diversification.isEmpty()) {
				model.addAttribute(NATURE_OF_PROJECT, expension + "/ " + diversification);
			} else if (!diversification.isEmpty()) {
				model.addAttribute(NATURE_OF_PROJECT, diversification);
			}
			List<EvaluateExistNewProjDetails> evaluateExistNewProjDetailsList = evaluateExistNewProjDetailsRepository
					.getEvalExistProjListByepdProjDtlId(evaluateProjectDetails.getId());
			model.addAttribute("evaluateExistNewProjDetailsList", evaluateExistNewProjDetailsList);

		} else {
			// fetch documents from MongoDB
			applicantViewController.newProjDocMongoDB(evaluateProjectDetails.getId(), model);

			model.addAttribute(NATURE_OF_PROJECT, evaluateProjectDetails.getNatureOfProject());
			List<NewProjectDetails> newProjList = newProjectService.getNewProjListById(projId);
			model.addAttribute("newProjList", newProjList);

			model.addAttribute("newProjProdDetail", evaluateProjectDetails.getProductDetails());

		}
		// Phase wise list
		List<PhaseWiseInvestmentDetails> pwInvDetailsList = pwRepository.findByPwApcId(appId);
		model.addAttribute("pwInvDetailsList", pwInvDetailsList);

		List<Dept_PhaseWiseInvestmentDetails> dept_PhaseWiseInvestmentDetails = deptPWInvRepository
				.findByPwApcId(appId);
		model.addAttribute("dept_PhaseWiseInvestmentDetails", dept_PhaseWiseInvestmentDetails);

		// bank loan details
		List<DisEvaluationBankDetails> disEvaluateBankDetailsList = loanBankDetailsRepository.findByapcId(appId);
		model.addAttribute("disEvaluateBankDetailsList", disEvaluateBankDetailsList);

		// Amount of Loan Disbursed towards investment in Plant & Machinery with dates
		// of disbursement
		List<DisEvaluationPandMBankDetails> disEvaluatePandMBankDetailsList = pAndmBankDetailsRepository
				.findByapcId(appId);
		model.addAttribute("disEvaluatePandMBankDetailsList", disEvaluatePandMBankDetailsList);

		// Capital Details
		CapitalInvestmentDetails capInvDetails = capitalInvestRepository.getDetailsBycapInvId(cisInvId);
		NewProjDisbursement newproject = newProjDisburseRepository.getDetailsBynewprojApcId(appId);
		ExistProjDisbursement existproject = projDisburseRepository.getDetailsByprojApcId(appId);

		// sachin

		model.addAttribute("TotalLoan", existproject.getTotalLoan());
		System.out.println(existproject.getTotalLoan());
		model.addAttribute("Totalinterest", existproject.getTotalInterest());
		model.addAttribute("capInvDetails", capInvDetails);
		if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(EXISTING_PROJECT)) {
			model.addAttribute("newtotal", newproject.getTotal());
			model.addAttribute("newpnm", existproject.getProjDisPlantMachCost());
			model.addAttribute("newinfra", existproject.getProjDisInfra());
			/* if (!expension.isEmpty() && !diversification.isEmpty()) { */

		} else {
			model.addAttribute("newtotal", newproject.getTotal());
			model.addAttribute("newpnm", newproject.getNewprojPlantMachCost());
			model.addAttribute("newinfra", newproject.getNewprojInfra());
		}
		// for show hide
		IncentiveDetails incentive = incentiveDetailsService.getIncentiveByisfapcId(appId);
		Long sgst = incentive.getISF_Ttl_SGST_Reim();
		model.addAttribute("sgst", sgst);
		Long stamp = incentive.getISF_Ttl_Stamp_Duty_EX();
		model.addAttribute("stamp", stamp);
		Long epf = incentive.getISF_Ttl_EPF_Reim();
		model.addAttribute("epf", epf);
		Long infra = incentive.getISF_Total_Int_Subsidy();
		model.addAttribute("disiis", infra);
		Long cis = incentive.getISF_Cis();
		// model.addAttribute("discis", cis);
		Long qis = incentive.getISF_Loan_Subsidy();
		model.addAttribute("qis", qis);
		Long taxcreadit = incentive.getISF_Tax_Credit_Reim();
		model.addAttribute("taxcreadit", taxcreadit);
		// Long payroll = incentive.getISF_Indus_Payroll_Asst() == null ? 0 :
		// incentive.getISF_Indus_Payroll_Asst();

		Long elecdutyCap = incentive.getISF_EX_E_Duty() == null ? 0 : incentive.getISF_EX_E_Duty();
		Long elecdutyCapPC = incentive.getISF_EX_E_Duty_PC() == null ? 0 : incentive.getISF_EX_E_Duty_PC();
		if (elecdutyCap != null || elecdutyCapPC != null) {
			model.addAttribute("electric", "electric");
		}
		Long mandi = incentive.getISF_EX_Mandee_Fee() == null ? 0 : incentive.getISF_EX_Mandee_Fee();
		model.addAttribute("mandi", mandi);

		// CIS
		long eligibleFixedCapitalInvestment = additionalInterest.getDISEligibleFixedCapitalInvestment(appId);
		String cisid = appId + "CIS";
		Discis discis = new Discis();
		discis = disbursmentCisService.getDiscisBydiscisId(cisid);
		model.addAttribute("discis", discis);
		String iisid = appId + "IIS";
		Disiis disiis = new Disiis();
		disiis = disbursmentIisService.getDisiisBydisiisId(iisid);
		model.addAttribute("disiis", disiis);
		discis.getRoi();
		model.addAttribute("roi", discis.getRoi());

		if (discis.getBankcert().equals("Yes")) {
			model.addAttribute("plantnmachine", discis.getPnmloan());
		} else {
			model.addAttribute("plantnmachine", discis.getAmtInvPlantMachin());
		}

		String equity = additionalInterest.getaddCIS(appId);
		if (discis != null) {
			model.addAttribute("bankcert", discis.getBankcert());

			model.addAttribute("cisincentiveDeatilsForm", discis);
			long noPnM;

			if (discis.getBankcert().equals("No")) {
				noPnM = additionalInterest.getDISPlantnMachineAmount(appId);
				model.addAttribute("bankcertdoc", "Proportionate interest for P&M at Applicable Rate of Interest");
			} else {
				noPnM = discis.getPnmloan();
				model.addAttribute("bankcertdoc", "Interest for P&M at Applicable Rate of Interest");
			}

			double propnateValue = Math.abs(noPnM / eligibleFixedCapitalInvestment);
			System.out.println(noPnM / eligibleFixedCapitalInvestment);

			model.addAttribute("propnateValue", propnateValue);
			model.addAttribute("eligibleFixedCapitalInvestment", eligibleFixedCapitalInvestment);
			model.addAttribute("noPnM", noPnM);
			ProjectDetails projectDetail = projectService.getProjDetById(projId);
			String district = projectDetail.getDistrictName();
			String region = projectDetail.getResionName();
			model.addAttribute("eligiblereg", region);
			model.addAttribute("eligibledis", district);

			double A = existproject.getTotalLoan();
			double B = existproject.getTotalInterest();
			long C = discis.getTotal();
			float D = discis.getRoi();
			long E = discis.getTotalint();

			// (5/E)
			double u;

			if (equity == "eligible") {
				model.addAttribute("stampadditional", 0.2);
				model.addAttribute("additional", 7.5);
				u = (7.5 / D);
			} else {
				model.addAttribute("additional", 5);
				model.addAttribute("stampadditional", 0.0);
				u = (5.0 / D);
			}
			// (B/A)*C

			double z = Math.abs(B / A);

			// (B/A)*E

			long propintforPnM = (long) (z * E);

			long loanforPnM = (long) (z * C);

			// (B/A)*E*(5/D)

			long propciS = (long) (propintforPnM * u);

			model.addAttribute("loanforPnM", loanforPnM);

			model.addAttribute("propintforPnM", propintforPnM);
			model.addAttribute("prop5", propciS);

			// Double.toString(d)

			// Eligible Amout of CIS --> Proportionate interest for P&M at Applicable Rate
			// of Interest for first year

			long l = Long.parseLong(discis.getFirstYP());

			long fproposnate = propintforPnM * l;
			model.addAttribute("fproposnate", fproposnate);

			/*
			 * double propfirst = fproposnate * u; if (propfirst > 25000000) {
			 * model.addAttribute("ceiling2", 2500000); } else
			 * model.addAttribute("ceiling2", propfirst); model.addAttribute("propfirst",
			 * String.format("%.2f", propfirst));
			 */

			float roi = discis.getRoi();
			model.addAttribute("roi", roi);
			long fPnMInter = discis.getFirstYI();
			long sPnMInter = discis.getSecondYI();
			long tPnMInter = discis.getThirdYI();
			long fourPnMInter = discis.getFourthYI();
			long fiftPnMInter = discis.getFifthYI();

			float fyearintvsROI = (fPnMInter / roi);
			float fyearintvsROIperc = fyearintvsROI * 100;

			float syearintvsROI = (sPnMInter / roi);
			float syearintvsROIperc = syearintvsROI * 100;

			float tyearintvsROI = (tPnMInter / roi);
			float tyearintvsROIperc = tyearintvsROI * 100;

			float fouryearintvsROI = (fourPnMInter / roi);
			float fouryearintvsROIperc = fouryearintvsROI * 100;

			float fiftyearintvsROI = (fiftPnMInter / roi);
			float fiftyearintvsROIperc = fiftyearintvsROI * 100;

			// String equity = additionalInterest.getaddCIS(appId);
			long finterPnMateligi;
			long sinterPnMateligi;
			long tinterPnMateligi;
			long fourinterPnMateligi;
			long fiftinterPnMateligi;

			if (equity == "eligible") {
				finterPnMateligi = (long) (fyearintvsROIperc * 0.075);
				model.addAttribute("finterPnMateligi", finterPnMateligi);

				sinterPnMateligi = (long) (syearintvsROIperc * 0.075);
				model.addAttribute("sinterPnMateligi", sinterPnMateligi);

				tinterPnMateligi = (long) (tyearintvsROIperc * 0.075);
				model.addAttribute("tinterPnMateligi", tinterPnMateligi);

				fourinterPnMateligi = (long) (fouryearintvsROIperc * 0.075);
				model.addAttribute("fourinterPnMateligi", fourinterPnMateligi);

				fiftinterPnMateligi = (long) (fiftyearintvsROIperc * 0.075);
				model.addAttribute("fiftinterPnMateligi", fiftinterPnMateligi);
			} else {
				finterPnMateligi = (long) (fyearintvsROIperc * 0.05);
				model.addAttribute("finterPnMateligi", finterPnMateligi);

				sinterPnMateligi = (long) (syearintvsROIperc * 0.05);
				model.addAttribute("sinterPnMateligi", sinterPnMateligi);

				tinterPnMateligi = (long) (tyearintvsROIperc * 0.05);
				model.addAttribute("tinterPnMateligi", tinterPnMateligi);

				fourinterPnMateligi = (long) (fouryearintvsROIperc * 0.05);
				model.addAttribute("fourinterPnMateligi", fourinterPnMateligi);

				fiftinterPnMateligi = (long) (fiftyearintvsROIperc * 0.05);
				model.addAttribute("fiftinterPnMateligi", fiftinterPnMateligi);
			}

			if ((fPnMInter <= finterPnMateligi) && (fPnMInter <= 5000000)) {
				System.out.println("Largest is: " + fPnMInter);
				model.addAttribute("ceiling1", fPnMInter);
			} else if (finterPnMateligi <= 5000000) {
				System.out.println("Largest is: " + finterPnMateligi);
				model.addAttribute("ceiling1", finterPnMateligi);
			} else {
				model.addAttribute("ceiling1", 5000000);
				System.out.println("Largest is: " + 5000000);
			}
			if ((sPnMInter <= sinterPnMateligi) && (sPnMInter <= 5000000)) {
				System.out.println("Largest is: " + sPnMInter);
				model.addAttribute("ceiling2", sPnMInter);
			} else if (sinterPnMateligi <= 5000000) {
				System.out.println("Largest is: " + sinterPnMateligi);
				model.addAttribute("ceiling2", finterPnMateligi);
			} else {
				model.addAttribute("ceiling2", 5000000);
				System.out.println("Largest is: " + 5000000);
			}
			if ((tPnMInter <= tinterPnMateligi) && (tPnMInter <= 5000000)) {
				System.out.println("Largest is: " + tPnMInter);
				model.addAttribute("ceiling3", tPnMInter);
			} else if (tinterPnMateligi <= 5000000) {
				System.out.println("Largest is: " + tinterPnMateligi);
				model.addAttribute("ceiling3", tinterPnMateligi);
			} else {
				model.addAttribute("ceiling3", 5000000);
				System.out.println("Largest is: " + 5000000);
			}
			if ((fourPnMInter <= fourinterPnMateligi) && (fourPnMInter <= 5000000)) {
				System.out.println("Largest is: " + fourPnMInter);
				model.addAttribute("ceiling4", fourPnMInter);
			} else if (fourinterPnMateligi <= 5000000) {
				System.out.println("Largest is: " + fourinterPnMateligi);
				model.addAttribute("ceiling4", fourinterPnMateligi);
			} else {
				model.addAttribute("ceiling4", 5000000);
				System.out.println("Largest is: " + 5000000);
			}
			if ((fiftPnMInter <= fiftinterPnMateligi) && (fiftPnMInter <= 5000000)) {
				System.out.println("Largest is: " + fiftPnMInter);
				model.addAttribute("ceiling5", fiftPnMInter);
			} else if (fiftinterPnMateligi <= 5000000) {
				System.out.println("Largest is: " + fiftinterPnMateligi);
				model.addAttribute("ceiling5", fiftinterPnMateligi);
			} else {
				model.addAttribute("ceiling5", 5000000);
				System.out.println("Largest is: " + 5000000);
			}

		}

		// for yearly IIS calculation--Sachin

		if (disiis != null) {
			model.addAttribute("iisincentiveDeatilsForm", disiis);

			long noinfra;
			model.addAttribute("bankcertiis", disiis.getBankcert());
			if (disiis.getBankcert().equals("No")) {
				noinfra = additionalInterest.getDISInfra(appId);
				model.addAttribute("bankcertdocinfra",
						"Proportionate interest for infrastructure at Applicable Rate of Interest");
			} else {
				noinfra = disiis.getIisloan();
				model.addAttribute("bankcertdocinfra", "Interest for infrastructure at Applicable Rate of Interest");
			}
			model.addAttribute("noinfra", noinfra);
			double propnateValue = Math.abs(noinfra / (eligibleFixedCapitalInvestment + noinfra));
			System.out.println(propnateValue);

			// model.addAttribute("propnateValue", propnateValue);
			// model.addAttribute("eligibleFixedCapitalInvestment",
			// eligibleFixedCapitalInvestment);
			model.addAttribute("noinfra", noinfra);

			float roiiis = disiis.getRoi();
			model.addAttribute("roiiis", roiiis);

			long fPnMInteriis = disiis.getFirstYI();
			long sPnMInteriis = disiis.getSecondYI();
			long tPnMInteriis = disiis.getThirdYI();
			long fourPnMInteriis = disiis.getFourthYI();
			long fiftPnMInteriis = disiis.getFifthYI();

			float fyearintvsROIiis = (fPnMInteriis / roiiis);
			float fyearintvsROIperciis = fyearintvsROIiis * 100;

			float syearintvsROIiis = (sPnMInteriis / roiiis);
			float syearintvsROIperciis = syearintvsROIiis * 100;

			float tyearintvsROIiis = (tPnMInteriis / roiiis);
			float tyearintvsROIperciis = tyearintvsROIiis * 100;

			float fouryearintvsROIiis = (fourPnMInteriis / roiiis);
			float fouryearintvsROIperciis = fouryearintvsROIiis * 100;

			float fiftyearintvsROIiis = (fiftPnMInteriis / roiiis);
			float fiftyearintvsROIperciis = fiftyearintvsROIiis * 100;

			// String equity = additionalInterest.getaddCIS(appId);
			long finterPnMateligiiis;
			long sinterPnMateligiiis;
			long tinterPnMateligiiis;
			long fourinterPnMateligiiis;
			long fiftinterPnMateligiiis;

			if (equity == "eligible") {
				finterPnMateligiiis = (long) (fyearintvsROIperciis * 0.075);
				model.addAttribute("finterPnMateligiiis", finterPnMateligiiis);

				sinterPnMateligiiis = (long) (syearintvsROIperciis * 0.075);
				model.addAttribute("sinterPnMateligiiis", sinterPnMateligiiis);

				tinterPnMateligiiis = (long) (tyearintvsROIperciis * 0.075);
				model.addAttribute("tinterPnMateligiiis", tinterPnMateligiiis);

				fourinterPnMateligiiis = (long) (fouryearintvsROIperciis * 0.075);
				model.addAttribute("fourinterPnMateligiiis", fourinterPnMateligiiis);

				fiftinterPnMateligiiis = (long) (fiftyearintvsROIperciis * 0.075);
				model.addAttribute("fiftinterPnMateligiiis", fiftinterPnMateligiiis);
			} else {
				finterPnMateligiiis = (long) (fyearintvsROIperciis * 0.05);
				model.addAttribute("finterPnMateligiiis", finterPnMateligiiis);

				sinterPnMateligiiis = (long) (syearintvsROIperciis * 0.05);
				model.addAttribute("sinterPnMateligiiis", sinterPnMateligiiis);

				tinterPnMateligiiis = (long) (tyearintvsROIperciis * 0.05);
				model.addAttribute("tinterPnMateligiiis", tinterPnMateligiiis);

				fourinterPnMateligiiis = (long) (fouryearintvsROIperciis * 0.05);
				model.addAttribute("fourinterPnMateligiiis", fourinterPnMateligiiis);

				fiftinterPnMateligiiis = (long) (fiftyearintvsROIperciis * 0.05);
				model.addAttribute("fiftinterPnMateligiiis", fiftinterPnMateligiiis);
			}

			if ((fPnMInteriis <= finterPnMateligiiis) && (fPnMInteriis <= 10000000)) {
				System.out.println("Largest is: " + fPnMInteriis);
				model.addAttribute("ceiling1iis", fPnMInteriis);
			} else if (finterPnMateligiiis <= 10000000) {
				System.out.println("Largest is: " + finterPnMateligiiis);
				model.addAttribute("ceiling1iis", finterPnMateligiiis);
			} else {
				model.addAttribute("ceiling1iis", 10000000);
				System.out.println("Largest is: " + 10000000);
			}
			if ((sPnMInteriis <= sinterPnMateligiiis) && (sPnMInteriis <= 10000000)) {
				System.out.println("Largest is: " + sPnMInteriis);
				model.addAttribute("ceiling2iis", sPnMInteriis);
			} else if (sinterPnMateligiiis <= 10000000) {
				System.out.println("Largest is: " + sinterPnMateligiiis);
				model.addAttribute("ceiling2iis", finterPnMateligiiis);
			} else {
				model.addAttribute("ceiling2iis", 10000000);
				System.out.println("Largest is: " + 10000000);
			}
			if ((tPnMInteriis <= tinterPnMateligiiis) && (tPnMInteriis <= 10000000)) {
				System.out.println("Largest is: " + tPnMInteriis);
				model.addAttribute("ceiling3iis", tPnMInteriis);
			} else if (tinterPnMateligiiis <= 10000000) {
				System.out.println("Largest is: " + tinterPnMateligiiis);
				model.addAttribute("ceiling3iis", tinterPnMateligiiis);
			} else {
				model.addAttribute("ceiling3iis", 10000000);
				System.out.println("Largest is: " + 10000000);
			}
			if ((fourPnMInteriis <= fourinterPnMateligiiis) && (fourPnMInteriis <= 10000000)) {
				System.out.println("Largest is: " + fourPnMInteriis);
				model.addAttribute("ceiling4iis", fourPnMInteriis);
			} else if (fourinterPnMateligiiis <= 10000000) {
				System.out.println("Largest is: " + fourinterPnMateligiiis);
				model.addAttribute("ceiling4iis", fourinterPnMateligiiis);
			} else {
				model.addAttribute("ceiling4iis", 10000000);
				System.out.println("Largest is: " + 10000000);
			}
			if ((fiftPnMInteriis <= fiftinterPnMateligiiis) && (fiftPnMInteriis <= 10000000)) {
				System.out.println("Largest is: " + fiftPnMInteriis);
				model.addAttribute("ceiling5iis", fiftPnMInteriis);
			} else if (fiftinterPnMateligiiis <= 10000000) {
				System.out.println("Largest is: " + fiftinterPnMateligiiis);
				model.addAttribute("ceiling5iis", fiftinterPnMateligiiis);
			} else {
				model.addAttribute("ceiling5iis", 10000000);
				System.out.println("Largest is: " + 10000000);
			}

		}

		// incentive details
		String isfId = incentiveDetailsService.getIsfIdByApplId(appId);

		IncentiveSpecificDetails incentiveSpecificDetails = new IncentiveSpecificDetails();
		IncentiveDetails IncentiveDetail = incentiveDetailsService.getIncentiveisfById(isfId);
		DeptIncentiveDetails deptIncDetails = deptIncRepository.findById(isfId);

		if (IncentiveDetail != null) {
			// PICUP Officer's Remark
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
			incentiveSpecificDetails.setISF_Ttl_EPF_Reim(IncentiveDetail.getISF_Ttl_EPF_Reim());// 1

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
			incentiveSpecificDetails.setIsfSgstComment(IncentiveDetail.getIsfSgstCommentdis());
			incentiveSpecificDetails.setIsfScstComment(IncentiveDetail.getIsfScstCommentdis());
			incentiveSpecificDetails.setIsffwComment(IncentiveDetail.getIsffwCommentdis());
			incentiveSpecificDetails.setIsfBplComment(IncentiveDetail.getIsfBplCommentdis());
			incentiveSpecificDetails.setIsfElecdutyComment(IncentiveDetail.getIsfElecdutyCommentdis());
			incentiveSpecificDetails.setIsfMandiComment(IncentiveDetail.getIsfMandiCommentdis());

			incentiveSpecificDetails.setIsfStampComment(IncentiveDetail.getIsfStampCommentdis());
			incentiveSpecificDetails.setIsfStampremComment(IncentiveDetail.getIsfStampremCommentdis());
			incentiveSpecificDetails.setIsfStampscstComment(IncentiveDetail.getIsfStampscstCommentdis());
			incentiveSpecificDetails.setIsfepfComment(IncentiveDetail.getIsfepfCommentdis());
			incentiveSpecificDetails.setIsfepfaddComment(IncentiveDetail.getIsfepfaddCommentdis());
			incentiveSpecificDetails.setIsfepfscComment(IncentiveDetail.getIsfepfscCommentdis());
			incentiveSpecificDetails.setIsfcapisComment(IncentiveDetail.getIsfcapisCommentdis());
			incentiveSpecificDetails.setIsfcapisaComment(IncentiveDetail.getIsfcapisaCommentdis());
			incentiveSpecificDetails.setIsfinfComment(IncentiveDetail.getIsfinfCommentdis());
			incentiveSpecificDetails.setIsfinfaComment(IncentiveDetail.getIsfinfaCommentdis());
			incentiveSpecificDetails.setIsfloanComment(IncentiveDetail.getIsfloanCommentdis());
			incentiveSpecificDetails.setIsfdisComment(IncentiveDetail.getIsfdisCommentdis());
			incentiveSpecificDetails.setIsfelepodownComment(IncentiveDetail.getIsfelepodownCommentdis());
			incentiveSpecificDetails.setIsfdifferabilComment(IncentiveDetail.getIsfdifferabilCommentdis());
			incentiveSpecificDetails.setId(IncentiveDetail.getId());

			evaluateProjectDetails.setSubDateAppObserv(deptIncDetails.getSubDateAppObserv());
			try {
				long totOther = incentiveSpecificDetails.getTotal_Other_Incentive() == null ? 0
						: incentiveSpecificDetails.getTotal_Other_Incentive();

				long totIntSub = incentiveSpecificDetails.getISF_Total_Int_Subsidy() == null ? 0
						: incentiveSpecificDetails.getISF_Total_Int_Subsidy();
				long totStamp = incentiveSpecificDetails.getISF_Ttl_Stamp_Duty_EX() == null ? 0
						: incentiveSpecificDetails.getISF_Ttl_Stamp_Duty_EX();
				long totSGST = incentiveSpecificDetails.getISF_Ttl_SGST_Reim() == null ? 0
						: incentiveSpecificDetails.getISF_Ttl_SGST_Reim();
				long totEPF = incentiveSpecificDetails.getISF_Ttl_EPF_Reim() == null ? 0
						: incentiveSpecificDetails.getISF_Ttl_EPF_Reim();

				Long total = totOther + totIntSub + totStamp + totSGST + totEPF;

				model.addAttribute("total", total);
			} catch (Exception e) {
				logger.error(String.format("#### evaluateApplication exception #### %s", e.getMessage()));
			}
		}

		StringBuilder products = new StringBuilder();
		if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(EXISTING_PROJECT)) {
			List<Object[]> existProductsList = existProjRepository.findProductsByProjId(projId);

			List<ExistingProjectDetails> existProductsList1 = existProjRepository.findExistProjListByEpdId(projId);

			List<EvaluateExistNewProjDetails> disEvaluateExistNewProjDetailsList = evaluateExistNewProjDetailsRepository
					.getEvalExistProjListByepdProjDtlId(projId);
			if (disEvaluateExistNewProjDetailsList != null) {
				model.addAttribute("existProductsList1", disEvaluateExistNewProjDetailsList);
			} else {
				model.addAttribute("existProductsList1", existProductsList1);
			}
			if (!existProductsList.isEmpty()) {
				for (Object[] prodArr : existProductsList) {
					if (prodArr.length > 0) {
						for (int k = 0; k < prodArr.length; k++) {
							products = products.append(prodArr[k].toString()).append(", ");
						}
					}
				}
				model.addAttribute(PRODUCTS, products.toString().substring(0, products.toString().lastIndexOf(',')));
			}
		} else if (evaluateProjectDetails.getNatureOfProject().equalsIgnoreCase(NEW_PROJECT)) {

			List<String> newproductsList = newexistProjRepository.findProductsByProjId(projId);
			if (!newproductsList.isEmpty()) {
				for (int k = 0; k < newproductsList.size(); k++) {
					products = products.append(newproductsList.get(k)).append(", ");
				}
				model.addAttribute(PRODUCTS, products.toString().substring(0, products.toString().lastIndexOf(',')));
			} else {
				model.addAttribute(PRODUCTS, "dont have product");
			}
		}

		List<Object> evalObjList = investRepository.getEvalDetailsByApplId(appId);
		model.addAttribute("evalObjList", evalObjList);
		model.addAttribute("appId", appId);
		model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);
		model.addAttribute(EVALUATE_PROJECT_DETAILS, evaluateProjectDetails);

		String evlId = appId + "DEV";
		String disEvalId = appId + "DISEVL";

		DisViewEvaluate disViewEvaluate1 = disViewEvaluateService.getDetailsByEvaluateId(evlId);
		if (disViewEvaluate1 != null) {
			incentiveSpecificDetails.setSgstRemark(disViewEvaluate1.getSgstRemark());
			incentiveSpecificDetails.setScstRemark(disViewEvaluate1.getScstRemark());
			incentiveSpecificDetails.setFwRemark(disViewEvaluate1.getFwRemark());
			incentiveSpecificDetails.setBplRemark(disViewEvaluate1.getBplRemark());
			incentiveSpecificDetails.setStampDutyExemptRemark(disViewEvaluate1.getStampDutyExemptRemark());
			incentiveSpecificDetails.setStampDutyReimRemark(disViewEvaluate1.getStampDutyReimRemark());
			incentiveSpecificDetails.setDivyangSCSTRemark(disViewEvaluate1.getDivyangSCSTRemark());
			incentiveSpecificDetails.setEpfUnsklRemark(disViewEvaluate1.getEpfUnsklRemark());
			incentiveSpecificDetails.setEpfSklUnsklRemark(disViewEvaluate1.getEpfSklUnsklRemark());
			incentiveSpecificDetails.setEpfDvngSCSTRemark(disViewEvaluate1.getEpfDvngSCSTRemark());
			incentiveSpecificDetails.setCapIntSubRemark(disViewEvaluate1.getCapIntSubRemark());
			incentiveSpecificDetails.setAciSubsidyRemark(disViewEvaluate1.getAciSubsidyRemark());
			incentiveSpecificDetails.setInfraIntSubRemark(disViewEvaluate1.getInfraIntSubRemark());
			incentiveSpecificDetails.setAiiSubsidyRemark(disViewEvaluate1.getAiiSubsidyRemark());
			incentiveSpecificDetails.setLoanIntSubRemark(disViewEvaluate1.getLoanIntSubRemark());
			incentiveSpecificDetails.setInputTaxRemark(disViewEvaluate1.getInputTaxRemark());
			incentiveSpecificDetails.setElecDutyCapRemark(disViewEvaluate1.getElecDutyCapRemark());
			incentiveSpecificDetails.setElecDutyDrawnRemark(disViewEvaluate1.getElecDutyDrawnRemark());
			incentiveSpecificDetails.setMandiFeeRemark(disViewEvaluate1.getMandiFeeRemark());
			incentiveSpecificDetails.setDiffAbleWorkRemark(disViewEvaluate1.getDiffAbleWorkRemark());

			model.addAttribute("incentiveDeatilsData", incentiveSpecificDetails);
			// get Mongo file
			disEvaluationDocumentService.evalDocFromMongoDB(disViewEvaluate1, disEvalId, model);

			String elgbleCISId = appId + "EACIS";
			DisEligibleAmtCIS disEligibleAmtCIS = new DisEligibleAmtCIS();
			disEligibleAmtCIS = disEligibleAmtCISService.getDetailsByeligibleAmtId(elgbleCISId);
			if (disEligibleAmtCIS != null) {
				disViewEvaluate1.setfYI(disEligibleAmtCIS.getfYI());
				disViewEvaluate1.setfYII(disEligibleAmtCIS.getfYII());
				disViewEvaluate1.setfYIII(disEligibleAmtCIS.getfYII());
				disViewEvaluate1.setfYIV(disEligibleAmtCIS.getfYIV());
				disViewEvaluate1.setfYV(disEligibleAmtCIS.getfYV());

				disViewEvaluate1.setIntPMI(disEligibleAmtCIS.getIntPMI());
				disViewEvaluate1.setIntPMII(disEligibleAmtCIS.getIntPMII());
				disViewEvaluate1.setIntPMIII(disEligibleAmtCIS.getIntPMIII());
				disViewEvaluate1.setIntPMIV(disEligibleAmtCIS.getIntPMIV());
				disViewEvaluate1.setIntPMV(disEligibleAmtCIS.getIntPMV());

				disViewEvaluate1.setDateofDISI(disEligibleAmtCIS.getDateofDISI());
				disViewEvaluate1.setDateofDISII(disEligibleAmtCIS.getDateofDISII());
				disViewEvaluate1.setDateofDISIII(disEligibleAmtCIS.getDateofDISIII());
				disViewEvaluate1.setDateofDISIV(disEligibleAmtCIS.getDateofDISIV());
				disViewEvaluate1.setDateofDISV(disEligibleAmtCIS.getDateofDISV());

				disViewEvaluate1.setActAmtIPI(disEligibleAmtCIS.getActAmtIPI());
				disViewEvaluate1.setActAmtIPII(disEligibleAmtCIS.getActAmtIPII());
				disViewEvaluate1.setActAmtIPIII(disEligibleAmtCIS.getActAmtIPIII());
				disViewEvaluate1.setActAmtIPIV(disEligibleAmtCIS.getActAmtIPIV());
				disViewEvaluate1.setActAmtIPV(disEligibleAmtCIS.getActAmtIPV());

				disViewEvaluate1.setDateofPI(disEligibleAmtCIS.getDateofPI());
				disViewEvaluate1.setDateofPII(disEligibleAmtCIS.getDateofPII());
				disViewEvaluate1.setDateofPIII(disEligibleAmtCIS.getDateofPIII());
				disViewEvaluate1.setDateofPIV(disEligibleAmtCIS.getDateofPIV());
				disViewEvaluate1.setDateofPV(disEligibleAmtCIS.getDateofPV());

				disViewEvaluate1.setPropIntRoiI(disEligibleAmtCIS.getPropIntRoiI());
				disViewEvaluate1.setPropIntRoiII(disEligibleAmtCIS.getPropIntRoiII());
				disViewEvaluate1.setPropIntRoiIII(disEligibleAmtCIS.getPropIntRoiIII());
				disViewEvaluate1.setPropIntRoiIV(disEligibleAmtCIS.getPropIntRoiIV());
				disViewEvaluate1.setPropIntRoiV(disEligibleAmtCIS.getPropIntRoiV());

				disViewEvaluate1.setPropIntPAI(disEligibleAmtCIS.getPropIntPAI());
				disViewEvaluate1.setPropIntPAII(disEligibleAmtCIS.getPropIntPAII());
				disViewEvaluate1.setPropIntPAIII(disEligibleAmtCIS.getPropIntPAIII());
				disViewEvaluate1.setPropIntPAIV(disEligibleAmtCIS.getPropIntPAIV());
				disViewEvaluate1.setPropIntPAV(disEligibleAmtCIS.getPropIntPAV());

				disViewEvaluate1.setElgAmtcisObserv(disEligibleAmtCIS.getElgAmtcisObserv());

			}
			// Eligible Amt IIS get View Evaluate
			String ElgbleIISId = appId + "EAIIS";
			DisEligibleAmtIIS disEligibleAmtIIS = new DisEligibleAmtIIS();
			disEligibleAmtIIS = disEligibleAmtIISService.getDetailsByeligibleAmtIISId(ElgbleIISId);
			if (disEligibleAmtIIS != null) {
				disViewEvaluate1.setIisFinYr1(disEligibleAmtIIS.getIisFinYr1());
				disViewEvaluate1.setIisFinYr2(disEligibleAmtIIS.getIisFinYr2());
				disViewEvaluate1.setIisFinYr3(disEligibleAmtIIS.getIisFinYr3());
				disViewEvaluate1.setIisFinYr4(disEligibleAmtIIS.getIisFinYr4());
				disViewEvaluate1.setIisFinYr5(disEligibleAmtIIS.getIisFinYr5());

				disViewEvaluate1.setIisTtlLoanAmt1(disEligibleAmtIIS.getIisTtlLoanAmt1());
				disViewEvaluate1.setIisTtlLoanAmt2(disEligibleAmtIIS.getIisTtlLoanAmt2());
				disViewEvaluate1.setIisTtlLoanAmt3(disEligibleAmtIIS.getIisTtlLoanAmt3());
				disViewEvaluate1.setIisTtlLoanAmt4(disEligibleAmtIIS.getIisTtlLoanAmt4());
				disViewEvaluate1.setIisTtlLoanAmt5(disEligibleAmtIIS.getIisTtlLoanAmt5());

				disViewEvaluate1.setPropIntInfra1(disEligibleAmtIIS.getPropIntInfra1());
				disViewEvaluate1.setPropIntInfra2(disEligibleAmtIIS.getPropIntInfra2());
				disViewEvaluate1.setPropIntInfra3(disEligibleAmtIIS.getPropIntInfra3());
				disViewEvaluate1.setPropIntInfra4(disEligibleAmtIIS.getPropIntInfra4());
				disViewEvaluate1.setPropIntInfra5(disEligibleAmtIIS.getPropIntInfra5());

				disViewEvaluate1.setPropIntInfraPA1(disEligibleAmtIIS.getPropIntInfraPA1());
				disViewEvaluate1.setPropIntInfraPA2(disEligibleAmtIIS.getPropIntInfraPA2());
				disViewEvaluate1.setPropIntInfraPA3(disEligibleAmtIIS.getPropIntInfraPA3());
				disViewEvaluate1.setPropIntInfraPA4(disEligibleAmtIIS.getPropIntInfraPA4());
				disViewEvaluate1.setPropIntInfraPA5(disEligibleAmtIIS.getPropIntInfraPA5());

				disViewEvaluate1.setEligibleIIS1(disEligibleAmtIIS.getEligibleIIS1());
				disViewEvaluate1.setEligibleIIS2(disEligibleAmtIIS.getEligibleIIS2());
				disViewEvaluate1.setEligibleIIS3(disEligibleAmtIIS.getEligibleIIS3());
				disViewEvaluate1.setEligibleIIS4(disEligibleAmtIIS.getEligibleIIS4());
				disViewEvaluate1.setEligibleIIS5(disEligibleAmtIIS.getEligibleIIS5());

				disViewEvaluate1.setEligAmtIisObserv(disEligibleAmtIIS.getEligAmtIisObserv());
			}
		

			model.addAttribute("disViewEvaluate", disViewEvaluate1);

			model.addAttribute("raiseQuery", new RaiseQuery());

		} else {
			model.addAttribute("raiseQuery", new RaiseQuery());
			model.addAttribute("disViewEvaluate", disViewEvaluate);

		}
		return new ModelAndView("/Disbursement/evaluateApplicationdis");

	}

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	@PostMapping(value = "/SaveEvaluateApplicationDis")
	public ModelAndView SaveEvaluateApplicationDis(@ModelAttribute("disViewEvaluate") DisViewEvaluate disViewEvaluate,
			Model model, HttpServletRequest request, HttpSession session) throws ParseException {

		logger.debug("Evaluate Application Start");
		String appId = (String) session.getAttribute("appId");
		String evlId = appId + "DEV";
		String epdProjDtlId = appId;
		String appStr = appId.substring(0, appId.length() - 2);
		String projId = appStr + "P1";     

		// Pankaj

		disViewEvaluate.setEvaluateId(evlId);
		disViewEvaluate.setApcId(appId);
		disViewEvaluateService.saveViewEvaluateDetails(disViewEvaluate);
		
		//Table-9 III saving start
		
		SGSTReimExpanDivers sgstReimExpanDivers= new SGSTReimExpanDivers();
		sgstReimExpanDivers.setSgstExpApcId(appId);
		
		sgstReimExpanDivers.setDurationFinYr(disViewEvaluate.getDurationFinYr());
		sgstReimExpanDivers.setTurnoverOfProduction(disViewEvaluate.getTurnoverOfProduction());
		sgstReimExpanDivers.setFinancialYrPeriod(disViewEvaluate.getFinancialYrPeriod());
		sgstReimExpanDivers.setTurnOverPartFullYrDivers(disViewEvaluate.getTurnOverPartFullYrDivers());
		
		sgstReimExpanDivers.setIncreTurnover(disViewEvaluate.getIncreTurnover());
		sgstReimExpanDivers.setTtlNetSGSTPaidFinYr(disViewEvaluate.getTtlNetSGSTPaidFinYr());
		sgstReimExpanDivers.setTtlNetSGSTPaidIncreTurnOver(disViewEvaluate.getTtlNetSGSTPaidIncreTurnOver());
		sgstReimExpanDivers.setAmtNetSGSTReimCliam(disViewEvaluate.getAmtNetSGSTReimCliam());

		sgstReimExpanDivers.setAmtOfNetSGSTReimEligibility(disViewEvaluate.getAmtOfNetSGSTReimEligibility());
		
		sgstReimExpDiversRepo.save(sgstReimExpanDivers);
		
		
		
		int k1 = 0;
		int l1;
		int count11 = 0;
		
		List<StampDutyExemption> stampAppList=stampDutyExemptionRepository.findAll();
		
		
		List<StampDutyExemption> stampDutyList=new ArrayList<StampDutyExemption>();
		for (StampDutyExemption stampData : stampAppList) {

			StampDutyExemption stampDutyExemption=new StampDutyExemption();
			
			stampDutyExemption.setComputationFinYr(stampData.getComputationFinYr());
			stampDutyExemption.setStampDutyDateFrom(stampData.getStampDutyDateFrom());
			stampDutyExemption.setStampDutyDateTo(stampData.getStampDutyDateTo());
			stampDutyExemption.setClaimStampDutyReimAmt(stampData.getClaimStampDutyReimAmt());
			stampDutyExemption.setApcId(stampData.getApcId());
			stampDutyExemption.setStampId(stampData.getStampId());

			String stampReimElig[] = disViewEvaluate.getStampDutyReimElig();
			List<StampDutyExemption> stampList=stampAppList;

			StampDutyExemption index = stampList.get(count11);
			for (; k1 < stampList.size();) {

				for (l1 = k1; l1 < stampReimElig.length; l1++) {// for loop to print the

					System.out.println(stampReimElig[l1] + " " + stampReimElig.length);
					System.out.println("PANSM List " + stampList.get(l1) + "   " + stampList.size());

					if (k1 == count11) {
						stampDutyExemption.setStampDutyReimElig(stampReimElig[l1]);
						count11++;
						break;
					}
					break;

				}
				k1++;
				break;

			}

			stampDutyList.add(stampDutyExemption);
		}
		stampDutyExemptionRepository.saveAll(stampDutyList);

		int k2 = 0;
		int l2;
		int count2 = 0;
		
		List<EPFComputationAndEligibility> allEpfClaims=disEPFCompAndElgblRepository.findAll();
		List<EPFComputationAndEligibility> epfComputationAndEligibility=new ArrayList<EPFComputationAndEligibility>();
		for(EPFComputationAndEligibility epf:allEpfClaims)
		{
			EPFComputationAndEligibility ePFComputationAndEligibility=new EPFComputationAndEligibility();
			ePFComputationAndEligibility.setEpfApcId(epf.getEpfApcId());
			ePFComputationAndEligibility.setEpfComputeId(epf.getEpfComputeId());
			ePFComputationAndEligibility.setEpfComputFinYr(epf.getEpfComputFinYr());
			ePFComputationAndEligibility.setDateFrom(epf.getDateFrom());
			ePFComputationAndEligibility.setDateTo(epf.getDateTo());
			ePFComputationAndEligibility.setEmployerContributionEPF(epf.getEmployerContributionEPF());
			
			String reimEligibility[]=disViewEvaluate.getReimEligibility();
			List<EPFComputationAndEligibility> epfcomputeList=allEpfClaims;
			
			EPFComputationAndEligibility index2 = epfcomputeList.get(count2);
			for (; k2 < epfcomputeList.size();) {

				for (l2 = k2; l2 < reimEligibility.length; l2++) {// for loop to print the

					System.out.println(reimEligibility[l2] + " " + reimEligibility.length);
					System.out.println("EPF List " + epfcomputeList.get(l2) + "   " + epfcomputeList.size());

					if (k2 == count2) {
						ePFComputationAndEligibility.setReimEligibility(reimEligibility[l2]);
						count2++;
						break;
					}
					break;

				}
				k2++;
				break;

			}

			epfComputationAndEligibility.add(ePFComputationAndEligibility);
		}
		disEPFCompAndElgblRepository.saveAll(epfComputationAndEligibility);
			
			
		
		int k3 = 0;
		int l3;
		int count3 = 0;

		 String capitivePower = "CaptivePower";
		  String powerDrawn = "PowerDrawn";
		  
		  List<ElectricityDutyExemption> electricityDutyList=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(appId,capitivePower);
		 // List<ElectricityDutyExemption> electricityDutyList22=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(appId,powerDrawn);
		
		//List<ElectricityDutyExemption> electricityDutyList=disElectricityDutyExemptionRepository.findAll();
		List<ElectricityDutyExemption> eDetailsList=new ArrayList<ElectricityDutyExemption>();
		for(ElectricityDutyExemption electricity:electricityDutyList)
		{
			ElectricityDutyExemption electricityDutyExeList=new ElectricityDutyExemption();
			
			electricityDutyExeList.setApcId(electricity.getApcId());
			electricityDutyExeList.setElectricDutyExeId(electricity.getElectricDutyExeId());
			electricityDutyExeList.setElectricityDutyExeFinYr(electricity.getElectricityDutyExeFinYr());
			electricityDutyExeList.setElectricityDateFrom(electricity.getElectricityDateFrom());
			electricityDutyExeList.setElectricityDateTo(electricity.getElectricityDateTo());
			electricityDutyExeList.setElectricityAmtClaim(electricity.getElectricityAmtClaim());
			electricityDutyExeList.setElectricityTypeStatus(electricity.getElectricityTypeStatus());
			
			String electric[]=disViewEvaluate.getElectricityEligAmt();
			
			List<ElectricityDutyExemption> eList=electricityDutyList;
			
			ElectricityDutyExemption index3 = eList.get(count3);
			for (; k3 < eList.size();) {

				for (l3 = k3; l3 < electric.length; l3++) {// for loop to print the

					System.out.println(electric[l3] + " " + electric.length);
					System.out.println("electricity List " + eList.get(l3) + "   " + eList.size());

					if (k3 == count3) {
						electricityDutyExeList.setElectricityEligAmt(electric[l3]);
						count3++;
						break;
					}
					break;

				}
				k3++;
				break;

			}

			eDetailsList.add(electricityDutyExeList);
		}
		disElectricityDutyExemptionRepository.saveAll(eDetailsList);
		
		int k4 = 0;
		int l4;
		int count4 = 0;

		
		  
		  //List<ElectricityDutyExemption> electricityDutyList=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(appId,capitivePower);
		  List<ElectricityDutyExemption> electricityDutyList22=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(appId,powerDrawn);
		
		//List<ElectricityDutyExemption> electricityDutyList=disElectricityDutyExemptionRepository.findAll();
		List<ElectricityDutyExemption> eDetailsList2=new ArrayList<ElectricityDutyExemption>();
		for(ElectricityDutyExemption electricity2:electricityDutyList22)
		{
			ElectricityDutyExemption electricityDutyExeList2=new ElectricityDutyExemption();
			
			electricityDutyExeList2.setApcId(electricity2.getApcId());
			electricityDutyExeList2.setElectricDutyExeId(electricity2.getElectricDutyExeId());
			electricityDutyExeList2.setElectricityDutyExeFinYr(electricity2.getElectricityDutyExeFinYr());
			electricityDutyExeList2.setElectricityDateFrom(electricity2.getElectricityDateFrom());
			electricityDutyExeList2.setElectricityDateTo(electricity2.getElectricityDateTo());
			electricityDutyExeList2.setElectricityAmtClaim(electricity2.getElectricityAmtClaim());
			electricityDutyExeList2.setElectricityTypeStatus(electricity2.getElectricityTypeStatus());
			
			String electric2[]=disViewEvaluate.getElectricityEligAmt2();
			
			List<ElectricityDutyExemption> eList2=electricityDutyList22;
			
			ElectricityDutyExemption index4 = eList2.get(count4);
			for (; k4 < eList2.size();) {

				for (l4 = k4; l4 < electric2.length; l4++) {// for loop to print the

					System.out.println(electric2[l4] + " " + electric2.length);
					System.out.println("electricity List " + eList2.get(l4) + "   " + eList2.size());

					if (k4 == count4) {
						electricityDutyExeList2.setElectricityEligAmt(electric2[l4]);
						count4++;
						break;
					}
					break;

				}
				k4++;
				break;

			}

			eDetailsList2.add(electricityDutyExeList2);
		}
		disElectricityDutyExemptionRepository.saveAll(eDetailsList2);
		
		
		
		int k5 = 0;
		int l5;
		int count5 = 0;

		
		  
		  List<MandiFeeExemption> mandiFeeList=disMandiFeeExemptionRepository.findAll();
		
		
		List<MandiFeeExemption> mandiFeeExemption=new ArrayList<MandiFeeExemption>();
		for(MandiFeeExemption mandi:mandiFeeList)
		{
			MandiFeeExemption mandiFee=new MandiFeeExemption();
			
			mandiFee.setApcId(mandi.getApcId());
			mandiFee.setMandiFeeExeId(mandi.getMandiFeeExeId());
			mandiFee.setMandiFeeExeFinYr1(mandi.getMandiFeeExeFinYr1());
			mandiFee.setMandiFeeDateFrom1(mandi.getMandiFeeDateFrom1());
			mandiFee.setMandiFeeDateTo1(mandi.getMandiFeeDateTo1());
			mandiFee.setClaimMandiFeeExe1(mandi.getClaimMandiFeeExe1());
			
			
			
			String mandi1[]=disViewEvaluate.getAvailMandiFeeExe1();
			
			List<MandiFeeExemption> mandiExeList=mandiFeeList;
			
			MandiFeeExemption index4 = mandiExeList.get(count5);
			for (; k5 < mandiExeList.size();) {

				for (l5 = k5; l5 < mandi1.length; l5++) {// for loop to print the

					System.out.println(mandi1[l5] + " " + mandi1.length);
					System.out.println("electricity List " + mandiExeList.get(l5) + "   " + mandiExeList.size());

					if (k5 == count5) {
						mandiFee.setAvailMandiFeeExe1(mandi1[l5]);
						count5++;
						break;
					}
					break;

				}
				k5++;
				break;

			}

			mandiFeeExemption.add(mandiFee);
		}
		disMandiFeeExemptionRepository.saveAll(mandiFeeExemption);
		
		

		int k6 = 0;
		int l6;
		int count6 = 0;

		
		  
		  List<DisReimDisallowedInput> disReimDisallowedInputList=reimDisAllowedRepository.findAll();
		
		
		List<DisReimDisallowedInput> disReimDisallowed=new ArrayList<DisReimDisallowedInput>();
		for(DisReimDisallowedInput disallowed:disReimDisallowedInputList)
		{
			DisReimDisallowedInput disallowedData=new DisReimDisallowedInput();
			
			disallowedData.setApcId(disallowed.getApcId());
			disallowedData.setDisallowedId(disallowed.getDisallowedId());
			disallowedData.setDisallowedFinYr(disallowed.getDisallowedFinYr());
			disallowedData.setDisallowedDateFr(disallowed.getDisallowedDateFr());
			disallowedData.setDisallowedDateTo(disallowed.getDisallowedDateTo());
			disallowedData.setDisallowedClaimAmt(disallowed.getDisallowedClaimAmt());
			
			String eligAmt[]=disViewEvaluate.getDisallowedEligAmt();
			
			List<DisReimDisallowedInput> disallowedReimList=disReimDisallowedInputList;
			
			DisReimDisallowedInput index6 = disallowedReimList.get(count6);
			for (; k6 < disallowedReimList.size();) {

				for (l6 = k6; l6 < eligAmt.length; l6++) {// for loop to print the

					System.out.println(eligAmt[l6] + " " + eligAmt.length);
					System.out.println("electricity List " + disallowedReimList.get(l6) + "   " + disallowedReimList.size());

					if (k6 == count6) {
						disallowedData.setDisallowedEligAmt(eligAmt[l6]);
						count6++;
						break;
					}
					break;

				}
				k6++;
				break;

			}

			disReimDisallowed.add(disallowedData);
		}
		reimDisAllowedRepository.saveAll(disReimDisallowed);
		
		
			
		

		List<ExistingProjectDetails> existProductsList1 = existProjRepository.findExistProjListByEpdId(projId);

		List<EvaluateExistNewProjDetails> disEvaluateExistNewProjDetailsList = new ArrayList<>();

		int i = 0;
		int j;
		int count = 0;
		String[] a = disViewEvaluate.getExpEligbleCapInv();
		if (a != null) {
			for (ExistingProjectDetails existProductsList1Obj : existProductsList1) {

				EvaluateExistNewProjDetails disExistEvlauteList = new EvaluateExistNewProjDetails();

				disExistEvlauteList.setEpdId(existProductsList1Obj.getEpdId());
				disExistEvlauteList.setEpdProjDtlId(existProductsList1Obj.getEpdProjDtlId());
				disExistEvlauteList.setEpdPropInstallCapacity(existProductsList1Obj.getEpdPropInstallCapacity());
				disExistEvlauteList.setEpdPropoGrossBlock(existProductsList1Obj.getEpdPropoGrossBlock());
				disExistEvlauteList.setEpdPropProducts(existProductsList1Obj.getEpdPropProducts());
				disExistEvlauteList.setEpdStatus(existProductsList1Obj.getEpdStatus());
				disExistEvlauteList.setEpdCreatedBy(existProductsList1Obj.getEpdCreatedBy());
				disExistEvlauteList.setEpdModifiyDate(existProductsList1Obj.getEpdModifiyDate());
				disExistEvlauteList.setEpdExisGrossBlock(existProductsList1Obj.getEpdExisGrossBlock());
				disExistEvlauteList.setEpdExisInstallCapacity(existProductsList1Obj.getEpdExisInstallCapacity());
				disExistEvlauteList.setEpdExisProducts(existProductsList1Obj.getEpdExisProducts());

				String[] b = disViewEvaluate.getExpPercOverGrsBlck();

				for (; i < existProductsList1.size();) {

					for (j = i; j < a.length; j++) {// for loop to print the

						System.out.println(a[j] + " " + a.length);
						System.out.println(
								"Existing List " + existProductsList1.get(i) + "   " + existProductsList1.size());

						if (i == count) {
							disExistEvlauteList.setExpEligbleCapInv(a[j]);
							disExistEvlauteList.setExpPercOverGrsBlck(b[j]);
							count++;
							break;
						}
						break;

					}
					i++;
					break;

				}

				disEvaluateExistNewProjDetailsList.add(disExistEvlauteList);
			}
		}
		evaluateExistNewProjDetailsRepository.saveAll(disEvaluateExistNewProjDetailsList);

		List<Dept_PhaseWiseInvestmentDetails> dept_PhaseWiseInvestmentDetailsList = deptPWInvRepository
				.findByPwApcId(appId);
		List<Dept_PhaseWiseInvestmentDetails> disEvaluatePhaswiseDetailsList = new ArrayList<>();

		int k = 0;
		int l;
		int count1 = 0;
		String[] b = disViewEvaluate.getPhwActualdateEval();
		if (b != null) {
			for (Dept_PhaseWiseInvestmentDetails deptPhswListObj : dept_PhaseWiseInvestmentDetailsList) {

				Dept_PhaseWiseInvestmentDetails disphswObj = new Dept_PhaseWiseInvestmentDetails();

				disphswObj.setPwApcId(deptPhswListObj.getPwApcId());
				disphswObj.setPwApply(deptPhswListObj.getPwApply());
				disphswObj.setPwCapacity(deptPhswListObj.getPwCapacity());
				disphswObj.setPwCreatedBy(deptPhswListObj.getPwCreatedBy());
				disphswObj.setPwCutoffProdAmt(deptPhswListObj.getPwCutoffProdAmt());
				disphswObj.setPwFci(deptPhswListObj.getPwFci());
				disphswObj.setPwId(deptPhswListObj.getPwId());
				disphswObj.setPwInvId(deptPhswListObj.getPwInvId());
				disphswObj.setPwModifiedDate(deptPhswListObj.getPwModifiedDate());
				disphswObj.setPwPhaseNo(deptPhswListObj.getPwPhaseNo());
				disphswObj.setPwProductName(deptPhswListObj.getPwProductName());
				disphswObj.setPwUnit(deptPhswListObj.getPwUnit());
				disphswObj.setPwId(deptPhswListObj.getPwId());
				disphswObj.setPwPropProductDate(deptPhswListObj.getPwPropProductDate());

				for (; k < dept_PhaseWiseInvestmentDetailsList.size();) {

					for (l = k; l < b.length; j++) {// for loop to print the

						System.out.println(b[k] + " " + b.length);
						System.out.println("Existing List " + dept_PhaseWiseInvestmentDetailsList.size());

						if (l == count1) {
							SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
							String dateInString = b[k];
							LocalDate date1 = LocalDate.parse(dateInString);

							Date date = formatter.parse(dateInString);
							System.out.println("Change Date : " + date);
							System.out.println("Change Date1  : " + date1);

							disphswObj.setPhwActualdateEval(b[k]);
							count1++;
							break;
						}
						break;

					}
					k++;
					break;

				}

				disEvaluatePhaswiseDetailsList.add(disphswObj);
			}
		}
		deptPWInvRepository.saveAll(disEvaluatePhaswiseDetailsList);

		DisEvaluationBankDetails loanBankDetails = new DisEvaluationBankDetails();
		String[] bankName = disViewEvaluate.getLoanBankName();
		String[] bankDate = disViewEvaluate.getLoanBankDate();
		String[] bankAmt = disViewEvaluate.getLoanBankAmt();
		String[] bankROI = disViewEvaluate.getLoanBankROI();

		List<DisEvaluationBankDetails> disEvaluateBankDetailsList = loanBankDetailsRepository.findByapcId(appId);

		if (bankName != null) {
			for (int x = 0; x < bankName.length-1; x++) {
				if (bankName[x] != null && bankDate[x] != null && bankAmt[x] != null) {
					loanBankDetails.setId(appId + "BD" + atomicInteger.getAndIncrement());
					loanBankDetails.setApcId(appId);
					loanBankDetails.setLoanBankName(bankName[x]);
					loanBankDetails.setLoanBankDate(bankDate[x]);
					loanBankDetails.setLoanBankAmt(bankAmt[x]);
					loanBankDetails.setLoanBankROI(bankROI[x]);

					
				}
				loanBankDetailsRepository.save(loanBankDetails);
			}
			
		}

		/*
		 * if (disEvaluateBankDetailsList != null &&
		 * !disEvaluateBankDetailsList.isEmpty()) {
		 * loanBankDetailsRepository.deleteAll(disEvaluateBankDetailsList); for (int x =
		 * 0; x < bankName.length; x++) {
		 * 
		 * System.out.println("------"); loanBankDetails.setId(appId + "BD" +
		 * atomicInteger.getAndIncrement()); loanBankDetails.setApcId(appId);
		 * loanBankDetails.setLoanBankName(bankName[x]);
		 * loanBankDetails.setLoanBankDate(bankDate[x]);
		 * loanBankDetails.setLoanBankAmt(bankAmt[x]);
		 * 
		 * loanBankDetailsRepository.save(loanBankDetails); }
		 * 
		 * } else { for (int x = 0; x < bankName.length; x++) {
		 * loanBankDetails.setId(appId + "BD" + atomicInteger.getAndIncrement());
		 * loanBankDetails.setApcId(appId);
		 * loanBankDetails.setLoanBankName(bankName[x]);
		 * loanBankDetails.setLoanBankDate(bankDate[x]);
		 * loanBankDetails.setLoanBankAmt(bankAmt[x]);
		 * 
		 * loanBankDetailsRepository.save(loanBankDetails); } }
		 */

		

		DisEvaluationPandMBankDetails pandmloanBankDetails = new DisEvaluationPandMBankDetails();
		String[] pmbankName = disViewEvaluate.getPandMBankName();
		String[] pmbankDate = disViewEvaluate.getPandMBankDate();
		String[] pmbankAmt = disViewEvaluate.getPandMBankAmt();

		// List<DisEvaluationPandMBankDetails> disEvaluatePandMBankDetailsList =
		// pAndmBankDetailsRepository.findByapcId(appId);

		if (pmbankName != null) {
			for (int x = 0; x < pmbankName.length-1; x++) {
				if (pmbankName[x] != null && pmbankDate[x] != null && pmbankAmt[x] != null) {
					pandmloanBankDetails.setId(appId + "PM" + atomicInteger.getAndIncrement());
					pandmloanBankDetails.setApcId(appId);
					pandmloanBankDetails.setLoanBankName(pmbankName[x]);
					pandmloanBankDetails.setLoanBankDate(pmbankDate[x]);
					pandmloanBankDetails.setLoanBankAmt(pmbankAmt[x]);

					
				}
				pAndmBankDetailsRepository.save(pandmloanBankDetails);
			}
			
		}
		
		int k7 = 0;
		int l7;
		int count7 = 0;

		 
		  List<DisReimbrsDepositeGstTable> disReimDepositeGstTblList=reimbrsGSTTableRepository.findAll();
		
		
		
		List<DisReimbrsDepositeGstTable> disReimDeposite=new ArrayList<DisReimbrsDepositeGstTable>();
		for(DisReimbrsDepositeGstTable reimData:disReimDepositeGstTblList)
		{
			DisReimbrsDepositeGstTable reimbrsDepositeGstTable=new DisReimbrsDepositeGstTable();
			
			reimbrsDepositeGstTable.setDisId(reimData.getDisId());
			reimbrsDepositeGstTable.setDisAppId(reimData.getDisAppId());
			reimbrsDepositeGstTable.setFinancialYear(reimData.getFinancialYear());
			reimbrsDepositeGstTable.setDurationPeriodDtFr(reimData.getDurationPeriodDtFr());
			reimbrsDepositeGstTable.setDurationPeriodDtTo(reimData.getDurationPeriodDtTo());
			reimbrsDepositeGstTable.setAmtAdmtTaxDeptGst(reimData.getAmtAdmtTaxDeptGst());
			
			String amtOfNetSGSTReim1New[]=disViewEvaluate.getAmtOfNetSGSTReim1New();
			String payableAmountOfSGSTNew[]=disViewEvaluate.getPayableAmountOfSGSTNew();
			
			
			List<DisReimbrsDepositeGstTable> reimDepositList=disReimDepositeGstTblList;
			
			DisReimbrsDepositeGstTable index7 = reimDepositList.get(count7);
			for (; k7 < reimDepositList.size();) {

				for (l7 = k7; l7 < amtOfNetSGSTReim1New.length; l7++) {// for loop to print the

					System.out.println(amtOfNetSGSTReim1New[l7] + " " + amtOfNetSGSTReim1New.length);
					System.out.println("electricity List " + reimDepositList.get(l7) + "   " + reimDepositList.size());

					if (k7 == count7) {
						reimbrsDepositeGstTable.setAmtOfNetSGSTReim1New(amtOfNetSGSTReim1New[l7]);
						reimbrsDepositeGstTable.setPayableAmountOfSGSTNew(payableAmountOfSGSTNew[l7]);
						
						count7++;
						break;
					}
					break;

				}
				k7++;
				break;

			}

			disReimDeposite.add(reimbrsDepositeGstTable);
		}
		reimbrsGSTTableRepository.saveAll(disReimDeposite);
		
		// Table-9 II save start
		
		int k8 = 0;
		int l8;
		int count8 = 0;
		List<SGSTReimTurnOver> sgstallReimTurnOver=sgstReimClaimTurnOverRepo.findAll();
		List<SGSTReimTurnOver> sgstReimTurnOverList=new ArrayList<SGSTReimTurnOver>();
		if(sgstallReimTurnOver.size()>0)
		{
		
		for(SGSTReimTurnOver reimTurnOverData:sgstallReimTurnOver)
		{
			
			SGSTReimTurnOver sgstReimTurnOver=new SGSTReimTurnOver();
			
			sgstReimTurnOver.setSgstApcId(reimTurnOverData.getSgstApcId());
			sgstReimTurnOver.setSgstId(reimTurnOverData.getSgstId());
			
			String sgstFinYr1[]=disViewEvaluate.getSgstFinYr();
			String sgstSales[] =disViewEvaluate.getSgstTurnOverSalesItems();
			String sgstBasrProd[] =disViewEvaluate.getSgstTurnOverBaseProduction();
		
			
			List<SGSTReimTurnOver> reimTurnOverList=sgstallReimTurnOver;
			
			SGSTReimTurnOver index8 = reimTurnOverList.get(count8);
			for (; k8 < reimTurnOverList.size();) {

				for (l8 = k8; l8 < sgstFinYr1.length; l8++) {// for loop to print the

					System.out.println(sgstFinYr1[l8] + " " + sgstFinYr1.length);
					System.out.println("electricity List " + reimTurnOverList.get(l8) + "   " + reimTurnOverList.size());

					if (k8 == count8) {
						sgstReimTurnOver.setSgstFinYr(sgstFinYr1[l8]);
						sgstReimTurnOver.setSgstTurnOverSalesItems(sgstSales[l8]);
						sgstReimTurnOver.setSgstTurnOverBaseProduction(sgstBasrProd[l8]);
						count8++;
						break;
					}
					break;

				}
				k8++;
				break;

			}

			sgstReimTurnOverList.add(sgstReimTurnOver);
		}
		sgstReimClaimTurnOverRepo.saveAll(sgstReimTurnOverList);
		
		}else
		{
				String sgstFinYr1[]=disViewEvaluate.getSgstFinYr();
			    String sgstSales[] =disViewEvaluate.getSgstTurnOverSalesItems();
				String sgstBasrProd[] =disViewEvaluate.getSgstTurnOverBaseProduction();
				
				
				if(sgstSales.length>0)
				{
				for(int m=0; m<=sgstSales.length-1; m++) {
							
				SGSTReimTurnOver sgstReimTurnOver=new SGSTReimTurnOver();
				
				Random random = new Random();   
				// Generates random integers 0 to 500  
				int x = random.nextInt(500);  
				sgstReimTurnOver.setSgstApcId(appId);
				sgstReimTurnOver.setSgstId(appId + "TVR" + x);
				sgstReimTurnOver.setSgstFinYr(sgstFinYr1[m]);
				sgstReimTurnOver.setSgstTurnOverSalesItems(sgstSales[m]);
				sgstReimTurnOver.setSgstTurnOverBaseProduction(sgstBasrProd[m]);
				sgstReimTurnOverList.add(sgstReimTurnOver);
				
				}
				sgstReimClaimTurnOverRepo.saveAll(sgstReimTurnOverList);
			}
				
		}

				 
			
	
// SGST TurnOver Saving start 
		
		//List<SGSTReimTurnOver> sgstTurnOverList=disViewEvaluate.getSgstList();
		
		
				
		/*
		 * if (!disReimbrsGSTTableList.isEmpty()) { for (DisReimbrsDepositeGstTable
		 * disList : disReimbrsGSTTableList) { DisEvaluationReimbrsDepositeGstTable
		 * ebaldisObject = new DisEvaluationReimbrsDepositeGstTable();
		 * ebaldisObject.setDisId(disList.getDisId());
		 * ebaldisObject.setDisAppId(disList.getDisAppId());
		 * ebaldisObject.setFinancialYear(disList.getFinancialYear());
		 * disEvalReimbrsGSTTableList.add(ebaldisObject); }
		 * disEvaluationReimbrsGStTableService.saveReimbrsGSTTableList(
		 * disEvalReimbrsGSTTableList); }
		 */

		// Eligible Amt CIS get View Evaluate and Save Save by Pankaj
		DisEligibleAmtCIS disEligibleAmtCIS = new DisEligibleAmtCIS();

		String ElgbleCISId = appId + "EACIS";

		disEligibleAmtCIS.setEligibleAmtId(ElgbleCISId);
		disEligibleAmtCIS.setApcId(appId);

		disEligibleAmtCIS.setfYI(disViewEvaluate.getfYI());
		disEligibleAmtCIS.setfYII(disViewEvaluate.getfYII());
		disEligibleAmtCIS.setfYIII(disViewEvaluate.getfYII());
		disEligibleAmtCIS.setfYIV(disViewEvaluate.getfYIV());
		disEligibleAmtCIS.setfYV(disViewEvaluate.getfYV());

		disEligibleAmtCIS.setIntPMI(disViewEvaluate.getIntPMI());
		disEligibleAmtCIS.setIntPMII(disViewEvaluate.getIntPMII());
		disEligibleAmtCIS.setIntPMIII(disViewEvaluate.getIntPMIII());
		disEligibleAmtCIS.setIntPMIV(disViewEvaluate.getIntPMIV());
		disEligibleAmtCIS.setIntPMV(disViewEvaluate.getIntPMV());

		disEligibleAmtCIS.setDateofDISI(disViewEvaluate.getDateofDISI());
		disEligibleAmtCIS.setDateofDISII(disViewEvaluate.getDateofDISII());
		disEligibleAmtCIS.setDateofDISIII(disViewEvaluate.getDateofDISIII());
		disEligibleAmtCIS.setDateofDISIV(disViewEvaluate.getDateofDISIV());
		disEligibleAmtCIS.setDateofDISV(disViewEvaluate.getDateofDISV());

		disEligibleAmtCIS.setActAmtIPI(disViewEvaluate.getActAmtIPI());
		disEligibleAmtCIS.setActAmtIPII(disViewEvaluate.getActAmtIPII());
		disEligibleAmtCIS.setActAmtIPIII(disViewEvaluate.getActAmtIPIII());
		disEligibleAmtCIS.setActAmtIPIV(disViewEvaluate.getActAmtIPIV());
		disEligibleAmtCIS.setActAmtIPV(disViewEvaluate.getActAmtIPV());

		disEligibleAmtCIS.setDateofPI(disViewEvaluate.getDateofPI());
		disEligibleAmtCIS.setDateofPII(disViewEvaluate.getDateofPII());
		disEligibleAmtCIS.setDateofPIII(disViewEvaluate.getDateofPIII());
		disEligibleAmtCIS.setDateofPIV(disViewEvaluate.getDateofPIV());
		disEligibleAmtCIS.setDateofPV(disViewEvaluate.getDateofPV());

		disEligibleAmtCIS.setPropIntRoiI(disViewEvaluate.getPropIntRoiI());
		disEligibleAmtCIS.setPropIntRoiII(disViewEvaluate.getPropIntRoiII());
		disEligibleAmtCIS.setPropIntRoiIII(disViewEvaluate.getPropIntRoiIII());
		disEligibleAmtCIS.setPropIntRoiIV(disViewEvaluate.getPropIntRoiIV());
		disEligibleAmtCIS.setPropIntRoiV(disViewEvaluate.getPropIntRoiV());

		disEligibleAmtCIS.setPropIntPAI(disViewEvaluate.getPropIntPAI());
		disEligibleAmtCIS.setPropIntPAII(disViewEvaluate.getPropIntPAII());
		disEligibleAmtCIS.setPropIntPAIII(disViewEvaluate.getPropIntPAIII());
		disEligibleAmtCIS.setPropIntPAIV(disViewEvaluate.getPropIntPAIV());
		disEligibleAmtCIS.setPropIntPAV(disViewEvaluate.getPropIntPAV());
		disEligibleAmtCIS.setElgAmtcisObserv(disViewEvaluate.getElgAmtcisObserv());

		disEligibleAmtCISService.saveEligibleAmtCISDetails(disEligibleAmtCIS);

		// Eligible Amt IIS get View Evaluate and Save Save by Pankaj
		DisEligibleAmtIIS disEligibleAmtIIS = new DisEligibleAmtIIS();

		String ElgbleIISId = appId + "EAIIS";

		disEligibleAmtIIS.setEligibleAmtIISId(ElgbleIISId);
		disEligibleAmtIIS.setApcIdIIS(appId);

		disEligibleAmtIIS.setIisFinYr1(disViewEvaluate.getIisFinYr1());
		disEligibleAmtIIS.setIisFinYr2(disViewEvaluate.getIisFinYr2());
		disEligibleAmtIIS.setIisFinYr3(disViewEvaluate.getIisFinYr3());
		disEligibleAmtIIS.setIisFinYr4(disViewEvaluate.getIisFinYr4());
		disEligibleAmtIIS.setIisFinYr5(disViewEvaluate.getIisFinYr5());

		disEligibleAmtIIS.setIisTtlLoanAmt1(disViewEvaluate.getIisTtlLoanAmt1());
		disEligibleAmtIIS.setIisTtlLoanAmt2(disViewEvaluate.getIisTtlLoanAmt2());
		disEligibleAmtIIS.setIisTtlLoanAmt3(disViewEvaluate.getIisTtlLoanAmt3());
		disEligibleAmtIIS.setIisTtlLoanAmt4(disViewEvaluate.getIisTtlLoanAmt4());
		disEligibleAmtIIS.setIisTtlLoanAmt5(disViewEvaluate.getIisTtlLoanAmt5());

		disEligibleAmtIIS.setPropIntInfra1(disViewEvaluate.getPropIntInfra1());
		disEligibleAmtIIS.setPropIntInfra2(disViewEvaluate.getPropIntInfra2());
		disEligibleAmtIIS.setPropIntInfra3(disViewEvaluate.getPropIntInfra3());
		disEligibleAmtIIS.setPropIntInfra4(disViewEvaluate.getPropIntInfra4());
		disEligibleAmtIIS.setPropIntInfra5(disViewEvaluate.getPropIntInfra5());

		disEligibleAmtIIS.setPropIntInfraPA1(disViewEvaluate.getPropIntInfraPA1());
		disEligibleAmtIIS.setPropIntInfraPA2(disViewEvaluate.getPropIntInfraPA2());
		disEligibleAmtIIS.setPropIntInfraPA3(disViewEvaluate.getPropIntInfraPA3());
		disEligibleAmtIIS.setPropIntInfraPA4(disViewEvaluate.getPropIntInfraPA4());
		disEligibleAmtIIS.setPropIntInfraPA5(disViewEvaluate.getPropIntInfraPA5());

		disEligibleAmtIIS.setEligibleIIS1(disViewEvaluate.getEligibleIIS1());
		disEligibleAmtIIS.setEligibleIIS2(disViewEvaluate.getEligibleIIS2());
		disEligibleAmtIIS.setEligibleIIS3(disViewEvaluate.getEligibleIIS3());
		disEligibleAmtIIS.setEligibleIIS4(disViewEvaluate.getEligibleIIS4());
		disEligibleAmtIIS.setEligibleIIS5(disViewEvaluate.getEligibleIIS5());

		disEligibleAmtIIS.setEligAmtIisObserv(disViewEvaluate.getEligAmtIisObserv());
		disEligibleAmtIISService.saveEligibleAmtIISDetails(disEligibleAmtIIS);


		

		

		model.addAttribute("disViewEvaluate", disViewEvaluate);
		return new ModelAndView("redirect:/evaluateApplicationDis");

	}

	// Pankaj Evaluation Start

	@PostMapping(value = "/disApplicationEvaluation")
	public ModelAndView disApplicationEvaluation(
			@ModelAttribute("applicationAgendaNote") @Validated DISPrepareAgendaNotes disPrepareAgendaNotes,
			Model model, HttpSession session) {

		String appId = (String) session.getAttribute("appId");

		BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(appId);

		InvestmentDetails invdtlFromDb = investmentDetailsService.getInvestmentDetailsByapplId(appId);

		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);

		String userId = (String) session.getAttribute("userId");
		disPrepareAgendaNotes.setId(appId + "DISAGN");
		disPrepareAgendaNotes.setAppliId(appId);
		disPrepareAgendaNotes.setCompanyName(businessEntityDetails.getBusinessEntityName());
		disPrepareAgendaNotes.setInvestment(String.valueOf(invdtlFromDb.getInvFci()));
		disPrepareAgendaNotes.setCategoryIndsType(invdtlFromDb.getInvIndType());
		disPrepareAgendaNotes.setDistrict(evaluateProjectDetails.getDistrictName());
		disPrepareAgendaNotes.setRegion(evaluateProjectDetails.getResionName());
		disPrepareAgendaNotes.setStatus("Evaluation Completed");
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

		model.addAttribute("msg", "Succefully Add Agenda Notes for DIS");
		return new ModelAndView("redirect:/evaluateApplicationDis");
	}

	// Pankaj Included In Agenda Note Start
	@PostMapping(value = "/disApplicationAgendaNote")
	public ModelAndView saveDisApplicationAgendaNote(
			@ModelAttribute("applicationAgendaNote") @Validated DISPrepareAgendaNotes disPrepareAgendaNotes,
			Model model, HttpSession session) {

		String appId = (String) session.getAttribute("appId");

		BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(appId);

		InvestmentDetails invdtlFromDb = investmentDetailsService.getInvestmentDetailsByapplId(appId);

		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);

		String userId = (String) session.getAttribute("userId");

		disPrepareAgendaNotes.setId(appId + "DISAGN");
		disPrepareAgendaNotes.setAppliId(appId);
		disPrepareAgendaNotes.setCompanyName(businessEntityDetails.getBusinessEntityName());
		disPrepareAgendaNotes.setInvestment(String.valueOf(invdtlFromDb.getInvFci()));
		disPrepareAgendaNotes.setCategoryIndsType(invdtlFromDb.getInvIndType());
		disPrepareAgendaNotes.setDistrict(evaluateProjectDetails.getDistrictName());
		disPrepareAgendaNotes.setRegion(evaluateProjectDetails.getResionName());
		disPrepareAgendaNotes.setStatus("Application Included In Agenda Note");
		System.out.println("Time....." + new Timestamp(System.currentTimeMillis()));
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

		model.addAttribute("msg", "Succefully Add Agenda Notes for DIS");
		return new ModelAndView("redirect:/evaluateApplicationDis");
	}

	// Pankaj Rejected Start
	@RequestMapping("/saveRejectApplicationDIS")
	public String saveRejectApplicationDIS(@RequestParam(name = "rejectValue") String rejectValue,
			DISPrepareAgendaNotes disPrepareAgendaNotes, HttpSession session) {

		String appId = (String) session.getAttribute("appId");

		BusinessEntityDetails businessEntityDetails = businessService.getBusinessEntityByapplicantDetailId(appId);

		InvestmentDetails invdtlFromDb = investmentDetailsService.getInvestmentDetailsByapplId(appId);

		EvaluateProjectDetails evaluateProjectDetails = evaluateProjectDetailsService
				.getEvalProjDetByapplicantDetailId(appId);

		String userId = (String) session.getAttribute("userId");

		disPrepareAgendaNotes.setId(appId + "DISAGN");
		disPrepareAgendaNotes.setAppliId(appId);
		disPrepareAgendaNotes.setCompanyName(businessEntityDetails.getBusinessEntityName());
		disPrepareAgendaNotes.setInvestment(String.valueOf(invdtlFromDb.getInvFci()));
		disPrepareAgendaNotes.setCategoryIndsType(invdtlFromDb.getInvIndType());
		disPrepareAgendaNotes.setDistrict(evaluateProjectDetails.getDistrictName());
		disPrepareAgendaNotes.setRegion(evaluateProjectDetails.getResionName());
		disPrepareAgendaNotes.setStatus("Application Rejected");
		disPrepareAgendaNotes.setRejectReason(rejectValue);
		System.out.println("Time....." + new Timestamp(System.currentTimeMillis()));
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

		return "redirect:/evaluateApplicationDis";
	}

}
