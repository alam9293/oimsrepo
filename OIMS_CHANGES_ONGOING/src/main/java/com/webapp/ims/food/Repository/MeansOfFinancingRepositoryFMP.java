package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FMPMeansofFinancing;
import com.webapp.ims.food.Model.Identifier;

@Repository
public interface MeansOfFinancingRepositoryFMP extends JpaRepository<FMPMeansofFinancing, Identifier> {

	@Query("from FMPMeansofFinancing pid where pid.unit_id =:unitId")
	public List<FMPMeansofFinancing> findAllByunit_id(String unitId);
}
