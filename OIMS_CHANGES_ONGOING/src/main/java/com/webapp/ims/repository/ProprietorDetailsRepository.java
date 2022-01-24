package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.ProprietorDetails;

@Repository
public interface ProprietorDetailsRepository extends JpaRepository<ProprietorDetails, String> {
	public List<ProprietorDetails> findAllByBusinessEntityDetails(String businessEntityDetails);

	public ProprietorDetails getBypropId(String propId);

	public void deleteBypropId(String propId);
	
	public void deleteAllBybusinessEntityDetails(String businessEntityDetails);
}
