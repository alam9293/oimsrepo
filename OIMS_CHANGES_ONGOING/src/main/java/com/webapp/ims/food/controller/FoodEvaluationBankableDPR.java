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
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.food.Model.DetailsOfPaymentBankableDPR;
import com.webapp.ims.food.Model.DetailsofPaymentforpreparationofBankableProjectIACVIHUBIL;
import com.webapp.ims.food.Model.DetailsofProposedPlantAndMachinerytobeInstalled;
import com.webapp.ims.food.Model.FoodBankableDPR;
import com.webapp.ims.food.Model.FoodElegibleCostOfPlantMach;
import com.webapp.ims.food.Model.FoodEvalutionViewMDBP;
import com.webapp.ims.food.Repository.DetailsOfPaymentOfBankableDPR;
import com.webapp.ims.food.Repository.DetailsOfPaymentPreparationBankableProject;
import com.webapp.ims.food.Repository.FoodEvaluateBankableDPR;

@Controller
public class FoodEvaluationBankableDPR {
	
	@Autowired
	FoodEvaluateBankableDPR foodEvaluateBankableDPR;
	
	@Autowired
	FoodDashboard foodDashboard;
	
	@Autowired
	DetailsOfPaymentOfBankableDPR detailsOfPaymentOfBankableDPRRepo;
	
	@Autowired
	DetailsOfPaymentPreparationBankableProject detailsOfPaymentPreparationBankableProject;
	
	@GetMapping(value = "/evaluationViewBankableDPR")
	public ModelAndView evaluationViewIS(@ModelAttribute("foodViewEvaluateBankableDPR") FoodBankableDPR foodBankableDPR,  Model model, HttpSession session) {
		System.out.println("Food Evalution Bankable DPR start");
	    String unitId=(String)session.getAttribute("unitId");
		System.out.println("Unit_Id: " + unitId);
		foodDashboard.commonFoodViewApplicationDetails(unitId, session, model);
		FoodBankableDPR dprList=foodEvaluateBankableDPR.getDetailsByunitId(unitId);
		if(dprList !=null)
		{
			
			model.addAttribute("foodViewEvaluateBankableDPR", dprList);
			} else {
				model.addAttribute("foodViewEvaluateBankableDPR", foodBankableDPR);
			}
		List<DetailsOfPaymentBankableDPR> detailsOfPaymentBankableDPRList=detailsOfPaymentOfBankableDPRRepo.getDetailsByunitId(unitId);
		if(detailsOfPaymentBankableDPRList !=null)
		{
			
			model.addAttribute("detailsOfPaymentBankableDPRList", detailsOfPaymentBankableDPRList);
		}
		List<DetailsofPaymentforpreparationofBankableProjectIACVIHUBIL> detailsOfPaymentdpeList=detailsOfPaymentPreparationBankableProject.findDetailsByUnitId(unitId);
		model.addAttribute("detailsOfPaymentdpeList", detailsOfPaymentdpeList);
		return new ModelAndView("/foodPolicy/foodEvalutionViewBankableDPR");
	}
	
	 @PostMapping(value = "/savefoodEvaluateBankableDPR") 
	  public ModelAndView savefoodEvalutionDPRView(@ModelAttribute("foodViewEvaluateBankableDPR") FoodBankableDPR foodBankableDPR, Model model, HttpSession
	  session) 
	  { 
	  
	  String unitId=(String) session.getAttribute("unitId");
	  
	  String cantrolid=unitId.substring(0, unitId.length()-2);
	 
	  
	  List<DetailsofPaymentforpreparationofBankableProjectIACVIHUBIL> detailsOfPaymentdpeList=detailsOfPaymentPreparationBankableProject.findDetailsByUnitId(unitId);
				
	    List<DetailsOfPaymentBankableDPR> paymentdprList= new ArrayList<DetailsOfPaymentBankableDPR>();
	    int i = 0;
		int j;
		int count = 0;
		
		
					 for(DetailsofPaymentforpreparationofBankableProjectIACVIHUBIL h : detailsOfPaymentdpeList) {
						
						 DetailsOfPaymentBankableDPR detailsOfPaymentBankableDPR=new DetailsOfPaymentBankableDPR();
						 
						  detailsOfPaymentBankableDPR.setId(h.getId());
						  detailsOfPaymentBankableDPR.setUnitId(unitId);
						  detailsOfPaymentBankableDPR.setControlId(cantrolid);
						 
						 detailsOfPaymentBankableDPR.setDetailsOfPaymentBankableBoucherNo(h.getVoucherno());
						  detailsOfPaymentBankableDPR.setDetailsOfPaymentBankableBoucherDate(h.getVoucherdate());
						  detailsOfPaymentBankableDPR.setDetailsOfPaymentBankableProjectAmount(h.getAmount());
						  detailsOfPaymentBankableDPR.setDetailsOfPaymentBankableProjectTDS(h.getTds());
						  detailsOfPaymentBankableDPR.setDetailsOfPaymentBankableTotalAmountPaid(h.getTotalpaidamount());
								
								
							 
							 String[] a = foodBankableDPR.getDetailsOfPaymentBankableProjectEligCost();
							
							 List<DetailsofPaymentforpreparationofBankableProjectIACVIHUBIL> pandmList = detailsOfPaymentdpeList;
							 DetailsofPaymentforpreparationofBankableProjectIACVIHUBIL index = pandmList.get(count);
								for (; i < pandmList.size();) {

									for (j = i; j < a.length; j++) {// for loop to print the

										System.out.println(a[j] + " " + a.length);
										System.out.println("PANSM List " + pandmList.get(i) + "   " + pandmList.size());

										if (i == count) {
											detailsOfPaymentBankableDPR.setDetailsOfPaymentBankableProjectEligCost(a[j]);
								
											count++;
											break;
										}
										break;

									}
									i++;
									break;

								}
						
								paymentdprList.add(detailsOfPaymentBankableDPR);
					 
					 }
	  
					 detailsOfPaymentOfBankableDPRRepo.saveAll(paymentdprList);
	  
	  foodBankableDPR.setAppId("123456");
	  foodBankableDPR.setUnitId(unitId);
	  foodBankableDPR.setControlId(cantrolid);
	  foodBankableDPR.setCreatedBy("Admin");
	  foodBankableDPR.setModifiedBy("Admin");
	  foodBankableDPR.setStatus("Active");
	  
	 
	  foodEvaluateBankableDPR.save(foodBankableDPR);
	return new ModelAndView("redirect:/evaluationViewBankableDPR");

}
}