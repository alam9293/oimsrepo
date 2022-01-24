package com.webapp.ims.dis.controller;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;
import com.webapp.ims.model.IncentiveDetails;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.service.IncentiveDetailsService;
import com.webapp.ims.service.impl.AdditionalInterest;
import com.webapp.ims.service.impl.BusinessEntityDetailsServiceImpl;

@Controller
public class DisIncentiveTypeController {
	private final Logger logger = LoggerFactory.getLogger(DisIncentiveTypeController.class);

	@Autowired
	AdditionalInterest additionalInterest;
	@Autowired
	BusinessEntityDetailsServiceImpl businessEntityDetailsServiceImpl;
	@Autowired
	IncentiveDetailsService incentiveDetailsService;

	@GetMapping(value = "/disincentivetype")

	public ModelAndView disincentivetype(Model model, HttpSession session) {

		logger.info("Start getdisincentivetype method");
		String appId=(String) session.getAttribute("appId");
		
		businessEntityDetailsServiceImpl.commonDetails(appId, session, model);

		return new ModelAndView("/Disbursement/disincentivetype");
	}

	@RequestMapping(value = "/submitIncentivetype", method = RequestMethod.POST)
	public RedirectView submitIncentivetype(@Validated IncentiveDetails incentiveDetails, BindingResult result,
			 Model model, HttpSession session) {
		RedirectView redirectView = new RedirectView();
		
		logger.info("Start postdisincentivetype method");
		/*
		 * Object niveshResponse = session.getAttribute("niveshSoapResponse");
		 * Map<String, String> businessResponce = ((SoapDataModel)
		 * niveshResponse).getNiveshSoapResponse();
		 * 
		 * String appId = ""; for (Map.Entry<String, String> entry :
		 * businessResponce.entrySet()) {
		 * 
		 * if (entry.getKey().equalsIgnoreCase("Unit_Id")) { appId = entry.getValue() +
		 * "A1"; session.setAttribute("appId", appId); } }
		 */
		String appId=(String) session.getAttribute("appId");
		
		
		
		IncentiveDetails incentiveDetails2 = incentiveDetailsService.getIncentiveByisfapcId(appId);
		try {
			incentiveDetails2.setDis_status("Submit");
			incentiveDetailsService.updateIncentiveDetails(incentiveDetails2);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		businessEntityDetailsServiceImpl.commonDetails(appId, session, model);
		redirectView.setUrl("http://72.167.225.87/testing_nmswp/nmmasters/Entrepreneur_Bck_page.aspx");
		return redirectView;
	}

}
