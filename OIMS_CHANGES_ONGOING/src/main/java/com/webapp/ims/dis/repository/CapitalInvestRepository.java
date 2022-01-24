package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.dis.model.DissbursmentApplicantDetails;

@Repository
public interface CapitalInvestRepository extends JpaRepository<CapitalInvestmentDetails, String> {
	 
	CapitalInvestmentDetails getDetailsBycapInvId(String id);
	
	CapitalInvestmentDetails getDetailsBycapInvApcId(String appid);
}
