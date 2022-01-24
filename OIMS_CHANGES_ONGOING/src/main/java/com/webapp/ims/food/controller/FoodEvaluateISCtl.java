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
import com.webapp.ims.food.Model.FoodEvaluationISPandM;
import com.webapp.ims.food.Model.FoodEvalutionViewIS;
import com.webapp.ims.food.Model.ISEligibleTechnicalCivilWork;
import com.webapp.ims.food.Repository.DetailsofEligibleTechnicalCivilWorkRepository;
import com.webapp.ims.food.Repository.EvaluateISRepository;
import com.webapp.ims.food.Repository.FoodISEvaluationCivilWorkRepository;
import com.webapp.ims.food.Repository.ISPlantAndMachineryRepository;
import com.webapp.ims.food.Repository.PlantAndMachinerytobeCSISRepository;
import com.webapp.ims.food.Service.FoodEvaluateViewISService;

@Controller
public class FoodEvaluateISCtl {

	@Autowired
	FoodEvaluateViewISService foodEvaluateViewISService;

	@Autowired
	EvaluateISRepository evaluateISRepository;

	@Autowired
	FoodDashboard fooddashboard;

	@Autowired
	PlantAndMachinerytobeCSISRepository plantAndMachinerytobeCSISRepository;

	@Autowired
	ISPlantAndMachineryRepository isPlantAndMachineryRepository;

	@Autowired
	DetailsofEligibleTechnicalCivilWorkRepository detailsofEligibleTechnicalCivilWorkRepository;

	@Autowired
	FoodISEvaluationCivilWorkRepository foodISEvaluationCivilWorkRepository;

	private List<DetailsofProposedPlantAndMachinerytobeInstalled> plantAndMachinerytobeCSISList = new ArrayList<>();

	private List<FoodEvaluationISPandM> plantAndMachinerytobeISList = new ArrayList<>();

	@GetMapping(value = "/evaluationViewIS")
	public ModelAndView evaluationViewIS(@RequestParam(value = "unit_Id", required = false) String unitId,
			@ModelAttribute("foodViewEvaluateIS") FoodEvalutionViewIS foodEvalutionViewCSIS, Model model,
			HttpSession session) {
		System.out.println("Food Evalution Start IS");
		String unitid = (String) session.getAttribute("unitId");
		System.out.println("Unit_Id: " + unitid);
		fooddashboard.commonFoodViewApplicationDetails(unitid, session, model);

		plantAndMachinerytobeCSISList = plantAndMachinerytobeCSISRepository.findAllByunit_id(unitid);

		plantAndMachinerytobeISList = isPlantAndMachineryRepository.findAllByunit_id(unitid);
		if (plantAndMachinerytobeISList != null) {
			model.addAttribute("plantAndMachinerytobeCSISList", plantAndMachinerytobeISList);
		} else {
			model.addAttribute("plantAndMachinerytobeCSISList", plantAndMachinerytobeCSISList);
		}

		List<DetailsofEligibleTechnicalCivilWork> detailsofEligibleTechnicalCivilWork = detailsofEligibleTechnicalCivilWorkRepository
				.findAllByunit_id(unitid);

		List<ISEligibleTechnicalCivilWork> detailsofEligibleTechnicalCivilWorkList = foodISEvaluationCivilWorkRepository
				.findAllByunitId(unitid);
		if (detailsofEligibleTechnicalCivilWorkList != null) {
			model.addAttribute("detailsofEligibleTechnicalCivilWorkList", detailsofEligibleTechnicalCivilWorkList);
		} else {
			model.addAttribute("detailsofEligibleTechnicalCivilWorkList", detailsofEligibleTechnicalCivilWork);
		}
		FoodEvalutionViewIS foodEvalutionViewIS1 = evaluateISRepository.getDetailsByunitid(unitid);
		if (foodEvalutionViewIS1 != null) {
			model.addAttribute("foodViewEvaluateIS", foodEvalutionViewIS1);
		} else {
			model.addAttribute("foodViewEvaluateIS", foodEvalutionViewCSIS);
		}
		return new ModelAndView("/foodPolicy/foodEvalutionViewIS");
	}

	@PostMapping(value = "/SavefoodEvaluateIS")
	public ModelAndView SavefoodEvaluateIS(
			@ModelAttribute("foodViewEvaluateIS") FoodEvalutionViewIS foodEvalutionViewIS, Model model,
			HttpSession session) {

		String unitId = (String) session.getAttribute("unitId");

		String cantrolid = unitId.substring(0, unitId.length() - 2);
		// FoodEvalutionViewIS foodEvalutionViewIS = new FoodEvalutionViewIS();
		foodEvalutionViewIS.setApp_id(unitId);
		foodEvalutionViewIS.setUnitid(unitId);
		foodEvalutionViewIS.setControl_id(cantrolid);
		foodEvalutionViewIS.setCreatedBy("Admin");
		foodEvalutionViewIS.setModifiedBy("Admin");
		foodEvalutionViewIS.setStatus("Active");

		foodEvaluateViewISService.saveViewEvaluateISDetails(foodEvalutionViewIS);

		List<FoodEvaluationISPandM> foodEvlPandMList = new ArrayList<>();

		int i = 0;
		int j;
		int count = 0;
		int k = 0;
		int l;
		int count1 = 0;
		plantAndMachinerytobeCSISList = plantAndMachinerytobeCSISRepository.findAllByunit_id(unitId);

		for (DetailsofProposedPlantAndMachinerytobeInstalled pandMlistObj : plantAndMachinerytobeCSISList) {

			FoodEvaluationISPandM foodEvaluationISPandM = new FoodEvaluationISPandM();

			foodEvaluationISPandM.setId(pandMlistObj.getId());
			foodEvaluationISPandM.setUnit_Id(pandMlistObj.getUnit_id());
			foodEvaluationISPandM.setControl_ID(pandMlistObj.getControl_id());
			foodEvaluationISPandM.setSno(pandMlistObj.getSno());
			foodEvaluationISPandM.setMachinename(pandMlistObj.getMachinename());
			foodEvaluationISPandM.setSuppliername(pandMlistObj.getSuppliername());
			foodEvaluationISPandM.setInvoicetype(pandMlistObj.getInvoicetype());
			foodEvaluationISPandM.setInvoiceno(pandMlistObj.getInvoiceno());
			foodEvaluationISPandM.setInvoicedate(pandMlistObj.getInvoicedate());
			foodEvaluationISPandM.setQuantity(pandMlistObj.getQuantity());
			foodEvaluationISPandM.setBasicprice(pandMlistObj.getBasicprice());
			foodEvaluationISPandM.setEligiblecost(pandMlistObj.getEligiblecost());
			foodEvaluationISPandM.setGstamount(pandMlistObj.getGstamount());
			foodEvaluationISPandM.setInstallationerectiontransporationcharges(
					pandMlistObj.getInstallationerectiontransporationcharges());
			foodEvaluationISPandM.setTotalamount(pandMlistObj.getTotalamount());

			String[] a = foodEvalutionViewIS.getTotalplandmachamt();
			String[] b = foodEvalutionViewIS.getEligiblecostinlacs();
			List<DetailsofProposedPlantAndMachinerytobeInstalled> pandmList = plantAndMachinerytobeCSISList;
			DetailsofProposedPlantAndMachinerytobeInstalled index = pandmList.get(count);
			for (; i < pandmList.size();) {

				for (j = i; j < a.length; j++) {// for loop to print the

					System.out.println(a[j] + " " + a.length);
					System.out.println("PANSM List " + pandmList.get(i) + "   " + pandmList.size());

					if (i == count) {
						foodEvaluationISPandM.setTotalplandmachamt(a[j]);
						foodEvaluationISPandM.setEligiblecostinlacs(b[j]);
						count++;
						break;
					}
					break;

				}
				i++;
				break;

			}

			foodEvlPandMList.add(foodEvaluationISPandM);
		}

		isPlantAndMachineryRepository.saveAll(foodEvlPandMList);

		List<DetailsofEligibleTechnicalCivilWork> detailsofEligibleTechnicalCivilWork = detailsofEligibleTechnicalCivilWorkRepository
				.findAllByunit_id(unitId);

		List<ISEligibleTechnicalCivilWork> techCivilWorkList = new ArrayList<>();

		for (DetailsofEligibleTechnicalCivilWork civilWork : detailsofEligibleTechnicalCivilWork) {

			ISEligibleTechnicalCivilWork foodTechnicalCivilWork = new ISEligibleTechnicalCivilWork();
			foodTechnicalCivilWork.setId(civilWork.getId());
			foodTechnicalCivilWork.setUnitId(civilWork.getUnit_id());
			foodTechnicalCivilWork.setControl_id(civilWork.getControl_id());
			foodTechnicalCivilWork.setSno(civilWork.getSno());
			foodTechnicalCivilWork.setParticulars(civilWork.getParticulars());
			foodTechnicalCivilWork.setAreasqft(civilWork.getAreasqft());
			foodTechnicalCivilWork.setRate(civilWork.getRate());
			foodTechnicalCivilWork.setAmount(civilWork.getAmount());
			foodTechnicalCivilWork.setEligiblecost(civilWork.getEligiblecost());

			String[] isElgCostinLac = foodEvalutionViewIS.getEligibleTechCivilWorkEligibleCost();

			List<DetailsofEligibleTechnicalCivilWork> pandmList = detailsofEligibleTechnicalCivilWork;
			DetailsofEligibleTechnicalCivilWork index = pandmList.get(count1);
			for (; k < pandmList.size();) {

				for (l = k; l < isElgCostinLac.length; l++) {// for loop to print the

					System.out.println(isElgCostinLac[l] + " " + isElgCostinLac.length);
					System.out.println("PANSM List " + pandmList.get(l) + "   " + pandmList.size());

					if (k == count1) {
						foodTechnicalCivilWork.setEligiblecostinlacs(isElgCostinLac[l]);
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
		foodISEvaluationCivilWorkRepository.saveAll(techCivilWorkList);

		return new ModelAndView("redirect:/evaluationViewIS");
	}

}
