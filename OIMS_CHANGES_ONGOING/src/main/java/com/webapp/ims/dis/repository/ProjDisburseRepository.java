package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.dis.model.ExistProjDisbursement;

@Repository
public interface ProjDisburseRepository extends JpaRepository<ExistProjDisbursement, String> {

	ExistProjDisbursement getDetailsByprojDisId(String id);
	
	ExistProjDisbursement getDetailsByprojApcId(String appId);

	/**
	 * Author:: Sachin
	* Created on::
	 */
		
}
