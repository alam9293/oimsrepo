package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapp.ims.food.Model.ISEligibleTechnicalCivilWork;

public interface FoodISEvaluationCivilWorkRepository extends JpaRepository<ISEligibleTechnicalCivilWork, String> {

	@Query("from ISEligibleTechnicalCivilWork dpnm where dpnm.unitId =:unitId order by dpnm.sno")
	public List<ISEligibleTechnicalCivilWork> findAllByunitId(String unitId);

}
