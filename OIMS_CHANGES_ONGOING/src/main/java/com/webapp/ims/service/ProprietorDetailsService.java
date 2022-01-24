package com.webapp.ims.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.webapp.ims.model.ProprietorDetails;

@Service
public interface ProprietorDetailsService {

	public List<ProprietorDetails> saveProprietorDetails(List<ProprietorDetails> proprietorDetailsList);

	public List<ProprietorDetails> findAllByBusinessId(String businessEntityDetails);

	public ProprietorDetails getBypropId(String propId);

	public void deleteBypropId(String propId);
	

}
