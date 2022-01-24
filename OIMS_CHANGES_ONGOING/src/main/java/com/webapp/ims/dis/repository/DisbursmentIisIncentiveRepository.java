package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.webapp.ims.dis.model.Disiis;


@Repository
public interface DisbursmentIisIncentiveRepository extends JpaRepository<Disiis, String> {
	
	Disiis getDetailsBydisAppId(String appId);

	/**
	 * Author:: Sachin
	* Created on::
	 */
	Disiis getDisiisBydisiisId(String id);

}