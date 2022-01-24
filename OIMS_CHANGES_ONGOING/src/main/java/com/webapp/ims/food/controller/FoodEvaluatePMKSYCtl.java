package com.webapp.ims.food.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.food.Model.DetailsofEligibleTechnicalCivilWork;
import com.webapp.ims.food.Model.DetailsofProposedPlantAndMachinerytobeInstalled;
import com.webapp.ims.food.Model.EligibleCostOfCivilWorkPMKSY;
import com.webapp.ims.food.Model.EligiblePlantMachEvalutionPMKSY;
import com.webapp.ims.food.Model.EligibleTechnicalCivilWork;
import com.webapp.ims.food.Model.FoodElegibleCostOfPlantMach;
import com.webapp.ims.food.Model.FoodEvalutionViewIS;
import com.webapp.ims.food.Model.FoodEvalutionViewPMKYS;
import com.webapp.ims.food.Model.FoodProposeProductsByProducts;
import com.webapp.ims.food.Model.ProductDetails;
import com.webapp.ims.food.Model.ProposedProductByProductPMKSY;
import com.webapp.ims.food.Repository.DetailsofEligibleTechnicalCivilWorkRepository;
import com.webapp.ims.food.Repository.EligibleCostOfCivilWorkPMKSYRepository;
import com.webapp.ims.food.Repository.EligibleCostOfPlantMachPMKSYRepository;
import com.webapp.ims.food.Repository.EvaluatePMKSYRepository;
import com.webapp.ims.food.Repository.PlantAndMachinerytobeCSISRepository;
import com.webapp.ims.food.Repository.ProductDetailsRepository;
import com.webapp.ims.food.Repository.ProposedProductByProductPMKSYRepository;
import com.webapp.ims.food.Service.FoodEvaluateViewISService;
import com.webapp.ims.food.Service.FoodEvaluateViewPMKSYService;

@Controller
public class FoodEvaluatePMKSYCtl {

	@Autowired
	FoodEvaluateViewISService foodEvaluateViewISService;
	
	@Autowired
	EligibleCostOfPlantMachPMKSYRepository eligibleCostOfPlantMachPMKSYRepository;
	
	@Autowired
	EligibleCostOfCivilWorkPMKSYRepository eligibleCostOfCivilWorkPMKSYRepository;
	
	@Autowired
	ProposedProductByProductPMKSYRepository proposedProductByProductPMKSYRepository;
	
	@Autowired
	ProductDetailsRepository productdetailsrepository;

	@Autowired
	DetailsofEligibleTechnicalCivilWorkRepository detailsofEligibleTechnicalCivilWorkRepository;
	
	
	
	@Autowired
	PlantAndMachinerytobeCSISRepository plantAndMachinerytobeCSISRepository;
	
	@Autowired
	FoodEvaluateViewPMKSYService foodEvaluateViewPMKSYService;

	@Autowired
	EvaluatePMKSYRepository evaluatePMKSYRepository;

	@Autowired
	FoodDashboard fooddashboard;

	@GetMapping(value = "/evaluationViewPMKSY")
	public ModelAndView foodEvalutionViewPMKSY(@RequestParam(value = "unit_Id", required = false) String unitId,
			@ModelAttribute("foodViewEvaluatePMKSY") FoodEvalutionViewPMKYS foodEvalutionViewPMKYS, Model model,
			HttpSession session) {
		System.out.println("Food Evalution Start PMKSY");
		String unitid = (String) session.getAttribute("unitId");
		System.out.println("Unit_Id: " + unitid);
		fooddashboard.commonFoodViewApplicationDetails(unitid, session, model);
		
		 FoodEvalutionViewPMKYS foodEvalutionViewPMKSY1 = evaluatePMKSYRepository.getDetailsByunitId(unitid);
		if (foodEvalutionViewPMKSY1 != null) {
			 model.addAttribute("foodViewEvaluatePMKSY", foodEvalutionViewPMKSY1);
		} else {
			model.addAttribute("foodViewEvaluatePMKSY", foodEvalutionViewPMKYS);
		}
		
 List<EligiblePlantMachEvalutionPMKSY> eligiblePlantMachPMKSYList=eligibleCostOfPlantMachPMKSYRepository.findAllByunitId(unitid);
		
		if (eligiblePlantMachPMKSYList != null) {
			model.addAttribute("eligiblePlantMachPMKSYList", eligiblePlantMachPMKSYList);
		}else
		{
			model.addAttribute("eligiblePlantMachPMKSYList", foodEvalutionViewPMKSY1);
		}
		
		List<EligibleCostOfCivilWorkPMKSY> eligibleCostOfCivilWorkPMKSYList=eligibleCostOfCivilWorkPMKSYRepository.findAllByunitId(unitid);
		if (eligibleCostOfCivilWorkPMKSYList != null) {
			model.addAttribute("eligibleCostOfCivilWorkPMKSYList", eligibleCostOfCivilWorkPMKSYList);
		}else
		{
			model.addAttribute("eligibleCostOfCivilWorkPMKSYList", foodEvalutionViewPMKSY1);
		}
		
		return new ModelAndView("/foodPolicy/foodEvalutionViewPMKSY");
	}
	
	
	
	@PostMapping(value = "/savefoodEvaluatePMKSY")
	public ModelAndView SavefoodEvaluatePMKSY(@ModelAttribute("foodViewEvaluatePMKSY") FoodEvalutionViewPMKYS foodEvalutionViewPMKYS, Model model,
			HttpSession session) {
           System.out.println("Save Start");
		String unitId = (String) session.getAttribute("unitId");

		String cantrolid = unitId.substring(0, unitId.length() - 2);

		foodEvalutionViewPMKYS.setApp_id(unitId);
		foodEvalutionViewPMKYS.setUnitId(unitId);
		foodEvalutionViewPMKYS.setControl_id(cantrolid);
		foodEvalutionViewPMKYS.setCreatedBy("Admin");
		foodEvalutionViewPMKYS.setModifiedBy("Admin");
		foodEvalutionViewPMKYS.setStatus("Active");

	foodEvaluateViewPMKSYService .saveViewEvaluatePMKSYDetails(foodEvalutionViewPMKYS);

	List<DetailsofProposedPlantAndMachinerytobeInstalled> plantAndMachinerytobeCSIS = plantAndMachinerytobeCSISRepository
			.findAllByunit_id(unitId);
			
    List<EligiblePlantMachEvalutionPMKSY> plantMachList= new ArrayList<EligiblePlantMachEvalutionPMKSY>();
    int i = 0;
	int j;
	int count = 0;
	int k = 0;
	int l;
	int count1 = 0;
	
				 for(DetailsofProposedPlantAndMachinerytobeInstalled h:plantAndMachinerytobeCSIS) {
					
					 EligiblePlantMachEvalutionPMKSY eligiblePlantMachEvalutionPMKSY= new EligiblePlantMachEvalutionPMKSY();
							
					 eligiblePlantMachEvalutionPMKSY.setUnitId(unitId);
					 eligiblePlantMachEvalutionPMKSY.setControl_id(cantrolid);
					 eligiblePlantMachEvalutionPMKSY.setId(h.getId());
					 eligiblePlantMachEvalutionPMKSY.setEligibleCostNameOfPlantMach(h.getMachinename());
					 eligiblePlantMachEvalutionPMKSY.setEligibleCostNameOfSupplierCompany(h.getSuppliername());
					 eligiblePlantMachEvalutionPMKSY.setEligibleCostBasicPrice(h.getBasicprice());
						 
						 String[] a = foodEvalutionViewPMKYS.getEligibleCostTotal();
						 String[] b = foodEvalutionViewPMKYS.getEligibleCostInLacs();// variable to store returned array
						 List<DetailsofProposedPlantAndMachinerytobeInstalled> pandmList = plantAndMachinerytobeCSIS;
							DetailsofProposedPlantAndMachinerytobeInstalled index = pandmList.get(count);
							for (; i < pandmList.size();) {

								for (j = i; j < a.length; j++) {// for loop to print the

									System.out.println(a[j] + " " + a.length);
									System.out.println("PANSM List " + pandmList.get(i) + "   " + pandmList.size());

									if (i == count) {
										eligiblePlantMachEvalutionPMKSY.setEligibleCostTotal(a[j]);
										eligiblePlantMachEvalutionPMKSY.setEligibleCostInLacs(b[j]);
										count++;
										break;
									}
									break;

								}
								i++;
								break;

							}
					
						 plantMachList.add(eligiblePlantMachEvalutionPMKSY);
				 }
					
				 eligibleCostOfPlantMachPMKSYRepository.saveAll(plantMachList);
					 
					 List<DetailsofEligibleTechnicalCivilWork> detailsofEligibleTechnicalCivilWork = detailsofEligibleTechnicalCivilWorkRepository
								.findAllByunit_id(unitId);
					  List<EligibleCostOfCivilWorkPMKSY> techCivilWorkList= new ArrayList<EligibleCostOfCivilWorkPMKSY>();
						
						 for(DetailsofEligibleTechnicalCivilWork civilWork:detailsofEligibleTechnicalCivilWork) {
							
							 {

								 EligibleCostOfCivilWorkPMKSY foodTechnicalCivilWork= new EligibleCostOfCivilWorkPMKSY();
								 foodTechnicalCivilWork.setUnitId(unitId);
								 foodTechnicalCivilWork.setControl_id(cantrolid);
								 foodTechnicalCivilWork.setId(civilWork.getId());
								 
								 foodTechnicalCivilWork.setEligibleTechCivilWorkParticular(civilWork.getParticulars());
								 foodTechnicalCivilWork.setEligibleTechCivilWorkArea(civilWork.getAreasqft());
								 foodTechnicalCivilWork.setEligibleTechCivilWorkRate(civilWork.getRate());
								 foodTechnicalCivilWork.setEligibleTechCivilWorkAmount(civilWork.getAmount());
								 foodTechnicalCivilWork.setEligibleTechCivilWorkEligibleCost(civilWork.getEligiblecost());
						
								 
								 String[] p = foodEvalutionViewPMKYS.getEligibleTechCivilWorkEligibleCost(); // variable to store returned array
								 List<DetailsofEligibleTechnicalCivilWork> pandmList = detailsofEligibleTechnicalCivilWork;
								 DetailsofEligibleTechnicalCivilWork index = pandmList.get(count1);
									for (; k < pandmList.size();) {

										for (l = k; l < p.length; l++) {// for loop to print the

											System.out.println(p[l] + " " + p.length);
											System.out.println("PANSM List " + pandmList.get(k) + "   " + pandmList.size());

											if (k == count1) {
												foodTechnicalCivilWork.setEligibleTechCivilWorkEligibleCost(p[l]);
												
												count1++;
												break;
											}
											break;

										}
										k++;
										break;

									}
							
									techCivilWorkList.add(foodTechnicalCivilWork);
							
								
								 }
							 eligibleCostOfCivilWorkPMKSYRepository.saveAll(techCivilWorkList);
					          }
						 
						 
						 List<ProductDetails> productdetails = productdetailsrepository.findAllByunit_id(unitId);
						 
						 List<ProposedProductByProductPMKSY> foodProposeProductsByProductsList= new ArrayList<ProposedProductByProductPMKSY>();
							
						 for(ProductDetails products:productdetails) {
							
							 {
								 ProposedProductByProductPMKSY foodProposeProductsByProducts= new ProposedProductByProductPMKSY();
									
								 foodProposeProductsByProducts.setUnitId(unitId);
								 foodProposeProductsByProducts.setId(products.getId());
								 foodProposeProductsByProducts.setControl_id(cantrolid);
							     foodProposeProductsByProducts.setProducts(products.getProducts());
								 foodProposeProductsByProducts.setByProducts(products.getByproducts());
								 foodProposeProductsByProductsList.add(foodProposeProductsByProducts);
							
								 }
							 proposedProductByProductPMKSYRepository.saveAll(foodProposeProductsByProductsList);
					          }
		

		return new ModelAndView("redirect:/evaluationViewPMKSY");
	}

}
