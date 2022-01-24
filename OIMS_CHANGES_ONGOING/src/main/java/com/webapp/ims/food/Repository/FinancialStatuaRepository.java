package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FinancialStatus;

@Repository
public interface FinancialStatuaRepository extends CrudRepository<FinancialStatus, String> {

	// List<FinancialStatus> findAllByid(String unitId);

	// List<FinancialStatus> findAllByunit_id(String unitId);

	@Query("from FinancialStatus fs2 where fs2.unit_id =:unitid")
	public List<FinancialStatus> findAllByunit_id(String unitid);

}
