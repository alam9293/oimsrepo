package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.FinishedproductQuantity;

@Repository
public interface FinishedproductQuantityRepository extends JpaRepository<FinishedproductQuantity, String> {

	@Query("from FinishedproductQuantity pd where pd.unit_id =:unitid")
	public List<FinishedproductQuantity> findAllByunit_id(String unitid);

}
