package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.ProductDetails;
import com.webapp.ims.food.Model.ProjectInvestmentDetails;
@Repository
public interface ProjectInvestmentDetailsRepository extends JpaRepository<ProjectInvestmentDetails, String> {

	@Query(value = "from ProjectInvestmentDetails pid")
	public List<ProjectInvestmentDetails> getAll();
	
	@Query("from ProjectInvestmentDetails pid where pid.unit_id =:unitId")
	public List<ProjectInvestmentDetails> findAllByunit_id(String unitId);
}
