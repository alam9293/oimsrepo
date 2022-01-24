package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.TotalAnnualExportTurnoverofunit;

@Repository
public interface TotalAnnualExportTurnoverofunitRepository
		extends CrudRepository<TotalAnnualExportTurnoverofunit, String> {

	@Query("from TotalAnnualExportTurnoverofunit pd where pd.unit_id =:unitid")
	public List<TotalAnnualExportTurnoverofunit> findAllByunit_id(String unitid);

}
