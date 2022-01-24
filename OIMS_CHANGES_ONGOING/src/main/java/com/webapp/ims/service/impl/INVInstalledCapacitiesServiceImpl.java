package com.webapp.ims.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.INVInstalledCapacities;
import com.webapp.ims.repository.INVInstalledCapacitiesRepository;
import com.webapp.ims.service.INVInstalledCapacitiesService;

@Service
@Transactional
public class INVInstalledCapacitiesServiceImpl implements INVInstalledCapacitiesService {

	@Autowired
	private INVInstalledCapacitiesRepository iNVInstalledCapacitiesRepository;

	@Override
	public List<INVInstalledCapacities> getAllByINV_ID(String INV_ID) {
		return iNVInstalledCapacitiesRepository.findAllByinvId(INV_ID);
	}

	@Override
	public void save(INVInstalledCapacities iNVInstalledCapacities) {
		iNVInstalledCapacitiesRepository.save(iNVInstalledCapacities);
	}

	@Override
	public Optional<INVInstalledCapacities> findById(String ID) {
		return iNVInstalledCapacitiesRepository.findById(ID);

	}

	@Override
	public void deleteById(String ID) {
		// TODO Auto-generated method stub
		iNVInstalledCapacitiesRepository.deleteById(ID);
	}

}
