package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.ims.dis.model.CirculateToDepartmentDis;
import com.webapp.ims.dis.repository.CirculateToDepartmentDisRepository;
import com.webapp.ims.dis.service.CirculateToDepartmentServiceDis;


@Service
public class CirculateToDepartmentDisImpl implements CirculateToDepartmentServiceDis {

	@Autowired
	CirculateToDepartmentDisRepository circulateToDepartmentDisRepository;
	@Override
	public CirculateToDepartmentDis save(CirculateToDepartmentDis circulateToDepartmentDis) {
		
		return circulateToDepartmentDisRepository.save(circulateToDepartmentDis);
	}

}
