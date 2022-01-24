package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.webapp.ims.model.DeptExistingProjectDetails;
import com.webapp.ims.model.ExistingProjectDetails;

public interface DeptExistingProjectDetailsRepository extends JpaRepository<DeptExistingProjectDetails, String>{

}
