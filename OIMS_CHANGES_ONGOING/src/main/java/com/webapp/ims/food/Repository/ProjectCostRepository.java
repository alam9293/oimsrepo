package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FoodTQMprojectDetails;
import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.ProjectCost;

@Repository
public interface ProjectCostRepository extends JpaRepository<FoodTQMprojectDetails, Identifier> {

	@Query("from ProjectCost pc where pc.unit_id =:unitid")
	public List<ProjectCost> findAllByunit_id(String unitid);

	
}
