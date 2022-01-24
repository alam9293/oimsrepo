package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.BusinessEntityDetailsFood;
import com.webapp.ims.food.Model.Identifier;

@Repository
public interface FoodPolicyDetailsRepository extends JpaRepository<BusinessEntityDetailsFood, Identifier> {

	@Query(value = "from BusinessEntityDetailsFood bedf")
	public List<BusinessEntityDetailsFood> getAll();

}
