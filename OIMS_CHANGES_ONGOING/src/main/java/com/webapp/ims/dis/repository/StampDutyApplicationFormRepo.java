package com.webapp.ims.dis.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.StampDutyApplicationForm;

@Repository
public interface StampDutyApplicationFormRepo extends JpaRepository<StampDutyApplicationForm, String> {

	@Query(value = "from StampDutyApplicationForm where stampApcId = :stampApcId ")
	public List<StampDutyApplicationForm> findAllByStampApcId(String stampApcId); 
		
	
}
