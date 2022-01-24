package com.webapp.ims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.webapp.ims.model.DeptProprietorDetails;
import com.webapp.ims.model.ProprietorDetails;
import com.webapp.ims.repository.DeptProprietorDetailsRepository;
import com.webapp.ims.repository.ProprietorDetailsRepository;
import com.webapp.ims.service.ProprietorDetailsService;

@Service
@Transactional
public class ProprietorServiceServiceImpl implements ProprietorDetailsService {

	@Autowired
	private ProprietorDetailsRepository proprietorDetailsRepository;
	@Autowired
	private DeptProprietorDetailsRepository deptProprietorDetailsRepository;

	@Override
	public List<ProprietorDetails> saveProprietorDetails(List<ProprietorDetails> proprietorDetailsList) {
		return saveProprietorDetailsExtra(proprietorDetailsList);
	
	}

	// vinay starts
	
	
	public List<ProprietorDetails> saveProprietorDetailsExtra(List<ProprietorDetails> proprietorDetailsList) {
			
		List<DeptProprietorDetails> extraProps=new ArrayList<>();
		
		
		if(!CollectionUtils.isEmpty(proprietorDetailsList)) {
			for(ProprietorDetails pd:proprietorDetailsList) {
				DeptProprietorDetails deptProprietorDetails = new DeptProprietorDetails();
				BeanUtils.copyProperties(pd, deptProprietorDetails);
				extraProps.add(deptProprietorDetails);
			}
		}
		
		deptProprietorDetailsRepository.saveAll(extraProps);
		return proprietorDetailsRepository.saveAll(proprietorDetailsList);
	}
	//vinay ends 
	@Override
	@Query(value = " from ProprietorDetails businessEntityDetails = :businessEntityDetails ")
	public List<ProprietorDetails> findAllByBusinessId(String businessEntityDetails) {
		return proprietorDetailsRepository.findAllByBusinessEntityDetails(businessEntityDetails);
	}

	@Override
	public void deleteBypropId(String propid) {
		proprietorDetailsRepository.deleteById(propid);

	}

	@Override
	@Query(" from ProprietorDetails propId = : propId ")
	public ProprietorDetails getBypropId(String propId) {
		return proprietorDetailsRepository.getBypropId(propId);
	}
	

}
