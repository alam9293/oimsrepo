package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.Identifier;
import com.webapp.ims.food.Model.ImplementationSchedule;

@Repository
public interface ImplementationScheduleRepository extends JpaRepository<ImplementationSchedule, Identifier> {

	@Query(value = "from ImplementationSchedule isc")
	public List<ImplementationSchedule> getAll();
	
	@Query("from ImplementationSchedule pid where pid.unit_id =:unitId")
	public List<ImplementationSchedule> findAllByunit_id(String unitId);
}
