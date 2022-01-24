package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.DetailsofproductExported;

@Repository
public interface DetailsofproductExportedRepository extends CrudRepository<DetailsofproductExported, String> {

	@Query("from DetailsofproductExported pd where pd.unit_id =:unitid")
	public List<DetailsofproductExported> findAllByunit_id(String unitid);

}
