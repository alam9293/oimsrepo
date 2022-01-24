package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.Disiis;
import com.webapp.ims.dis.repository.DisbursmentIisIncentiveRepository;
import com.webapp.ims.dis.service.DisbursmentIisService;

@Service
@Transactional
public class DisbursmentIisServiceImple implements DisbursmentIisService {

	@Autowired
	DisbursmentIisIncentiveRepository disbursmentIisIncentiveRepository;

	@Override
	public Disiis saveDisbursmentCis(Disiis iis) {
		return disbursmentIisIncentiveRepository.save(iis);
	}

	@Override
	public Disiis updateDisbursmentCis(Disiis iis) {
		return disbursmentIisIncentiveRepository.save(iis);
	}

	@Override
	public Disiis getDisiisBydisiisId(String id) {
		return disbursmentIisIncentiveRepository.getDisiisBydisiisId(id);

	}

	@Override
	public Disiis getDetailsBydisAppId(String appId) {
		disbursmentIisIncentiveRepository.getDetailsBydisAppId(appId);
		return null;
	}

}
