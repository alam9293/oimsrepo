package com.webapp.ims.dis.service;

import org.springframework.stereotype.Service;

import com.webapp.ims.dis.model.Discis;

@Service
public interface DisbursmentCisService {

	public Discis saveDisbursmentCis(Discis cis);

	public Discis updateDisbursmentCis(Discis cis);

	public Discis getDiscisBydiscisId(String id);
	
	public Discis getDetailsBydisAppId(String appId);

}