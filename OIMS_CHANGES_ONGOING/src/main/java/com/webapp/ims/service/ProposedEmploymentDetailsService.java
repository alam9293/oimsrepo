package com.webapp.ims.service;

import java.util.Optional;

import com.webapp.ims.model.ProposedEmploymentDetails;

public interface ProposedEmploymentDetailsService {
	
	public ProposedEmploymentDetails saveProposedEmploymentDetails(ProposedEmploymentDetails proposedEmploymentDetails);
	
	public ProposedEmploymentDetails updateProposedEmploymentDetails(ProposedEmploymentDetails proposedEmploymentDetails);
	
	
	
	public Optional<ProposedEmploymentDetails> getProposedEmploymentById(String peid);

	ProposedEmploymentDetails getProposedEmploymentDetailsByappId(String appId);
	public ProposedEmploymentDetails getPropEmpById(String propId);
	
	
	public String getPropEmplIdByapplId(String appId);
	
	
}
