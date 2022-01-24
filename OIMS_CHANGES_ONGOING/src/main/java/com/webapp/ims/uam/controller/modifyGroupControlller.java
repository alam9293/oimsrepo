/**
 * Author:: Mohd Alam
* Created on:: 
 */
package com.webapp.ims.uam.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.model.TblGroup;
import com.webapp.ims.uam.service.TblGroupService;
import com.webapp.ims.uam.service.TblPendingWorksService;

/**
 * @author dell
 */
@Controller
public class modifyGroupControlller 
{
	private final Logger logger = LoggerFactory.getLogger(modifyGroupControlller.class);
	
	@Autowired
	private TblGroupService TblgroupService;

	
	@Autowired
	TblPendingWorksService tblPendingWorksService;
	
	
	public List<TblGroup> getEnableGroupslist() 
	{
		return TblgroupService.getEnableGroups();
	}
	
	public List<TblGroup> getDisableGroupslist() 
	{
		return TblgroupService.getDisableGroups();
	}
	
	
	
	@PostMapping(value = "/enblGroup")
	public ModelAndView EnableRole(
	   @ModelAttribute("enbldsblgroupmodel") @Validated TblGroup tblgroup, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		System.out.println(tblgroup.getGroupId().split("seprator")[0].replace(",", ""));
        String id     = tblgroup.getGroupId().split("seprator")[0].replace(",", "");
        
		String taskquery = "UPDATE loc.\"Tbl_Group\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Creator_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"', \"Status\" = 'E' WHERE \"Group_Id\" ='"+id+"'";
		
		int updatecount = TblgroupService.executeNativeQuery(taskquery);
		
		model.addAttribute("enblGrouplist", getEnableGroupslist());
		model.addAttribute("dsblGrouplist", getDisableGroupslist());
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Group have been Enabled Successfully");
		return new ModelAndView("/uam/modifyGroup");
	}
	
	@PostMapping(value = "/dsblGroup")
	public ModelAndView DisableRole(
	   @ModelAttribute("enbldsblgroupmodel") @Validated TblGroup tblgroup, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		System.out.println(tblgroup.getGroupId().split("seprator")[0].replace(",", ""));
        String id     = tblgroup.getGroupId().split("seprator")[0].replace(",", "");
        
		String taskquery = "UPDATE loc.\"Tbl_Group\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Creator_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"', \"Status\" = 'D' WHERE \"Group_Id\" ='"+id+"'";
		
		int updatecount = TblgroupService.executeNativeQuery(taskquery);
		
		model.addAttribute("enblGrouplist", getEnableGroupslist());
		model.addAttribute("dsblGrouplist", getDisableGroupslist());
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Group have been Disabled Successfully");
		return new ModelAndView("/uam/modifyGroup");
	}
	
}


