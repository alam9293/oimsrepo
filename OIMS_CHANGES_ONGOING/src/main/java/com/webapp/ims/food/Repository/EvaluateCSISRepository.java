package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.food.Model.FoodEvalutionViewCSIS;

@Repository
public interface EvaluateCSISRepository extends JpaRepository<FoodEvalutionViewCSIS, String> {

	public FoodEvalutionViewCSIS getDetailsByunitId(String unitId);
}
