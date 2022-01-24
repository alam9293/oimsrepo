/**
 * Author:: Pankaj Sahu
* Created on:: 
* Created date::15/02/2021
 */

package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.ims.dis.model.DisEligibleAmtIIS;
import com.webapp.ims.dis.repository.DisEligibleAmtIISRepository;
import com.webapp.ims.dis.service.DisEligibleAmtIISService;

@Service
public class DisEligibleAmtIISServiceImpl implements DisEligibleAmtIISService {

	@Autowired
	DisEligibleAmtIISRepository disEligibleAmtIISRepository;

	// Pankaj
	@Override
	public void saveEligibleAmtIISDetails(DisEligibleAmtIIS disEligibleAmtIIS) {
		disEligibleAmtIISRepository.save(disEligibleAmtIIS);

	}

	@Override
	public DisEligibleAmtIIS getDetailsByeligibleAmtIISId(String eligibleAmtIISId) {
		return disEligibleAmtIISRepository.getDetailsByeligibleAmtIISId(eligibleAmtIISId);
	}

}
