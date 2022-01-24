package com.webapp.ims.dis.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.webapp.ims.dis.model.Discis;

@Repository
public interface DisbursmentCisIncentiveRepository extends JpaRepository<Discis, String> {
	
	Discis getDetailsBydisAppId(String appId);

	/**
	 * Author:: Sachin
	* Created on::
	 */
	public Discis getDiscisBydiscisId(String id);

}