package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.CapacityofthePlantFood;

@Repository
public interface CapacityofthaPlantRepository extends CrudRepository<CapacityofthePlantFood, String> {

	@Query("from CapacityofthePlantFood pd where pd.unit_id =:unitid")
	public List<CapacityofthePlantFood> findAllByunit_id(String unitid);

}
