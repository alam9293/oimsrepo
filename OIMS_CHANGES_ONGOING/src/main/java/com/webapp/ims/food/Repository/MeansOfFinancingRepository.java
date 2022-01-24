package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.MeansofFinancing;
import com.webapp.ims.food.Model.ProjectInvestmentDetails;

@Repository
public interface MeansOfFinancingRepository extends JpaRepository<MeansofFinancing, Identifier>{

	@Query(value = "from MeansofFinancing mof")
	public List<MeansofFinancing> getAll();
	
	@Query("from MeansofFinancing pid where pid.unit_id =:unitId")
	public List<MeansofFinancing> findAllByunit_id(String unitId);
}
