package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodEvalutionViewISRV;
import com.webapp.ims.food.Model.FoodEvalutionViewMDBP;

@Repository
public interface EvaluateMDBPRepository extends JpaRepository<FoodEvalutionViewMDBP, String>{


	public FoodEvalutionViewMDBP getDetailsByunitId(String unitId);
}
