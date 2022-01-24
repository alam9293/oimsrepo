/**
 * Author:: Pankaj
* Created on:: 
* Created date::15/02/2021
 */

package com.webapp.ims.dis.service;

import com.webapp.ims.dis.model.DisEligibleAmtIIS;

public interface DisEligibleAmtIISService {

	public void saveEligibleAmtIISDetails(DisEligibleAmtIIS disEligibleAmtIIS);

	// Pankaj
	public DisEligibleAmtIIS getDetailsByeligibleAmtIISId(String eligibleAmtIISId);
}
