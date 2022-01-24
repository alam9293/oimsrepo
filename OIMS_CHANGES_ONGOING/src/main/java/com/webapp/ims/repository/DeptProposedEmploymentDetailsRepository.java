package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.webapp.ims.model.DeptProposedEmploymentDetails;
import com.webapp.ims.model.DeptSkilledUnSkilledEmployemnt;

public interface DeptProposedEmploymentDetailsRepository extends JpaRepository<DeptProposedEmploymentDetails, String> {

	public DeptProposedEmploymentDetails findByappId(String applId);

//	
}
