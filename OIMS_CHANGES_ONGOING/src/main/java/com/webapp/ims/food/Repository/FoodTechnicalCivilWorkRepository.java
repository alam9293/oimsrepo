package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.EligibleTechnicalCivilWork;
import com.webapp.ims.food.Model.FoodElegibleCostOfPlantMach;

@Repository
public interface FoodTechnicalCivilWorkRepository extends JpaRepository<EligibleTechnicalCivilWork, String> {

	EligibleTechnicalCivilWork	getDetailsByunitId(String unitId);
	

	@Query("from EligibleTechnicalCivilWork ecivil where ecivil.unitId =:unitId")
	public List<EligibleTechnicalCivilWork> findAllByunitId(String unitId);
}
