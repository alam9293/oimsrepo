package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodElegibleCostOfPlantMach;
import com.webapp.ims.food.Model.ISEligibleTechnicalCivilWork;

@Repository
public interface FoodEligiblePlantMachRepository extends JpaRepository<FoodElegibleCostOfPlantMach,String> {

	FoodElegibleCostOfPlantMach getDetailsByunitId( String unitId);
	
	@Query("from FoodElegibleCostOfPlantMach epnm where epnm.unitId =:unitId")
	public List<FoodElegibleCostOfPlantMach> findAllByunitId(String unitId);
}
