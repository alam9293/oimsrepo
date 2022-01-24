package com.webapp.ims.food.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodBankableDPR;
import com.webapp.ims.food.Model.FoodEvalutionViewMDBP;

@Repository
public interface FoodEvaluateBankableDPR extends JpaRepository<FoodBankableDPR, String> {

	public FoodBankableDPR getDetailsByunitId(String unitId);
}
