package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapp.ims.food.Model.FoodEvaluationISPandM;

public interface ISPlantAndMachineryRepository extends JpaRepository<FoodEvaluationISPandM, String> {

	@Query("from FoodEvaluationISPandM dpnm where dpnm.Unit_Id =:unitId order by dpnm.sno")
	public List<FoodEvaluationISPandM> findAllByunit_id(String unitId);

}
