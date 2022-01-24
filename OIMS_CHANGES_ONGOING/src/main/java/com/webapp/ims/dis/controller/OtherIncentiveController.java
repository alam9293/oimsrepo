package com.webapp.ims.dis.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpSession;
import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.dis.model.DisReimDisallowedInput;
import com.webapp.ims.dis.model.DisStampDeauty;
import com.webapp.ims.dis.model.ElectricityDutyExemption;
import com.webapp.ims.dis.model.MandiFeeExemption;
import com.webapp.ims.dis.model.OtherIncentive;
import com.webapp.ims.dis.model.StampDutyExemption;
import com.webapp.ims.dis.repository.DisElectricityDutyExemptionRepository;
import com.webapp.ims.dis.repository.DisMandiFeeExemptionRepository;
import com.webapp.ims.dis.repository.OtherIncRepository;
import com.webapp.ims.dis.repository.ReimDisAllowedRepository;
import com.webapp.ims.model.DeptIncentiveDetails;

@Controller
public class OtherIncentiveController {
	
	@Autowired
	OtherIncRepository otherIncRepository;
	
	@Autowired
	DisMandiFeeExemptionRepository disMandiFeeExemptionRepository;
	
	@Autowired
	DisElectricityDutyExemptionRepository disElectricityDutyExemptionRepository;
	
	@Autowired
	ReimDisAllowedRepository reimDisAllowedRepository;
	
	@GetMapping("/otherIncentive")
	public ModelAndView otherIncentive(@ModelAttribute("otherDisbursement") OtherIncentive otherIncentive,HttpSession session, Model model)
	{
		
		  String applId = (String) session.getAttribute("appId");
		  
		  String capitivePower = "CaptivePower";
		  String powerDrawn = "PowerDrawn";
		  
		  List<ElectricityDutyExemption> electricityDutyLis1=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(applId,capitivePower);
		  List<ElectricityDutyExemption> electricityDutyList2=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(applId,powerDrawn);
		  
		  if(electricityDutyLis1.size()>0)
		  {
			  model.addAttribute("electricityDutyLis1", electricityDutyLis1);
		  }
		  
		  if(electricityDutyList2.size()>0)
		  {
			  model.addAttribute("electricityDutyList2", electricityDutyList2);
		  }
		  
		  List<MandiFeeExemption> mandiList=disMandiFeeExemptionRepository.getDetailsByApcId(applId);
		  if(mandiList.size()>0)
		  {
			  model.addAttribute("mandiList", mandiList);
		  }
		  
		  List<DisReimDisallowedInput> disReimDisallowedInput=reimDisAllowedRepository.findByApcId(applId);
		  if(disReimDisallowedInput.size()>0)
		  {
			  model.addAttribute("disReimDisallowedInput", disReimDisallowedInput);
		  }
		  
		  
		  OtherIncentive otherInc=otherIncRepository.getDetailsByothApcid(applId);
			if (otherInc != null) {
				otherIncentive.setReimDissAllowedAvailAmt(otherInc.getReimDissAllowedAvailAmt());
				otherIncentive.setExCapitivePowerAmt(otherInc.getExCapitivePowerAmt());
				otherIncentive.setExePowerDrawnAmt(otherInc.getExePowerDrawnAmt());
				otherIncentive.setExeMandiFreeAmt(otherInc.getExeMandiFreeAmt());
				otherIncentive.setIndustrialUnitAmt(otherInc.getIndustrialUnitAmt());
			}
		return new ModelAndView("/Disbursement/otherIncentive");
		
	}

	

	@PostMapping("/saveOtherIncentive")
	public ModelAndView saveOtherIncentive(@ModelAttribute("otherDisbursement") OtherIncentive otherIncentive , HttpSession session)
	{
		

		  String applId = (String) session.getAttribute("appId");
			String appStr = applId.substring(0, applId.length() - 2);
		  otherIncentive.setId(appStr+"OTH");
		  otherIncentive.setOthApcid(applId);
		  otherIncentive.setStatus("Active");
		  otherIncentive.setCreateBy("Admin");
		  otherIncentive.setCreateDate(new Date());
		  otherIncentive.setModifiyDate(new Date());
		  otherIncentive.setModifiedBy("Admin");
		  
		  otherIncRepository.save(otherIncentive);
		
		
		return new ModelAndView("redirect:/disincentivetype");
		
	}

	@RequestMapping(value = "/saveElectricityTblData", method = RequestMethod.POST)
	public ModelAndView saveElectricityTableData(@RequestParam("abc") String status, @ModelAttribute("otherDisbursement") OtherIncentive otherIncentive,
			HttpSession session,ElectricityDutyExemption electricityDutyExemption)
	{
		 String applId = (String) session.getAttribute("appId");
		 
			String electricityEditId=(String) session.getAttribute("electricityEditId");
		 
		//ElectricityDutyExemption ecData=disElectricityDutyExemptionRepository.getDetailsByelectricDutyExeId(electricityEditId);
			if(status.equals("CaptivePower"))
			{
		
		
		electricityDutyExemption.setApcId(applId);
		electricityDutyExemption.setElectricityDutyExeFinYr(otherIncentive.getElectricityDutyExeFinYr());
		electricityDutyExemption.setElectricityDateFrom(otherIncentive.getElectricityDateFrom());
		electricityDutyExemption.setElectricityDateTo(otherIncentive.getElectricityDateTo());
		electricityDutyExemption.setElectricityAmtClaim(otherIncentive.getElectricityAmtClaim());
		
		
		
		electricityDutyExemption.setElectricityTypeStatus("CaptivePower");
		}
		else {
			electricityDutyExemption.setApcId(applId);
			electricityDutyExemption.setElectricityDutyExeFinYr(otherIncentive.getElectricityDutyExeFinYr2());
			electricityDutyExemption.setElectricityDateFrom(otherIncentive.getElectricityDateFrom2());
			electricityDutyExemption.setElectricityDateTo(otherIncentive.getElectricityDateTo2());
			electricityDutyExemption.setElectricityAmtClaim(otherIncentive.getElectricityAmtClaim2());
		electricityDutyExemption.setElectricityTypeStatus("PowerDrawn");
		}
		
		//String electricityEditId=(String) session.getAttribute("electricityEditId");
		if(electricityEditId == null || electricityEditId.isEmpty() || electricityEditId == "")
		{
		Random random = new Random();   
		// Generates random integers 0 to 49  
		int x = random.nextInt(50);  
		
		electricityDutyExemption.setElectricDutyExeId(applId + "ELC" + x);
		}
		else
		{
			electricityDutyExemption.setElectricDutyExeId(electricityEditId);
			session.setAttribute("electricityEditId", null);
		}
		disElectricityDutyExemptionRepository.save(electricityDutyExemption);
		
		return new ModelAndView("redirect:/otherIncentive");
		
	}
	
	@GetMapping("/removeElectricDutyList")
	public ModelAndView deleteStampDuty(@ModelAttribute("removeElectricItem") String removeId, HttpSession session, Model model)
	{
		
		System.out.println("hi");
		disElectricityDutyExemptionRepository.deleteById(removeId);
		
		return  new ModelAndView("redirect:/otherIncentive");
		
	}
	
	@GetMapping("/editElectricDetailsRecord")
	public ModelAndView editStampDuty(@RequestParam("electricData") String editId,
			OtherIncentive otherIncentive,ElectricityDutyExemption electricityDutyExemption, HttpSession session, Model model)
	{
		
		
		 String applId = (String) session.getAttribute("appId");
		
		
		 
		  String capitivePower = "CaptivePower";
		  String powerDrawn = "PowerDrawn";
		  
		  List<ElectricityDutyExemption> electricityDutyLis1=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(applId,capitivePower);
		  List<ElectricityDutyExemption> electricityDutyList2=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(applId,powerDrawn);
		  
		  if(electricityDutyLis1.size()>0)
		  {
			  model.addAttribute("electricityDutyLis1", electricityDutyLis1);
		  }
		  
		  if(electricityDutyList2.size()>0)
		  {
			  model.addAttribute("electricityDutyList2", electricityDutyList2);
		  }
		  
		  
		  OtherIncentive otherInc=otherIncRepository.getDetailsByothApcid(applId);
			if (otherInc != null) {
				otherIncentive.setReimDissAllowedAvailAmt(otherInc.getReimDissAllowedAvailAmt());
				otherIncentive.setExCapitivePowerAmt(otherInc.getExCapitivePowerAmt());
				otherIncentive.setExePowerDrawnAmt(otherInc.getExePowerDrawnAmt());
				otherIncentive.setExeMandiFreeAmt(otherInc.getExeMandiFreeAmt());
				otherIncentive.setIndustrialUnitAmt(otherInc.getIndustrialUnitAmt());
			}
		  
		  ElectricityDutyExemption electricityDuty=disElectricityDutyExemptionRepository.findAllByelectricDutyExeId(editId);
			if (electricityDuty != null && electricityDuty.getElectricityTypeStatus().equalsIgnoreCase("CaptivePower")) {
				
				
				otherIncentive.setElectricityDutyExeFinYr(electricityDuty.getElectricityDutyExeFinYr());
				otherIncentive.setElectricityDateFrom(electricityDuty.getElectricityDateFrom());
				otherIncentive.setElectricityDateTo(electricityDuty.getElectricityDateTo());
				otherIncentive.setElectricityAmtClaim(electricityDuty.getElectricityAmtClaim());
				
				
				model.addAttribute("otherDisbursement", otherIncentive);
				session.setAttribute("electricityEditId", electricityDuty.getElectricDutyExeId());
				model.addAttribute("finYr", electricityDuty.getElectricityDutyExeFinYr());
	
			}else
			{
				otherIncentive.setElectricityDutyExeFinYr2(electricityDuty.getElectricityDutyExeFinYr());
				otherIncentive.setElectricityDateFrom2(electricityDuty.getElectricityDateFrom());
				otherIncentive.setElectricityDateTo2(electricityDuty.getElectricityDateTo());
				otherIncentive.setElectricityAmtClaim2(electricityDuty.getElectricityAmtClaim());
				
				
				model.addAttribute("otherDisbursement", otherIncentive);
				session.setAttribute("electricityEditId", electricityDuty.getElectricDutyExeId());
				model.addAttribute("finYr", electricityDuty.getElectricityDutyExeFinYr());	
			}
			model.addAttribute("otherDisbursement", otherIncentive);
		model.addAttribute("edit", "edit");
		return new ModelAndView("/Disbursement/otherIncentive");
	}
	
	@RequestMapping(value = "/saveMandiFeeTblData", method = RequestMethod.POST)
	public ModelAndView saveMandiFeeTableData(@ModelAttribute("otherDisbursement") OtherIncentive otherIncentive,
			HttpSession session,MandiFeeExemption mandiFeeExemption)
	{
		 String applId = (String) session.getAttribute("appId");
		 
			String mandiFeeEditId=(String) session.getAttribute("mandiFeeEditId");
		 
		
			mandiFeeExemption.setApcId(applId);
			mandiFeeExemption.setMandiFeeExeFinYr1(otherIncentive.getMandiFeeExeFinYr1());
			mandiFeeExemption.setMandiFeeDateFrom1(otherIncentive.getMandiFeeDateFrom1());
			mandiFeeExemption.setMandiFeeDateTo1(otherIncentive.getMandiFeeDateTo1());
			mandiFeeExemption.setClaimMandiFeeExe1(otherIncentive.getClaimMandiFeeExe1());
			
		
		if(mandiFeeEditId == null || mandiFeeEditId.isEmpty() || mandiFeeEditId == "")
		{
		Random random = new Random();   
		// Generates random integers 0 to 49  
		int x = random.nextInt(50);  
		
		mandiFeeExemption.setMandiFeeExeId(applId + "MND" + x);
		}
		else
		{
			mandiFeeExemption.setMandiFeeExeId(mandiFeeEditId);
			session.setAttribute("mandiFeeEditId", null);
		}
		disMandiFeeExemptionRepository.save(mandiFeeExemption);
		
		return new ModelAndView("redirect:/otherIncentive");
		
	}
	
	@GetMapping("/removeMandiFeeList")
	public ModelAndView deleteMandiFee(@ModelAttribute("removeMandiItem") String removeId, HttpSession session, Model model)
	{
		
		System.out.println("hi");
		disMandiFeeExemptionRepository.deleteById(removeId);
		
		return  new ModelAndView("redirect:/otherIncentive");
		
	}
	
	
	@GetMapping("/editMandiDetails")
	public ModelAndView editStampDuty(@RequestParam("editMandiFeeRecord") String editId,
			MandiFeeExemption mandiFeeExemption, OtherIncentive otherIncentive, HttpSession session, Model model)
	{
		
		 String applId = (String) session.getAttribute("appId");
		  
		  String capitivePower = "CaptivePower";
		  String powerDrawn = "PowerDrawn";
		  
		  List<ElectricityDutyExemption> electricityDutyLis1=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(applId,capitivePower);
		  List<ElectricityDutyExemption> electricityDutyList2=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(applId,powerDrawn);
		  
		  if(electricityDutyLis1.size()>0)
		  {
			  model.addAttribute("electricityDutyLis1", electricityDutyLis1);
		  }
		  
		  if(electricityDutyList2.size()>0)
		  {
			  model.addAttribute("electricityDutyList2", electricityDutyList2);
		  }
		  
		  List<MandiFeeExemption> mandiList=disMandiFeeExemptionRepository.getDetailsByApcId(applId);
		  if(mandiList.size()>0)
		  {
			  model.addAttribute("mandiList", mandiList);
		  }
		  
	       mandiFeeExemption=disMandiFeeExemptionRepository.findByMandiFeeExeId(editId);
			
			otherIncentive.setMandiFeeExeFinYr1(mandiFeeExemption.getMandiFeeExeFinYr1());
			otherIncentive.setMandiFeeDateFrom1(mandiFeeExemption.getMandiFeeDateFrom1());
			otherIncentive.setMandiFeeDateTo1(mandiFeeExemption.getMandiFeeDateTo1());
			otherIncentive.setClaimMandiFeeExe1(mandiFeeExemption.getClaimMandiFeeExe1());
			
			model.addAttribute("finYr", mandiFeeExemption.getMandiFeeExeFinYr1());
			model.addAttribute("otherDisbursement", otherIncentive);
			
			session.setAttribute("mandiFeeEditId", editId);
			
		  
		  OtherIncentive otherInc=otherIncRepository.getDetailsByothApcid(applId);
			if (otherInc != null) {
				otherIncentive.setReimDissAllowedAvailAmt(otherInc.getReimDissAllowedAvailAmt());
				otherIncentive.setExCapitivePowerAmt(otherInc.getExCapitivePowerAmt());
				otherIncentive.setExePowerDrawnAmt(otherInc.getExePowerDrawnAmt());
				otherIncentive.setExeMandiFreeAmt(otherInc.getExeMandiFreeAmt());
				otherIncentive.setIndustrialUnitAmt(otherInc.getIndustrialUnitAmt());
			}
		
	model.addAttribute("otherDisbursement", otherIncentive);
			
		model.addAttribute("edit", "edit");
		return new ModelAndView("/Disbursement/otherIncentive");
	}
	
	@RequestMapping(value = "/saveDisallowedInputTblData", method = RequestMethod.POST)
	public ModelAndView saveDisallowedTableData(@ModelAttribute("otherDisbursement") OtherIncentive otherIncentive,
			HttpSession session,DisReimDisallowedInput disReimDisallowedInput)
	{
		 String applId = (String) session.getAttribute("appId");
		 
			String disallowedEditId=(String) session.getAttribute("disallowedEditId");
		 
			disReimDisallowedInput.setApcId(applId);
			disReimDisallowedInput.setDisallowedFinYr(otherIncentive.getDisallowedFinYr());
			disReimDisallowedInput.setDisallowedDateFr(otherIncentive.getDisallowedDateFr());
			disReimDisallowedInput.setDisallowedDateTo(otherIncentive.getDisallowedDateTo());
			disReimDisallowedInput.setDisallowedClaimAmt(otherIncentive.getDisallowedClaimAmt());
			
		
		if(disallowedEditId == null || disallowedEditId.isEmpty() || disallowedEditId == "")
		{
		Random random = new Random();   
		// Generates random integers 0 to 49  
		int x = random.nextInt(50);  
		
		disReimDisallowedInput.setDisallowedId(applId + "REIM" + x);
		}
		else
		{
			disReimDisallowedInput.setDisallowedId(disallowedEditId);
			session.setAttribute("disallowedEditId", null);
		}
		reimDisAllowedRepository.save(disReimDisallowedInput);
		
		return new ModelAndView("redirect:/otherIncentive");
		
	}
	
	@GetMapping("/removeDisallowedList")
	public ModelAndView deleteDisallowed(@ModelAttribute("removedisallowedItem") String removeId, HttpSession session, Model model)
	{
		
		System.out.println("hi");
		reimDisAllowedRepository.deleteById(removeId);
		
		return  new ModelAndView("redirect:/otherIncentive");
		
	}
	
	@GetMapping("/editDisallowedDetails")
	public ModelAndView editDisallowedList(@RequestParam("editDisallowedRecord") String editId,
			DisReimDisallowedInput disReimDisallowed, OtherIncentive otherIncentive, HttpSession session, Model model)
	{
		
		 String applId = (String) session.getAttribute("appId");
		  
		  String capitivePower = "CaptivePower";
		  String powerDrawn = "PowerDrawn";
		  
		  List<ElectricityDutyExemption> electricityDutyLis1=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(applId,capitivePower);
		  List<ElectricityDutyExemption> electricityDutyList2=disElectricityDutyExemptionRepository.findByapcIdAndElectricityTypeStatus(applId,powerDrawn);
		  
		  if(electricityDutyLis1.size()>0)
		  {
			  model.addAttribute("electricityDutyLis1", electricityDutyLis1);
		  }
		  
		  if(electricityDutyList2.size()>0)
		  {
			  model.addAttribute("electricityDutyList2", electricityDutyList2);
		  }
		  
		  List<MandiFeeExemption> mandiList=disMandiFeeExemptionRepository.getDetailsByApcId(applId);
		  if(mandiList.size()>0)
		  {
			  model.addAttribute("mandiList", mandiList);
		  }
		  
		  List<DisReimDisallowedInput> disReimDisallowedInput=reimDisAllowedRepository.findByApcId(applId);
		  if(disReimDisallowedInput.size()>0)
		  {
			  model.addAttribute("disReimDisallowedInput", disReimDisallowedInput);
		  }
		  
		  DisReimDisallowedInput DisReimDisallowedDetails=reimDisAllowedRepository.findByDisallowedId(editId);
		  otherIncentive.setDisallowedFinYr(DisReimDisallowedDetails.getDisallowedFinYr());
		  otherIncentive.setDisallowedDateFr(DisReimDisallowedDetails.getDisallowedDateFr());
		  otherIncentive.setDisallowedDateTo(DisReimDisallowedDetails.getDisallowedDateTo());
		  otherIncentive.setDisallowedClaimAmt(DisReimDisallowedDetails.getDisallowedClaimAmt());
		  
		  model.addAttribute("finYr", DisReimDisallowedDetails.getDisallowedFinYr());
		  session.setAttribute("disallowedEditId", editId);
		  model.addAttribute("otherDisbursement", otherIncentive);
		  
		  
		  OtherIncentive otherInc=otherIncRepository.getDetailsByothApcid(applId);
			if (otherInc != null) {
				otherIncentive.setReimDissAllowedAvailAmt(otherInc.getReimDissAllowedAvailAmt());
				otherIncentive.setExCapitivePowerAmt(otherInc.getExCapitivePowerAmt());
				otherIncentive.setExePowerDrawnAmt(otherInc.getExePowerDrawnAmt());
				otherIncentive.setExeMandiFreeAmt(otherInc.getExeMandiFreeAmt());
				otherIncentive.setIndustrialUnitAmt(otherInc.getIndustrialUnitAmt());
			}
			model.addAttribute("otherDisbursement", otherIncentive);
		model.addAttribute("edit", "edit");
		return new ModelAndView("/Disbursement/otherIncentive");
	}
	
}
