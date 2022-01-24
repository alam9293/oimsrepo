package com.webapp.ims.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.webapp.ims.model.ApplicantDetails;
import com.webapp.ims.service.PolicyService;
import com.webapp.ims.webservices.SoapConsumeEx;

@Controller
@Component
public class PolicyController {

	@Autowired
	PolicyService PolicyService;
	
	
	
	SoapConsumeEx soapdetails = new SoapConsumeEx();
	
	@RequestMapping(value = {"/selectionpolicy"}, method = RequestMethod.GET)	
	public String policySelection(Model model){
	//	model.addAttribute("addressList", addressDetailsService.getAllAddressList());
		ApplicantDetails applicantDetails=new ApplicantDetails();
		soapRestData(model, applicantDetails);
			model.addAttribute("applicantDetails", applicantDetails);
			return "applicant_details";
		}

	
	@RequestMapping(value = {"/selectionpolicy"}, method = RequestMethod.POST)	
	public String selectionolicy(Model model){		
		/* model.addAttribute("selection_policy", PolicyService.getAllPolicy()); */	
		if("SC54001".equalsIgnoreCase("SC54001")){
			ApplicantDetails applicantDetails=new ApplicantDetails();
			soapRestData(model, applicantDetails);
			model.addAttribute("applicantDetails", applicantDetails);
			return "applicant_details";
		}
		return null;
		
       }
	
	
	private void soapRestData(Model model, ApplicantDetails applicantDetails) {
		Map<String, String> applicantResponce = new HashMap<String, String>(soapdetails.soapwebservice());
		for (Map.Entry<String,String> entry : applicantResponce.entrySet())
		{   
			if(entry.getKey().equalsIgnoreCase("Occupier_First_Name")) {
			applicantDetails.setAppFirstName(entry.getValue());
			}
			if(entry.getKey().equalsIgnoreCase("Occupier_Middle_Name")) {
				applicantDetails.setAppMiddleName(entry.getValue());
			}
			if(entry.getKey().equalsIgnoreCase("Occupier_Last_Name")) {
				applicantDetails.setAppLastName(entry.getValue());
			}
			if(entry.getKey().equalsIgnoreCase("Occupier_Email_ID")) {
				applicantDetails.setAppEmailId(entry.getValue());
			}
			if(entry.getKey().equalsIgnoreCase("Occupier_Mobile_No")) {
				//applicantDetails.setAppMobileNo(entry.getValue());
			}
			if(entry.getKey().equalsIgnoreCase("Occupier_Gender")) {
				applicantDetails.setGender(entry.getValue());
			}
			if(entry.getKey().equalsIgnoreCase("Occupier_PAN")) {
				//applicantDetails.setAppPancardNo(entry.getValue());
			}
			if(entry.getKey().equalsIgnoreCase("Occupier_Address")) {
				applicantDetails.setAppAddressLine1(entry.getValue());
			}
			if(entry.getKey().equalsIgnoreCase("Occupier_Country")) {
				applicantDetails.setAppCountry(entry.getValue());
			}
			if(entry.getKey().equalsIgnoreCase("Occupier_State_ID")) {
					
					model.addAttribute("appState", entry.getValue());
			//Todo need to dicuss
					//applicantDetails.setAppState(entry.getValue());
			
			}
			if(entry.getKey().equalsIgnoreCase("Occupier_District_Name")) {
				model.addAttribute("appDistrict", entry.getValue());
			}
			if(entry.getKey().equalsIgnoreCase("Occupier_Pin_Code")) {
				//applicantDetails.setAppPinCode(entry.getValue());
			}
		}		
		model.addAttribute("applicantDetails", applicantDetails);
	}
	
}
