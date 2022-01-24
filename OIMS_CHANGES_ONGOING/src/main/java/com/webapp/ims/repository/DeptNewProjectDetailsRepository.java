package com.webapp.ims.repository;

import org.springframework.data.jpa.repository.JpaRepository;


import com.webapp.ims.model.DeptNewProjectDetails;


public interface DeptNewProjectDetailsRepository extends JpaRepository<DeptNewProjectDetails, String>{

	/* void saveAll(List<DeptNewProjectDetails> extraProps); */

}
