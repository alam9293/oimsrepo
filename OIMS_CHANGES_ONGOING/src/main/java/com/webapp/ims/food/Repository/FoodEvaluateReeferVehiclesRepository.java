package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodEvalutionViewCSIS;
import com.webapp.ims.food.Model.FoodEvalutionViewISRV;

@Repository
public interface FoodEvaluateReeferVehiclesRepository extends JpaRepository<FoodEvalutionViewISRV, String>{

	public FoodEvalutionViewISRV getDetailsByunitId(String unitId);
}
