package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DisStampDeauty;
import com.webapp.ims.dis.model.Disepfriem;
import com.webapp.ims.dis.model.NewProjDisbursement;

@Repository
public interface DisStampDeautyRepository extends JpaRepository<DisStampDeauty, String>{

	DisStampDeauty getDetailsByStampApcId(String stampApcId);
	
	
	
}
