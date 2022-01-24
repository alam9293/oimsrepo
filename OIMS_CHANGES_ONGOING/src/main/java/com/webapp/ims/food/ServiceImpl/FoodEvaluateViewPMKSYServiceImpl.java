/**
 * Author:: Gyan
* Created on:: 
* Created date::03/02/2021
 */

package com.webapp.ims.food.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.ims.food.Model.FoodEvalutionViewPMKYS;
import com.webapp.ims.food.Repository.EvaluatePMKSYRepository;
import com.webapp.ims.food.Service.FoodEvaluateViewISService;
import com.webapp.ims.food.Service.FoodEvaluateViewPMKSYService;

@Service
public class FoodEvaluateViewPMKSYServiceImpl implements FoodEvaluateViewPMKSYService {

	@Autowired
	EvaluatePMKSYRepository evaluatePMKSYRepository;


	@Override
	public void saveViewEvaluatePMKSYDetails(FoodEvalutionViewPMKYS foodEvalutionViewPMKYS) {
		evaluatePMKSYRepository.save(foodEvalutionViewPMKYS);
		
	}

}
