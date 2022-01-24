package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.DeptProprietorDetails;

@Repository
public interface DeptProprietorDetailsRepository  extends JpaRepository<DeptProprietorDetails, String> {
	public List<DeptProprietorDetails> findAllByBusinessEntityDetails(String businessEntityDetails);
	
	public List<DeptProprietorDetails> findByBusinessEntityDetails(String beId);
	
	//public void saveAll(DeptProprietorDetails deptProprietorDetails);

}
