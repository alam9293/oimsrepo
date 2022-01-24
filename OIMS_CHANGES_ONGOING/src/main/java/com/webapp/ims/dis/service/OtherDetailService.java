/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.dis.service;

import org.springframework.stereotype.Service;
import com.webapp.ims.dis.model.OtherIncentive;

/**
 * @author dell
 *
 */
@Service
public interface OtherDetailService {
	
	public OtherIncentive getDetailsByOthApcid(String othApcid);
	
	
}
