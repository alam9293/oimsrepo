package com.webapp.ims.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.ims.model.AvailCustomisedDetails;

@Service
public interface AvailCustmisedDetailsService {

	List<AvailCustomisedDetails> saveAvailCustomisedDetails(List<AvailCustomisedDetails> availCustomisedDetailsList);

	public AvailCustomisedDetails updateAvailCustomisedDetails(AvailCustomisedDetails availCustomisedDetails);

	LinkedList<AvailCustomisedDetails> findAllByAvaCustId(String incentiveDetails);

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */

	public AvailCustomisedDetails getByAcdId(String id);

	public void deleteById(String id);
	
	public void deleteAllById(String incentiveDetails);

//	public AvailCustomisedDetails getId(String id);

}
