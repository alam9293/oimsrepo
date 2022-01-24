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

import com.webapp.ims.food.Model.FinancialStatus;
import com.webapp.ims.food.Model.FinancialStatusEvaluateViewMDBP;
import com.webapp.ims.food.Model.FoodElegibleCostOfPlantMach;
import com.webapp.ims.food.Model.FoodEvalutionViewIS;
import com.webapp.ims.food.Model.FoodEvalutionViewISRV;
import com.webapp.ims.food.Model.FoodEvalutionViewMDBP;
import com.webapp.ims.food.Model.SubsidyCalForFobValueTQM;
import com.webapp.ims.food.Model.SubsidyCalForRoadTransportTQM;
import com.webapp.ims.food.Repository.EvaluateMDBPRepository;
import com.webapp.ims.food.Repository.FinancialStatuaRepository;
import com.webapp.ims.food.Repository.FinancialStatusSaveMDBP;
import com.webapp.ims.food.Repository.FoodDocumentMDBPRepository;
import com.webapp.ims.food.Repository.SubCalFOBValueRepository;
import com.webapp.ims.food.Repository.SubCalRoadTransportTQMRepository;

@Controller
public class FoodEvalutionMDBPController {
	
	@Autowired
	FoodDashboard fooddashboard;
	
	@Autowired
	FinancialStatusSaveMDBP financialStatusSaveMDBP;
	
	@Autowired
	EvaluateMDBPRepository evaluateMDBPRepository;
	
	@Autowired
	FinancialStatuaRepository financeStatusRepos;
	
	@Autowired
	SubCalRoadTransportTQMRepository subCalRoadTransportTQMRepository;
	
	@Autowired
	SubCalFOBValueRepository subCalFOBValueRepository;
	
	@GetMapping(value = "/evaluationViewmdbp")
	public ModelAndView evaluationViewIS(@ModelAttribute("foodViewEvaluateMDBP") FoodEvalutionViewMDBP foodEvalutionViewMDBP,
			Model model, HttpSession session) {
		System.out.println("Food Evalution MDBP start");
	    String unitid=(String)session.getAttribute("unitId");
		System.out.println("Unit_Id: " + unitid);
		fooddashboard.commonFoodViewApplicationDetails(unitid, session, model);
		
		List<SubsidyCalForRoadTransportTQM> roadTransportList=subCalRoadTransportTQMRepository.getDetailsByunitId(unitid);
		if(roadTransportList != null) {
			model.addAttribute("roadTransportList", roadTransportList);
		}
		List<SubsidyCalForFobValueTQM> fobValueList=subCalFOBValueRepository.getDetailsByunitId(unitid);
		if(fobValueList != null)
		{
			model.addAttribute("fobValueList", fobValueList);
		}
		
		FoodEvalutionViewMDBP mdbpList=evaluateMDBPRepository.getDetailsByunitId(unitid);
		if(mdbpList !=null)
		{
			
			model.addAttribute("foodViewEvaluateMDBP", mdbpList);
			} else {
				model.addAttribute("foodViewEvaluateMDBP", foodEvalutionViewMDBP);
			}
		
		return new ModelAndView("/foodPolicy/foodEvalutionViewMDBP");
	}
	
	 @PostMapping(value = "/savefoodEvaluateApplicationMDBP") 
	  public ModelAndView savefoodEvalutionISRVView(@ModelAttribute("foodViewEvaluateMDBP") FoodEvalutionViewMDBP foodEvalutionViewMDBP, Model model, HttpSession
	  session) 
	  { 
	  
	  String unitId=(String) session.getAttribute("unitId");
	  
	  String cantrolid=unitId.substring(0, unitId.length()-2);
	  List<FinancialStatus> fianancStatus = financeStatusRepos.findAllByunit_id(unitId);
	  List<FinancialStatusEvaluateViewMDBP> finStatusList= new ArrayList<FinancialStatusEvaluateViewMDBP>();
	 
		 for(FinancialStatus f : fianancStatus)
		 {
			
			 FinancialStatusEvaluateViewMDBP finStatusEvaluateViewMDBP=new FinancialStatusEvaluateViewMDBP();
			 finStatusEvaluateViewMDBP.setUnitId(unitId);
			 finStatusEvaluateViewMDBP.setControlId(cantrolid);
			 finStatusEvaluateViewMDBP.setId(f.getId());
			 finStatusEvaluateViewMDBP.setFinancialYear(f.getFinancialyear());
			 finStatusEvaluateViewMDBP.setTurnOverInLacs(f.getTurnover());
			 
			 finStatusList.add(finStatusEvaluateViewMDBP);
		 }
		 financialStatusSaveMDBP.saveAll(finStatusList);
		
	 
	  
	  
	  SubsidyCalForRoadTransportTQM subsidyCalForRoadTransportTQM=new SubsidyCalForRoadTransportTQM();
	  
	  subsidyCalForRoadTransportTQM.setUnitId(unitId);
	  subsidyCalForRoadTransportTQM.setControl_id(cantrolid);
	  subsidyCalForRoadTransportTQM.setSubCalRoadTransportConcorInvNo(foodEvalutionViewMDBP.getSubCalRoadTransportConcorInvNo());
	  subsidyCalForRoadTransportTQM.setSubCalRoadTransportDate(foodEvalutionViewMDBP.getSubCalRoadTransportDate());
	  subsidyCalForRoadTransportTQM.setSubCalRoadTransportBasicPrice(foodEvalutionViewMDBP.getSubCalRoadTransportBasicPrice());
	  subsidyCalForRoadTransportTQM.setSubCalRoadTransportAbatedValue(foodEvalutionViewMDBP.getSubCalRoadTransportAbatedValue());
	  subsidyCalForRoadTransportTQM.setSubCalRoadTransportCGST(foodEvalutionViewMDBP.getSubCalRoadTransportCGST());
	  subsidyCalForRoadTransportTQM.setSubCalRoadTransportSGST(foodEvalutionViewMDBP.getSubCalRoadTransportSGST());
	  subsidyCalForRoadTransportTQM.setSubCalTotalAmount(foodEvalutionViewMDBP.getSubCalTotalAmount());
	  subsidyCalForRoadTransportTQM.setSubCalRoadTransportEligibleCostInLacs(foodEvalutionViewMDBP.getSubCalRoadTransportEligibleCostInLacs());
	  subCalRoadTransportTQMRepository.save(subsidyCalForRoadTransportTQM);
	  
	  SubsidyCalForFobValueTQM subsidyCalForFobValueTQM=new SubsidyCalForFobValueTQM();
	  subsidyCalForFobValueTQM.setUnitId(unitId);
	  subsidyCalForFobValueTQM.setControl_id(cantrolid);
	  subsidyCalForFobValueTQM.setSubCalForFobValueShippingBillNo(foodEvalutionViewMDBP.getSubCalForFobValueShippingBillNo());
	  subsidyCalForFobValueTQM.setSubCalForFobValueDate(foodEvalutionViewMDBP.getSubCalForFobValueDate());
	  subsidyCalForFobValueTQM.setSubCalForFobValue(foodEvalutionViewMDBP.getSubCalForFobValue());
	  subsidyCalForFobValueTQM.setSubCalForFobValueCGST(foodEvalutionViewMDBP.getSubCalForFobValueCGST());
	  subsidyCalForFobValueTQM.setSubCalForFobValueSGST(foodEvalutionViewMDBP.getSubCalForFobValueSGST());
	  subsidyCalForFobValueTQM.setSubCalForFobValueTotalAmount(foodEvalutionViewMDBP.getSubCalForFobValueTotalAmount());
	  subsidyCalForFobValueTQM.setSubCalForFobValueEligibleCostInLacs(foodEvalutionViewMDBP.getSubCalForFobValueEligibleCostInLacs());
	  subCalFOBValueRepository.save(subsidyCalForFobValueTQM);
	  
	  foodEvalutionViewMDBP.setAppId("123456");
	  foodEvalutionViewMDBP.setUnitId(unitId);
	  foodEvalutionViewMDBP.setControl_id(cantrolid);
	  foodEvalutionViewMDBP.setCreatedBy("Admin");
	  foodEvalutionViewMDBP.setModifiedBy("Admin");
	  foodEvalutionViewMDBP.setStatus("Active");
	  
	  
	  
	 
	  evaluateMDBPRepository.save(foodEvalutionViewMDBP);
	return new ModelAndView("redirect:/evaluationViewmdbp");
	  }
}
