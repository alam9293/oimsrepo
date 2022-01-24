package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.Discis;
import com.webapp.ims.dis.repository.DisbursmentCisIncentiveRepository;
import com.webapp.ims.dis.service.DisbursmentCisService;

@Service
@Transactional
public class DisbursmentCisServiceImple implements DisbursmentCisService {

	@Autowired
	DisbursmentCisIncentiveRepository disbursmentCisIncentiveRepository;

	@Override
	public Discis saveDisbursmentCis(Discis cis) {
		return disbursmentCisIncentiveRepository.save(cis);
	}

	@Override
	public Discis updateDisbursmentCis(Discis cis) {
		return disbursmentCisIncentiveRepository.save(cis);
	}

	@Override
	public Discis getDiscisBydiscisId(String id) {
		return disbursmentCisIncentiveRepository.getDiscisBydiscisId(id);

	}

	@Override
	public Discis getDetailsBydisAppId(String appId) {
		disbursmentCisIncentiveRepository.getDetailsBydisAppId(appId);
		return null;
	}

}
