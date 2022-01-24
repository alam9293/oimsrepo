/**
 * Author:: Sachin
* Created on:: 
 */
package com.webapp.ims.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;
import com.webapp.ims.model.IndustryPolicyBean;
import com.webapp.ims.service.IndustryPolicyService;

/**
 * @author dell
 *
 */
@Service
public class IndustryPolicyServiceImpl implements IndustryPolicyService {
	
	public IndustryPolicyBean createIndustryPolicy(IndustryPolicyBean industryPolicyBean) {
		IndustryPolicyBean industryPolicyBean1 = new IndustryPolicyBean();

		
		
		return industryPolicyBean1;
	}

	@Override
	public List<IndustryPolicyBean> findCustomerAddress(String companyCode, int custId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
