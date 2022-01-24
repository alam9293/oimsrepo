package com.webapp.ims.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.webapp.ims.model.ApplicantDetails;

@Service
public interface ApplicantDetailsService {
	public List<ApplicantDetails> getAllApplicants();

	public ApplicantDetails saveApplicantDetails(ApplicantDetails applicant);

	public Optional<ApplicantDetails> getApplicantById(Integer id);

	public void deleteApplicantById(Integer id);

	public void deleteApplicant(ApplicantDetails applicant);

	public ApplicantDetails updateApplicant(ApplicantDetails applicant);

	public ApplicantDetails getApplicantDetailsByAppId(String appId);

	/* List<Object[]> getDetailsByAppId(String appId); */

}