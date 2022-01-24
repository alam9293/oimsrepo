/**
 * Author:: Gyan
* Created on:: 
* Created date::03/02/2021
 */

package com.webapp.ims.food.ServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.ims.food.Model.FoodEvalutionViewIS;
import com.webapp.ims.food.Repository.EvaluateISRepository;
import com.webapp.ims.food.Service.FoodEvaluateViewISService;

@Service
public class FoodEvaluateViewISServiceImpl implements FoodEvaluateViewISService {

	@Autowired
	EvaluateISRepository evaluateISRepository;

	@Override
	public void saveViewEvaluateISDetails(FoodEvalutionViewIS foodEvalutionViewIS) {
		evaluateISRepository.save(foodEvalutionViewIS);
	}

}
