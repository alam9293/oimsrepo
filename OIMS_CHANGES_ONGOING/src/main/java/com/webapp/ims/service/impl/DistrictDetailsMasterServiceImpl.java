package com.webapp.ims.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.DistrictDetails1;
import com.webapp.ims.repository.DistrictDetailsMasterRepository;
import com.webapp.ims.service.DistrictDetailsMasterService;

@Service
@Transactional
public class DistrictDetailsMasterServiceImpl implements DistrictDetailsMasterService {

	@Autowired
	DistrictDetailsMasterRepository districtDetailsRepository;

	@Override
	public List<DistrictDetails1> findAllByDistrictName() {
		return districtDetailsRepository.findAll();
	}

}
