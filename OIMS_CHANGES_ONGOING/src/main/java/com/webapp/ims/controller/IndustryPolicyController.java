package com.webapp.ims.controller;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.webapp.ims.model.IndustryPolicyBean;
import com.webapp.ims.service.IndustryPolicyService;

@CrossOrigin
@RestController
@RequestMapping("/ims/customer")
public class IndustryPolicyController {

	private final Logger log = LoggerFactory.getLogger(IndustryPolicyController.class);

	@Autowired
	IndustryPolicyService industryPolicyService;

	@PostMapping("/create" )
	public IndustryPolicyBean createCustomerAddress(@RequestBody IndustryPolicyBean industryPolicyBean) {
		System.out.println("inside controller");
		return industryPolicyService.createIndustryPolicy(industryPolicyBean);
	}

	// method for find
	@GetMapping("/find/{companyCode}/{custId}")
	public List<IndustryPolicyBean> findCustomerAddressByCustId(@PathVariable String companyCode, @PathVariable int custId) {
		System.out.println("inside controller");
		List<IndustryPolicyBean> customerAddressBean = industryPolicyService.findCustomerAddress(companyCode, custId);
		return customerAddressBean;
	}
	//
	// @GetMapping("/findcity/{companyCode}/{city}")
	// public List<CustomerAddressBean> findCustomerAddressByCity(@PathVariable String companyCode,
	// @PathVariable String city) {
	// List<CustomerAddressBean> customerAddressBean = customerAddressService.findCustomerAddByCity(companyCode, city);
	// return customerAddressBean;
	// }

}
