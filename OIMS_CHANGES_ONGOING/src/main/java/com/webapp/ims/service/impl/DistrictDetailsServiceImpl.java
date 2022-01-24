package com.webapp.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.DistrictDetails;
import com.webapp.ims.repository.DistrictDetailsRepository;
import com.webapp.ims.service.DistrictDetailsService;

@Service
@Transactional
public class DistrictDetailsServiceImpl implements DistrictDetailsService {

	@Autowired
	DistrictDetailsRepository districtDetailsRepository;

	@Override
	@Query(" from DistrictDetails districtCode = :districtCode ")
	public DistrictDetails getDistrictByDistrictCode(long districtCode) {
		return districtDetailsRepository.getDistrictByDistrictCode(districtCode);
	}

	@Override
	public List<DistrictDetails> findAllByDistrictName() {
		return districtDetailsRepository.findAll();
	}

	@Override
	@Query(" from DistrictDetails districtCode = : stateCode ")
	public DistrictDetails findlDistrictNameByStateCode(Long stateCode) {
		return districtDetailsRepository.getDistrictByDistrictCode(stateCode);

	}

}
