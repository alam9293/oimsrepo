package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.ExistingProjectDetails;
@Repository
public interface ExistingProjectDetailsRepository extends JpaRepository<ExistingProjectDetails, String> {

	@Query("from ExistingProjectDetails where epdProjDtlId=:epdProjDtlId ")
	public List<ExistingProjectDetails> findExistProjListByEpdId(@Param(value = "epdProjDtlId")String projId);
	
	@Query("Select epd.epdExisProducts, epd.epdPropProducts from ExistingProjectDetails  epd where epd.epdProjDtlId=:epdProjDtlId")
	public List<Object[]>findProductsByProjId(@Param(value = "epdProjDtlId")String projId);
	
	
}
