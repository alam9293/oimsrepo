package com.webapp.ims.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.BusinessEntityDetails;

@Repository
public interface BusinessEntityDetailsRepository extends JpaRepository<BusinessEntityDetails, Integer> 
{
	
	Optional<BusinessEntityDetails> findById(String id);

	public BusinessEntityDetails getBusinessEntityByapplicantDetailId(String appId);
	
	public BusinessEntityDetails getBusinDetById(String besiId);

}
