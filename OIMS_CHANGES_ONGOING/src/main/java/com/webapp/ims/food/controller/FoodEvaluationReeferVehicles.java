package com.webapp.ims.food.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.food.Model.FoodEvalutionViewCSIS;
import com.webapp.ims.food.Model.FoodEvalutionViewISRV;
import com.webapp.ims.food.Repository.FoodEvaluateReeferVehiclesRepository;

@Controller
public class FoodEvaluationReeferVehicles {
	
@Autowired
FoodEvaluateReeferVehiclesRepository foodEvaluateReeferVehiclesRepository;
	
	 @PostMapping(value = "/savefoodEvaluateApplicationISRV") 
	  public ModelAndView savefoodEvalutionISRVView(@ModelAttribute("foodViewEvaluateISRV") FoodEvalutionViewISRV foodEvalutionViewISRV, Model model, HttpSession
	  session) 
	  { 
	  
	  String unitId=(String) session.getAttribute("unitId");
	  
	  String cantrolid=unitId.substring(0, unitId.length()-2);
	  
	  foodEvalutionViewISRV.setAppId("123456");
	  foodEvalutionViewISRV.setUnitId(unitId);
	  foodEvalutionViewISRV.setControlId(cantrolid);
	  foodEvalutionViewISRV.setCreatedBy("Admin");
	  foodEvalutionViewISRV.setModifiedBy("Admin");
	  foodEvalutionViewISRV.setStatus("Active");
	  
	 
	  foodEvaluateReeferVehiclesRepository.save(foodEvalutionViewISRV);
	return new ModelAndView("redirect:/evaluationViewISRV");

}}
