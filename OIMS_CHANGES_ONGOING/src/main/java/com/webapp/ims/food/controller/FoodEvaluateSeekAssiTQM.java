package com.webapp.ims.food.controller;

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

import com.webapp.ims.food.Model.DetailsOfPaymentBankableDPR;
import com.webapp.ims.food.Model.DetailsOfPaymentFoodEvaluateTQM;
import com.webapp.ims.food.Model.FoodBankableDPR;
import com.webapp.ims.food.Model.FoodEvaluateViewTQM;
import com.webapp.ims.food.Model.FoodEvalutionViewIS;
import com.webapp.ims.food.Model.FoodEvalutionViewMDBP;
import com.webapp.ims.food.Repository.DetailsOfPaymentTQMRepository;
import com.webapp.ims.food.Repository.EvaluateISRepository;
import com.webapp.ims.food.Repository.FoodEvaluateTQMRepository;
import com.webapp.ims.food.Service.FoodEvaluateViewISService;

@Controller
public class FoodEvaluateSeekAssiTQM {
	
	@Autowired
	FoodDashboard foodDashboard;
	
	@Autowired
	DetailsOfPaymentTQMRepository detailsOfPaymentTQMRepository;

	@Autowired
	FoodEvaluateTQMRepository foodEvaluateTQMRepository;

	@GetMapping(value = "/evaluationViewTQM")
	public ModelAndView evaluationViewTQM(@ModelAttribute("foodViewEvaluateTQM") FoodEvaluateViewTQM foodEvaluateViewTQM,
			Model model, HttpSession session) {
		System.out.println("Food Evalution TQM start");
	    String unitid=(String)session.getAttribute("unitId");
		System.out.println("Unit_Id: " + unitid);
		foodDashboard.commonFoodViewApplicationDetails(unitid, session, model);
		FoodEvaluateViewTQM FoodEvaluateViewTQMList=foodEvaluateTQMRepository.getDetailsByunitId(unitid);
		if(FoodEvaluateViewTQMList != null)
		{
			model.addAttribute("foodViewEvaluateTQM", FoodEvaluateViewTQMList);
		}else
		{
			model.addAttribute("foodViewEvaluateTQM", foodEvaluateViewTQM);	
		}
		List<DetailsOfPaymentFoodEvaluateTQM> detailsOfPaymentFoodEvaluateTQMList=detailsOfPaymentTQMRepository.getDetailsByunitId(unitid);
		if(detailsOfPaymentFoodEvaluateTQMList != null)
		{
			model.addAttribute("detailsOfPaymentFoodEvaluateTQMList", detailsOfPaymentFoodEvaluateTQMList);
		}
		return new ModelAndView("/foodPolicy/foodEvalutionViewTQM");
}
	
	 @PostMapping(value = "/savefoodEvaluateViewTQM") 
	  public ModelAndView savefoodEvalutionDPRView(@ModelAttribute("foodViewEvaluateTQM") FoodEvaluateViewTQM foodEvaluateViewTQM, Model model, HttpSession
	  session) 
	  { 
	  
	  String unitId=(String) session.getAttribute("unitId");
	  
	  String cantrolid=unitId.substring(0, unitId.length()-2);
		/*
		 * DetailsOfPaymentFoodEvaluateTQM detailsOfPaymentFoodEvaluateTQM=new
		 * DetailsOfPaymentFoodEvaluateTQM();
		 * 
		 * detailsOfPaymentFoodEvaluateTQM.setApp_id("123");
		 * detailsOfPaymentFoodEvaluateTQM.setUnitId(unitId);
		 * detailsOfPaymentFoodEvaluateTQM.setControl_id(cantrolid); //
		 * detailsOfPaymentFoodEvaluateTQM.setDetailsOfPaymentSno(foodEvaluateViewTQM.
		 * getDetailsOfPaymentSno());
		 * detailsOfPaymentFoodEvaluateTQM.setDetailsOfPaymentDetails(
		 * foodEvaluateViewTQM.getDetailsOfPaymentDetails());
		 * detailsOfPaymentFoodEvaluateTQM.setDetailsOfPaymentTDS(foodEvaluateViewTQM.
		 * getDetailsOfPaymentTDS());
		 * detailsOfPaymentFoodEvaluateTQM.setDetailsOfPaymentamount(foodEvaluateViewTQM
		 * .getDetailsOfPaymentamount());
		 * detailsOfPaymentFoodEvaluateTQM.setDetailsOfPaymentEligCost(
		 * foodEvaluateViewTQM.getDetailsOfPaymentEligCost());
		 * 
		 * detailsOfPaymentTQMRepository.save(detailsOfPaymentFoodEvaluateTQM);
		 */
	  
	  foodEvaluateViewTQM.setApp_id("123456");
	  foodEvaluateViewTQM.setUnitId(unitId);
	  foodEvaluateViewTQM.setControl_id(cantrolid);
	  foodEvaluateViewTQM.setCreatedBy("Admin");
	  foodEvaluateViewTQM.setModifiedBy("Admin");
	  foodEvaluateViewTQM.setStatus("Active");
	  
	 
	  foodEvaluateTQMRepository.save(foodEvaluateViewTQM);
	return new ModelAndView("redirect:/evaluationViewTQM");

}
}
