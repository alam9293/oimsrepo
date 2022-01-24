package com.webapp.ims.food.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.IOUtils;
import org.apache.tomcat.util.codec.binary.Base64;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.food.Model.BEDReeferVehicles;
import com.webapp.ims.food.Model.BankTermLoanDetails;
import com.webapp.ims.food.Model.BankTermLoanDetailsFMP;
import com.webapp.ims.food.Model.BusinessEntityDetailsFood;
import com.webapp.ims.food.Model.CapacityofpnmUnit;
import com.webapp.ims.food.Model.CapacityofthePlantFood;
import com.webapp.ims.food.Model.CapacityofthePlantOrUnit;
import com.webapp.ims.food.Model.DetailsofEligibleTechnicalCivilWork;
import com.webapp.ims.food.Model.DetailsofExport;
import com.webapp.ims.food.Model.DetailsofProposedPlantAndMachinerytobeInstalled;
import com.webapp.ims.food.Model.DetailsofproductExported;
import com.webapp.ims.food.Model.DocumentsReeferVehicles;
import com.webapp.ims.food.Model.EligibleTechnicalCivilWork;
import com.webapp.ims.food.Model.EmploymentGeneration;
import com.webapp.ims.food.Model.ExportincentivecalculatedonthebasisofAnnualExportTurnover;
import com.webapp.ims.food.Model.FMPMeansofFinancing;
import com.webapp.ims.food.Model.FinancialStatus;
import com.webapp.ims.food.Model.FinishedproductQuantity;
import com.webapp.ims.food.Model.FoodBankDetails;
import com.webapp.ims.food.Model.FoodCapitalInvestmentDetails;
import com.webapp.ims.food.Model.FoodDocument;
import com.webapp.ims.food.Model.FoodDocumentFMP_PMKSY;
import com.webapp.ims.food.Model.FoodDocumentPMKSY;
import com.webapp.ims.food.Model.FoodDocumentTQM;
import com.webapp.ims.food.Model.FoodDocumentsBankDPR;
import com.webapp.ims.food.Model.FoodDocumentsMDBP;
import com.webapp.ims.food.Model.FoodElegibleCostOfPlantMach;
import com.webapp.ims.food.Model.FoodEvalutionViewCSIS;
import com.webapp.ims.food.Model.FoodEvalutionViewISRV;
import com.webapp.ims.food.Model.FoodEvalutionViewPMKYS;
import com.webapp.ims.food.Model.FoodTQMprojectDetails;
import com.webapp.ims.food.Model.ISEligibleTechnicalCivilWork;
import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.ImplementationSchedule;
import com.webapp.ims.food.Model.ImportexportcodeandAddressofissuingDGFToffice;
import com.webapp.ims.food.Model.InvestmentDetailsFood;
import com.webapp.ims.food.Model.MeansOfFinancingReeferVehicles;
import com.webapp.ims.food.Model.MeansofFinanceBankDPR;
import com.webapp.ims.food.Model.MeansofFinancing;
import com.webapp.ims.food.Model.MeansofFinancingTQM;
import com.webapp.ims.food.Model.ProductDetail;
import com.webapp.ims.food.Model.ProductDetails;
import com.webapp.ims.food.Model.ProjectCost;
import com.webapp.ims.food.Model.ProjectCostReeferVehicles;
import com.webapp.ims.food.Model.ProjectDtailsPreVehicles;
import com.webapp.ims.food.Model.ProjectInvestmentDetails;
import com.webapp.ims.food.Model.ProjectandProposedEmploymentDetails;
import com.webapp.ims.food.Model.ReeferVehiclesDetails;
import com.webapp.ims.food.Model.RegistrationCertificatefromAPEDA;
import com.webapp.ims.food.Model.TotalAnnualExportTurnoverofunit;
import com.webapp.ims.food.Model.TotalProjectCostproposedinDPR;
import com.webapp.ims.food.Repository.BEDReeferVehiclesRepository;
import com.webapp.ims.food.Repository.BankTermLoanDetailsRepository;
import com.webapp.ims.food.Repository.BankTermLoanDetailsRepositoryFMP;
import com.webapp.ims.food.Repository.CapacityodpnmUnitRepository;
import com.webapp.ims.food.Repository.CapacityofthaPlantRepository;
import com.webapp.ims.food.Repository.CapacityofthePlantOrUnitRepository;
import com.webapp.ims.food.Repository.DetailsofEligibleTechnicalCivilWorkRepository;
import com.webapp.ims.food.Repository.DetailsofExportRepository;
import com.webapp.ims.food.Repository.DetailsofproductExportedRepository;
import com.webapp.ims.food.Repository.DocumentsReeferVehiclesRepository;
import com.webapp.ims.food.Repository.EmploymentGenerationRepository;
import com.webapp.ims.food.Repository.EvaluateCSISRepository;
import com.webapp.ims.food.Repository.ExportincentivecalculatedonthebasisofAnnualExportTurnoverRepository;
import com.webapp.ims.food.Repository.FinancialStatuaRepository;
import com.webapp.ims.food.Repository.FinishedproductQuantityRepository;
import com.webapp.ims.food.Repository.FoodBankDetailsRepository;
import com.webapp.ims.food.Repository.FoodCapitalInvestmentDetailsRepository;
import com.webapp.ims.food.Repository.FoodDocumentBankDPRepository;
import com.webapp.ims.food.Repository.FoodDocumentMDBPRepository;
import com.webapp.ims.food.Repository.FoodDocumentPMKSYRepository;
import com.webapp.ims.food.Repository.FoodDocumentPMKSYRepositoryFMP;
import com.webapp.ims.food.Repository.FoodDocumentRepository;
import com.webapp.ims.food.Repository.FoodDocumentTQMRepository;
import com.webapp.ims.food.Repository.FoodEligiblePlantMachRepository;
import com.webapp.ims.food.Repository.FoodEvaluateReeferVehiclesRepository;
import com.webapp.ims.food.Repository.FoodPolicyDetailsRepository;
import com.webapp.ims.food.Repository.FoodTQMprojectDetailsRepository;
import com.webapp.ims.food.Repository.FoodTechnicalCivilWorkRepository;
import com.webapp.ims.food.Repository.ImplementationScheduleRepository;
import com.webapp.ims.food.Repository.ImplementationScheduleTQMRepository;
import com.webapp.ims.food.Repository.ImportexportcodeandAddressofissuingDGFTofficeRepository;
import com.webapp.ims.food.Repository.InvestmentDetailsFoodRepository;
import com.webapp.ims.food.Repository.MeanceOfFinancReeferVehiclesRepository;
import com.webapp.ims.food.Repository.MeansOfFinanceBankDPRRepository;
import com.webapp.ims.food.Repository.MeansOfFinancingRepository;
import com.webapp.ims.food.Repository.MeansOfFinancingRepositoryFMP;
import com.webapp.ims.food.Repository.MeansofFinancingTQMRepository;
import com.webapp.ims.food.Repository.PlantAndMachinerytobeCSISRepository;
import com.webapp.ims.food.Repository.ProductDetailRepository;
import com.webapp.ims.food.Repository.ProductDetailsRepository;
import com.webapp.ims.food.Repository.ProjectCostReeferVehiclesRepository;
import com.webapp.ims.food.Repository.ProjectCostRepository;
import com.webapp.ims.food.Repository.ProjectDetailsReeferVehilesRepository;
import com.webapp.ims.food.Repository.ProjectInvestmentDetailsRepository;
import com.webapp.ims.food.Repository.ProjectandProposedEmploymentDetailsRepository;
import com.webapp.ims.food.Repository.RegistrationCertificatefromAPEDARepository;
import com.webapp.ims.food.Repository.TotalAnnualExportTurnoverofunitRepository;
import com.webapp.ims.food.Repository.TotalProjectCostproposedinDPRRepository;
import com.webapp.ims.food.Repository.VehiclesDetailsRepository;

@Controller
public class FoodDashboard {

	@Autowired
	FoodPolicyDetailsRepository foodPolicyDetailsRepository;
	
	@Autowired
	FoodTechnicalCivilWorkRepository foodTechnicalCivilWorkRepository;
	
	@Autowired
	FoodEvaluateReeferVehiclesRepository foodEvaluateReeferVehiclesRepository;
	
	@Autowired
	EvaluateCSISRepository evaluateCSISRepository;

	@Autowired
	ProjectDetailsReeferVehilesRepository projectDetailsReeferVehilesRepository;

	@Autowired
	TotalProjectCostproposedinDPRRepository totalProjectCostproposedinDPRRepository;

	@Autowired
	FinancialStatuaRepository financeStatusRepos;

	@Autowired
	MeanceOfFinancReeferVehiclesRepository meanceOfFinancReeferVehiclesRepository;

	@Autowired
	MeansOfFinancingRepositoryFMP meanceOfFinanceFMPRepos;

	@Autowired
	FoodDocumentPMKSYRepositoryFMP foodDocumentPMKSYRepositoryFMP;

	@Autowired
	FoodDocumentMDBPRepository foodDocumentMDBPRepository;

	@Autowired
	FoodDocumentBankDPRepository foodDocumentBankDPRepository;

	@Autowired
	VehiclesDetailsRepository vehiclesDetailsRepository;

	@Autowired
	ProjectCostReeferVehiclesRepository projectCostReeferVehiclesRepository;

	@Autowired
	ImplementationScheduleRepository implementationScheduleRepository;

	@Autowired
	ProjectInvestmentDetailsRepository projectInvestmentDetailsRepository;

	@Autowired
	FoodCapitalInvestmentDetailsRepository foodCapitalInvestmentDetailsRepository;
	
	@Autowired
	FoodEligiblePlantMachRepository foodEligiblePlantMachRepository;

	@Autowired
	InvestmentDetailsFoodRepository investmentDetailsFoodRepository;

	@Autowired
	ProjectandProposedEmploymentDetailsRepository projectandproposedemploymentdetailsrepository;

	@Autowired
	CapacityofthePlantOrUnitRepository capacityoftheplantorunitrepository;

	@Autowired
	EmploymentGenerationRepository employmentgenerationrepository;

	@Autowired
	ProductDetailsRepository productdetailsrepository;

	@Autowired
	BEDReeferVehiclesRepository bEDReeferVehiclesRepository;

	@Autowired
	BankTermLoanDetailsRepository bankTermLoanDetailsRepository;

	@Autowired
	FoodBankDetailsRepository foodBankDetailsRepository;

	@Autowired
	CapacityofthaPlantRepository capacityofthaPlantRepository;

	@Autowired
	DetailsofproductExportedRepository detailsofproductExportedRepository;

	@Autowired
	FinishedproductQuantityRepository finishedproductQuantityRepository;

	@Autowired
	TotalAnnualExportTurnoverofunitRepository totalAnnualExportTurnoverofunitRepository;

	@Autowired
	ExportincentivecalculatedonthebasisofAnnualExportTurnoverRepository exptInctvCalAnualExptTrnOverRepos;

	@Autowired
	RegistrationCertificatefromAPEDARepository registrationCertificatefromAPEDARepository;

	@Autowired
	ImportexportcodeandAddressofissuingDGFTofficeRepository ImptExptCodeAddDGFToffRepos;

	@Autowired
	DetailsofExportRepository detailsofExportRepository;

	@Autowired
	BankTermLoanDetailsRepositoryFMP bankTermLoanDetailsRepositoryFMP;

	@Autowired
	MeansOfFinancingRepository meansOfFinancingRepository;

	@Autowired
	MeansOfFinanceBankDPRRepository meansOfFinanceBankDPRRepository;

	@Autowired
	FoodDocumentRepository foodDocumentRepository;

	@Autowired
	DocumentsReeferVehiclesRepository documentsReeferVehiclesRepository;

	@Autowired
	FoodDocumentPMKSYRepository foodDocumentPMKSYRepository;

	@Autowired
	FoodTQMprojectDetailsRepository foodTQMprojectDetailsRepository;

	@Autowired
	CapacityodpnmUnitRepository capacityodpnmUnitRepository;

	@Autowired
	ProductDetailRepository productDetailRepository;

	@Autowired
	ProjectCostRepository projectCostRepository;

	@Autowired
	MeansofFinancingTQMRepository meansofFinancingTQMRepository;

	@Autowired
	ImplementationScheduleTQMRepository implementationScheduleTQMRepository;

	@Autowired
	FoodDocumentTQMRepository foodDocumentTQMRepository;

	@Autowired
	PlantAndMachinerytobeCSISRepository plantAndMachinerytobeCSISRepository;
	@Autowired
	DetailsofEligibleTechnicalCivilWorkRepository detailsofEligibleTechnicalCivilWorkRepository;

private final Logger logger = LoggerFactory.getLogger(FoodDashboard.class);

	@GetMapping(value = "/foodPolicyDashboard")
	public ModelAndView foodDashboard() {
		return new ModelAndView("/foodPolicy/food-dashboard");

	}

	@GetMapping(value = "/foodviewEvaluate")
	public ModelAndView viewAndEvaluateApp() {
		return new ModelAndView("/foodPolicy/food-viewEvaluate");

	}

	@GetMapping(value = "/foodProcessIndustryPolicy")
	public ModelAndView foodProcessIndusPolicy(Model model) {
		logger.debug("Applications For Food LOC");
		List<BusinessEntityDetailsFood> list = foodPolicyDetailsRepository.getAll();
		model.addAttribute("foddPolicyList", list);
		return new ModelAndView("/foodPolicy/food_Process_Industry_Policy");

	}

	@GetMapping(value = "/foodviewApplicationDetails")
	public ModelAndView foodProcessLoc(@RequestParam(value = "unit_Id", required = false) String unitId, Model model,
			HttpSession session) {
		logger.debug("Applications For Food LOC");
		System.out.println("Unit ID: " + unitId);
		model.addAttribute("UnitId", unitId);
		session.setAttribute("unitId", unitId);
		commonFoodViewApplicationDetails(unitId, session, model);
		// sachin
		List<DetailsofProposedPlantAndMachinerytobeInstalled> plantAndMachinerytobeCSIS = plantAndMachinerytobeCSISRepository
				.findAllByunit_id(unitId);
		model.addAttribute("plantAndMachinerytobeCSIS", plantAndMachinerytobeCSIS);
		List<DetailsofEligibleTechnicalCivilWork> detailsofEligibleTechnicalCivilWork = detailsofEligibleTechnicalCivilWorkRepository
				.findAllByunit_id(unitId);
		model.addAttribute("detailsofEligibleTechnicalCivilWork", detailsofEligibleTechnicalCivilWork);

		// sachan

		return new ModelAndView("/foodPolicy/food_Processing-loc");

	}

	@GetMapping(value = "/foodviewApplicatPMKSY")
	public ModelAndView foodProcessLocPMKSY(@RequestParam(value = "unit_Id", required = false) String unitId,
			Model model, HttpSession session) {
		logger.debug("Applications For Food LOC");
		System.out.println("Unit ID: " + unitId);
		commonFoodViewApplicationDetails(unitId, session, model);
		// sachin
		List<DetailsofProposedPlantAndMachinerytobeInstalled> plantAndMachinerytobeCSIS = plantAndMachinerytobeCSISRepository
				.findAllByunit_id(unitId);
		model.addAttribute("plantAndMachinerytobeCSIS", plantAndMachinerytobeCSIS);
		List<DetailsofEligibleTechnicalCivilWork> detailsofEligibleTechnicalCivilWork = detailsofEligibleTechnicalCivilWorkRepository
				.findAllByunit_id(unitId);
		model.addAttribute("detailsofEligibleTechnicalCivilWork", detailsofEligibleTechnicalCivilWork);

		// sachan
		return new ModelAndView("/foodPolicy/food_Processing-loc-PMKSY");

	}

	@GetMapping(value = "/foodViewApplication_MFP_PMKSY")
	public ModelAndView foodviewApplication_MFP_PMKSY(@RequestParam(value = "unit_Id", required = false) String unitId,
			Model model, HttpSession session) {
		logger.debug("Applications For Food LOC");
		System.out.println("Unit ID: " + unitId);
		session.setAttribute("unitId", unitId);
		commonFoodViewApplicationDetails(unitId, session, model);
		return new ModelAndView("/foodPolicy/foodProcessingLocMFPPMKSY");

	}

	@GetMapping(value = "/foodISReeferVehicles")
	public ModelAndView foodReeferVehicles(@RequestParam(value = "unit_Id", required = false) String unitId,
			Model model, HttpSession session) {
		logger.debug("Applications For Food LOC");
		System.out.println("Unit ID: " + unitId);
		session.setAttribute("unitId", unitId);
		commonFoodViewApplicationDetails(unitId, session, model);
		return new ModelAndView("/foodPolicy/reeferVehiclesFoodPolicyForm");

	}

	@GetMapping(value = "/foodProcessingFPI")
	public ModelAndView foodProcessingFPI(@RequestParam(value = "unit_Id", required = false) String unitId, Model model,
			HttpSession session) {
		logger.debug("Applications For Food LOC");
		// System.out.println("Unit ID: " + unitId);
		// sachin
		List<DetailsofProposedPlantAndMachinerytobeInstalled> plantAndMachinerytobeCSIS = plantAndMachinerytobeCSISRepository
				.findAllByunit_id(unitId);
		model.addAttribute("plantAndMachinerytobeCSIS", plantAndMachinerytobeCSIS);
		List<DetailsofEligibleTechnicalCivilWork> detailsofEligibleTechnicalCivilWork = detailsofEligibleTechnicalCivilWorkRepository
				.findAllByunit_id(unitId);
		model.addAttribute("detailsofEligibleTechnicalCivilWork", detailsofEligibleTechnicalCivilWork);

		// sachan
		commonFoodViewApplicationDetails(unitId, session, model);
		return new ModelAndView("/foodPolicy/food_Processing-FPI");

	}

	@GetMapping(value = "/foodProcessingTQM")
	public ModelAndView foodProcessingTQM(@RequestParam(value = "unit_Id", required = false) String unitId, Model model,
			HttpSession session) {
		logger.debug("Applications For Food LOC");
		System.out.println("Unit ID: " + unitId);
		session.setAttribute("unitId", unitId);
		commonFoodViewApplicationDetails(unitId, session, model);
		return new ModelAndView("/foodPolicy/food_TQM_Patent");

	}

	@GetMapping(value = "/foodProcessingMDBP")
	public ModelAndView foodProcessingMDBP(@RequestParam(value = "unit_Id", required = false) String unitId,
			Model model, HttpSession session) {
		logger.debug("Applications MDBP For Food LOC");
		System.out.println("Unit ID: " + unitId);
		session.setAttribute("unitId", unitId);
		commonFoodViewApplicationDetails(unitId, session, model);
		return new ModelAndView("/foodPolicy/foodProcessingMDBP");

	}

	@GetMapping(value = "/foodProcessingBankDPR")
	public ModelAndView foodProcessingBankDPR(@RequestParam(value = "unit_Id", required = false) String unitId,
			Model model, HttpSession session) {
		logger.debug("Applications MDBP For Food LOC");
		System.out.println("Unit ID: " + unitId);
		session.setAttribute("unitId", unitId);
		commonFoodViewApplicationDetails(unitId, session, model);
		return new ModelAndView("/foodPolicy/foodProcessingBankDPR");

	}

	@GetMapping(value = "/evaluationViewCsIs")
	public ModelAndView foodEvalutionView(
			@ModelAttribute("foodViewEvaluateCSIS") FoodEvalutionViewCSIS foodEvalutionViewCSIS, Model model,
			HttpSession session) {
		
		String unitId=(String) session.getAttribute("unitId");

		// sachan
		commonFoodViewApplicationDetails(unitId, session, model);
		// sachin
		List<DetailsofProposedPlantAndMachinerytobeInstalled> plantAndMachinerytobeCSIS = plantAndMachinerytobeCSISRepository
				.findAllByunit_id(unitId);
		model.addAttribute("plantAndMachinerytobeCSIS", plantAndMachinerytobeCSIS);
		List<DetailsofEligibleTechnicalCivilWork> detailsofEligibleTechnicalCivilWork = detailsofEligibleTechnicalCivilWorkRepository
				.findAllByunit_id(unitId);
		model.addAttribute("detailsofEligibleTechnicalCivilWork", detailsofEligibleTechnicalCivilWork);

		
		
		FoodEvalutionViewCSIS evaluteList=evaluateCSISRepository.getDetailsByunitId(unitId);
		if(evaluteList !=null)
		{
			
			model.addAttribute("foodViewEvaluateCSIS", evaluteList);
			} else {
				model.addAttribute("foodViewEvaluateCSIS", foodEvalutionViewCSIS);
			}
			
			
		List<FoodElegibleCostOfPlantMach> foodElegibleCostOfPlantMach = foodEligiblePlantMachRepository.findAllByunitId(unitId);
		
		if (foodElegibleCostOfPlantMach != null) {
			model.addAttribute("plantAndMachinerytobeCSISList", foodElegibleCostOfPlantMach);
		}else
		{
			model.addAttribute("plantAndMachinerytobeCSISList", evaluteList);
		}
		
		  List<EligibleTechnicalCivilWork> eligibleTechnicalCivilWork =
		  foodTechnicalCivilWorkRepository .findAllByunitId(unitId); 
		  if(eligibleTechnicalCivilWork != null) {
		  model.addAttribute("eligibleTechnicalCivilWorkList",
		  eligibleTechnicalCivilWork); } else {
		  model.addAttribute("eligibleTechnicalCivilWorkList", evaluteList); }
	
		return new ModelAndView("/foodPolicy/foodEvalutionViewCSIS");
		}

	@GetMapping(value = "/evaluationViewISRV")
	public ModelAndView evaluationViewISRV(@ModelAttribute("foodViewEvaluateISRV") FoodEvalutionViewISRV foodEvalutionViewISRV, Model model,
			HttpSession session) {
        String unitId=(String) session.getAttribute("unitId");
		System.out.println("ggggggggggg" + unitId);
		commonFoodViewApplicationDetails(unitId, session, model);
		FoodEvalutionViewISRV isrvList=foodEvaluateReeferVehiclesRepository.getDetailsByunitId(unitId);
		if(isrvList !=null)
		{
			
			model.addAttribute("foodViewEvaluateISRV", isrvList);
			} else {
				model.addAttribute("foodViewEvaluateISRV", foodEvalutionViewISRV);
			}
			
		
		//model.addAttribute("foodViewEvaluateISRV", foodEvalutionViewISRV);
		return new ModelAndView("/foodPolicy/foodEvalutionViewISRV");
	}

	// Common Method get Data All Form
	public void commonFoodViewApplicationDetails(String unitId, HttpSession session, Model model) {

		session.setAttribute("unitId", unitId);
		String controlId = unitId.substring(0, unitId.length() - 2);

		/* Business Entity Details */
		BusinessEntityDetailsFood busfood = new BusinessEntityDetailsFood();
		Identifier identifier = new Identifier();
		identifier.setUnit_id(unitId);
		identifier.setControl_id(controlId);
		Optional<BusinessEntityDetailsFood> businessEntityDetailsfood = foodPolicyDetailsRepository
				.findById(identifier);

		byte[] encodeBase64 = Base64.encodeBase64(businessEntityDetailsfood.get().getPhotographofpromoterData());
		String base64Encoded = null;
		try {
			base64Encoded = new String(encodeBase64, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		busfood.setBase64imageFile(base64Encoded);
		model.addAttribute("businesfoodImg", busfood.getBase64imageFile());
		model.addAttribute("businessEntityDetailsfood", businessEntityDetailsfood.get());
		Date dateofincorp = businessEntityDetailsfood.get().getDateofincorporation();
		System.out.println("dateofincorp" + dateofincorp);

		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String strDate = formatter.format(dateofincorp);
		System.out.println(strDate);

		model.addAttribute("corformatdate", strDate);

		List<FinancialStatus> fianancStatus = financeStatusRepos.findAllByunit_id(unitId);
		model.addAttribute("fianancStatusList", fianancStatus);

		Optional<ProjectandProposedEmploymentDetails> projectandproposedemploymentdetails = projectandproposedemploymentdetailsrepository
				.findById(identifier);
		model.addAttribute("projectandproposedemploymentdetails", projectandproposedemploymentdetails.get());

		Optional<CapacityofthePlantOrUnit> capacityoftheplantorunit = capacityoftheplantorunitrepository
				.findById(identifier);
		model.addAttribute("capacityoftheplantorunit", capacityoftheplantorunit.get());

		Optional<EmploymentGeneration> employmentgeneration = employmentgenerationrepository.findById(identifier);
		model.addAttribute("employmentgeneration", employmentgeneration.get());

		List<ProductDetails> productdetails = productdetailsrepository.findAllByunit_id(unitId);
		model.addAttribute("productdetails", productdetails);

		List<MeansofFinancingTQM> meansofFinancingTQM = meansofFinancingTQMRepository.findAllByunit_id(unitId);
		model.addAttribute("meansofFinancingTQM", meansofFinancingTQM);

		Optional<InvestmentDetailsFood> investmentDetailsFood = investmentDetailsFoodRepository.findById(unitId);

		model.addAttribute("investmentDetailsFood", investmentDetailsFood.get());

		List<ProjectInvestmentDetails> projectInvestmentDetails = projectInvestmentDetailsRepository
				.findAllByunit_id(unitId);
		model.addAttribute("projectInvestmentDetails", projectInvestmentDetails);

		List<MeansofFinancing> meansofFinancing = meansOfFinancingRepository.findAllByunit_id(unitId);
		model.addAttribute("meansofFinancing", meansofFinancing);

		List<ImplementationSchedule> implementationSchedule = implementationScheduleRepository.findAllByunit_id(unitId);
		model.addAttribute("implementationSchedule", implementationSchedule);

		for (ImplementationSchedule implementationScheduledate : implementationSchedule) {
			if(implementationScheduledate.getDetailsacquisitiondateofland() != null)
			{
			Date acquisition = implementationScheduledate.getDetailsacquisitiondateofland();
			model.addAttribute("acquisitiondate", formatter.format(acquisition));

			Date buildingconstructionfrom = implementationScheduledate.getDetailsdateofbuildingconstructionfrom();
			model.addAttribute("buildingconstructionfrom", formatter.format(buildingconstructionfrom));

			Date buildingconstructionto = implementationScheduledate.getDetailsdateofbuildingconstructionto();
			model.addAttribute("buildingconstructionto", formatter.format(buildingconstructionto));

			Date commercialproductionrunning = implementationScheduledate.getDetailsdateofcommercialproductionrunning();
			model.addAttribute("commercialproductionrunning", formatter.format(commercialproductionrunning));

			Date plantmachineryfrom = implementationScheduledate.getDetailsdateofplacingorderforplantmachineryfrom();
			model.addAttribute("plantmachineryfrom", formatter.format(plantmachineryfrom));

			Date plantmachineryto = implementationScheduledate.getDetailsdateofplacingorderforplantmachineryto();
			model.addAttribute("plantmachineryto", formatter.format(plantmachineryto));

			Date triaproductiondatefrom = implementationScheduledate.getDetailstrialproductiondatefrom();
			model.addAttribute("triaproductiondatefrom", formatter.format(triaproductiondatefrom));

			Date triaproductiondateto = implementationScheduledate.getDetailstrialproductiondateto();
			model.addAttribute("triaproductiondateto", formatter.format(triaproductiondateto));
			}
		}

		List<BankTermLoanDetails> bankTermLoanDetails = bankTermLoanDetailsRepository.findAllByunit_id(unitId);
		model.addAttribute("bankTermLoanDetails", bankTermLoanDetails);

		List<FoodCapitalInvestmentDetails> capitalInvestmentDetails = foodCapitalInvestmentDetailsRepository
				.findAllByunit_id(unitId);
		model.addAttribute("capitalInvestmentDetails", capitalInvestmentDetails);

		List<FMPMeansofFinancing> meansofFinancingFMP = meanceOfFinanceFMPRepos.findAllByunit_id(unitId);
		model.addAttribute("meansofFinancingFMP", meansofFinancingFMP);

		List<BankTermLoanDetailsFMP> bankTermLoanDetailsFMP = bankTermLoanDetailsRepositoryFMP.findAllByunit_id(unitId);
		model.addAttribute("bankTermLoanDetailsFMP", bankTermLoanDetailsFMP);

		Optional<FoodDocument> foodDocument = foodDocumentRepository.findById(identifier);
		model.addAttribute("foodDocument", foodDocument.get());

		Optional<FoodDocumentPMKSY> foodDocumentPMKSY = foodDocumentPMKSYRepository.findById(identifier);
		model.addAttribute("foodDocumentPMKSY", foodDocumentPMKSY.get());

		Optional<FoodDocumentTQM> foodDocumentTQM = foodDocumentTQMRepository.findById(identifier);
		// System.out.println(foodDocumentTQM.get().getDetailedprojectreport());;
		model.addAttribute("foodDocumentTQM", foodDocumentTQM.get());

		Optional<FoodDocumentFMP_PMKSY> foodDocumentFMP_PMKSY = foodDocumentPMKSYRepositoryFMP.findById(identifier);
		model.addAttribute("foodDocumentFMP_PMKSY", foodDocumentFMP_PMKSY.get());

		// Project Details Reefer Vehicles

		Optional<BEDReeferVehicles> bEDVReeferVehicles = bEDReeferVehiclesRepository.findById(identifier);

		model.addAttribute("bEDVReeferVehicles", bEDVReeferVehicles.get());

		Optional<ProjectDtailsPreVehicles> ProjectDtailsReeVehicles = projectDetailsReeferVehilesRepository
				.findById(identifier);

		model.addAttribute("ProjectDtailsReefereVehicles", ProjectDtailsReeVehicles.get());
		model.addAttribute("projectDtailsMDBP", ProjectDtailsReeVehicles.get());

		List<CapacityofthePlantFood> capacityofthePlantFood = capacityofthaPlantRepository.findAllByunit_id(unitId);
		model.addAttribute("capacityofthePlantFood", capacityofthePlantFood);

		List<DetailsofproductExported> detailsofproductExported = detailsofproductExportedRepository
				.findAllByunit_id(unitId);
		model.addAttribute("detailsofproductExported", detailsofproductExported);

		List<FinishedproductQuantity> finishedproductQuantity = finishedproductQuantityRepository
				.findAllByunit_id(unitId);
		model.addAttribute("finishedproductQuantity", finishedproductQuantity);

		List<TotalAnnualExportTurnoverofunit> totalAnnualExportTurnoverofunit = totalAnnualExportTurnoverofunitRepository
				.findAllByunit_id(unitId);
		model.addAttribute("totalAnnualExportTurnoverofunit", totalAnnualExportTurnoverofunit);

		List<ExportincentivecalculatedonthebasisofAnnualExportTurnover> exptInctvCalAnualExptTrnOver = exptInctvCalAnualExptTrnOverRepos
				.findAllByunit_id(unitId);
		model.addAttribute("exptInctvCalAnualExptTrnOver", exptInctvCalAnualExptTrnOver);

		List<RegistrationCertificatefromAPEDA> registrationCertificatefromAPEDA = registrationCertificatefromAPEDARepository
				.findAllByunit_id(unitId);
		model.addAttribute("registrationCertificatefromAPEDA", registrationCertificatefromAPEDA);

		List<ImportexportcodeandAddressofissuingDGFToffice> ImptExptCodeAddDGFToff = ImptExptCodeAddDGFToffRepos
				.findAllByunit_id(unitId);
		model.addAttribute("ImptExptCodeAddDGFToff", ImptExptCodeAddDGFToff);

		List<DetailsofExport> detailsofExport = detailsofExportRepository.findAllByunit_id(unitId);
		model.addAttribute("detailsofExport", detailsofExport);

		List<FoodBankDetails> bankDetails = foodBankDetailsRepository.findAllByunit_id(unitId);
		model.addAttribute("bankDetails", bankDetails);

		Optional<FoodDocumentsMDBP> foodDocumentMDBP = foodDocumentMDBPRepository.findById(identifier);
		model.addAttribute("foodDocumentMDBP", foodDocumentMDBP.get());

		Optional<FoodDocumentsBankDPR> foodDocumentsBankDPR = foodDocumentBankDPRepository.findById(identifier);
		model.addAttribute("foodDocumentsBankDPR", foodDocumentsBankDPR.get());

		// vehicle details

		List<ReeferVehiclesDetails> vehiclesDetails = vehiclesDetailsRepository.findAllByunit_id(unitId);
		for (ReeferVehiclesDetails p : vehiclesDetails) {
			System.out.println(p.getquantitynumberofthereefervehiclesmobileprecoolingvans());
		}
		model.addAttribute("reeferVehiclesDetails", vehiclesDetails);

		// Project Cost

		List<ProjectCostReeferVehicles> projectCostReeferVehicles = projectCostReeferVehiclesRepository
				.findAllByunit_id(unitId);
		model.addAttribute("projectCostReeferVehicles", projectCostReeferVehicles);

		// MeanceOfFinancing for ReeferVehicles

		List<MeansOfFinancingReeferVehicles> meansOfFinancingReeferVehicles = meanceOfFinancReeferVehiclesRepository
				.findAllByunit_id(unitId);
		model.addAttribute("meansOfFinancingReeferVehicles", meansOfFinancingReeferVehicles);

		Optional<DocumentsReeferVehicles> documentsReeferVehicles = documentsReeferVehiclesRepository
				.findById(identifier);

		model.addAttribute("documentsReeferVehicles", documentsReeferVehicles.get());

		session.setAttribute("identifier", identifier);

		Optional<FoodTQMprojectDetails> foodTQMprojectdetails = foodTQMprojectDetailsRepository.findById(identifier);
		model.addAttribute("foodTQMprojectdetails", foodTQMprojectdetails.get());

		List<ProductDetail> productDetail = productDetailRepository.findAllByunit_id(unitId);
		model.addAttribute("productDetail", productDetail);

		List<CapacityofpnmUnit> capacityofpnmUnit = capacityodpnmUnitRepository.findAllByunit_id(unitId);
		model.addAttribute("capacityofpnmUnit", capacityofpnmUnit);

		List<ProjectCost> projectCost = projectCostRepository.findAllByunit_id(unitId);
		model.addAttribute("projectCost", projectCost);

		List<DetailsofProposedPlantAndMachinerytobeInstalled> plantAndMachinerytobeCSIS = plantAndMachinerytobeCSISRepository
				.findAllByunit_id(unitId);
		model.addAttribute("plantAndMachinerytobeCSIS", plantAndMachinerytobeCSIS);

		List<DetailsofEligibleTechnicalCivilWork> detailsofEligibleTechnicalCivilWork = detailsofEligibleTechnicalCivilWorkRepository
				.findAllByunit_id(unitId);
		model.addAttribute("detailsofEligibleTechnicalCivilWork", detailsofEligibleTechnicalCivilWork);

		// Bank DPR
		Optional<TotalProjectCostproposedinDPR> totalProjectCostproposedinDPR = totalProjectCostproposedinDPRRepository
				.findById(identifier);

		model.addAttribute("totalProjectCostproposedinDPR", totalProjectCostproposedinDPR.get());
		List<MeansofFinanceBankDPR> meansofFinanceBankDPR = meansOfFinanceBankDPRRepository.findAllByunit_id(unitId);
		model.addAttribute("meansofFinanceBankDPR", meansofFinanceBankDPR);

	}

	/// Start Document Downoad by Pankaj
	@GetMapping(value = "/downloadFoodDocument")
	public void downloadFoodDocument(@RequestParam(value = "fileName", required = false) String fileName, Model model,
			HttpServletResponse response, HttpSession session) {

		Identifier identifier = (Identifier) session.getAttribute("identifier");

		Optional<FoodDocument> foodDocument = foodDocumentRepository.findById(identifier);
		byte[] fileData = null;
		if (fileName.equals(foodDocument.get().getIncorporationcertificateoffirm())) {
			fileData = foodDocument.get().getIncorporationcertificateoffirmData();
		}
		if (fileName.equals(foodDocument.get().getPartnershipdeedbyelawsofthefirm())) {
			fileData = foodDocument.get().getPartnershipdeedbyelawsofthefirmData();
		}
		if (fileName.equals(foodDocument.get().getRegisteredlanddeedrentagreement())) {
			fileData = foodDocument.get().getRegisteredlanddeedrentagreementData();
		}
		if (fileName.equals(foodDocument.get().getKhasrakhatauni())) {
			fileData = foodDocument.get().getKhasrakhatauniData();
		}
		if (fileName.equals(foodDocument.get().getDetailedprojectreportasperannexurea16())) {
			fileData = foodDocument.get().getDetailedprojectreportasperannexurea16Data();
		}
		if (fileName.equals(foodDocument.get().getTermloansanctionletter())) {
			fileData = foodDocument.get().getTermloansanctionletterData();
		}
		if (fileName.equals(foodDocument.get().getBankappraisalreport())) {
			fileData = foodDocument.get().getBankappraisalreportData();
		}
		if (fileName.equals(foodDocument.get().getBuildingplanoffactoryapprovedbycompetentauthority())) {
			fileData = foodDocument.get().getBuildingplanoffactoryapprovedbycompetentauthorityData();
		}
		if (fileName.equals(foodDocument.get().getUdyogaadharudyamregistration())) {
			fileData = foodDocument.get().getUdyogaadharudyamregistrationData();
		}
		if (fileName.equals(foodDocument.get().getNocforpollution())) {
			fileData = foodDocument.get().getNocforpollutionData();
		}
		if (fileName.equals(foodDocument.get().getNocforfiresafety())) {
			fileData = foodDocument.get().getNocforfiresafetyData();
		}
		if (fileName.equals(foodDocument.get().getPowerloadsanctionletterformuppcl())) {
			fileData = foodDocument.get().getPowerloadsanctionletterformuppclData();
		}
		if (fileName.equals(foodDocument.get().getFssailicensecertificate())) {
			fileData = foodDocument.get().getFssailicensecertificateData();
		}
		if (fileName.equals(foodDocument.get().getRepaymentscheduleoftermloan())) {
			fileData = foodDocument.get().getRepaymentscheduleoftermloanData();
		}
		if (fileName.equals(foodDocument.get().getAnnexurea6())) {
			fileData = foodDocument.get().getAnnexurea6data();
		}
		if (fileName.equals(foodDocument.get().getAnnexurea7charteredengineercertificateforcivilwork())) {
			fileData = foodDocument.get().getAnnexurea7charteredengineercertificateforcivilworkData();
		}
		if (fileName.equals(foodDocument.get().getAnnexurea8cecertificateforplantampmachinery())) {
			fileData = foodDocument.get().getAnnexurea8cecertificateforplantampmachineryData();
		}
		if (fileName.equals(foodDocument.get().getAnnexurea18cacertificateforincreaseingrossblockvalue())) {
			fileData = foodDocument.get().getAnnexurea18cacertificateforincreaseingrossblockvalueData();
		}
		try {
			downloadFoodDoc(fileName, fileData, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@SuppressWarnings("unlikely-arg-type")
	@GetMapping(value = "/downloadFoodReeferVehiclesDocument")
	public void downloadFoodReeferVehiclesDocument(@RequestParam(value = "fileName", required = false) String fileName,
			Model model, HttpServletResponse response, HttpSession session) {

		Identifier identifier = (Identifier) session.getAttribute("identifier");

		Optional<DocumentsReeferVehicles> foodRVDocument = documentsReeferVehiclesRepository.findById(identifier);
		byte[] fileData = null;
		if (fileName.equals(foodRVDocument.get().getIncorporationcertificateoffirm())) {
			fileData = foodRVDocument.get().getIncorporationcertificateoffirmData();
		}
		if (fileName.equals(foodRVDocument.get().getSelfnbspattesteddetailedprojectreportnbsp())) {
			fileData = foodRVDocument.get().getSelfnbspattesteddetailedprojectreportnbspData();
		}
		if (fileName.equals(foodRVDocument.get().getTermloansanctionletter())) {
			fileData = foodRVDocument.get().getTermloansanctionletterData();
		}
		if (fileName.equals(foodRVDocument.get().getBankappraisalreport())) {
			fileData = foodRVDocument.get().getBankappraisalreportData();
		}
		if (fileName.equals(foodRVDocument.get().getIncorporationcertificateoffirm())) {
			fileData = foodRVDocument.get().getIncorporationcertificateoffirmData();
		}
		if (fileName.equals(foodRVDocument.get().getPartnershipdeedbylaws())) {
			fileData = foodRVDocument.get().getPartnershipdeedbylawsData();
		}
		if (fileName.equals(foodRVDocument.get().getCopyofdeliverychallanampreceiptofchassisbodyforreefervehicles())) {
			fileData = foodRVDocument.get().getCopyofdeliverychallanampreceiptofchassisbodyforreefervehiclesData();
		}
		if (fileName.equals(foodRVDocument.get().getBillboucherinvoicescertifiedbycharteredengineer())) {
			fileData = foodRVDocument.get().getBillboucherinvoicescertifiedbycharteredengineerData();
		}
		if (fileName.equals(foodRVDocument.get().getInvoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankf())) {
			fileData = foodRVDocument.get().getInvoicesofnbspchassisbodyamprefrigerationunitcertifiedbybankfData();
		}
		if (fileName.equals(foodRVDocument.get().getRepaymentscheduleofnbsptermloanforinterestbybankfinancialinst())) {
			fileData = foodRVDocument.get().getRepaymentscheduleofnbsptermloanforinterestbybankfinancialinstData();
		}
		if (fileName.equals(foodRVDocument.get().getAffidavitonnonjudicialstamppaperofrs100())) {
			fileData = foodRVDocument.get().getAffidavitonnonjudicialstamppaperofrs100Data();
		}
		if (fileName.equals(foodRVDocument.get().getBankclaimnbspcertificatenbspforreefervehiclesmobileprecooling())) {
			fileData = foodRVDocument.get().getBankclaimnbspcertificatenbspforreefervehiclesmobileprecoolingData();
		}
		if (fileName.equals(foodRVDocument.get().getTechnicalspecificationdetailsofnbspreefervehiclemobileprecool())) {
			fileData = foodRVDocument.get().getTechnicalspecificationdetailsofnbspreefervehiclemobileprecoolData();
		}

		try {
			downloadFoodDoc(fileName, fileData, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@GetMapping(value = "/downloadfoodDocumentPMKSY")
	public void downloadfoodDocumentPMKSY(@RequestParam(value = "fileName", required = false) String fileName,
			Model model, HttpServletResponse response, HttpSession session) {

		Identifier identifier = (Identifier) session.getAttribute("identifier");

		Optional<FoodDocumentPMKSY> foodDocumentPMKSY = foodDocumentPMKSYRepository.findById(identifier);

		byte[] fileData = null;
		if (fileName.equals(foodDocumentPMKSY.get().getIncorporationcertificateoffirm())) {
			fileData = foodDocumentPMKSY.get().getIncorporationcertificateoffirmData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getPartnershipdeedbyelawsofthefirm())) {
			fileData = foodDocumentPMKSY.get().getPartnershipdeedbyelawsofthefirmData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getRegisteredlanddeedrentagreement())) {
			fileData = foodDocumentPMKSY.get().getRegisteredlanddeedrentagreementData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getKhasrakhatauni())) {
			fileData = foodDocumentPMKSY.get().getKhasrakhatauniData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getDetailedprojectreportsubmittedtomofpi())) {
			fileData = foodDocumentPMKSY.get().getDetailedprojectreportsubmittedtomofpi_data();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getTermloansanctionletter())) {
			fileData = foodDocumentPMKSY.get().getTermloansanctionletterData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getBankappraisalreport())) {
			fileData = foodDocumentPMKSY.get().getBankappraisalreportData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getIemudyogaadharudyamregister())) {
			fileData = foodDocumentPMKSY.get().getIemudyogaadharudyamregisterData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getNocforpollution())) {
			fileData = foodDocumentPMKSY.get().getNocforpollutionData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getNocforfiresafety())) {
			fileData = foodDocumentPMKSY.get().getNocforfiresafetyData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getPowerloadsanctionletterformuppcl())) {
			fileData = foodDocumentPMKSY.get().getPowerloadsanctionletterformuppclData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getRepaymentscheduleoftermloan())) {
			fileData = foodDocumentPMKSY.get().getRepaymentscheduleoftermloanData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getAnnexurea6())) {
			fileData = foodDocumentPMKSY.get().getAnnexurea6Data();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getAnnexurea7charteredengineercertificateforcivilwork())) {
			fileData = foodDocumentPMKSY.get().getAnnexurea7charteredengineercertificateforcivilworkData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getAnnexurea8cecertificateforplantampmachinery())) {
			fileData = foodDocumentPMKSY.get().getAnnexurea8cecertificateforplantampmachineryData();
		}
		if (fileName.equals(foodDocumentPMKSY.get().getSanctionletterissuedbymofpigovernmentofindia())) {
			fileData = foodDocumentPMKSY.get().getSanctionletterissuedbymofpigovernmentofindiaData();
		}
		try {
			downloadFoodDoc(fileName, fileData, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@GetMapping(value = "/downloadfoodDocumentFMP_PMKSY")
	public void downloadfoodDocumentFMP_PMKSY(@RequestParam(value = "fileName", required = false) String fileName,
			Model model, HttpServletResponse response, HttpSession session) {

		Identifier identifier = (Identifier) session.getAttribute("identifier");

		Optional<FoodDocumentFMP_PMKSY> foodDocumentFMP_PMKSY = foodDocumentPMKSYRepositoryFMP.findById(identifier);

		byte[] fileData = null;
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getIncorporationcertificateofspvpea())) {
			fileData = foodDocumentFMP_PMKSY.get().getIncorporationcertificateofspvpeaData();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getMemorandumamparticleofassociationandbyelawsofthespvpea())) {
			fileData = foodDocumentFMP_PMKSY.get().getMemorandumamparticleofassociationandbyelawsofthespvpeaData();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getRegisteredlanddeed())) {
			fileData = foodDocumentFMP_PMKSY.get().getRegisteredlanddeedData();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getRentagreementregisterednbsp())) {
			fileData = foodDocumentFMP_PMKSY.get().getRentagreementregisterednbspData();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getKhasrakhatauni())) {
			fileData = foodDocumentFMP_PMKSY.get().getKhasrakhatauniData();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getLandusecertificate())) {
			fileData = foodDocumentFMP_PMKSY.get().getLandusecertificateData();
		}

		if (fileName.equals(
				foodDocumentFMP_PMKSY.get().getDetailedprojectreportsubmittedtoministryoffoodprocessingindus())) {
			fileData = foodDocumentFMP_PMKSY.get()
					.getDetailedprojectreportsubmittedtoministryoffoodprocessingindusData();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getTermloansanctionletter())) {
			fileData = foodDocumentFMP_PMKSY.get().getTermloansanctionletterData();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getBankappraisalreport())) {
			fileData = foodDocumentFMP_PMKSY.get().getBankappraisalreportData();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getLayoutplanformegafoodparkagroprocessingcluster())) {
			fileData = foodDocumentFMP_PMKSY.get().getLayoutplanformegafoodparkagroprocessingclusterData();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getAnnexurea6())) {
			fileData = foodDocumentFMP_PMKSY.get().getAnnexurea6Data();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getAnnexurea4())) {
			fileData = foodDocumentFMP_PMKSY.get().getAnnexurea4Data();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getAnnexurea10())) {
			fileData = foodDocumentFMP_PMKSY.get().getAnnexurea10Data();
		}
		if (fileName.equals(foodDocumentFMP_PMKSY.get().getAnnexurea11())) {
			fileData = foodDocumentFMP_PMKSY.get().getAnnexurea11Data();
		}
		if (fileName.equals(
				foodDocumentFMP_PMKSY.get().getSanctionedletterissuedbyministryoffoodprocessingindustriesgov())) {
			fileData = foodDocumentFMP_PMKSY.get()
					.getSanctionedletterissuedbyministryoffoodprocessingindustriesgovData();
		}
		try {
			downloadFoodDoc(fileName, fileData, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@GetMapping(value = "/downloadfoodDocumentMDBP")
	public void downloadfoodDocumentMDBP(@RequestParam(value = "fileName", required = false) String fileName,
			Model model, HttpServletResponse response, HttpSession session) {

		Identifier identifier = (Identifier) session.getAttribute("identifier");

		Optional<FoodDocumentsMDBP> foodDocumentMDBP = foodDocumentMDBPRepository.findById(identifier);

		byte[] fileData = null;
		if (fileName.equals(foodDocumentMDBP.get().getIncorporationcertificateoffirm())) {
			fileData = foodDocumentMDBP.get().getIncorporationcertificateoffirmData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getPartnershipdeedbyelawsofthefirm())) {
			fileData = foodDocumentMDBP.get().getPartnershipdeedbyelawsofthefirmData();
		}
		if (fileName
				.equals(foodDocumentMDBP.get().getAffiliationletterfromauthorizedsignatoryofapplicationoforgani())) {
			fileData = foodDocumentMDBP.get().getAffiliationletterfromauthorizedsignatoryofapplicationoforganiData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getSelfattestedpromotersbiodataoforganization())) {
			fileData = foodDocumentMDBP.get().getSelfattestedpromotersbiodataoforganizationData();
		}
		if (fileName
				.equals(foodDocumentMDBP.get().getForcompletionofexportresponsibilityshipmentairwaybillampexpor())) {
			fileData = foodDocumentMDBP.get().getForcompletionofexportresponsibilityshipmentairwaybillampexporData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getDetailofquantityofagricultureampexportedproduce())) {
			fileData = foodDocumentMDBP.get().getDetailofquantityofagricultureampexportedproduceData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getImporterexportercodecertificatefromdgftoffice())) {
			fileData = foodDocumentMDBP.get().getImporterexportercodecertificatefromdgftofficeData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getRcmcregistrationcertificatefromapeeda())) {
			fileData = foodDocumentMDBP.get().getRcmcregistrationcertificatefromapeedaData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getBillsofshipmentairforexportedmaterialquantity())) {
			fileData = foodDocumentMDBP.get().getBillsofshipmentairforexportedmaterialquantityData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getExportgeneralmanifestcertificatefromcustomdepartment())) {
			fileData = foodDocumentMDBP.get().getExportgeneralmanifestcertificatefromcustomdepartmentData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getDetailsofrawmaterialnbsp())) {
			fileData = foodDocumentMDBP.get().getDetailsofrawmaterialnbspData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getFreightbillsissuingauthoritydetails())) {
			fileData = foodDocumentMDBP.get().getFreightbillsissuingauthoritydetailsData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getBuyerpurchaseorderfromimportingcountry())) {
			fileData = foodDocumentMDBP.get().getBuyerpurchaseorderfromimportingcountryData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getPhytosanitarycertificate())) {
			fileData = foodDocumentMDBP.get().getPhytosanitarycertificateData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getExportinvoicecumpackinglistissuingauthoritydetails())) {
			fileData = foodDocumentMDBP.get().getExportinvoicecumpackinglistissuingauthoritydetailsData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getNameofbankrealizationcertificate())) {
			fileData = foodDocumentMDBP.get().getNameofbankrealizationcertificateData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getShippingbillsforexports())) {
			fileData = foodDocumentMDBP.get().getShippingbillsforexportsData();
		}
		if (fileName.equals(foodDocumentMDBP.get().getDeclarationupload())) {
			fileData = foodDocumentMDBP.get().getDeclarationuploadData();
		}
		try {
			downloadFoodDoc(fileName, fileData, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	@GetMapping(value = "/downloadfoodDocumentsBankDPR")
	public void downloadfoodDocumentsBankDPR(@RequestParam(value = "fileName", required = false) String fileName,
			Model model, HttpServletResponse response, HttpSession session) {

		Identifier identifier = (Identifier) session.getAttribute("identifier");

		Optional<FoodDocumentsBankDPR> foodDocumentsBankDPR = foodDocumentBankDPRepository.findById(identifier);

		byte[] fileData = null;
		if (fileName.equals(foodDocumentsBankDPR.get().getIncorporationcertificateoffirm())) {
			fileData = foodDocumentsBankDPR.get().getIncorporationcertificateoffirmData();
		}
		if (fileName.equals(foodDocumentsBankDPR.get().getPartnershipdeedbyelawsofthefirm())) {
			fileData = foodDocumentsBankDPR.get().getPartnershipdeedbyelawsofthefirmData();
		}
		if (fileName.equals(foodDocumentsBankDPR.get().getChiefpromoterdirectorsbiodata())) {
			fileData = foodDocumentsBankDPR.get().getChiefpromoterdirectorsbiodataData();
		}
		if (fileName.equals(foodDocumentsBankDPR.get().getAuditedstatementofaccounts())) {
			fileData = foodDocumentsBankDPR.get().getAuditedstatementofaccountsData();
		}
		if (fileName.equals(foodDocumentsBankDPR.get().getCopiesofpaidbillvoucheragainstdpr())) {
			fileData = foodDocumentsBankDPR.get().getCopiesofpaidbillvoucheragainstdprData();
		}
		if (fileName.equals(foodDocumentsBankDPR.get().getBankloansanctionletter())) {
			fileData = foodDocumentsBankDPR.get().getBankloansanctionletterData();
		}
		if (fileName.equals(foodDocumentsBankDPR.get().getLoanreleasedocument())) {
			fileData = foodDocumentsBankDPR.get().getLoanreleasedocumentData();
		}
		if (fileName.equals(foodDocumentsBankDPR.get().getChartedengineercertificateforprojectimplementation())) {
			fileData = foodDocumentsBankDPR.get().getChartedengineercertificateforprojectimplementationData();
		}
		if (fileName.equals(foodDocumentsBankDPR.get().getProjectreportdetail())) {
			fileData = foodDocumentsBankDPR.get().getProjectreportdetailData();
		}
		if (fileName.equals(foodDocumentsBankDPR.get().getCopyofdprdulysignedbypreparingagencyinstitution())) {
			fileData = foodDocumentsBankDPR.get().getCopyofdprdulysignedbypreparingagencyinstitutionData();
		}

		try {
			downloadFoodDoc(fileName, fileData, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public void downloadFoodDoc(String fileName, byte[] fileData, HttpServletResponse response) {

		if (fileData != null) {
			response.setHeader("Content-Disposition", "attachment; filename=" + fileName + "");
			response.setHeader("Content-Type", "application/pdf");

			// try-with-resources statement
			InputStream is = new ByteArrayInputStream(fileData);
			try {
				IOUtils.copy(is, response.getOutputStream());

			} catch (IOException e) {
				logger.error(String.format("#### downloadFoodDoc exception $$$ %s", e.getMessage()));
			}
			try {
				is.close();
			} catch (IOException e) {

				e.printStackTrace();
			}
		}
	}

	@GetMapping(value = "/downloadfoodDocumentTQM")
	public void downloadfoodDocumentTQM(@RequestParam(value = "fileName", required = false) String fileName,
			Model model, HttpServletResponse response, HttpSession session) {

		Identifier identifier = (Identifier) session.getAttribute("identifier");

		Optional<FoodDocumentTQM> foodDocumentTQM = foodDocumentTQMRepository.findById(identifier);

		byte[] fileData = null;

		if (fileName.equals(foodDocumentTQM.get().getDetailedprojectreport())) {
			fileData = foodDocumentTQM.get().getDetailedprojectreportData();
		}
		if (fileName.equals(foodDocumentTQM.get().getIncometaxreturnsoftheproprietororganizationforpreviousthreeye())) {
			fileData = foodDocumentTQM.get().getIncometaxreturnsoftheproprietororganizationforpreviousthreeyeData();
		}
		if (fileName.equals(foodDocumentTQM.get().getUdyogaadharudyamregistration())) {
			fileData = foodDocumentTQM.get().getUdyogaadharudyamregistration_data();
		}
		if (fileName.equals(foodDocumentTQM.get().getFssailicense())) {
			fileData = foodDocumentTQM.get().getFssailicenseData();
		}
		if (fileName.equals(foodDocumentTQM.get().getDetailedprojectreport())) {
			fileData = foodDocumentTQM.get().getDetailedprojectreportData();
		}
		if (fileName.equals(foodDocumentTQM.get().getBiodataexperienceofconsultant())) {
			fileData = foodDocumentTQM.get().getBiodataexperienceofconsultantData();
		}
		if (fileName.equals(foodDocumentTQM.get().getQuotationofconsultant())) {
			fileData = foodDocumentTQM.get().getQuotationofconsultantData();
		}
		if (fileName.equals(foodDocumentTQM.get().getConsultantregistrationdetails())) {
			fileData = foodDocumentTQM.get().getConsultantregistrationdetailsData();
		}

		if (fileName.equals(foodDocumentTQM.get().getQuotationfromcertificationbodyalongwiththedetailsofthecertifi())) {
			fileData = foodDocumentTQM.get().getQuotationfromcertificationbodyalongwiththedetailsofthecertifiData();
		}
		if (fileName.equals(foodDocumentTQM.get().getDivcertificationagencyshouldbenbspaccreditedbytheqciornationa())) {
			fileData = foodDocumentTQM.get().getDivcertificationagencyshouldbenbspaccreditedbytheqciornationaData();
		}
		if (fileName.equals(foodDocumentTQM.get().getDetailsofplantampmachineryaspergapstudyreportcountersignedbyc())) {
			fileData = foodDocumentTQM.get().getDetailsofplantampmachineryaspergapstudyreportcountersignedbycData();
		}
		if (fileName.equals(foodDocumentTQM.get().getDetailsofplantampmachinerynbspquotationsdulycertifiedbyapprov())) {
			fileData = foodDocumentTQM.get().getDetailsofplantampmachinerynbspquotationsdulycertifiedbyapprovData();
		}

		if (fileName.equals(foodDocumentTQM.get().getGapstudyreportasper())) {
			fileData = foodDocumentTQM.get().getGapstudyreportasperData();
		}
		if (fileName.equals(foodDocumentTQM.get().getAffidavitasper())) {
			fileData = foodDocumentTQM.get().getAffidavitasperData();
		}
		if (fileName.equals(foodDocumentTQM.get().getImplementationscheduleofthehaccpiso9000iso22000gmpghpsystemim())) {
			fileData = foodDocumentTQM.get().getImplementationscheduleofthehaccpiso9000iso22000gmpghpsystemimData();
		}
		if (fileName.equals(foodDocumentTQM.get().getTheapplicantorganizationisrequiredtogiveanundertakingthatthet())) {
			fileData = foodDocumentTQM.get().getTheapplicantorganizationisrequiredtogiveanundertakingthatthetData();
		}

		if (fileName.equals(foodDocumentTQM.get().getProcessofmanufacture())) {
			fileData = foodDocumentTQM.get().getProcessofmanufactureData();
		}
		if (fileName.equals(foodDocumentTQM.get().getListofexistingplantampmachineryandqualitycontrolfacilitieswit())) {
			fileData = foodDocumentTQM.get().getListofexistingplantampmachineryandqualitycontrolfacilitieswitData();
		}
		if (fileName.equals(foodDocumentTQM.get().getPatentcertificateissuedbycompetentauthority())) {
			fileData = foodDocumentTQM.get().getPatentcertificateissuedbycompetentauthorityData();
		}

		if (fileName.equals(foodDocumentTQM.get().getDeclarationupload())) {
			fileData = foodDocumentTQM.get().getDeclarationuploadData();
		}

		try {
			downloadFoodDoc(fileName, fileData, response);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
