package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.DetailsofEligibleTechnicalCivilWork;
import com.webapp.ims.food.Model.Identifier;

@Repository
public interface DetailsofEligibleTechnicalCivilWorkRepository extends JpaRepository<DetailsofEligibleTechnicalCivilWork, Identifier>{

	@Query("from DetailsofEligibleTechnicalCivilWork detc where detc.unit_id =:unitId order by detc.sno")
	public List<DetailsofEligibleTechnicalCivilWork> findAllByunit_id(String unitId);
}
