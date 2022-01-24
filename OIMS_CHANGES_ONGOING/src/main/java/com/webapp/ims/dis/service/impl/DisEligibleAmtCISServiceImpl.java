/**
 * Author:: Pankaj Sahu
* Created on:: 
* Created date::15/02/2021
 */

package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.ims.dis.model.DisEligibleAmtCIS;
import com.webapp.ims.dis.repository.DisEligibleAmtCISRepository;
import com.webapp.ims.dis.service.DisEligibleAmtCISService;

@Service
public class DisEligibleAmtCISServiceImpl implements DisEligibleAmtCISService {

	@Autowired
	DisEligibleAmtCISRepository disEligibleAmtCISRepository;

	// Pankaj
	@Override
	public void saveEligibleAmtCISDetails(DisEligibleAmtCIS disEligibleAmtCIS) {
		disEligibleAmtCISRepository.save(disEligibleAmtCIS);

	}

	@Override
	public DisEligibleAmtCIS getDetailsByeligibleAmtId(String eligibleAmtId) {
		return disEligibleAmtCISRepository.getDetailsByeligibleAmtId(eligibleAmtId);
	}

}
