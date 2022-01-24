package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.DetailsofExport;

@Repository
public interface DetailsofExportRepository extends CrudRepository<DetailsofExport, String> {

	@Query("from DetailsofExport pd where pd.unit_id =:unitid")
	public List<DetailsofExport> findAllByunit_id(String unitid);

}
