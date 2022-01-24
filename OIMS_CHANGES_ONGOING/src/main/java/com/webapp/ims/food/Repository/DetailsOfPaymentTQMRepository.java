package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.DetailsOfPaymentFoodEvaluateTQM;
import com.webapp.ims.food.Model.FoodEvaluateViewTQM;

@Repository
public interface DetailsOfPaymentTQMRepository extends JpaRepository<DetailsOfPaymentFoodEvaluateTQM, String>{

	public List<DetailsOfPaymentFoodEvaluateTQM> getDetailsByunitId(String unitId);
}
