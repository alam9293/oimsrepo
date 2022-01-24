package com.webapp.ims.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.model.DeptAvailCustomisedDetails;

@Repository
public interface DeptAvailCustomisedDetailsRepository extends JpaRepository<DeptAvailCustomisedDetails, String> {
	public List<DeptAvailCustomisedDetails> findAllByIncentiveDetails(String incentiveDetails);

	public DeptAvailCustomisedDetails getByacdid(String acdid);

	public void deleteByacdid(String acdid);

}
