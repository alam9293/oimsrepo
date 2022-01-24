/**
 * Author:: Mohd Alam
* Created on:: 
 */
package com.webapp.ims.uam.controller;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.webapp.ims.uam.model.TblDepartment;
import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblFormService;
import com.webapp.ims.uam.service.TblPendingWorksService;

/**
 * @author dell
 */
@Controller
public class AddGroupControlller 
{
	private final Logger logger = LoggerFactory.getLogger(AddGroupControlller.class);
	
	
	@Autowired
	TblDepartmentService tblDepartmentService;
	
	@Autowired
	TblPendingWorksService tblPendingWorksService;
	
	@Autowired
	private TblFormService tblFormService;
	
	@ModelAttribute("DeaprtmentList")
	public List<TblDepartment> getDepartmentList() 
	{
	      List<TblDepartment> DeaprtmentList = tblDepartmentService.getEnableDepartmentName();
	      return DeaprtmentList;
	}
	
	public LinkedHashMap<String,String> getFormActionMap(HttpSession session) 
	{
		
		LinkedHashMap<String,String>Tblformactiondsplname = new LinkedHashMap<String,String>();
		Tblformactiondsplname = tblFormService.getActionandDsplayLbl((String) session.getAttribute("allowedform"));
		return Tblformactiondsplname;
	}
	
	public LinkedHashMap<String,LinkedHashMap<String,String>> getformNameDeptCodeMap() 
	{
		
		LinkedHashMap<String,LinkedHashMap<String,String>>formNamedeptIDandIdMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		formNamedeptIDandIdMap = tblFormService.getformNameDeptCodeMap();
		return formNamedeptIDandIdMap;
	}
	
	@PostMapping(value = "/AddNewGroup")
	public ModelAndView AddNewRole(
	   @ModelAttribute("userGroupmodel") @Validated TblDepartment tblDepartment, BindingResult result,
	   Model model, HttpSession session,
	   @RequestParam("group") String group,	   
	   @RequestParam("groupDesc") String groupDesc,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		System.out.println("Group :"+group);
		System.out.println("groupDesc :"+groupDesc);
		
		String verifydisplaystring ="";
		if(!group.equalsIgnoreCase(""))
		{
			verifydisplaystring += "User Group-"+group.toUpperCase();
		}
		if(!groupDesc.equalsIgnoreCase(""))
		{
			verifydisplaystring +="|User Group Description-"+groupDesc.toUpperCase();
		}
		
		//
		String descString  =  "Group Add code : "+group.toUpperCase();
		verifydisplaystring += "#"+descString;
		
		String taskquery = "INSERT INTO loc.\"Tbl_Group\"(\"Group\", \"Group_Desc\", \"Created_By\", \"Creator_Date\", \"Creator_Ip_Address\",\"Status\")	VALUES ('"+group+"', '"+groupDesc+"', '"+session.getAttribute("userEmaiId")+"', '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"','"+(String)session.getAttribute("ipaddress")+"','E')";
		//String taskquery = "INSERT INTO loc.\"Tbl_Role\"(\"Group\", \"Role_Description\", \"Creator\",\"Creator_Date\", \"Allowed_Forms\", \"Creator_Ip_Address\", \"Active\") VALUES ('"+role+"', '"+roleDesc+"', '"+session.getAttribute("userEmaiId")+"', '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"', '"+AllowedformIds+"', '"+(String)session.getAttribute("ipaddress")+"','E')";
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage("Group With Group Name "+group+" added by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("ADD NEW GROUP");//task_subject
		tblApprovalWorks.setActivity("ADD GROUP");//activity
		tblApprovalWorks.setModuleName("ADD GROUP FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"Group\" = '"+group+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Group have been Added and Queued for Approval");
		return new ModelAndView("/uam/Add_Group");
	}
	
}


