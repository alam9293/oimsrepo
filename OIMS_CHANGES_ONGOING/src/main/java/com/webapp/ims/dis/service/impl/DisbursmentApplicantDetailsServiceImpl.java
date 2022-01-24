package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.DissbursmentApplicantDetails;
import com.webapp.ims.dis.repository.DisbursmentApplicantDetailsRepository;
import com.webapp.ims.dis.service.DisbursmentApplicantDetailsService;

@Service
@Transactional
public class DisbursmentApplicantDetailsServiceImpl implements DisbursmentApplicantDetailsService {

	@Autowired
	DisbursmentApplicantDetailsRepository disbursmentApplicantDetailsRepository;

	@Override
	public DissbursmentApplicantDetails saveDisbursmentApplicantDetails(DissbursmentApplicantDetails applicant) {
		return disbursmentApplicantDetailsRepository.save(applicant);
	}

	@Override
	public DissbursmentApplicantDetails updateDisbursmentApplicantDetails(DissbursmentApplicantDetails applicant) {
		return disbursmentApplicantDetailsRepository.save(applicant);
	}

	@Override
	public DissbursmentApplicantDetails getDetailsBydisAppId(String appId) {
		return disbursmentApplicantDetailsRepository.getDetailsBydisAppId(appId);
	}

}
