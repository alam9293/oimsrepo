package com.webapp.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.StateDetails;
import com.webapp.ims.repository.StateDetailsRepository;
import com.webapp.ims.service.StateDetailsService;

@Service
@Transactional
public class StateDetailsServiceImpl implements StateDetailsService {

	@Autowired
	StateDetailsRepository stateDetailsRepository;

	@Override
	@Query(" from StateDetails stateCode = :stateCode ")
	public StateDetails getStateBystateCode(long stateCode) {
		return stateDetailsRepository.getStateBystateCode(stateCode);
	}

	@Override
	public List<StateDetails> findAllByStateName() {
		return stateDetailsRepository.findAll();
	}

}
