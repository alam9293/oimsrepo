/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.webapp.ims.model.CirculateToDepartment;
import com.webapp.ims.repository.CirculateToDepartmentRepository;
import com.webapp.ims.service.CirculateToDepartmentService;

/**
 * @author dell
 *
 */

@Service
public class CirculateToDepartmentServiceimpl implements CirculateToDepartmentService {

	@Autowired
	CirculateToDepartmentRepository circulateToDepartmentRepository;

	@Override
	public CirculateToDepartment save(CirculateToDepartment circulateToDepartment) {
		return circulateToDepartmentRepository.save(circulateToDepartment);

	}

	/*
	 * @Override public List<CirculateToDepartment> findBydeptIdAndAppId(String
	 * deptId, String appId) { return
	 * circulateToDepartmentRepository.findBydeptIdAndAppId(deptId, appId); }
	 */
	@Override
	public void deletebydeptId(String deptId) {
		circulateToDepartmentRepository.deleteById(deptId);
	}

}
