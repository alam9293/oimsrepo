package com.webapp.ims.service.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.model.DeptApplicantDetails;
import com.webapp.ims.repository.ApplicantDetailsRepository;
import com.webapp.ims.repository.DeptApplicantDetailsRepository;
import com.webapp.ims.service.ApplicantDetailsService;

@Service
@Transactional
public class ApplicantDetailsServiceImpl implements ApplicantDetailsService {
	@Autowired
	ApplicantDetailsRepository applicantRepository;

	@Autowired
	DeptApplicantDetailsRepository deptApplicantRepository;

	@Override
	public List<ApplicantDetails> getAllApplicants() {
		return applicantRepository.findAll();
	}

	@Override
	public ApplicantDetails saveApplicantDetails(ApplicantDetails applicant) {
		return saveApplicantDetailsExtra(applicant);
	}

// vinay 
	private ApplicantDetails saveApplicantDetailsExtra(ApplicantDetails applicant) {
		DeptApplicantDetails deptApplicantDetails = new DeptApplicantDetails();
		BeanUtils.copyProperties(applicant, deptApplicantDetails);
		deptApplicantRepository.save(deptApplicantDetails);
		return applicantRepository.save(applicant);

	}

// vinay end 
	@Override
	public Optional<ApplicantDetails> getApplicantById(Integer id) {
		return applicantRepository.findById(id);
	}

	@Override
	public void deleteApplicantById(Integer id) {
		applicantRepository.deleteById(id);
	}

	@Override
	public ApplicantDetails updateApplicant(ApplicantDetails applicant) {
		return saveApplicantDetailsExtra(applicant);
	}

	@Override
	public void deleteApplicant(ApplicantDetails applicant) {
		applicantRepository.delete(applicant);

	}

	@Override
	@Query(" from ApplicantDetails appId = :appId ")
	public ApplicantDetails getApplicantDetailsByAppId(String appId) {

		return applicantRepository.getApplicantDetailsByAppId(appId);
	}

	 

}
