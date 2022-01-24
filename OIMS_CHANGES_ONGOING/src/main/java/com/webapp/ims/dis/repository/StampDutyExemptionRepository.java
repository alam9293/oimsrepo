package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.StampDutyExemption;

@Repository
public interface StampDutyExemptionRepository extends JpaRepository<StampDutyExemption, String>{
	
	@Query(value = "from StampDutyExemption where apcId = :apcId ")
	public List<StampDutyExemption> findAllByStampId(String apcId); 
	
	@Query(value = "from StampDutyExemption where stampId = :stampId ")
	StampDutyExemption findByStampId(String stampId); 

}
