package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.ImplementationSchedule;
import com.webapp.ims.food.Model.ImplementationScheduleTQM;

public interface ImplementationScheduleTQMRepository extends JpaRepository<ImplementationScheduleTQM, Identifier> {

	
	
	@Query("from ImplementationScheduleTQM istqm where istqm.Unit_Id =:unitId")
	public List<ImplementationScheduleTQM> findAllByunit_id(String unitId);
}