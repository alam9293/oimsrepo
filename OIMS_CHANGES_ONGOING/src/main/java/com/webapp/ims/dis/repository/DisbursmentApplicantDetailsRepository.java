package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.DissbursmentApplicantDetails;

@Repository
public interface DisbursmentApplicantDetailsRepository extends JpaRepository<DissbursmentApplicantDetails, Integer> {

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	DissbursmentApplicantDetails getDetailsBydisAppId(String appId);

}