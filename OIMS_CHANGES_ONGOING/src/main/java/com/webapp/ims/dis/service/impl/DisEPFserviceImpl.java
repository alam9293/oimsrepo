package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.webapp.ims.dis.model.Disepfriem;
import com.webapp.ims.dis.repository.DisEPFRepository;
import com.webapp.ims.dis.service.DisEPFservice;

@Service
@Transactional
public class DisEPFserviceImpl implements DisEPFservice{
	
  @Autowired
  DisEPFRepository disEPFRepository;

	@Override
	public void saveEPF(Disepfriem disepfriem) {
		disEPFRepository.save(disepfriem);
		
	}

	@Override
	public Disepfriem getDetailsByappId(String appId) {
		
		return disEPFRepository.getDetailsByappId(appId);
	}

}
