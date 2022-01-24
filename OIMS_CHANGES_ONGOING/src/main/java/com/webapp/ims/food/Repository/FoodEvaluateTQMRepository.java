
package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodBankableDPR;
import com.webapp.ims.food.Model.FoodEvaluateViewTQM;

@Repository
public interface FoodEvaluateTQMRepository extends JpaRepository<FoodEvaluateViewTQM, String>{

	public FoodEvaluateViewTQM getDetailsByunitId(String unitId);
}
