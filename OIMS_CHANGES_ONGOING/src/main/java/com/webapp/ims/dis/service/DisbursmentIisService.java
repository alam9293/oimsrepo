package com.webapp.ims.dis.service;

import org.springframework.stereotype.Service;

import com.webapp.ims.dis.model.Disiis;

@Service
public interface DisbursmentIisService {

	public Disiis saveDisbursmentCis(Disiis iis);

	public Disiis updateDisbursmentCis(Disiis iis);

	public Disiis getDisiisBydisiisId(String id);
	
	public Disiis getDetailsBydisAppId(String appId);

	/**
	 * Author:: Sachin
	* Created on::
	 */
	

}