/**
 * Author:: Mohd Alam
* Created on:: 
 */
package com.webapp.ims.uam.controller;

import java.util.ArrayList;
import java.util.Date;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
public class AddUserControlller 
{
	private final Logger logger = LoggerFactory.getLogger(AddRoleControlller.class);
	
	@Autowired
	TblDepartmentService tblDepartmentService;
	
	@Autowired
	TblPendingWorksService tblPendingWorksService;
	
	@Autowired
	private TblFormService tblFormService;
	
	@Autowired
	private TblUsersService tblUsersService;
	
	@ModelAttribute("DeaprtmentList")
	public List<TblDepartment> getDepartmentList() 
	{
	      List<TblDepartment> DeaprtmentList = tblDepartmentService.getEnableDepartmentName();
	      return DeaprtmentList;
	}
	
	@PostMapping(value = "/AddNewUsr")
	public ModelAndView AddNewUsr(
	   @ModelAttribute("userDtlmodel") @Validated TblDepartment tblDepartment, BindingResult result,
	   Model model, HttpSession session,
	   @RequestParam("usremailid") String usremailid,	   
	   @RequestParam("usrloginpassword") String usrloginpassword,
	   @RequestParam("usrFirstName") String usrFirstName,
	   @RequestParam("usrMidleName") String usrMidleName,
	   @RequestParam("usrLastName") String usrLastName,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		
		String verifydisplaystring ="";
		verifydisplaystring +="Email Id-"+usremailid;
		verifydisplaystring +="|Password-"+usrloginpassword;
		verifydisplaystring +="|First Name-"+usrFirstName;
		verifydisplaystring +="|Middle Name-"+usrMidleName;
		verifydisplaystring +="|Last Name-"+usrLastName;
		verifydisplaystring +="|Department Name-"+tblDepartment.getDeptName();
		
		String taskquery = "INSERT INTO loc.\"Tbl_Users\"(\"Department_Name\", \"User_First_Name\", \"User_Last_Name\", \"User_Middle_Name\", \"Password\", \"User_Name\",\"Status\")	VALUES ('"+tblDepartment.getDeptName()+"','"+usrFirstName+"','"+usrLastName+"', '"+usrMidleName+"', '"+usrloginpassword+"', '"+usremailid+"','A')";
		
		String descString  =  usremailid+"|"+usrloginpassword+"|"+usrFirstName+"|"+""+"|"+""+"|"+usrMidleName+"|"+usrLastName+"|"+tblDepartment.getDeptName()+"|"+"Add User"+"|"+"A";
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage("User With EmailId "+usremailid+" added by "+session.getAttribute("userName"));//task_message
		tblApprovalWorks.setTaskSubject("ADD NEW USER");//task_subject
		tblApprovalWorks.setActivity("ADD USER");//activity
		tblApprovalWorks.setModuleName("ADD USER FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole("Admin Role");//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"User_Name\" = '"+usremailid+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
	    System.out.println(model.containsAttribute("Tblformactiondsplname"));
	    System.out.println("getDeaprtmentList() :"+getDepartmentList());
	    List<String>DepartmentList =new ArrayList<String>();
	    DepartmentList.add("Select Department");
	    for(TblDepartment  l:getDepartmentList())
	    { 
	    	DepartmentList.add(l.getDeptName()); 
	    }
	    model.addAttribute("DepartmentList", DepartmentList);
	    model.addAttribute("Done", "done");
		model.addAttribute("Message", "User Details have been Added Successfully and Queued for Approval");
	    return new ModelAndView("/uam/Add_User");
	}
	
	
	@RequestMapping(value = "/checkEmailIdduplicacy", method = RequestMethod.POST, produces = "text/plain")
	public @ResponseBody String check(@RequestBody String emailId) {
		
		System.out.println("Check Action Called..........."+emailId);
		TblUsers obj = tblUsersService.getLoginByuserName(emailId);
		if(obj !=null)
		{	
			if(obj.getUserName().equalsIgnoreCase(emailId))
			{
				return "User Already Exist";
			}
			else
			{
				return "Success";
			}	
		}
		return "Success";
	}
	
}
