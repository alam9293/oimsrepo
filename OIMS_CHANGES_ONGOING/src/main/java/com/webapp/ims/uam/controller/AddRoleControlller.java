/**
 * Author:: Mohd Alam
* Created on:: 
 */
package com.webapp.ims.uam.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

import com.webapp.ims.uam.model.TblApprovalWorks;
import com.webapp.ims.uam.model.TblDepartment;
import com.webapp.ims.uam.service.TblDepartmentService;
import com.webapp.ims.uam.service.TblFormService;
import com.webapp.ims.uam.service.TblPendingWorksService;

/**
 * @author dell
 */
@Controller
public class AddRoleControlller 
{
	private final Logger logger = LoggerFactory.getLogger(AddRoleControlller.class);
	
	
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
	
	public LinkedHashMap<String,LinkedHashMap<String,String>> getformNameDeptCodeMap() 
	{
		
		LinkedHashMap<String,LinkedHashMap<String,String>>formNamedeptIDandIdMap = new LinkedHashMap<String,LinkedHashMap<String,String>>();
		formNamedeptIDandIdMap = tblFormService.getformNameDeptCodeMap();
		return formNamedeptIDandIdMap;
	}
	
	@PostMapping(value = "/AddNewRole")
	public ModelAndView AddNewRole(
	   @ModelAttribute("userRolemodel") @Validated TblDepartment tblDepartment, BindingResult result,
	   Model model, HttpSession session,
	   @RequestParam("role") String role,	   
	   @RequestParam("roleDesc") String roleDesc,
	   @RequestParam("selectFormsIds") String selectFormsIds,
	   @ModelAttribute("SpringWeb")TblApprovalWorks tblApprovalWorks
	)  
	{
		System.out.println("Role :"+role);
		System.out.println("roleDesc :"+roleDesc);
		System.out.println("mainchkbox :"+selectFormsIds.split(","));
		System.out.println("DepartmentId :"+tblDepartment.getDeptId());
		
		LinkedHashMap<String,String>DepartmentMap =new LinkedHashMap<String,String>();
		List<String>DeptCodeList =new ArrayList<String>();
		LinkedHashMap<String,LinkedHashMap<String,String>>formNameDeptCodeMap = getformNameDeptCodeMap();
		DepartmentMap.put("NA","Select Department");
		for(TblDepartment l:getDepartmentList())
		{
			DepartmentMap.put(l.getDeptId(), l.getDeptName());
		}
		for(TblDepartment l:getDepartmentList())
		{
			DeptCodeList.add(l.getDeptId());
		}
		
		String AllowedformIds ="",displayforms ="";
		for(String str:selectFormsIds.split(","))
		{
			AllowedformIds+=str.substring(str.indexOf("id")+2)+",";
		}
		AllowedformIds =AllowedformIds.substring(0, AllowedformIds.length()-1);
		System.out.println("AllowedformIds :"+AllowedformIds);
		
		Iterator <String> itrouter = formNameDeptCodeMap.keySet().iterator(); 
		while(itrouter.hasNext())
		{
			LinkedHashMap<String,String>lhmp = formNameDeptCodeMap.get(itrouter.next());
			for(String str:AllowedformIds.split(","))
			{
				if(lhmp.containsKey(str))
				displayforms+=lhmp.get(str)+",";
			}
		}
		displayforms =displayforms.substring(0, displayforms.length()-1);
		String verifydisplaystring ="";
		if(!role.equalsIgnoreCase(""))
		{
			verifydisplaystring += "User Role-"+role.toUpperCase();
		}
		if(!roleDesc.equalsIgnoreCase(""))
		{
			verifydisplaystring +="|User Role Description-"+roleDesc.toUpperCase();
		}
		if(!displayforms.equalsIgnoreCase(""))
		{
			verifydisplaystring +="|Allowed Forms-"+displayforms;
		}
		//
		String descString  =  "Role Add code : "+role.toUpperCase();
		verifydisplaystring += "#"+descString;
		
		String taskquery = "INSERT INTO loc.\"Tbl_Role\"(\"Role\", \"Role_Description\", \"Created_By\",\"Creator_Date\", \"Allowed_Forms\", \"Creator_Ip_Address\", \"Status\") VALUES ('"+role+"', '"+roleDesc+"', '"+session.getAttribute("userEmaiId")+"', '"+new SimpleDateFormat("yyyy-MM-dd").format(new Date())+"', '"+AllowedformIds+"', '"+(String)session.getAttribute("ipaddress")+"','E')";
		
		verifydisplaystring += "#"+descString;
		tblApprovalWorks.setTaskQuery(taskquery);
		tblApprovalWorks.setTaskMessage("Role With Role Name "+role+" added by "+session.getAttribute("userEmaiId"));//task_message
		tblApprovalWorks.setTaskSubject("ADD NEW ROLE");//task_subject
		tblApprovalWorks.setActivity("ADD ROLE");//activity
		tblApprovalWorks.setModuleName("ADD ROLE FORM");//module_name
		tblApprovalWorks.setTaskPurpose("A");//task_purpose
		tblApprovalWorks.setTaskStatus(9);//task_status
		tblApprovalWorks.setTaskCreatorRole((String)session.getAttribute("userr"));//task_creator_role
		tblApprovalWorks.setCreator((String)session.getAttribute("userEmaiId"));//creator
		tblApprovalWorks.setTaskCreateDate(new Date());
		tblApprovalWorks.setCreatorDate(new Date());
		tblApprovalWorks.setApproverDisplayString(verifydisplaystring);//approver_display_string
		tblApprovalWorks.setIdmsStatus("M");//idms_status
		tblApprovalWorks.setWhereClause("where \"Role\" = '"+role+"'");//whereclause
		tblPendingWorksService.saveDatatobeApprove(tblApprovalWorks);
		
		
		System.out.println("DepartmentList :"+getDepartmentList());
		System.out.println("DeptCodeList :"+DeptCodeList);
		System.out.println("formNameDeptCodeMap :"+formNameDeptCodeMap);
		
		model.addAttribute("DepartmentMap", DepartmentMap);
		model.addAttribute("DeptCodeList", DeptCodeList);
		model.addAttribute("formNameDeptCodeMap", formNameDeptCodeMap);
		model.addAttribute("Done", "done");
		model.addAttribute("Message", "Role have been Added and Queued for Approval");
		return new ModelAndView("/uam/Add_Role");
	}
	
}


