/**
 * Author:: Gyan
* Created on:: 
* Created date::03/02/2021
 */

package com.webapp.ims.dis.service;

import com.webapp.ims.dis.model.DisViewEvaluate;

public interface DisViewEvaluateService {

	public void saveViewEvaluateDetails(DisViewEvaluate disViewEvaluate);

	// Pnakaj
	public DisViewEvaluate getDetailsByEvaluateId(String evaluateId);

	public byte[] searchEvaluateDisList();
}
