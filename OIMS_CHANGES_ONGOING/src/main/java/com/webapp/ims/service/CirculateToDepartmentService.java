/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.service;

import org.springframework.stereotype.Service;

import com.webapp.ims.model.CirculateToDepartment;

/**
 * @author dell
 *
 */
@Service
public interface CirculateToDepartmentService {

	public CirculateToDepartment save(CirculateToDepartment circulateToDepartment);

	// public List<CirculateToDepartment> findBydeptIdAndAppId(String deptId, String
	// AppId);

	/**
	 * (method description)
	 *
	 * @param (parameter name) (Describe the first parameter here)
	 * @param (parameter name) (Do the same for each additional parameter)
	 * @return (description of the return value)
	 */
	public void deletebydeptId(String deptId);

}
