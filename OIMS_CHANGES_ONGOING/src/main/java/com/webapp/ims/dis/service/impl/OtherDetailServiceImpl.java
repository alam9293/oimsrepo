/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.dis.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.webapp.ims.dis.model.OtherIncentive;
import com.webapp.ims.dis.repository.OtherIncRepository;
import com.webapp.ims.dis.service.OtherDetailService;

/**
 * @author dell
 *
 */
@Service
@Transactional
public class OtherDetailServiceImpl implements OtherDetailService {

	@Autowired
	OtherIncRepository otherIncRepository;
	
	@Override
	public OtherIncentive getDetailsByOthApcid(String othApcid) {
		return otherIncRepository.getDetailsByOthApcid(othApcid);
		 
	}

}
