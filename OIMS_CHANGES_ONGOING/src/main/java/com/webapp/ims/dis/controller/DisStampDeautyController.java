package com.webapp.ims.dis.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;

import javax.servlet.http.HttpSession;

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

import com.webapp.ims.dis.model.DisStampDeauty;


import com.webapp.ims.dis.model.StampDutyExemption;
import com.webapp.ims.dis.repository.DisStampDeautyRepository;
import com.webapp.ims.dis.repository.StampDutyApplicationFormRepo;
import com.webapp.ims.dis.repository.StampDutyExemptionRepository;
import com.webapp.ims.model.DeptIncentiveDetails;
import com.webapp.ims.repository.DeptIncentiveDetailsRepository;

@Controller
public class DisStampDeautyController {

	@Autowired
	DisStampDeautyRepository disStampDeautyRepository;

	@Autowired
	DeptIncentiveDetailsRepository deptInctRepos;
	
	@Autowired
	StampDutyApplicationFormRepo stampDutyApplicationFormRepo;
	
	@Autowired
	StampDutyExemptionRepository stampDutyExemptionRepository;

	

	/*
	 * @Autowired StampDutyService stampDutyService;
	 */

	@GetMapping("/stampDuty")
	public ModelAndView getStampDuty(@ModelAttribute("stampDisbursement") DisStampDeauty disStampDeauty, Model model,
			HttpSession session) {


		String applId = (String) session.getAttribute("appId");
		String stampId = applId + "STM";
		
		List<StampDutyExemption> stampList=stampDutyExemptionRepository.findAllByStampId(applId);
		if(stampList.size()>0)
		{
			
			model.addAttribute("stampList", stampList);
		}
		Optional<DisStampDeauty> disStampDeauty1=disStampDeautyRepository.findById(stampId);
		
		if(disStampDeauty1.isPresent()) {
			
			Long examt=disStampDeauty1.get().getExemptionAvailAmount();
			Long reimamt=disStampDeauty1.get().getReimbursementAvailAmount();
			Long addamt=disStampDeauty1.get().getAdditionalStampAvailAmount();
			Long ttlamt=disStampDeauty1.get().getTotalAvailAmount();
		
			disStampDeauty.setExemptionAvailAmount(examt);
			disStampDeauty.setReimbursementAvailAmount(reimamt);
			disStampDeauty.setAdditionalStampAvailAmount(addamt);
			disStampDeauty.setTotalAvailAmount(ttlamt);
			
			model.addAttribute("stampDisbursement", disStampDeauty);
			
			DeptIncentiveDetails deptInctive = deptInctRepos.getIncentiveByisfapcId(applId);
			  
			  model.addAttribute("exe", deptInctive.getISF_Stamp_Duty_EX());
			  model.addAttribute("reim", deptInctive.getISF_Amt_Stamp_Duty_Reim());
			  model.addAttribute("additional",deptInctive.getISF_Additonal_Stamp_Duty_EX()); 
			  model.addAttribute("total",deptInctive.getISF_Ttl_Stamp_Duty_EX());
			
			}
		else {
		
			 DeptIncentiveDetails deptInctive = deptInctRepos.getIncentiveByisfapcId(applId);
			  
			  model.addAttribute("exe", deptInctive.getISF_Stamp_Duty_EX());
			  model.addAttribute("reim", deptInctive.getISF_Amt_Stamp_Duty_Reim());
			  model.addAttribute("additional",deptInctive.getISF_Additonal_Stamp_Duty_EX()); 
			  model.addAttribute("total",deptInctive.getISF_Ttl_Stamp_Duty_EX());
		}
		return new ModelAndView("/Disbursement/disstampduty");

	}

	@PostMapping("/saveStampDuty")
	public ModelAndView insertStampDuty(@ModelAttribute("stampDisbursement") DisStampDeauty disStampDeauty, Model model,
			HttpSession session) {
		String applId = (String) session.getAttribute("appId");

		disStampDeauty.setStampId(applId + "STM");
		disStampDeauty.setStampApcId(applId);
		disStampDeauty.setStatus("Active");
		disStampDeauty.setCreateBy("Admin");
		disStampDeauty.setCreateDate(new Date());
		disStampDeauty.setModifiyDate(new Date());
		disStampDeauty.setModifiedBy("Admin");
		

		disStampDeautyRepository.save(disStampDeauty);
		

		//List<StampDutyApplicationForm> StampDutyExemptionList=new ArrayList<StampDutyApplicationForm>();
		
		//StampDutyExemption stampDutyExemption=new StampDutyExemption();
		/*
		 * String[] a1 = disStampDeauty.getStampDutyFinYr(); String[] b1 =
		 * disStampDeauty.getDurationFrom(); String[] c1 =
		 * disStampDeauty.getDurationTo(); String[] d1 =
		 * disStampDeauty.getClaimReimAmt();
		 * 
		 * StampDutyApplicationForm stampDutyExemption=new StampDutyApplicationForm();
		 * for(int i=0;i<a1.length;i++) {
		 * 
		 * 
		 * stampDutyExemption.setStampApcId(applId);
		 * stampDutyExemption.setStampDutyFinYr(a1[i]);
		 * stampDutyExemption.setDurationFrom(b1[i]);
		 * stampDutyExemption.setDurationTo(c1[i]);
		 * stampDutyExemption.setClaimReimAmt(d1[i]);
		 * 
		 * 
		 * 
		 * } StampDutyExemptionList.add(stampDutyExemption);
		 * stampDutyApplicationFormRepo.saveAll(StampDutyExemptionList);
		 */
		
		return new ModelAndView("redirect:/disincentivetype");
	}

	@RequestMapping(value = "/saveStampTblData", method = RequestMethod.POST)
	public ModelAndView saveStampTableData(@ModelAttribute("stampDisbursement") DisStampDeauty disStampDeauty, Model model,
			HttpSession session)
	{
		String applId = (String) session.getAttribute("appId");
		//StampDutyApplicationForm stampDutyAppForm=new StampDutyApplicationForm();
		
		StampDutyExemption stampDutyAppForm=new StampDutyExemption();
		stampDutyAppForm.setApcId(applId);
		
		stampDutyAppForm.setComputationFinYr(disStampDeauty.getStampDutyFinYr());
		stampDutyAppForm.setStampDutyDateFrom(disStampDeauty.getDurationFrom());
		stampDutyAppForm.setStampDutyDateTo(disStampDeauty.getDurationTo());
		stampDutyAppForm.setClaimStampDutyReimAmt(disStampDeauty.getClaimReimAmt());
		
		String editStampId=(String) session.getAttribute("stampEditId");
		if(editStampId == null || editStampId.isEmpty() || editStampId == "")
		{
		Random random = new Random();   
		// Generates random integers 0 to 49  
		int x = random.nextInt(50);  
		
		stampDutyAppForm.setStampId(applId + "PR" + x);
		}
		else
		{
			stampDutyAppForm.setStampId(editStampId);
			session.setAttribute("stampEditId", null);
		}
		stampDutyExemptionRepository.save(stampDutyAppForm);
		
		return new ModelAndView("redirect:/stampDuty");
		
	}
	
	@GetMapping("/removeStampList")
	public ModelAndView deleteStampDuty(@ModelAttribute("removeStampItem") String removeId, HttpSession session, Model model)
	{
		
		stampDutyExemptionRepository.deleteById(removeId);
		
		return  new ModelAndView("redirect:/stampDuty");
		
	}
	
	@GetMapping("/editStampDetails")
	public ModelAndView editStampDuty(@RequestParam("editStampDetailsRecord") String editId,
			StampDutyExemption stampDutyExemption, DisStampDeauty disStampDeauty, HttpSession session, Model model)
	{
		
		
		String applId = (String) session.getAttribute("appId");
		String stampId = applId + "STM";
		
		
		
		List<StampDutyExemption> stampList=stampDutyExemptionRepository.findAllByStampId(applId);
		if(stampList.size()>0)
		{
			
			model.addAttribute("stampList", stampList);
		}
		
		stampDutyExemption=stampDutyExemptionRepository.findByStampId(editId);
		
		disStampDeauty.setStampDutyFinYr(stampDutyExemption.getComputationFinYr());
		disStampDeauty.setDurationFrom(stampDutyExemption.getStampDutyDateFrom());
		disStampDeauty.setDurationTo(stampDutyExemption.getStampDutyDateTo());
		disStampDeauty.setClaimReimAmt(stampDutyExemption.getClaimStampDutyReimAmt());
		
		session.setAttribute("stampEditId", stampDutyExemption.getStampId());
		model.addAttribute("finYr", disStampDeauty.getStampDutyFinYr());
		
		Optional<DisStampDeauty> disStampDeauty1=disStampDeautyRepository.findById(stampId);
		
		if(disStampDeauty1.isPresent()) {
			
			Long examt=disStampDeauty1.get().getExemptionAvailAmount();
			Long reimamt=disStampDeauty1.get().getReimbursementAvailAmount();
			Long addamt=disStampDeauty1.get().getAdditionalStampAvailAmount();
			Long ttlamt=disStampDeauty1.get().getTotalAvailAmount();
		
			disStampDeauty.setExemptionAvailAmount(examt);
			disStampDeauty.setReimbursementAvailAmount(reimamt);
			disStampDeauty.setAdditionalStampAvailAmount(addamt);
			disStampDeauty.setTotalAvailAmount(ttlamt);
			
			model.addAttribute("stampDisbursement", disStampDeauty);
			
			DeptIncentiveDetails deptInctive = deptInctRepos.getIncentiveByisfapcId(applId);
			  
			  model.addAttribute("exe", deptInctive.getISF_Stamp_Duty_EX());
			  model.addAttribute("reim", deptInctive.getISF_Amt_Stamp_Duty_Reim());
			  model.addAttribute("additional",deptInctive.getISF_Additonal_Stamp_Duty_EX()); 
			  model.addAttribute("total",deptInctive.getISF_Ttl_Stamp_Duty_EX());
			
			}
		else {
		
			 DeptIncentiveDetails deptInctive = deptInctRepos.getIncentiveByisfapcId(applId);
			  
			  model.addAttribute("exe", deptInctive.getISF_Stamp_Duty_EX());
			  model.addAttribute("reim", deptInctive.getISF_Amt_Stamp_Duty_Reim());
			  model.addAttribute("additional",deptInctive.getISF_Additonal_Stamp_Duty_EX()); 
			  model.addAttribute("total",deptInctive.getISF_Ttl_Stamp_Duty_EX());
		}
		model.addAttribute("edit", "edit");
		return new ModelAndView("/Disbursement/disstampduty");
	}
	
	
}
