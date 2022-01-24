package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.ProductDetails;
import com.webapp.ims.food.Model.ReeferVehiclesDetails;
@Repository
public interface VehiclesDetailsRepository extends JpaRepository<ReeferVehiclesDetails, String> {

	@Query("from ReeferVehiclesDetails rvd where rvd.unit_id =:unitid")
	public List<ReeferVehiclesDetails> findAllByunit_id(String unitid);
}
