package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.MeansOfFinancingReeferVehicles;
import com.webapp.ims.food.Model.MeansofFinancing;

@Repository
public interface MeanceOfFinancReeferVehiclesRepository extends JpaRepository<MeansOfFinancingReeferVehicles, String>{

	@Query("from MeansOfFinancingReeferVehicles mof where mof.unit_id =:unitId")
	public List<MeansOfFinancingReeferVehicles> findAllByunit_id(String unitId);
}
