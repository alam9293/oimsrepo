package com.webapp.ims.dis.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.model.DisLogin;
import com.webapp.ims.model.PrepareAgendaNotes;
import com.webapp.ims.model.SoapDataModel;
import com.webapp.ims.repository.PrepareAgendaNoteRepository;
import com.webapp.ims.service.impl.AdditionalInterest;
import com.webapp.ims.webservices.SoapConsumeEx;

@Controller
public class DisLoginController {
	private final Logger logger = LoggerFactory.getLogger(DisLoginController.class);

	@Autowired
	PrepareAgendaNoteRepository prepareAgendaNoteRepository;

	@Autowired
	AdditionalInterest additionalInterest;
	SoapConsumeEx soapdetails = null;

	@RequestMapping(value = "/dislogin", method = RequestMethod.GET)
	public ModelAndView dislogin(@ModelAttribute("dislogin") @Validated DisLogin dislogin, BindingResult result,
			Model model, HttpSession session, @RequestParam Map<String, String> params) {
		logger.debug("Initilize show login Process!");

		SoapDataModel niveshSoapData = new SoapDataModel();
		soapdetails = new SoapConsumeEx(params);
		Map<String, String> niveshResponse = new HashMap<String, String>(soapdetails.soapwebservice());
		niveshResponse.put("appID", niveshResponse.get("Unit_Id") + "A1");
		niveshSoapData.setNiveshSoapResponse(niveshResponse);
		model.addAttribute("niveshSoapResponse", niveshSoapData);

		String appId = "";
		appId = niveshResponse.get("Unit_Id") + "A1";
		session.setAttribute("appId", appId);
		model.addAttribute("dislogin", dislogin);

		List<PrepareAgendaNotes> prepareAgendaNotes1 = prepareAgendaNoteRepository.findAll();
		Set<String> listLoc = new HashSet<String>();

		// Create an iterator from the list
		Iterator<PrepareAgendaNotes> itr = prepareAgendaNotes1.iterator();

		// Find and remove all null
		if (itr != null) {
			while (itr.hasNext()) {
				String locNumber = itr.next().getLocNumber();
				if (locNumber != null && !(locNumber.isEmpty())) {
					listLoc.add(locNumber);
				}
			}
		}
		model.addAttribute("locList", listLoc);
		return new ModelAndView("/Disbursement/dislogin");
	}

	@PostMapping(value = { "/", "/dislogin" }, consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
	public ModelAndView getdislogin(@ModelAttribute("dislogin") @Validated DisLogin dislogin, BindingResult result,
			Model model, @RequestParam Map<String, String> params, HttpSession session) {
		try {
			SoapDataModel niveshSoapData = new SoapDataModel();
			soapdetails = new SoapConsumeEx(params);

			System.out.println(params);
			Map<String, String> niveshResponse = new HashMap<String, String>(soapdetails.soapwebservice());
			niveshResponse.put("appID", niveshResponse.get("Unit_Id") + "A1");
			niveshSoapData.setNiveshSoapResponse(niveshResponse);
			model.addAttribute("niveshSoapResponse", niveshSoapData);

			String appId = "";
			appId = niveshResponse.get("Unit_Id") + "A1";
			session.setAttribute("appId", appId);

			logger.debug("Initilize Login Processing!");
			List<PrepareAgendaNotes> prepareAgendaNotes1 = prepareAgendaNoteRepository.findAll();
			Set<String> listLoc = new HashSet<String>();

			// Create an iterator from the list
			Iterator<PrepareAgendaNotes> itr = prepareAgendaNotes1.iterator();

			// Find and remove all null
			if (itr != null) {
				while (itr.hasNext()) {
					String locNumber = itr.next().getLocNumber();
					if (locNumber != null && !(locNumber.isEmpty())) {
						listLoc.add(locNumber);
					}
				}
			}
			model.addAttribute("locList", listLoc);
			model.addAttribute("dislogin", dislogin);

		} catch (Exception e) {
			e.printStackTrace();

		}
		return new ModelAndView("/Disbursement/dislogin");

	}

	
	@RequestMapping(value = "/disloginapp", method = RequestMethod.POST)
	public ModelAndView getdislogin(@ModelAttribute("dislogin") @Validated DisLogin dislogin, BindingResult result,
			Model model, HttpSession session) {

		String appId = (String) session.getAttribute("appId");

		logger.debug("Initilize Login Processing!");
		String locNumber = additionalInterest.getLOCNumber(appId);
		System.out.println("locNumber" + locNumber);
		model.addAttribute("dislogin", dislogin);

		if (dislogin.getLocNumber().equalsIgnoreCase(locNumber)) {
			return new ModelAndView("redirect:/disApplicantDetails");
		} else {

			List<PrepareAgendaNotes> prepareAgendaNotes1 = prepareAgendaNoteRepository.findAll();
			Set<String> listLoc = new HashSet<String>();

			// Create an iterator from the list
			Iterator<PrepareAgendaNotes> itr = prepareAgendaNotes1.iterator();

			// Find and remove all null
			if (itr != null) {
				while (itr.hasNext()) {
					String locNumber2 = itr.next().getLocNumber();
					if (locNumber2 != null && !(locNumber2.isEmpty())) {
						listLoc.add(locNumber2);
					}
				}
			}
			model.addAttribute("locList", listLoc);
			model.addAttribute("message", "Please enter correct LOC number.");

			return new ModelAndView("/Disbursement/dislogin");
		}
	}

}
