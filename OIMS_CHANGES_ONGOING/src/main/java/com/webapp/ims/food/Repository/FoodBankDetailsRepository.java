package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodBankDetails;
import com.webapp.ims.food.Model.Identifier;

@Repository
public interface FoodBankDetailsRepository extends JpaRepository<FoodBankDetails, Identifier> {

	@Query("from FoodBankDetails pid where pid.unit_id =:unitId")
	public List<FoodBankDetails> findAllByunit_id(String unitId);
}
