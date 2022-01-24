package com.webapp.ims.food.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.food.Model.DetailsofEligibleTechnicalCivilWork;
import com.webapp.ims.food.Model.DetailsofProposedPlantAndMachinerytobeInstalled;
import com.webapp.ims.food.Model.EligibleTechnicalCivilWork;
import com.webapp.ims.food.Model.FoodElegibleCostOfPlantMach;
import com.webapp.ims.food.Model.FoodEvaluationISPandM;
import com.webapp.ims.food.Model.FoodEvalutionViewCSIS;
import com.webapp.ims.food.Model.FoodProposeProductsByProducts;
import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.ProductDetails;
import com.webapp.ims.food.Repository.DetailsofEligibleTechnicalCivilWorkRepository;
import com.webapp.ims.food.Repository.EvaluateCSISRepository;
import com.webapp.ims.food.Repository.FodProposedProductsByProductsRepository;
import com.webapp.ims.food.Repository.FoodEligiblePlantMachRepository;
import com.webapp.ims.food.Repository.FoodTechnicalCivilWorkRepository;
import com.webapp.ims.food.Repository.PlantAndMachinerytobeCSISRepository;
import com.webapp.ims.food.Repository.ProductDetailsRepository;
import com.webapp.ims.model.PhaseWiseInvestmentDetails;

@Controller
public class FoodEvaluateView {
	
	@Autowired
	EvaluateCSISRepository evaluateCSISRepository;
	
	@Autowired
	FodProposedProductsByProductsRepository foodProposedProductsByProductsRepository;
	
	@Autowired
	FoodTechnicalCivilWorkRepository foodTechnicalCivilWorkRepository;
	
	@Autowired
	ProductDetailsRepository productdetailsrepository;
	
	@Autowired
	DetailsofEligibleTechnicalCivilWorkRepository detailsofEligibleTechnicalCivilWorkRepository;
	
	@Autowired
	PlantAndMachinerytobeCSISRepository plantAndMachinerytobeCSISRepository;
	
	@Autowired
	FoodEligiblePlantMachRepository foodEligiblePlantMachRepository;
	
	private List<DetailsofProposedPlantAndMachinerytobeInstalled> plantAndMachinerytobeCSISList = new ArrayList<>();
	

	
	  @PostMapping(value = "/savefoodEvaluateApplicationCSIS") 
	  public ModelAndView savefoodEvalutionView(@ModelAttribute("foodViewEvaluateCSIS") FoodEvalutionViewCSIS foodEvalutionViewCSIS, Model model, HttpSession
	  session) 
	  { 
	  
	  String unitId=(String) session.getAttribute("unitId");
	  
	  String cantrolid=unitId.substring(0, unitId.length()-2);
	  
	  foodEvalutionViewCSIS.setApp_id("123456");
	  foodEvalutionViewCSIS.setUnitId(unitId);
	  foodEvalutionViewCSIS.setControl_id(cantrolid);
	  foodEvalutionViewCSIS.setCreatedBy("Admin");
	  foodEvalutionViewCSIS.setModifiedBy("Admin");
	  foodEvalutionViewCSIS.setStatus("Active");
	  
	 
	  evaluateCSISRepository.save(foodEvalutionViewCSIS);

		List<DetailsofProposedPlantAndMachinerytobeInstalled> plantAndMachinerytobeCSIS = plantAndMachinerytobeCSISRepository
				.findAllByunit_id(unitId);
				
	    List<FoodElegibleCostOfPlantMach> plantMachList= new ArrayList<FoodElegibleCostOfPlantMach>();
	    int i = 0;
		int j;
		int count = 0;
		int k = 0;
		int l;
		int count1 = 0;
		
					 for(DetailsofProposedPlantAndMachinerytobeInstalled h:plantAndMachinerytobeCSIS) {
						
								FoodElegibleCostOfPlantMach foodElegibleCostOfPlantMach= new FoodElegibleCostOfPlantMach();
								
								foodElegibleCostOfPlantMach.setUnitId(unitId);
								foodElegibleCostOfPlantMach.setControl_id(cantrolid);
								foodElegibleCostOfPlantMach.setId(h.getId());
							 foodElegibleCostOfPlantMach.setNameOfPlantMach(h.getMachinename());
							 foodElegibleCostOfPlantMach.setNameOfSupplierCompany(h.getSuppliername());
							 foodElegibleCostOfPlantMach.setBasicPrice(h.getBasicprice());
							 
							 String[] a = foodEvalutionViewCSIS.getTotalPlantMach();
							 String[] b = foodEvalutionViewCSIS.getEligibleCostInLacsPM();// variable to store returned array
							 List<DetailsofProposedPlantAndMachinerytobeInstalled> pandmList = plantAndMachinerytobeCSIS;
								DetailsofProposedPlantAndMachinerytobeInstalled index = pandmList.get(count);
								for (; i < pandmList.size();) {

									for (j = i; j < a.length; j++) {// for loop to print the

										System.out.println(a[j] + " " + a.length);
										System.out.println("PANSM List " + pandmList.get(i) + "   " + pandmList.size());

										if (i == count) {
											foodElegibleCostOfPlantMach.setTotalPlantMach(a[j]);
											foodElegibleCostOfPlantMach.setEligibleCostInLacsPM(b[j]);
											count++;
											break;
										}
										break;

									}
									i++;
									break;

								}
						
							 plantMachList.add(foodElegibleCostOfPlantMach);
					 }
						
						 foodEligiblePlantMachRepository.saveAll(plantMachList);
				          
					 List<DetailsofEligibleTechnicalCivilWork> detailsofEligibleTechnicalCivilWork = detailsofEligibleTechnicalCivilWorkRepository
								.findAllByunit_id(unitId);
					  List<EligibleTechnicalCivilWork> techCivilWorkList= new ArrayList<EligibleTechnicalCivilWork>();
						
						 for(DetailsofEligibleTechnicalCivilWork civilWork:detailsofEligibleTechnicalCivilWork) {
							
							 {

								 EligibleTechnicalCivilWork foodTechnicalCivilWork= new EligibleTechnicalCivilWork();
								 foodTechnicalCivilWork.setUnitId(unitId);
								 foodTechnicalCivilWork.setControl_id(cantrolid);
								 foodTechnicalCivilWork.setId(civilWork.getId());
								 
								 foodTechnicalCivilWork.setParticular(civilWork.getParticulars());
								 foodTechnicalCivilWork.setAreaSqmt(civilWork.getAreasqft());
								 foodTechnicalCivilWork.setRate(civilWork.getRate());
								 foodTechnicalCivilWork.setAmount(civilWork.getAmount());
								 foodTechnicalCivilWork.setEligibleCostInLacs(civilWork.getEligiblecost());
						
								 
								 String[] p = foodEvalutionViewCSIS.getEligibleCostInLacs(); // variable to store returned array
								 List<DetailsofEligibleTechnicalCivilWork> pandmList = detailsofEligibleTechnicalCivilWork;
								 DetailsofEligibleTechnicalCivilWork index = pandmList.get(count1);
									for (; k < pandmList.size();) {

										for (l = k; l < p.length; l++) {// for loop to print the

											System.out.println(p[l] + " " + p.length);
											System.out.println("PANSM List " + pandmList.get(k) + "   " + pandmList.size());

											if (k == count1) {
												foodTechnicalCivilWork.setEligibleCostInLacs(p[l]);
												
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
							 foodTechnicalCivilWorkRepository.saveAll(techCivilWorkList);
					          }
						 
						 
						 List<ProductDetails> productdetails = productdetailsrepository.findAllByunit_id(unitId);
						 
						 List<FoodProposeProductsByProducts> foodProposeProductsByProductsList= new ArrayList<FoodProposeProductsByProducts>();
							
						 for(ProductDetails products:productdetails) {
							
							 {
								 FoodProposeProductsByProducts foodProposeProductsByProducts= new FoodProposeProductsByProducts();
									
								 foodProposeProductsByProducts.setUnitId(unitId);
								 foodProposeProductsByProducts.setControl_id(cantrolid);
							     foodProposeProductsByProducts.setProducts(products.getProducts());
								 foodProposeProductsByProducts.setByProducts(products.getByproducts());
								 foodProposeProductsByProductsList.add(foodProposeProductsByProducts);
							
								 }
							 foodProposedProductsByProductsRepository.saveAll(foodProposeProductsByProductsList);
					          }

	  return new ModelAndView("redirect:/evaluationViewCsIs"); 
	  }
	  
	 
	
}
