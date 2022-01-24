package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.webapp.ims.dis.model.DisStampDeauty;
import com.webapp.ims.dis.model.Disepfriem;
import com.webapp.ims.dis.repository.DisStampDeautyRepository;
import com.webapp.ims.dis.service.StampDutyService;


@Service

public class StampDutyServiceImpl implements StampDutyService {

	@Autowired
	DisStampDeautyRepository disStampDeautyRepository;

@Override
 public String findDataById( String id)
 {

	disStampDeautyRepository.findById(id);
	return id;
 }
 
@Override
public DisStampDeauty getDetailsByStampApcId(String stampApcId) {
	return disStampDeautyRepository.getDetailsByStampApcId(stampApcId);
	
}
	

}
