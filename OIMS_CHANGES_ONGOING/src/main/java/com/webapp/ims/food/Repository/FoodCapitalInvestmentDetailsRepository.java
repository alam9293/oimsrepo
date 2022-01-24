package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodCapitalInvestmentDetails;

@Repository
public interface FoodCapitalInvestmentDetailsRepository extends JpaRepository<FoodCapitalInvestmentDetails, String> {

	@Query("from FoodCapitalInvestmentDetails pid where pid.unit_id =:unitId")
	public List<FoodCapitalInvestmentDetails> findAllByunit_id(String unitId);
}
