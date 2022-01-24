package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.CapacityofthePlantOrUnit;
import com.webapp.ims.food.Model.Identifier;

@Repository
public interface CapacityofthePlantOrUnitRepository extends JpaRepository<CapacityofthePlantOrUnit, Identifier> {

	@Query(value = "from CapacityofthePlantOrUnit cpou")
	public List<CapacityofthePlantOrUnitRepository> getAll();

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	// public BusinessEntityDetailsFood findByunitid(String unitid);

	/*
	 * @Override
	 * 
	 * @Query("from BusinessEntityDetailsFood bedf where bedf.id=:unitid") public
	 * Optional<BusinessEntityDetailsFood> findById(Identifier unitid);
	 */
}
