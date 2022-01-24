/**
 * Author:: Mohd Alam
* Created on:: 
 */
package com.webapp.ims.uam.controller;

import java.util.LinkedHashMap;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblPendingWorksService;

/**
 * @author dell
 */
@Controller
public class ApprovalWorksController 
{
	private final Logger logger = LoggerFactory.getLogger(AddRoleControlller.class);
	

	@Autowired
	TblDepartmentService tblDepartmentService;
	
	@Autowired
	TblPendingWorksService tblPendingWorksService;
	
	public List<TblApprovalWorks> getListofDataTobeApproved(HttpSession session)
	{
		List<TblApprovalWorks>datalist = tblPendingWorksService.getListofPendingDataTobeApproved((String)(session.getAttribute("userEmaiId")));
		return datalist;
	}
	
	public String getModuleName(long taskId)
	{
		return tblPendingWorksService.getApprovalDataDetailsBytaskId(taskId).getModuleName();
	}
	
	public LinkedHashMap<String,String> getDetailsofDataTobeApproved(long taskId)
	{
		LinkedHashMap<String,String>AprovalDataMap = tblPendingWorksService.getAprovalDataMap(taskId);
		return AprovalDataMap;
	}
	
	public void approveDisplayDataDeatil(TblApprovalWorks tblApprovalWorks,HttpSession session) 
	{
		tblPendingWorksService.approveDisplayDataDeatil(tblApprovalWorks,session);
	}
	
	public void disApproveDisplayDataDeatil(TblApprovalWorks tblApprovalWorks,HttpSession session) 
	{
		tblPendingWorksService.disApproveDisplayDataDeatil(tblApprovalWorks,session);
	}
	
	@PostMapping(value = "/ViewApprovalDataDetail")
	public ModelAndView PendingworksApproval
	(
        Model model, HttpSession session,
        @RequestParam(value = "Task_Id", required = true) String taskId
	)
	{
		model.addAttribute("displayDataMap", getDetailsofDataTobeApproved(Long.valueOf(taskId)));
		model.addAttribute("taskId", taskId);
		model.addAttribute("ListofDataTobeApproved", getListofDataTobeApproved(session));
		model.addAttribute("ModuleName", getModuleName(Long.valueOf(taskId)));
		return new ModelAndView("/uam/ViewApprovalDataDetail");
	}
	
	@GetMapping(value = "/Approve")
	public ModelAndView Approve
	(
		@ModelAttribute("datalistmodel") @Validated TblApprovalWorks tblApprovalWorks, BindingResult result,
        Model model, HttpSession session,
        @RequestParam(value = "Task_Id", required = true) long taskId
	)
	{
		for(TblApprovalWorks pendingworksdtl:getListofDataTobeApproved(session))
		{
			if(pendingworksdtl.getTaskId()==taskId)
			{
				approveDisplayDataDeatil(pendingworksdtl,session);
			}
		}
		model.addAttribute("ListofDataTobeApproved", getListofDataTobeApproved(session));
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Application Data Have Been Approved Successfully!");
		return new ModelAndView("/uam/Unsettled_Works_Approval");
	}
	
	@GetMapping(value = "/disApprove")
	public ModelAndView DisApprove
	(
		@ModelAttribute("datalistmodel") @Validated TblApprovalWorks tblApprovalWorks, BindingResult result,
        Model model, HttpSession session,
        @RequestParam(value = "Task_Id", required = true) long taskId
	)
	{
		for(TblApprovalWorks pendingworksdtl:getListofDataTobeApproved(session))
		{
			if(pendingworksdtl.getTaskId()==taskId)
			{
				disApproveDisplayDataDeatil(pendingworksdtl,session);
			}
		}
		model.addAttribute("ListofDataTobeApproved", getListofDataTobeApproved(session));
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Application Data Have Been DisApproved Successfully!");
		return new ModelAndView("/uam/Unsettled_Works_Approval");
	}
	
}
