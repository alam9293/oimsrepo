package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapp.ims.food.Model.ProjectCostReeferVehicles;
import com.webapp.ims.food.Model.ReeferVehiclesDetails;

public interface ProjectCostReeferVehiclesRepository extends JpaRepository<ProjectCostReeferVehicles, String> {


	@Query("from ProjectCostReeferVehicles rvd where rvd.unit_id =:unitid")
	public List<ProjectCostReeferVehicles> findAllByunit_id(String unitid);
}
