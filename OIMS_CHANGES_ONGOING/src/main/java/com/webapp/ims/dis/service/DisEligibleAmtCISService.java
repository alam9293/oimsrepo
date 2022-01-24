/**
 * Author:: Pankaj
* Created on:: 
* Created date::15/02/2021
 */

package com.webapp.ims.dis.service;

import com.webapp.ims.dis.model.DisEligibleAmtCIS;

public interface DisEligibleAmtCISService {

	public void saveEligibleAmtCISDetails(DisEligibleAmtCIS disEligibleAmtCIS);

	// Pankaj
	public DisEligibleAmtCIS getDetailsByeligibleAmtId(String eligibleAmtId);
}
