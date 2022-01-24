package com.webapp.ims.dis.service;

import org.springframework.stereotype.Service;

import com.webapp.ims.dis.model.DisEmploymentDetails;

@Service
public interface DisbursmentEmploymentDetailsService {

	public DisEmploymentDetails saveDisEmploymentDetails(DisEmploymentDetails cis);

	public DisEmploymentDetails updateDisEmploymentDetails(DisEmploymentDetails cis);

	public DisEmploymentDetails getBydisId(String id);

	public DisEmploymentDetails getDetailsBydisAppId(String appId);

}