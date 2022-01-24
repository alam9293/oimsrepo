package com.webapp.ims.dis.service;

import org.springframework.stereotype.Service;

import com.webapp.ims.dis.model.Disepfriem;

@Service
public interface DisEPFservice {

	public void saveEPF(Disepfriem disepfriem);
	
	Disepfriem getDetailsByappId(String appId);
	
}
