package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DisEmploymentDetails;

/**
 * Author:: Pankaj
 */

@Repository
public interface DisbursmentEmploymentDetailsRepository extends JpaRepository<DisEmploymentDetails, String> {

	DisEmploymentDetails getDetailsBydisAppId(String appId);

	DisEmploymentDetails getBydisId(String id);

}