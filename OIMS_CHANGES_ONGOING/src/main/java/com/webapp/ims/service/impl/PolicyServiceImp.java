package com.webapp.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webapp.ims.model.Policy;
import com.webapp.ims.repository.PolicyRepository;
import com.webapp.ims.service.PolicyService;

@Service
@Transactional
public class PolicyServiceImp implements PolicyService {

	@Autowired
	PolicyRepository policyRepository;
	@Override
	public List<Policy> getAllPolicy() {
		
		return policyRepository.findAll();
	}

}
