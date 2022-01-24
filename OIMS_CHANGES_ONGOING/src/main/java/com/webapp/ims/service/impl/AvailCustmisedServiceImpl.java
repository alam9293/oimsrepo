package com.webapp.ims.service.impl;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.model.AvailCustomisedDetails;
import com.webapp.ims.model.DeptAvailCustomisedDetails;
import com.webapp.ims.repository.AvailCustmisedDetailsRepository;
import com.webapp.ims.repository.DeptAvailCustomisedDetailsRepository;
import com.webapp.ims.service.AvailCustmisedDetailsService;

@Service
@Transactional
public class AvailCustmisedServiceImpl implements AvailCustmisedDetailsService {

	@Autowired
	AvailCustmisedDetailsRepository availCustmisedDetailsRepository;
	@Autowired
	DeptAvailCustomisedDetailsRepository deptAvailCustRepository;

	@Override
	public List<AvailCustomisedDetails> saveAvailCustomisedDetails(
			List<AvailCustomisedDetails> availCustomisedDetailsList) {

		for (AvailCustomisedDetails availCustomisedDetails : availCustomisedDetailsList) {
			DeptAvailCustomisedDetails obj = new DeptAvailCustomisedDetails();
			BeanUtils.copyProperties(availCustomisedDetails, obj);
			deptAvailCustRepository.save(obj);
		}

		return availCustmisedDetailsRepository.saveAll(availCustomisedDetailsList);
	}

	@Override
	@Query(value = " from AvailCustomisedDetails incentiveDetails = : incentiveDetails order by acdid")
	public LinkedList<AvailCustomisedDetails> findAllByAvaCustId(String incentiveDetails) {
		return availCustmisedDetailsRepository.findAllByIncentiveDetails(incentiveDetails);
	}

	@Override
	public AvailCustomisedDetails updateAvailCustomisedDetails(AvailCustomisedDetails availCustomisedDetails) {
		DeptAvailCustomisedDetails obj = new DeptAvailCustomisedDetails();
		BeanUtils.copyProperties(availCustomisedDetails, obj);
		deptAvailCustRepository.save(obj);
		return availCustmisedDetailsRepository.save(availCustomisedDetails);

	}

	@Override
	@Query(" from AvailCustomisedDetails acdid = : acdid ")
	public AvailCustomisedDetails getByAcdId(String acdid) {
		return availCustmisedDetailsRepository.getByacdid(acdid);
	}

	@Override
	public void deleteById(String id) {
		deptAvailCustRepository.deleteById(id);
		availCustmisedDetailsRepository.deleteById(id);

	}

	@Override
	public void deleteAllById(String incentiveDetails) {
		availCustmisedDetailsRepository.deleteAll(availCustmisedDetailsRepository.findAllByIncentiveDetails(incentiveDetails));
		
	}

}
