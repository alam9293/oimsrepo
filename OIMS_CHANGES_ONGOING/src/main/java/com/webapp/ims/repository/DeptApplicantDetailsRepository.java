package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.DeptApplicantDetails;

@Repository
public interface DeptApplicantDetailsRepository extends JpaRepository<DeptApplicantDetails, Integer> {	
	
}