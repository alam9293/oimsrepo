package com.webapp.ims.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webapp.ims.webservices.SoapConsumeEx;

@Controller
@Component
public class NiveshCtl {

	SoapConsumeEx soapdetails = new SoapConsumeEx();

	@RequestMapping(value = { "/getnivesh" }, method = RequestMethod.GET)
	public String niveshformget(Model model) {
		return "niveshsoap";
	}

}
