package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.NewProjDisbursement;

@Repository
public interface NewProjDisburseRepository extends JpaRepository<NewProjDisbursement, String> {

	NewProjDisbursement getDetailsBynewprojApcId(String id);

}
