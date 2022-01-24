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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.uam.model.TblDepartment;
import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblFormService;
import com.webapp.ims.uam.service.TblPendingWorksService;
import com.webapp.ims.uam.service.TblUsersService;

/**
 * @author dell
 */
@Controller
public class modifyUserControlller 
{
	private final Logger logger = LoggerFactory.getLogger(modifyUserControlller.class);
	
	@Autowired
	private TblUsersService tblUsersService;
	
	@Autowired
	TblDepartmentService tblDepartmentService;
	
	@Autowired
	TblPendingWorksService tblPendingWorksService;
	
	
	public List<TblUsers> getenblUserlist(String loginUser) 
	{
		return tblUsersService.getEnableUserName(loginUser);
	}
	
	public List<TblUsers> getdisableblUserlist(String loginUser) 
	{
		return tblUsersService.getDisableUserName(loginUser);
	}
	
	public List<TblUsers> getLockUserlist(String loginUser) 
	{
		return tblUsersService.getLockUserName(loginUser);
	}
	
	@PostMapping(value = "/enblUser")
	public ModelAndView EnableUserID(
	   @ModelAttribute("enbldsblusermodel") @Validated TblUsers tblusers, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks,
	   @RequestParam("dsblusrmail") String dsblusrmail
	)  
	{
		System.out.println(dsblusrmail);
		TblUsers userObj = tblUsersService.getLoginByuserName(dsblusrmail);
        String id     = userObj.getid();
        String loginUser = (String) session.getAttribute("userEmaiId");
        
		String taskquery = "UPDATE loc.\"Tbl_Users\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Created_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"', \"Status\" = 'A',\"Usr_Active_Status\" ='A' WHERE \"ID\" ='"+id+"'";
		
		int updatecount = tblUsersService.executeNativeQuery(taskquery);
		
		model.addAttribute("enblUserlist", getenblUserlist(loginUser));
		model.addAttribute("dsblUserlist", getdisableblUserlist(loginUser));
		model.addAttribute("lockUserlist", getLockUserlist(loginUser));
		model.addAttribute("dltUserlist", getenblUserlist(loginUser));
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "UserID have been Enabled Successfully");
		return new ModelAndView("/uam/modifyUser");
	}
	
	@PostMapping(value = "/dsblUser")
	public ModelAndView DisableUserID(
	   @ModelAttribute("enbldsblusermodel") @Validated TblUsers tblusers, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks,
	   @RequestParam("enblusrmail") String enblusrmail
	)  
	{
		System.out.println(enblusrmail);
		TblUsers userObj = tblUsersService.getLoginByuserName(enblusrmail);
        String id     = userObj.getid();
        String loginUser = (String) session.getAttribute("userEmaiId");
        
		String taskquery = "UPDATE loc.\"Tbl_Users\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Created_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"', \"Status\" = 'D',\"Usr_Active_Status\" = 'X' WHERE \"ID\" ='"+id+"'";
		
		int updatecount = tblUsersService.executeNativeQuery(taskquery);
		
		model.addAttribute("enblUserlist", getenblUserlist(loginUser));
		model.addAttribute("dsblUserlist", getdisableblUserlist(loginUser));
		model.addAttribute("lockUserlist", getLockUserlist(loginUser));
		model.addAttribute("dltUserlist", getenblUserlist(loginUser));
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "UserID have been Disabled Successfully");
		return new ModelAndView("/uam/modifyUser");
	}
	
	@PostMapping(value = "/UnlockUser")
	public ModelAndView UnlockUserID(
	   @ModelAttribute("enbldsblusermodel") @Validated TblUsers tblusers, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks,
	   @RequestParam("unlockusrmail") String unlockusrmail
	)  
	{
		System.out.println(unlockusrmail);
		TblUsers userObj = tblUsersService.getLoginByuserName(unlockusrmail);
        String id     = userObj.getid();
        String loginUser = (String) session.getAttribute("userEmaiId");
        
		String taskquery = "UPDATE loc.\"Tbl_Users\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Created_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"',\"Logged_Status\" = '0', \"Locked_Status\" = '0',\"Current_Attempts\" ='0' WHERE \"ID\" ='"+id+"'";
		
		int updatecount = tblUsersService.executeNativeQuery(taskquery);
		
		model.addAttribute("enblUserlist", getenblUserlist(loginUser));
		model.addAttribute("dsblUserlist", getdisableblUserlist(loginUser));
		model.addAttribute("lockUserlist", getLockUserlist(loginUser));
		model.addAttribute("dltUserlist", getenblUserlist(loginUser));
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "UserID have been Released Successfully");
		return new ModelAndView("/uam/modifyUser");
	}
	
	@PostMapping(value = "/TerminateUser")
	public ModelAndView TerminateUserID(
	   @ModelAttribute("enbldsblusermodel") @Validated TblUsers tblusers, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks,
	   @RequestParam("dltusrmail") String dltusrmail
	)  
	{
		System.out.println(dltusrmail);
		TblUsers userObj = tblUsersService.getLoginByuserName(dltusrmail);
        String id     = userObj.getid();
        String loginUser = (String) session.getAttribute("userEmaiId");
        
		String taskquery = "UPDATE loc.\"Tbl_Users\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Created_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"', \"Status\" = 'T' WHERE \"ID\" ='"+id+"'";
		
		int updatecount = tblUsersService.executeNativeQuery(taskquery);
		
		model.addAttribute("enblUserlist", getenblUserlist(loginUser));
		model.addAttribute("dsblUserlist", getdisableblUserlist(loginUser));
		model.addAttribute("lockUserlist", getLockUserlist(loginUser));
		model.addAttribute("dltUserlist", getenblUserlist(loginUser));
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "UserID have been Terminated Successfully");
		return new ModelAndView("/uam/modifyUser");
	}
	
}


