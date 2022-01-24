package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodEvalutionViewPMKYS;

@Repository
public interface EvaluatePMKSYRepository extends JpaRepository<FoodEvalutionViewPMKYS, String> 
{

	FoodEvalutionViewPMKYS getDetailsByunitId(String unitId);
}
