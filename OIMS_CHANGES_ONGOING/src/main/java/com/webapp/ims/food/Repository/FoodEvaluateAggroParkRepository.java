package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodEvaluationViewAggroPark;
import com.webapp.ims.food.Model.FoodEvalutionViewMDBP;

@Repository
public interface FoodEvaluateAggroParkRepository extends JpaRepository<FoodEvaluationViewAggroPark, String> {

	public FoodEvaluationViewAggroPark getDetailsByunitId(String unitId);
}
