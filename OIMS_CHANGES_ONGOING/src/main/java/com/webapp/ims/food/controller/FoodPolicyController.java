/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.food.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author nic
 *
 */
@Controller
public class FoodPolicyController {

	@GetMapping(value = "/foodpolicy")
	public ModelAndView viewfoodpolicy() {
		return new ModelAndView("/foodPolicy/selectFoodPolicy");
	}

	@GetMapping(value = "/notefoodpolicy1")
	public ModelAndView notefoodpolicy1() {
		return new ModelAndView("/foodPolicy/noteFoodPolicy1");
	}

	@GetMapping(value = "/notefoodpolicy3")
	public ModelAndView notefoodpolicy3() {
		return new ModelAndView("/foodPolicy/notefoodpolicy3");
	}

	@GetMapping(value = "/notefoodpolicyboth")
	public ModelAndView notefoodpolicyboth() {
		return new ModelAndView("/foodPolicy/notefoodpolicyboth");
	}

	// CS Form
	@GetMapping(value = "/settingUpNewUnitFoodPolicyStepFirst")
	public ModelAndView settingUpNewUnitFoodPolicyStepFirst() {
		return new ModelAndView("/foodPolicy/settingUpNewUnitFoodPolicyStepFirst");
	}

	@GetMapping(value = "/settingUpNewUnitFoodPolicyStepSecond")
	public ModelAndView settingUpNewUnitFoodPolicyStepSecond() {
		return new ModelAndView("/foodPolicy/settingUpNewUnitFoodPolicyStepSecond");
	}

	@GetMapping(value = "/settingUpNewUnitFoodPolicyStepThird")
	public ModelAndView settingUpNewUnitFoodPolicyStepThird() {
		return new ModelAndView("/foodPolicy/settingUpNewUnitFoodPolicyStepThird");
	}

	@GetMapping(value = "/settingUpNewUnitFoodPolicyStepFourth")
	public ModelAndView settingUpNewUnitFoodPolicyStepFourth() {
		return new ModelAndView("/foodPolicy/settingUpNewUnitFoodPolicyStepFourth");
	}

	// CS END

	// IS Form
	@GetMapping(value = "/isSettingUpNewUnitFoodPolicyStepFirst")
	public ModelAndView isSettingUpNewUnitFoodPolicyStepFirst() {
		return new ModelAndView("/foodPolicy/isSettingUpNewUnitFoodPolicyStepFirst");
	}

	@GetMapping(value = "/isSettingUpNewUnitFoodPolicyStepSecond")
	public ModelAndView isSettingUpNewUnitFoodPolicyStepSecond() {
		return new ModelAndView("/foodPolicy/isSettingUpNewUnitFoodPolicyStepSecond");
	}

	@GetMapping(value = "/isSettingUpNewUnitFoodPolicyStepThird")
	public ModelAndView isSettingUpNewUnitFoodPolicyStepThird() {
		return new ModelAndView("/foodPolicy/isSettingUpNewUnitFoodPolicyStepThird");
	}

	@GetMapping(value = "/isSettingUpNewUnitFoodPolicyStepFourth")
	public ModelAndView isSettingUpNewUnitFoodPolicyStepFourth() {
		return new ModelAndView("/foodPolicy/isSettingUpNewUnitFoodPolicyStepFourth");
	}

	// IS End

	@GetMapping(value = "/pmKishanSampadaYojnaStepFirst")
	public ModelAndView PMKishanSampadaYojnaStepFirst() {
		return new ModelAndView("/foodPolicy/pmKishanSampadaYojnaStepFirst");
	}

	@GetMapping(value = "/pmKishanSampadaYojnaStepSecond")
	public ModelAndView PMKishanSampadaYojnaStepSecond() {
		return new ModelAndView("/foodPolicy/pmKishanSampadaYojnaStepSecond");
	}

	@GetMapping(value = "/pmKishanSampadaYojnaStepThird")
	public ModelAndView PMKishanSampadaYojnaStepThird() {
		return new ModelAndView("/foodPolicy/pmKishanSampadaYojnaStepThird");
	}

	@GetMapping(value = "/intrestSubsidyFoodPolicyStepFirst")
	public ModelAndView intrestSubsidyFoodPolicyStepFirst() {
		return new ModelAndView("/foodPolicy/intrestSubsidyFoodPolicyStepFirst");
	}

	@GetMapping(value = "/intrestSubsidyFoodPolicyStepSecond")
	public ModelAndView PintrestSubsidyFoodPolicyStepSecond() {
		return new ModelAndView("/foodPolicy/intrestSubsidyFoodPolicyStepSecond");
	}

	@GetMapping(value = "/intrestSubsidyFoodPolicyStepThird")
	public ModelAndView intrestSubsidyFoodPolicyStepThird() {
		return new ModelAndView("/foodPolicy/intrestSubsidyFoodPolicyStepThird");
	}

	@GetMapping(value = "/csmfps_PMKSY_FoodPolicyForm")
	public ModelAndView csmfps_PMKSY_FoodPolicyForm() {
		return new ModelAndView("/foodPolicy/csmfps_PMKSY_FoodPolicyForm");
	}

	@GetMapping(value = "/reeferVehiclesFoodPolicy")
	public ModelAndView reeferVehiclesFoodPolicyForm() {
		return new ModelAndView("/foodPolicy/reeferVehiclesFoodPolicyForm");
	}

	@GetMapping(value = "/drpfFoodPolicyForm")
	public ModelAndView drpfFoodPolicyForm() {
		return new ModelAndView("/foodPolicy/drpfFoodPolicyForm");
	}

	@GetMapping(value = "/mdbpFoodPolicy")
	public ModelAndView mdbpFoodPolicyForm() {
		return new ModelAndView("/foodPolicy/mdbpFoodPolicyForm");
	}

	@GetMapping(value = "/bankableFoodPolicy")
	public ModelAndView bankableFoodPolicyForm() {
		return new ModelAndView("/foodPolicy/bankableFoodPolicyForm");
	}

}
