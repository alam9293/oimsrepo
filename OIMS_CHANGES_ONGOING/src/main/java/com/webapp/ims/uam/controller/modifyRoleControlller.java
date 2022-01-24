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

import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.model.TblRole;
import com.webapp.ims.uam.model.TblUsers;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblPendingWorksService;
import com.webapp.ims.uam.service.TblRoleService;
import com.webapp.ims.uam.service.TblUsersService;

/**
 * @author dell
 */
@Controller
public class modifyRoleControlller 
{
	private final Logger logger = LoggerFactory.getLogger(modifyRoleControlller.class);
	
	@Autowired
	private TblRoleService TblroleService;
	
	@Autowired
	TblDepartmentService tblDepartmentService;
	
	@Autowired
	TblPendingWorksService tblPendingWorksService;
	
	
	public List<TblRole> getEnableRoleslist() 
	{
		return TblroleService.getEnableRoles();
	}
	
	public List<TblRole> getDisableRoleslist() 
	{
		return TblroleService.getDisableRoles();
	}
	
	
	
	@PostMapping(value = "/enblRole")
	public ModelAndView EnableRole(
	   @ModelAttribute("enbldsblrolemodel") @Validated TblRole tblrole, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		System.out.println(tblrole.getRoleId().split("seprator")[0].replace(",", ""));
        String id     = tblrole.getRoleId().split("seprator")[0].replace(",", "");
        
		String taskquery = "UPDATE loc.\"Tbl_Role\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Creator_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"', \"Status\" = 'E' WHERE \"Role_Id\" ='"+id+"'";
		
		int updatecount = TblroleService.executeNativeQuery(taskquery);
		
		model.addAttribute("enblRolelist", getEnableRoleslist());
		model.addAttribute("dsblRolelist", getDisableRoleslist());
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Role have been Enabled Successfully");
		return new ModelAndView("/uam/modifyRole");
	}
	
	@PostMapping(value = "/dsblRole")
	public ModelAndView DisableRole(
	   @ModelAttribute("enbldsblrolemodel") @Validated TblRole tblrole, BindingResult result,
	   Model model, HttpSession session,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		System.out.println(tblrole.getRoleId().split("seprator")[0]);
        String id     = tblrole.getRoleId().split("seprator")[0];
        
		String taskquery = "UPDATE loc.\"Tbl_Role\" SET \"Created_By\" = '"+session.getAttribute("userEmaiId")+"' ,\"Creator_Date\" = '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"' , \"Creator_Ip_Address\" = '"+(String)session.getAttribute("ipaddress")+"', \"Status\" = 'D' WHERE \"Role_Id\" ='"+id+"'";
		
		int updatecount = TblroleService.executeNativeQuery(taskquery);
		
		model.addAttribute("enblRolelist", getEnableRoleslist());
		model.addAttribute("dsblRolelist", getDisableRoleslist());
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Role have been Disabled Successfully");
		return new ModelAndView("/uam/modifyRole");
	}
	
}


