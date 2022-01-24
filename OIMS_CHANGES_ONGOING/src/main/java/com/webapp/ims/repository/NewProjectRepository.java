package com.webapp.ims.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.NewProjectDetails;

@Repository

	public interface NewProjectRepository extends JpaRepository<NewProjectDetails, String> {

		@Query("from NewProjectDetails where npdProjDtlId=:npdProjDtlId ")
		

		public List<NewProjectDetails> findNewProjListByNpdId(@Param(value = "npdProjDtlId")String projId);
		
		@Query("Select npd.newPropProducts from NewProjectDetails  npd where npd.npdProjDtlId=:npdProjDtlId")
		public List<String>findProductsByProjId(@Param(value = "npdProjDtlId")String projId);
		
		/*
		 * @Query("from NewProjectDetails where epdProjDtlId=:epdProjDtlId ") public List<ExistingProjectDetails>
		 * findExistProjListByEpdId(@Param(value = "epdProjDtlId")String projId);
		 */
	}
	

