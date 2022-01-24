package com.webapp.ims.dis.service;

import org.springframework.stereotype.Service;

import com.webapp.ims.dis.model.DissbursmentApplicantDetails;

@Service
public interface DisbursmentApplicantDetailsService {

	public DissbursmentApplicantDetails saveDisbursmentApplicantDetails(DissbursmentApplicantDetails applicant);

	public DissbursmentApplicantDetails updateDisbursmentApplicantDetails(DissbursmentApplicantDetails applicant);

	public DissbursmentApplicantDetails getDetailsBydisAppId(String appId);
	
	

}