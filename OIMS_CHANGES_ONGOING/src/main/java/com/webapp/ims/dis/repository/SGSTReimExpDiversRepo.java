package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.SGSTReimExpanDivers;

@Repository
public interface SGSTReimExpDiversRepo extends JpaRepository<SGSTReimExpanDivers, String>{
	
	
	@Query(value="from SGSTReimExpanDivers where sgstExpApcId = :sgstExpApcId")
	SGSTReimExpanDivers findBySgstExpApcId(String sgstExpApcId);

}
