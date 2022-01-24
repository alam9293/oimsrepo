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

import com.webapp.ims.food.Model.FinancialStatus;
import com.webapp.ims.food.Model.FinancialStatusEvaluateViewMDBP;
import com.webapp.ims.food.Model.FoodEvaluationViewAggroPark;
import com.webapp.ims.food.Model.FoodEvalutionViewMDBP;
import com.webapp.ims.food.Model.SubsidyCalForFobValueTQM;
import com.webapp.ims.food.Model.SubsidyCalForRoadTransportTQM;
import com.webapp.ims.food.Repository.FoodEvaluateAggroParkRepository;

@Controller
public class FoodEvaluateAggroParkController {
	
	@Autowired
	FoodDashboard fooddashboard;
	
	@Autowired
	FoodEvaluateAggroParkRepository foodEvaluateAggroParkRepository;

	@GetMapping(value = "/evaluationViewAggroPark")
	public ModelAndView evaluationViewAggroPark(@ModelAttribute("foodViewEvaluateAggroPark") FoodEvaluationViewAggroPark foodEvaluationViewAggroPark,
			Model model, HttpSession session) {
		System.out.println("Food Evalution Aggro Park start");
	    String unitid=(String)session.getAttribute("unitId");
		System.out.println("Unit_Id: " + unitid);
		fooddashboard.commonFoodViewApplicationDetails(unitid, session, model);
		FoodEvaluationViewAggroPark foodEvaluationViewAggroParkList=foodEvaluateAggroParkRepository.getDetailsByunitId(unitid);
		if(foodEvaluationViewAggroParkList != null)
		{
			model.addAttribute("foodViewEvaluateAggroPark", foodEvaluationViewAggroParkList);
		}
		else
		{
			model.addAttribute("foodViewEvaluateAggroPark", foodEvaluationViewAggroPark);
		}
		return new ModelAndView("/foodPolicy/foodEvalutionViewAggroPark");
	}
	
	 @PostMapping(value = "/savefoodEvaluateApplicationAggroPark") 
	  public ModelAndView savefoodEvalutionAggroPark(@ModelAttribute("foodViewEvaluateAggroPark") FoodEvaluationViewAggroPark foodEvaluationViewAggroPark, Model model, HttpSession
	  session) 
	  { 
	  
	  String unitId=(String) session.getAttribute("unitId");
	  
	  String cantrolid=unitId.substring(0, unitId.length()-2);
	 
	  foodEvaluationViewAggroPark.setAppId("123456");
	  foodEvaluationViewAggroPark.setUnitId(unitId);
	  foodEvaluationViewAggroPark.setControlId(cantrolid);
	  foodEvaluationViewAggroPark.setCreatedBy("Admin");
	  foodEvaluationViewAggroPark.setModifiedBy("Admin");
	  foodEvaluationViewAggroPark.setStatus("Active");
	  
	  
	  
	 
	  foodEvaluateAggroParkRepository.save(foodEvaluationViewAggroPark);
	return new ModelAndView("redirect:/evaluationViewAggroPark");
}
}
