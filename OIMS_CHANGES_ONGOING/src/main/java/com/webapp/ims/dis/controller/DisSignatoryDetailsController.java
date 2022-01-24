/*
 *     Copyright 2020 - IT Solution powered by National Informatics Centre Uttar Pradesh State Unit
 *
 */
package com.webapp.ims.dis.controller;

import java.util.Date;
import javax.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import com.webapp.ims.dis.model.CapitalInvestmentDetails;
import com.webapp.ims.dis.model.DisEmploymentDetails;
import com.webapp.ims.dis.model.DissbursmentApplicantDetails;
import com.webapp.ims.dis.service.CapitalInvestService;
import com.webapp.ims.dis.service.DisbursmentApplicantDetailsService;
import com.webapp.ims.dis.service.DisbursmentEmploymentDetailsService;
import com.webapp.ims.service.BusinessEntityDetailsService;
import com.webapp.ims.service.impl.AdditionalInterest;

/**
 * @author nic
 *
 */
@Controller
public class DisSignatoryDetailsController {

	private final Logger logger = LoggerFactory.getLogger(DisSignatoryDetailsController.class);

	@Autowired
	BusinessEntityDetailsService businessService;

	@Autowired
	DisbursmentApplicantDetailsService disApplicantService;
	
	@Autowired
	CapitalInvestService capitalInvestService;

	@Autowired
	AdditionalInterest additionalInterest;
	
	@Autowired
	DisbursmentEmploymentDetailsService disbursmentEmploymentDetailsService;

	@GetMapping(value = "/disApplicantDetails")
	public ModelAndView disApplicantDetails(
			@ModelAttribute("disApplicantDetails") DissbursmentApplicantDetails disApplicantDetails, String appId,
			Model model, HttpSession session) {

		logger.info(" Start Disbursment, disApplicantDetails get Method ");
		try {
			appId = (String) session.getAttribute("appId");

			businessService.commonDetails(appId, session, model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		model.addAttribute("disApplicantDetails", disApplicantDetails);
		
		//String proID = appId.substring(0, appId.length() - 2) + "DSB";
		DissbursmentApplicantDetails applicant = disApplicantService.getDetailsBydisAppId(appId);
		CapitalInvestmentDetails capi = capitalInvestService.getDetailsBycapInvApcId(appId);
		DisEmploymentDetails empappdis = disbursmentEmploymentDetailsService.getDetailsBydisAppId(appId);
		try {
			String applicantID = applicant.getDisAppId();
			if(applicantID != null) {
				model.addAttribute("applicantID", applicantID);
			}
		} catch (Exception e) {}
		
		try {
			String capiapp = capi.getCapInvApcId();
			if(capiapp != null) {
				model.addAttribute("capiapp", capiapp);
			}
			
		} catch (Exception e) {
			
		}
		try {
			 String empapp = empappdis.getDisAppId();
			if(empapp != null) {
				model.addAttribute("empapp", empapp);
			}
			
		} catch (Exception e) {
			
		}
		logger.info(" End Disbursment, disApplicantDetails get Method ");
		return new ModelAndView("/Disbursement/disApplicantDetails");
	}

	@PostMapping(value = "/disApplicantDetailsSave")
	public ModelAndView disApplicantDetailsSave(
			@ModelAttribute("disApplicantDetails") DissbursmentApplicantDetails disApplicantDetails, Model model,
			HttpSession session, String appId) {

		logger.info(" Start Disbursment, disApplicantDetails Save Method ");

		try {
			appId = (String) session.getAttribute("appId");

			disApplicantDetails.setDisId("DIS" + appId);
			disApplicantDetails.setDisAppId(appId);

			String locNumber = additionalInterest.getLOCNumber(appId);

			disApplicantDetails.setLocNumberDis(locNumber);
			disApplicantDetails.setCreateBy("Admin");
			disApplicantDetails.setCreateDate(new Date());

			DissbursmentApplicantDetails disAppDetails = disApplicantService.getDetailsBydisAppId(appId);
			if (disAppDetails != null) {
				disApplicantService.updateDisbursmentApplicantDetails(disApplicantDetails);
			} else {
				disApplicantService.saveDisbursmentApplicantDetails(disApplicantDetails);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.addAttribute("disApplicantDetails", disApplicantDetails);

		logger.info(" End Disbursment, disApplicantDetails Save Method");
		return new ModelAndView("redirect:/disbIncentiveCapInv");
	}

	@GetMapping(value = "/discapitalinvestment")
	public ModelAndView discapitalinvestment() {
		return new ModelAndView("/Disbursement/discapitalinvestment");
	}

	@GetMapping(value = "/training")
	public ModelAndView training() {
		return new ModelAndView("/training");
	}

}
