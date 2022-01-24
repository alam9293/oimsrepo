package com.webapp.ims.repository;

import java.util.LinkedList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.AvailCustomisedDetails;

@Repository
public interface AvailCustmisedDetailsRepository extends JpaRepository<AvailCustomisedDetails, String> {
	public LinkedList<AvailCustomisedDetails> findAllByIncentiveDetails(String incentiveDetails);

	public AvailCustomisedDetails getByacdid(String acdid);

	
	public void deleteByacdid(String acdid);

//	public AvailCustomisedDetails getId(String id);

}
