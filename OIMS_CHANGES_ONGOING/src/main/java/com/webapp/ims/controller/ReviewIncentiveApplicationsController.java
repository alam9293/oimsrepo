package com.webapp.ims.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ReviewIncentiveApplicationsController {

	
	/*
	 * @Autowired InfrastructureSubsidy iis;
	 * 
	 * @Autowired Quality_Development_Interest_Subsidy quinsu;
	 */

private final Logger logger = LoggerFactory.getLogger(ReviewIncentiveApplicationsController.class);
	
	@RequestMapping(value = "/reviewIncentiveApplication", method = RequestMethod.GET)	
	public String reviewIncentiveApplication(Model model){
		
		logger.debug("Review Incentive Applications");
       return "review_incentive_applications";
       }
	
	
	@RequestMapping(value = "/incentiveCalculationDetails", method = RequestMethod.GET)	
	public String incentiveCalculationDetails(Model model, HttpServletRequest request, HttpServletResponse resp){
		/*
		 * request.setAttribute("testCIS", test.getCIS());
		 * request.setAttribute("invCIS", test.invincentive());
		 * request.setAttribute("IIS", iis.getIIS()); request.setAttribute("invIIS",
		 * iis.invincentiveIIS());
		 * 
		 * request.setAttribute("quIS", quinsu.getQIS()); request.setAttribute("invQIS",
		 * quinsu.invincentive_qis());
		 */
		logger.debug("Incentive details Applications");
       return "incentive_details";
       }
}
