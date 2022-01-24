package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.CapacityofpnmUnit;

@Repository
public interface CapacityodpnmUnitRepository extends CrudRepository<CapacityofpnmUnit, String> {


	@Query("from CapacityofpnmUnit cpu where cpu.unit_id =:unitid")
	public List<CapacityofpnmUnit> findAllByunit_id(String unitid);

}
