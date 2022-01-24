/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.webapp.ims.model.IndustryPolicyBean;

/**
 * @author dell
 */
@Service
public interface IndustryPolicyService {

	/**
	 * Author:: Sachin
	* Created on::
	 */
	IndustryPolicyBean createIndustryPolicy(IndustryPolicyBean industryPolicyBean);

	/**
	 * Author:: Sachin
	* Created on::
	 */
	List<IndustryPolicyBean> findCustomerAddress(String companyCode, int custId);

	/**
	 * Author:: Sachin Created on::
	 */
 

}
