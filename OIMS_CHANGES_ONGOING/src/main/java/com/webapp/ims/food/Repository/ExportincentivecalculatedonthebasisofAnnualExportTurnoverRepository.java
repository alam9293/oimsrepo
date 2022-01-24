package com.webapp.ims.food.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.food.Model.ExportincentivecalculatedonthebasisofAnnualExportTurnover;

@Repository
public interface ExportincentivecalculatedonthebasisofAnnualExportTurnoverRepository
		extends CrudRepository<ExportincentivecalculatedonthebasisofAnnualExportTurnover, String> {

	@Query("from ExportincentivecalculatedonthebasisofAnnualExportTurnover pd where pd.unit_id =:unitid")
	public List<ExportincentivecalculatedonthebasisofAnnualExportTurnover> findAllByunit_id(String unitid);

}
