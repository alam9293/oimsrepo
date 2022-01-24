package com.webapp.ims.service;

import java.util.List;

import com.webapp.ims.model.StateDetails;

public interface StateDetailsService {

	public StateDetails getStateBystateCode(long stateCode);

	public List<StateDetails> findAllByStateName();

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */

}
