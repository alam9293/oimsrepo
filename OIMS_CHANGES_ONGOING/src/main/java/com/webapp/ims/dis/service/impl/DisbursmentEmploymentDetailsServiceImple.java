package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.DisEmploymentDetails;
import com.webapp.ims.dis.repository.DisbursmentEmploymentDetailsRepository;
import com.webapp.ims.dis.service.DisbursmentEmploymentDetailsService;

@Service
@Transactional
public class DisbursmentEmploymentDetailsServiceImple implements DisbursmentEmploymentDetailsService {

	@Autowired
	DisbursmentEmploymentDetailsRepository disbursmentCisIncentiveRepository;

	@Override
	public DisEmploymentDetails saveDisEmploymentDetails(DisEmploymentDetails cis) {
		return disbursmentCisIncentiveRepository.save(cis);
	}

	@Override
	public DisEmploymentDetails updateDisEmploymentDetails(DisEmploymentDetails cis) {
		return disbursmentCisIncentiveRepository.save(cis);
	}

	@Override
	public DisEmploymentDetails getBydisId(String id) {
		return disbursmentCisIncentiveRepository.getBydisId(id);

	}

	@Override
	public DisEmploymentDetails getDetailsBydisAppId(String appId) {
		return disbursmentCisIncentiveRepository.getDetailsBydisAppId(appId);

	}

}
